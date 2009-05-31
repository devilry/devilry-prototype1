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
	long deliveryId;
//	
//	@Before
//	public void setUp() throws NamingException {
//		setupEjbContainer();
//			
//		delivery = getRemoteBean(DeliveryImpl.class);
//		delivery.create(assignmentId);
//	}
//
//	@Test
//	public void getAssignmentId() {
//		assertEquals(assignmentId, delivery.getAssignment(deliveryId));
//	}
//
//	@Test
//	public void getDeliveryCandidateIds() {
//		delivery.create(assignmentId);
//		delivery.create(assignmentId);
//		delivery.create(assignmentId);
//		assertEquals(3, delivery.getDeliveryCandidates(deliveryId).size());
//	}
//
//	@Test
//	public void addDeliveryCandidate() throws NamingException {
//		long id = delivery.create(assignmentId);
//
//		List<Long> c = delivery.getDeliveryCandidates(deliveryId);
//		assertEquals(1, c.size());
//		assertEquals(id, (long)c.get(0));
//		DeliveryCandidateRemote d = getRemoteBean(DeliveryCandidateImpl.class);
//		
//	}
//	
//	@Test
//	public void exists() {
//		assertTrue(delivery.exists(deliveryId));
//	}
//
//	@After
//	public void tearDown() {
//	}
}
