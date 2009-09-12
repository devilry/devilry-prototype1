package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public abstract class AdminDeliveryCommonTest extends UserDeliveryCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	AdminPeriod period;
	AdminPeriod period2;
	
	AdminAssignment assignment;
	AdminDelivery delivery;
		
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		
		period = new AdminPeriod(inf1000Spring09, connection);
		period2 = new AdminPeriod(inf1000Fall09, connection);
				
		assignment = new AdminAssignment(assignmentId, connection); 
		
		delivery = new AdminDelivery(deliveryId, connection);
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
	
	
	@Test
	public void getDeliveryCandidates() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
		
		// Add some deliveries
		
		assertEquals(0, delivery.getDeliveryCandidates().size());
				
		long candidateId = deliveryCandidateBean.create(deliveryId);
		
		assertEquals(1, delivery.getDeliveryCandidates().size());
		assertEquals(candidateId, delivery.getDeliveryCandidates().get(0).deliveryCandidateId);
		
		long candidate2Id = deliveryCandidateBean.create(deliveryId);
		
		List<AdminDeliveryCandidate> candidates = delivery.getDeliveryCandidates();
				
		assertEquals(2, candidates.size());
		
		for (AdminDeliveryCandidate c : candidates) {
			long val = c.deliveryCandidateId;
			assertTrue(val == candidateId || val == candidate2Id);
		}
	}
	
	
	@Test
	public void removeDeliveryCandidate() throws NoSuchObjectException, UnauthorizedException, NamingException {
		
		assertEquals(0, delivery.getDeliveryCandidates().size());
		
		long candidateId = deliveryCandidateBean.create(deliveryId);
		long candidate2Id = deliveryCandidateBean.create(deliveryId);
				
		deliveryCandidateBean.remove(candidateId);
		
		assertEquals(1, delivery.getDeliveryCandidates().size());
		
		deliveryCandidateBean.remove(candidate2Id);
		
		assertEquals(0, delivery.getDeliveryCandidates().size());
	}
	

	@Test
	public void addStudent() throws NamingException, UnauthorizedException, NoSuchObjectException {
		
		AdminDelivery adminDelivery = assignment.addDelivery();
		adminDelivery.addStudent(homerId);
		adminDelivery.addStudent(lisaId);
		
		List<Long> students = adminDelivery.getStudents();
		assertEquals(2, students.size());
	}
	
	@Test
	public void getStudents() throws NamingException, UnauthorizedException, NoSuchObjectException {
		AdminDelivery adminDelivery = assignment.addDelivery();
		adminDelivery.addStudent(homerId);
		adminDelivery.addStudent(lisaId);
		
		List<Long> students = adminDelivery.getStudents();
		assertEquals(2, students.size());
		
		for (long userId : students) {
			assertTrue(userId == homerId || userId == lisaId);
		}
	}
	
	@Test
	public void addExaminer() throws NamingException, UnauthorizedException, NoSuchObjectException {
		
		AdminDelivery adminDelivery = assignment.addDelivery();
		adminDelivery.addExaminer(homerId);
		adminDelivery.addExaminer(lisaId);
		
		List<Long> students = adminDelivery.getExaminers();
		assertEquals(2, students.size());
	}
	
	@Test
	public void getExaminers() throws NamingException, UnauthorizedException, NoSuchObjectException {
		AdminDelivery adminDelivery = assignment.addDelivery();
		adminDelivery.addExaminer(homerId);
		adminDelivery.addExaminer(lisaId);
		
		List<Long> examiners = adminDelivery.getExaminers();
		assertEquals(2, examiners.size());
		
		for (long userId : examiners) {
			assertTrue(userId == homerId || userId == lisaId);
		}
	}
	
	@Test
	public void getStatus() throws NamingException, UnauthorizedException {
		deliveryBean.setStatus(deliveryId, 2);
		assertEquals(2, delivery.getStatus());
	}
}
