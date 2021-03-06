package org.devilry.core.dao;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.*;
import org.devilry.core.entity.AssignmentNode;
import org.devilry.core.entity.PeriodNode;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Stateless
//@Interceptors( {AuthorizeAssignmentNode.class} )
public class AssignmentNodeImpl extends BaseNodeImpl implements
		AssignmentNodeRemote, AssignmentNodeLocal {

	@EJB(beanInterface=DeliveryLocal.class) 
	private DeliveryCommon deliveryBean;

	@EJB(beanInterface=PeriodNodeLocal.class) 
	private PeriodNodeCommon periodBean;

	// No authorization required
	private AssignmentNode getAssignmentNode(long nodeId) throws UnauthorizedException {
		return getNode(AssignmentNode.class, nodeId);
	}

	@SuppressWarnings("unchecked")
	public List<Long> getDeliveries(long nodeId) throws UnauthorizedException {
		Query q = em
				.createQuery("SELECT d.id FROM Delivery d WHERE d.assignment.id = :id");
		q.setParameter("id", nodeId);
		return q.getResultList();
	}

	// No authorization required
	public Date getDeadline(long nodeId) throws UnauthorizedException {
		return getAssignmentNode(nodeId).getDeadline();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setDeadline(long nodeId, Date deadline) throws UnauthorizedException {
		AssignmentNode a = getAssignmentNode(nodeId);
		a.setDeadline(deadline);
		em.persist(a);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, Date deadline,
			long parentId) throws UnauthorizedException {
		AssignmentNode node = new AssignmentNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setDeadline(deadline);
		node.setPeriod(getNode(PeriodNode.class, parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	// No authorization required
	public boolean exists(long nodeId) throws UnauthorizedException {
		try {
			return getAssignmentNode(nodeId) != null;
		} catch (ClassCastException e) {
			return false;
		}
	}

	// No authorization required
	public List<Long> getAssignmentsWhereIsAdmin() throws UnauthorizedException {
		return getNodesWhereIsAdmin(AssignmentNode.class);
	}

	public void addAssignmentAdmin(long assignmentNodeId, long userId) throws UnauthorizedException {
		AssignmentNode node = getAssignmentNode(assignmentNodeId);
		addAdmin(node, userId);
	}

	public void removeAssignmentAdmin(long assignmentNodeId, long userId) throws UnauthorizedException {
		AssignmentNode node = getAssignmentNode(assignmentNodeId);
		removeAdmin(node, userId);
	}

	// No authorization required
	public long getParentPeriod(long assignmentNodeId) throws UnauthorizedException {
		return getAssignmentNode(assignmentNodeId).getPeriod().getId();
	}

	public void remove(long assignmentId) throws UnauthorizedException {

		// Remove childnodes (deliveries)
		List<Long> childDeliveries = getDeliveries(assignmentId);
		for (Long childDeliveryId : childDeliveries) {
			deliveryBean.remove(childDeliveryId);
		}

		// Remove *this* node
		removeNode(assignmentId, AssignmentNode.class);
	}
	

	public List<Long> getAssignmentAdmins(long assignmentId) throws UnauthorizedException {
		AssignmentNode node = getAssignmentNode(assignmentId);
		return getAdmins(node);
	}

	public boolean isAssignmentAdmin(long assignmentId) throws UnauthorizedException {
		AssignmentNode courseNode = getAssignmentNode(assignmentId);
		return isAdmin(courseNode, userBean.getAuthenticatedUser());
	}

	// No authorization required
	public NodePath getPath(long assignmentNodeId) throws NoSuchObjectException, InvalidNameException, UnauthorizedException {
		
		AssignmentNode assignment = getAssignmentNode(assignmentNodeId);
		String assignmentName = assignment.getName();
		
		// Get path from parent period
		NodePath path = periodBean.getPath(assignment.getPeriod().getId());
				
		// Add current node name to path
		path.addToEnd(assignmentName);
				
		return path;
	}
	
	// No authorization required
	public long getIdFromPath(String [] nodePath, long parentNodeId) throws UnauthorizedException {
		
		long courseid = getAssignmentNodeId(nodePath[0], parentNodeId);
		
		if (nodePath.length == 1) {
			return courseid;
		}
		else {
			// path is too deep
			return -1;
		}
	}
	
	// No authorization required
	public long getIdFromPath(NodePath nodePath) throws NoSuchObjectException, UnauthorizedException {
		
		NodePath pathCopy = new NodePath(nodePath);
		String assignmentName = pathCopy.removeLastPathElement();
		
		long parentNodeId = periodBean.getIdFromPath(pathCopy);
		long assignmentId = getAssignmentNodeId(assignmentName, parentNodeId);
		
		return assignmentId;
	}
	
	// No authorization required
	/**
	 * Get the id of the period node name with parent parentId
	 * @param name
	 * @param parentId
	 * @return
	 */
	protected long getAssignmentNodeId(String name, long parentId) throws UnauthorizedException {
		Query q;

		if (parentId == -1) {
			return -1;
		} 
		
		q = em.createQuery("SELECT n FROM AssignmentNode n " +
				"WHERE n.name=:name AND n.course IS NOT NULL " +
				"AND n.period.id=:parentId");
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
