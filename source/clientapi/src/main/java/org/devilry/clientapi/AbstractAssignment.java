package org.devilry.clientapi;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;

public abstract class AbstractAssignment<E extends AbstractDelivery<?>> {

	protected DevilryConnection connection;
	
	AssignmentNodeCommon assignment;
	protected long assignmentId;
		
	DeliveryCommon delivery;
	
	protected AbstractAssignment(long assignmentId, DevilryConnection connection) {
		this.connection = connection;
		this.assignmentId = assignmentId;
	}
		
	protected AssignmentNodeCommon getAssignmentNodeBean() throws NamingException {
		return assignment == null ? assignment = connection.getAssignmentNode() : assignment;
	}
	
	protected DeliveryCommon getDeliveryBean() throws NamingException {
		return delivery == null ? delivery = connection.getDelivery() : delivery;
	}
	
	public Date getDeadline() throws NoSuchObjectException, NamingException, UnauthorizedException {
		return getAssignmentNodeBean().getDeadline(assignmentId);
	}
		
	
	abstract class DeliveryIterator implements Iterable<E>, Iterator<E> {

		Iterator<Long> deliveryIterator;
		
		DeliveryIterator(List<Long> delivryIds) {
			deliveryIterator = delivryIds.iterator();
		}
		
		public Iterator<E> iterator() {
			return this;
		}

		public boolean hasNext() {
			return deliveryIterator.hasNext();
		}

		abstract public E next();

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	abstract Iterator<E> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException;
	
	public List<E> getDeliveries() throws NamingException, NoSuchObjectException, UnauthorizedException {
		
		LinkedList<E> deliveries = new LinkedList<E>();
		
		Iterator<E> iter = deliveries();
				
		while (iter.hasNext()) {
			deliveries.add(iter.next());
		}
		return deliveries;
	}
	
	public NodePath getPath() throws NamingException, NoSuchObjectException, InvalidNameException, UnauthorizedException {
		return getAssignmentNodeBean().getPath(assignmentId);
	}
	
	public String getName() throws NoSuchObjectException, NamingException, UnauthorizedException {
		return getAssignmentNodeBean().getName(assignmentId);
	}
	
	public String getDisplayName() throws NoSuchObjectException, NamingException, UnauthorizedException {
		return getAssignmentNodeBean().getName(assignmentId);
	}
}
