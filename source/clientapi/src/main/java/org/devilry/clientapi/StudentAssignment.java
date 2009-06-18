package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;


public class StudentAssignment extends AbstractAssignment {
	
	StudentAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
	
	class StudentDeliveryIterator implements Iterable<StudentDelivery>, Iterator<StudentDelivery> {

		Iterator<Long> deliveryIterator;
		
		StudentDeliveryIterator(List<Long> delivryIds) {
			deliveryIterator = delivryIds.iterator();
		}
		
		public Iterator<StudentDelivery> iterator() {
			return this;
		}

		public boolean hasNext() {
			return deliveryIterator.hasNext();
		}

		public StudentDelivery next() {
			return new StudentDelivery(deliveryIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	Iterator<StudentDelivery> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		return new StudentDeliveryIterator(ids).iterator();
	}
	
	public List<StudentDelivery> getDeliveries() throws NamingException, NoSuchObjectException, UnauthorizedException {
		
		LinkedList<StudentDelivery> deliveries = new LinkedList<StudentDelivery>();
		
		Iterator<StudentDelivery> iter = deliveries();
				
		while (iter.hasNext()) {
			deliveries.add(iter.next());
		}
		return deliveries;
	}
	
	public NodePath getPath() throws NamingException, NoSuchObjectException {
		return getAssignmentNodeBean().getPath(assignmentId);
	}
}
