package org.devilry.core.session.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.DeliveryCandidateImpl;
import org.devilry.core.dao.DeliveryImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.naming.NamingException;


public abstract class DeliveryCommonTest {
	
	protected static CoreTestHelper testHelper;
	
	NodeCommon node;
	UserCommon userBean;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	AssignmentNodeCommon assignmentNode;
	
	DeliveryCommon delivery;
	DeliveryCandidateCommon deliveryCandidate;
		
	long superId;
	
	long uioId;
	long matnatId;
	long courseId;

	long periodId;
	long periodId2;
		
	long assignmentId, assignmentId2, assignmentId3;
	Calendar deadline;
		
	long deliveryId, deliveryId2;

	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		node = testHelper.getNodeCommon();
		userBean = testHelper.getUserCommon();
		courseNode = testHelper.getCourseNodeCommon();
		periodNode = testHelper.getPeriodNodeCommon();
		assignmentNode = testHelper.getAssignmentNodeCommon();
		delivery = testHelper.getDeliveryCommon();
		deliveryCandidate = testHelper.getDeliveryCandidateCommon();
		
		superId = userBean.create("Homer Simpson", "homr@stuff.org", "123");
		userBean.setIsSuperAdmin(superId, true);
		userBean.addIdentity(superId, "homer");
		
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		courseId = courseNode.create("inf1000", "Object oriented programming", matnatId);
				
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		periodId = periodNode.create("fall09", "Fall 2009", start.getTime(),
				end.getTime(), courseId);
		periodId2 = periodNode.create("spring09", "Spring 2009", start
				.getTime(), end.getTime(), courseId);
		
		deadline = new GregorianCalendar(2009, 07, 20);
		
		// Add assignment
		assignmentId = assignmentNode.create("oblig1", "Obligatory assignemnt 1", deadline.getTime(), periodId);
		
		deliveryId = delivery.create(assignmentId);
		deliveryId2 = delivery.create(assignmentId);
		
		deadline = new GregorianCalendar(2009, 05, 17);
		
		deadline = new GregorianCalendar(2009, 06, 17);
		assignmentId2 = assignmentNode.create("oblig2", "Obligatory assignment 2", deadline.getTime(), periodId);
		
		deadline = new GregorianCalendar(2009, 07, 17);
		assignmentId3 = assignmentNode.create("oblig1", "Obligatory assignment 1", deadline.getTime(), periodId2);
	}

	@After
	public void tearDown() throws Exception {
		testHelper.clearUsersAndNodes();
	}
	

	@Test
	public void remove() throws NoSuchObjectException {
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
		assertFalse(delivery.isStudent(deliveryId, superId));
		delivery.addStudent(deliveryId, superId);
		assertTrue(delivery.isStudent(deliveryId, superId));
	}

	@Test
	public void addStudent() {
		delivery.addStudent(deliveryId, superId);
		assertTrue(delivery.isStudent(deliveryId, superId));

		assertEquals(1, delivery.getStudents(deliveryId).size());
		delivery.addStudent(deliveryId, superId);
		
		// Check that duplicate value isn't added
		assertEquals(1, delivery.getStudents(deliveryId).size());
	}

	@Test
	public void removeStudent() {
		delivery.addStudent(deliveryId, superId);
		delivery.removeStudent(deliveryId, superId);
		assertFalse(delivery.isStudent(deliveryId, superId));
		assertTrue(userBean.userExists(superId)); // make sure the user is not removed from the system as well!
	}
	
	@Test
	public void getDeliveriesWhereIsStudent() {
		delivery.addStudent(deliveryId, superId);
		List<Long> l = delivery.getDeliveriesWhereIsStudent();
		assertEquals(1, l.size());
		assertEquals(deliveryId, (long) l.get(0));

		// Make sure adding another student does not affect the authenticated student.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		delivery.addStudent(deliveryId, margeId);
		assertEquals(1, delivery.getDeliveriesWhereIsStudent().size());

		// It works with more than one delivery?
		delivery.addStudent(deliveryId2, superId);
		assertEquals(2, delivery.getDeliveriesWhereIsStudent().size());
	}


	@Test
	public void isExaminer() {
		assertFalse(delivery.isExaminer(deliveryId, superId));
		delivery.addExaminer(deliveryId, superId);
		assertTrue(delivery.isExaminer(deliveryId, superId));
	}

	@Test
	public void addExaminer() {
		delivery.addExaminer(deliveryId, superId);
		assertTrue(delivery.isExaminer(deliveryId, superId));

		assertEquals(1, delivery.getExaminers(deliveryId).size());
		delivery.addExaminer(deliveryId, superId);
		
		// Check that duplicate value isn't added
		assertEquals(1, delivery.getExaminers(deliveryId).size());
	}

	@Test
	public void removeExaminer() {
		delivery.addExaminer(deliveryId, superId);
		delivery.removeExaminer(deliveryId, superId);
		assertFalse(delivery.isExaminer(deliveryId, superId));
		assertTrue(userBean.userExists(superId)); // make sure the user is not removed from the system as well!
	}
	
	@Test
	public void getDeliveriesWhereIsExaminer() {
		delivery.addExaminer(deliveryId, superId);
		List<Long> l = delivery.getDeliveriesWhereIsExaminer();
		assertEquals(1, l.size());
		assertEquals(deliveryId, (long) l.get(0));

		// Make sure adding another Examiner does not affect the authenticated Examiner.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		delivery.addExaminer(deliveryId, margeId);
		assertEquals(1, delivery.getDeliveriesWhereIsExaminer().size());

		// It works with more than one delivery?
		delivery.addExaminer(deliveryId2, superId);
		assertEquals(2, delivery.getDeliveriesWhereIsExaminer().size());
	}
}