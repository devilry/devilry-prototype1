package org.devilry.core.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.entity.AssignmentNode;
import org.devilry.core.entity.PeriodNode;

@Stateless
public class AssignmentNodeImpl extends BaseNodeImpl implements
		AssignmentNodeRemote, AssignmentNodeLocal {

	@EJB(beanInterface=DeliveryLocal.class) 
	private DeliveryCommon deliveryBean;

	@EJB(beanInterface=AssignmentNodeLocal.class) 
	private AssignmentNodeCommon assignmentBean;
	
	@EJB(beanInterface=PeriodNodeLocal.class) 
	private PeriodNodeCommon periodBean;
	
	
	private AssignmentNode getAssignmentNode(long nodeId) {
		return getNode(AssignmentNode.class, nodeId);
	}

	@SuppressWarnings("unchecked")
	public List<Long> getDeliveries(long nodeId) {
		Query q = em
				.createQuery("SELECT d.id FROM Delivery d WHERE d.assignment.id = :id");
		q.setParameter("id", nodeId);
		return q.getResultList();
	}

//	@SuppressWarnings("unchecked")
//	public List<Long> getDeliveriesWhereIsStudent(long assignmentId) {
//		long userId = userBean.getAuthenticatedUser();
//
//		Query q = em
//				.createQuery("SELECT d.id FROM Delivery d INNER JOIN d.students user WHERE user.id = :userId AND d.assignment.id =:assignmentId");
//		q.setParameter("assignmentId", assignmentId);
//		q.setParameter("userId", userId);
//
//		return q.getResultList();
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Long> getDeliveriesWhereIsExaminer(long assignmentId) {
//		long userId = userBean.getAuthenticatedUser();
//
//		Query q = em
//				.createQuery("SELECT d.id FROM Delivery d INNER JOIN d.examiners user WHERE user.id = :userId AND d.assignment.id =:assignmentId");
//		q.setParameter("assignmentId", assignmentId);
//		q.setParameter("userId", userId);
//
//		return q.getResultList();
//	}

	public List<Long> getChildren(long nodeId) {
		throw new UnsupportedOperationException(
				"AssignmentNode does not have any children. Did you mean getDeliveries?");
	}

	public Date getDeadline(long nodeId) {
		return getAssignmentNode(nodeId).getDeadline();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setDeadline(long nodeId, Date deadline) {
		AssignmentNode a = getAssignmentNode(nodeId);
		a.setDeadline(deadline);
		em.persist(a);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, Date deadline,
			long parentId) {
		AssignmentNode node = new AssignmentNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setDeadline(deadline);
		node.setPeriod(getNode(PeriodNode.class, parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	public boolean exists(long nodeId) {
		try {
			return getAssignmentNode(nodeId) != null;
		} catch (ClassCastException e) {
			return false;
		}
	}

	public List<Long> getAssignmentsWhereIsAdmin() {
		return getNodesWhereIsAdmin(AssignmentNode.class);
	}

	public void addAssignmentAdmin(long assignmentNodeId, long userId) {
		AssignmentNode node = getAssignmentNode(assignmentNodeId);
		addAdmin(node, userId);
	}

	public void removeAssignmentAdmin(long assignmentNodeId, long userId) {
		AssignmentNode node = getAssignmentNode(assignmentNodeId);
		removeAdmin(node, userId);
	}

	public long getParentPeriod(long assignmentNodeId) {
		return getAssignmentNode(assignmentNodeId).getPeriod().getId();
	}

	public void remove(long assignmentId) {

		// Remove childnodes (deliveries)
		List<Long> childDeliveries = getDeliveries(assignmentId);
		for (Long childDeliveryId : childDeliveries) {
			deliveryBean.remove(childDeliveryId);
		}

		// Remove *this* node
		removeNode(assignmentId, AssignmentNode.class);
	}
	

	public List<Long> getAssignmentAdmins(long assignmentId) {
		AssignmentNode node = getAssignmentNode(assignmentId);
		return getAdmins(node);
	}

	public boolean isAssignmentAdmin(long assignmentId, long userId) {
		AssignmentNode courseNode = getAssignmentNode(assignmentId);
		return isAdmin(courseNode, userId);
	}
	
	

	public NodePath getPath(long assignmentNodeId) throws NoSuchObjectException {
		
		AssignmentNode assignment = getAssignmentNode(assignmentNodeId);
		String assignmentName = assignment.getName();
		
		// Get path from parent period
		NodePath path = periodBean.getPath(assignment.getPeriod().getId());
				
		// Add current node name to path
		path.addToEnd(assignmentName);
				
		return path;
	}
	
	
	/**
	 * Get period node id, or id of subnode assignment
	 * @param nodePath
	 * @param parentNodeId
	 * @return
	 */
	public long getIdFromPath(String [] nodePath, long parentNodeId) {
		
		long courseid = getAssignmentNodeId(nodePath[0], parentNodeId);
		
		if (nodePath.length == 1) {
			return courseid;
		}
		else {
			// path is too deep
			return -1;
		}
	}
	
	
	public long getIdFromPath(NodePath nodePath) throws NoSuchObjectException {
		
		NodePath pathCopy = new NodePath(nodePath);
		String assignmentName = pathCopy.removeLastPathComponent();
		
		long parentNodeId = periodBean.getIdFromPath(pathCopy);
		long assignmentId = getAssignmentNodeId(assignmentName, parentNodeId);
		
		return assignmentId;
	}
	
	
	/**
	 * Get the id of the period node name with parent parentId
	 * @param name
	 * @param parentId
	 * @return
	 */
	protected long getAssignmentNodeId(String name, long parentId) {
		Query q;

		if (parentId == -1) {
			return -1;
		} 
		
		q = em.createQuery("SELECT n FROM AssignmentNode n WHERE n.name=:name AND n.course IS NOT NULL AND n.period.id=:parentId");
		q.setParameter("name", name);
		q.setParameter("parentId", parentId);

		AssignmentNode node;

		try {
			node = (AssignmentNode) q.getSingleResult();
		} catch (NoResultException e) {
			node = null;
		}

		return node == null ? -1 : node.getId();
	}

}
