package org.devilry.core.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.entity.AssignmentNode;
import org.devilry.core.entity.PeriodNode;

@Stateless
public class AssignmentNodeImpl extends BaseNodeImpl implements
		AssignmentNodeRemote, AssignmentNodeLocal {

	@EJB
	private DeliveryLocal deliveryBean;

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

	public List<Long> getDeliveriesWhereIsStudent(long assignmentId) {
		long userId = userBean.getAuthenticatedUser();

		Query q = em
				.createQuery("SELECT d.id FROM Delivery d INNER JOIN d.students user WHERE user.id = :userId AND d.assignment.id =:assignmentId");
		q.setParameter("assignmentId", assignmentId);
		q.setParameter("userId", userId);

		return q.getResultList();
	}

	public List<Long> getDeliveriesWhereIsExaminer(long assignmentId) {
		long userId = userBean.getAuthenticatedUser();

		Query q = em
				.createQuery("SELECT d.id FROM Delivery d INNER JOIN d.examiners user WHERE user.id = :userId AND d.assignment.id =:assignmentId");
		q.setParameter("assignmentId", assignmentId);
		q.setParameter("userId", userId);

		return q.getResultList();
	}

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

	public long getPeriod(long nodeId) {
		return getAssignmentNode(nodeId).getPeriod().getId();
	}

	public long getIdFromPath(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getPath(long nodeId) {
		// TODO Auto-generated method stub
		return null;
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
}
