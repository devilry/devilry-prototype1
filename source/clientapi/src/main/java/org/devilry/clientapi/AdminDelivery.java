package org.devilry.clientapi;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;

public class AdminDelivery extends AbstractDelivery<AdminDeliveryCandidate> {
	
	AdminDelivery(long deliveryId, DevilryConnection connection) {
		super(deliveryId, connection);
	}
		
	DeliveryCandidateCommon deliveryCandidate;
	
	protected DeliveryCandidateCommon getDeliveryCandidateBean() throws NamingException {
		return deliveryCandidate == null ? deliveryCandidate = connection.getDeliveryCandidate() : deliveryCandidate;
	}
	
	public AdminDeliveryCandidate createDeliveryCandidate() throws NamingException {
		
		DeliveryCandidateCommon deliveryCandidate = connection.getDeliveryCandidate();
		
		long candidateId = deliveryCandidate.create(deliveryId);
		AdminDeliveryCandidate candidate = new AdminDeliveryCandidate(candidateId, connection);
		
		return candidate;
	}
	
	
	class AdminDeliveryCandidateIterator extends DeliveryCandidateIterator {

		AdminDeliveryCandidateIterator(List<Long> ids) {
			super(ids);
		}
		
		public AdminDeliveryCandidate next() {
			return new AdminDeliveryCandidate(deliveryCandidateIterator.next(), connection); 
		}
	}
		
	Iterator<AdminDeliveryCandidate> deliveryCandidates() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> candidates = getDeliveryBean().getDeliveryCandidates(deliveryId);
		return new AdminDeliveryCandidateIterator(candidates).iterator();
	}
		
	public AdminDeliveryCandidate getDeliveryCandidate() {
		return null;
	}
	
	public void removeDeliveryCandidate(AdminDeliveryCandidate candidate) throws NamingException {
		getDeliveryCandidateBean().remove(candidate.deliveryCandidateId);
	}
	
	public void addStudent(long userId) throws NamingException, UnauthorizedException {
		getDeliveryBean().addStudent(deliveryId, userId);
	}
	
	public void addExaminer(long userId) throws NamingException, UnauthorizedException {
		getDeliveryBean().addExaminer(deliveryId, userId);
	}
	
	public List<Long> getStudents() throws NamingException, UnauthorizedException {
		List<Long> students = getDeliveryBean().getStudents(deliveryId);
		return students;
	}
	
	public List<Long> getExaminers() throws NamingException, UnauthorizedException {
		List<Long> students = getDeliveryBean().getExaminers(deliveryId);
		return students;
	}
}
