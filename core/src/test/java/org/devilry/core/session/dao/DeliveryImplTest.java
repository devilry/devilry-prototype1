package org.devilry.core.session.dao;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.naming.NamingException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DeliveryImplTest extends AbstractDeliveryDaoTst {
	
	DeliveryRemote delivery;
	DeliveryCandidateRemote deliveryCandidate;
	long deliveryId;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		delivery = getRemoteBean(DeliveryImpl.class);
		deliveryCandidate = getRemoteBean(DeliveryCandidateImpl.class);
		deliveryId = delivery.create(assignmentId);
	}

	@After
	public void tearDown() {
		periodNode.remove(uioId);
	}


	@Test
	public void getAssignment() {
		assertEquals(assignmentId, delivery.getAssignment(deliveryId));
	}
	
	@Test
	public void getStatus() {
		// TODO
	}

	@Test
	public void setStatus() {
		// TODO
	}
	

	@Test
	public void getDeliveryCandidates() {
		long id = deliveryCandidate.create(deliveryId);
		assertEquals(1, delivery.getDeliveryCandidates(deliveryId).size());
		assertEquals(id, (long) delivery.getDeliveryCandidates(deliveryId).get(0));

		deliveryCandidate.create(deliveryId);
		deliveryCandidate.create(deliveryId);
		assertEquals(3, delivery.getDeliveryCandidates(deliveryId).size());
	}

	@Test
	public void getLastDeliveryCandidate() {
		// TODO
	}
	
	@Test
	public void getLastValidDeliveryCandidate() {
		// TODO		
	}

	
	@Test
	public void getCorrectors() {
		// TODO		
	}
	
	@Test
	public void getStudents() {
		// TODO		
	}

	@Test
	public void exists() {
		assertTrue(delivery.exists(deliveryId));
	}
	

}
