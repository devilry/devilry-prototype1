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

public abstract class StudentDeliveryCommonTest extends UserDeliveryCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	StudentPeriod period;
	StudentPeriod period2;
	
	StudentAssignment assignment;
	StudentDelivery delivery;
		
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		
		period = new StudentPeriod(inf1000Spring09, connection);
		period2 = new StudentPeriod(inf1000Fall09, connection);
				
		assignment = new StudentAssignment(assignmentId, connection); 
		
		delivery = new StudentDelivery(deliveryId, connection);
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
	
	@Test
	public void createDeliveryCandidate() throws NamingException, NoSuchObjectException, UnauthorizedException {
	
		assertEquals(0, delivery.getDeliveryCandidates().size());
		
		StudentDeliveryCandidate candidate = delivery.createDeliveryCandidate();
		
		assertEquals(1, delivery.getDeliveryCandidates().size());
		
		List<StudentDeliveryCandidate> candidates = delivery.getDeliveryCandidates();
		assertEquals(candidate.deliveryCandidateId, candidates.get(0).deliveryCandidateId);
		
	}
	
	@Test
	public void getDeliveryCandidates() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
		
		// Add some deliveries
		
		assertEquals(0, delivery.getDeliveryCandidates().size());
				
		long candidateId = deliveryCandidateBean.create(deliveryId);
		
		assertEquals(1, delivery.getDeliveryCandidates().size());
		assertEquals(candidateId, delivery.getDeliveryCandidates().get(0).deliveryCandidateId);
		
		long candidate2Id = deliveryCandidateBean.create(deliveryId);
		
		List<StudentDeliveryCandidate> candidates = delivery.getDeliveryCandidates();
				
		assertEquals(2, candidates.size());
		
		for (StudentDeliveryCandidate c : candidates) {
			long val = c.deliveryCandidateId;
			assertTrue(val == candidateId || val == candidate2Id);
		}
	}
	
	@Test
	public void getStatus() throws NamingException {
		deliveryBean.setStatus(deliveryId, 2);
		assertEquals(2, delivery.getStatus());
	}
}
