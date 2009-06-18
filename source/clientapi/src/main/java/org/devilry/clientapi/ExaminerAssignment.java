package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;


public class ExaminerAssignment extends AbstractAssignment {
	
	ExaminerAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
		
	/*
	public Collection<ExaminerDelivery> getDeliveries() throws NamingException, NoSuchObjectException, UnauthorizedException {
		
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
				
		List<ExaminerDelivery> deliveries = new LinkedList<ExaminerDelivery>();
		ExaminerDelivery deliveryTmp;
		
		for (long id : ids) {
			deliveryTmp = new ExaminerDelivery(id, connection);
			deliveries.add(deliveryTmp);
		}
		
		return deliveries;
	}
	*/
	
	class ExaminerDeliveryIterator implements Iterable<ExaminerDelivery>, Iterator<ExaminerDelivery> {

		Iterator<Long> deliveryIterator;
		
		ExaminerDeliveryIterator(List<Long> delivryIds) {
			deliveryIterator = delivryIds.iterator();
		}
		
		public Iterator<ExaminerDelivery> iterator() {
			return this;
		}

		public boolean hasNext() {
			return deliveryIterator.hasNext();
		}

		public ExaminerDelivery next() {
			return new ExaminerDelivery(deliveryIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	Iterator<ExaminerDelivery> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		return new ExaminerDeliveryIterator(ids).iterator();
	}
	
	public List<ExaminerDelivery> getDeliveries() throws NamingException, NoSuchObjectException, UnauthorizedException {
		
		LinkedList<ExaminerDelivery> deliveries = new LinkedList<ExaminerDelivery>();
		
		Iterator<ExaminerDelivery> iter = deliveries();
				
		while (iter.hasNext()) {
			deliveries.add(iter.next());
		}
		return deliveries;
	}
	
	public NodePath getPath() throws NamingException, NoSuchObjectException {
		return getAssignmentNodeBean().getPath(assignmentId);
	}
	
}
