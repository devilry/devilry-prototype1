package org.devilry.core.session.dao;

import java.util.List;

import org.devilry.core.dao.DeliveryCandidateImpl;
import org.devilry.core.dao.DeliveryImpl;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.naming.NamingException;


public class DeliveryImplTest extends AbstractDeliveryDaoTst {
	
	DeliveryRemote delivery;
	DeliveryCandidateRemote deliveryCandidate;
	long deliveryId, deliveryId2;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		delivery = getRemoteBean(DeliveryImpl.class);
		deliveryCandidate = getRemoteBean(DeliveryCandidateImpl.class);
		deliveryId = delivery.create(assignmentId);
		deliveryId2 = delivery.create(assignmentId);
	}


	@Test
	public void remove() {
		delivery.remove(deliveryId);
		assertFalse(delivery.exists(deliveryId));

		long id1 = delivery.create(assignmentId);
		long id2 = delivery.create(assignmentId);
		node.remove(uioId);
		assertFalse(assignmentNode.exists(assignmentId));
		assertFalse(delivery.exists(id1));
		assertFalse(delivery.exists(id2));
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
	public void exists() {
		assertTrue(delivery.exists(deliveryId));
		assertFalse(delivery.exists(deliveryId + deliveryId2));
	}


	@Test
	public void isStudent() {
		assertFalse(delivery.isStudent(deliveryId, homerId));
		delivery.addStudent(deliveryId, homerId);
		assertTrue(delivery.isStudent(deliveryId, homerId));
	}

	@Test
	public void addStudent() {
		delivery.addStudent(deliveryId, homerId);
		assertTrue(delivery.isStudent(deliveryId, homerId));

		assertEquals(1, delivery.getStudents(deliveryId).size());
		delivery.addStudent(deliveryId, homerId);
		
		// Check that duplicate value isn't added
		assertEquals(1, delivery.getStudents(deliveryId).size());
	}

	@Test
	public void removeStudent() {
		delivery.addStudent(deliveryId, homerId);
		delivery.removeStudent(deliveryId, homerId);
		assertFalse(delivery.isStudent(deliveryId, homerId));
		assertTrue(userBean.userExists(homerId)); // make sure the user is not removed from the system as well!
	}
	
	@Test
	public void getDeliveriesWhereIsStudent() {
		delivery.addStudent(deliveryId, homerId);
		List<Long> l = delivery.getDeliveriesWhereIsStudent();
		assertEquals(1, l.size());
		assertEquals(deliveryId, (long) l.get(0));

		// Make sure adding another student does not affect the authenticated student.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		delivery.addStudent(deliveryId, margeId);
		assertEquals(1, delivery.getDeliveriesWhereIsStudent().size());

		// It works with more than one delivery?
		delivery.addStudent(deliveryId2, homerId);
		assertEquals(2, delivery.getDeliveriesWhereIsStudent().size());
	}


	@Test
	public void isExaminer() {
		assertFalse(delivery.isExaminer(deliveryId, homerId));
		delivery.addExaminer(deliveryId, homerId);
		assertTrue(delivery.isExaminer(deliveryId, homerId));
	}

	@Test
	public void addExaminer() {
		delivery.addExaminer(deliveryId, homerId);
		assertTrue(delivery.isExaminer(deliveryId, homerId));

		assertEquals(1, delivery.getExaminers(deliveryId).size());
		delivery.addExaminer(deliveryId, homerId);
		
		// Check that duplicate value isn't added
		assertEquals(1, delivery.getExaminers(deliveryId).size());
	}

	@Test
	public void removeExaminer() {
		delivery.addExaminer(deliveryId, homerId);
		delivery.removeExaminer(deliveryId, homerId);
		assertFalse(delivery.isExaminer(deliveryId, homerId));
		assertTrue(userBean.userExists(homerId)); // make sure the user is not removed from the system as well!
	}
	
	@Test
	public void getDeliveriesWhereIsExaminer() {
		delivery.addExaminer(deliveryId, homerId);
		List<Long> l = delivery.getDeliveriesWhereIsExaminer();
		assertEquals(1, l.size());
		assertEquals(deliveryId, (long) l.get(0));

		// Make sure adding another Examiner does not affect the authenticated Examiner.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		delivery.addExaminer(deliveryId, margeId);
		assertEquals(1, delivery.getDeliveriesWhereIsExaminer().size());

		// It works with more than one delivery?
		delivery.addExaminer(deliveryId2, homerId);
		assertEquals(2, delivery.getDeliveriesWhereIsExaminer().size());
	}
}