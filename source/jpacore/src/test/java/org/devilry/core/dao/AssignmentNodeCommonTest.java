package org.devilry.core.dao;

import org.devilry.core.*;
import org.devilry.core.daointerfaces.*;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class AssignmentNodeCommonTest {
	
	protected static CoreTestHelper testHelper;
	
	NodeCommon node;
	UserCommon userBean;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	AssignmentNodeCommon assignmentNode;
	
	long superId;
	
	long uioId;
	long matnatId;
	long courseId;

	long periodId;
	long periodId2;
		
	long assignmentId;
	Calendar deadline;
		
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		node = testHelper.getNodeCommon();
		userBean = testHelper.getUserCommon();
		courseNode = testHelper.getCourseNodeCommon();
		periodNode = testHelper.getPeriodNodeCommon();
		assignmentNode = testHelper.getAssignmentNodeCommon();
		
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
	}

	@After
	public void tearDown() throws Exception {
		testHelper.clearUsersAndNodes();
	}
	
	
	@Test
	public void getDeliveries() throws NamingException, NoSuchObjectException, UnauthorizedException {
		
		DeliveryCommon delivery = testHelper.getDeliveryCommon();
		long deliveryId = delivery.create(assignmentId);
		long deliveryId2 = delivery.create(assignmentId);
		long deliveryId3 = delivery.create(assignmentId);
		
		List<Long> ids = assignmentNode.getDeliveries(assignmentId);
		assertEquals(3, ids.size());
		assertTrue(ids.contains(deliveryId));
		assertTrue(ids.contains(deliveryId2));
		assertTrue(ids.contains(deliveryId3));
	}
	
	
	
	@Test
	public void getDeadline() throws NamingException, NoSuchObjectException {
		assertEquals(deadline.getTime(), assignmentNode.getDeadline(assignmentId));
	}
	
	@Test
	public void setDeadline() throws NamingException, UnauthorizedException, NoSuchObjectException {
		Calendar newDeadline = new GregorianCalendar(2009, 05, 16);
		
		assignmentNode.setDeadline(assignmentId, newDeadline.getTime());
		assertEquals(newDeadline.getTime(), assignmentNode.getDeadline(assignmentId));
	}
	
	@Test
	public void remove() throws NoSuchObjectException, UnauthorizedException {
		node.remove(uioId);
		assertFalse(node.exists(assignmentId));
	}

	@Test
	public void exists() throws NoSuchObjectException {
		assertTrue(assignmentNode.exists(assignmentId));
		assertFalse(assignmentNode.exists(uioId));
	}
	
	@Test
	public void getPath() throws NoSuchObjectException, InvalidNameException {		
		assertEquals(new NodePath("uio.matnat.inf1000.fall09.oblig1", "\\."), assignmentNode.getPath(assignmentId));
	}
	
	@Test(expected=Exception.class)
	public void createDuplicate() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		assignmentNode.create("unique", "Unique", new GregorianCalendar().getTime(), periodId);
		assignmentNode.create("unique", "Unique", new GregorianCalendar().getTime(), periodId);
	}


	@Test
	public void isAssignmentAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException {
		assertFalse(assignmentNode.isAssignmentAdmin(assignmentId));
		assignmentNode.addAssignmentAdmin(assignmentId, superId);
		assertTrue(assignmentNode.isAssignmentAdmin(assignmentId));
	}

	@Test
	public void addAssignmentAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException {
		assignmentNode.addAssignmentAdmin(assignmentId, superId);
		assertTrue(assignmentNode.isAssignmentAdmin(assignmentId));

		int adminCount = assignmentNode.getAssignmentAdmins(assignmentId).size();

		// Test duplicates
		assignmentNode.addAssignmentAdmin(assignmentId, superId);
		assertEquals(adminCount, assignmentNode.getAssignmentAdmins(assignmentId).size());
	}

	@Test
	public void removeAssignmentAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException {
		assignmentNode.removeAssignmentAdmin(assignmentId, superId);
		assertFalse(assignmentNode.isAssignmentAdmin(assignmentId));
	}

	@Test
	public void getAssignmentsWhereIsAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, PathExistsException, InvalidNameException {
		assignmentNode.addAssignmentAdmin(assignmentId, superId);
		List<Long> l = assignmentNode.getAssignmentsWhereIsAdmin();
		assertEquals(1, l.size());
		assertEquals(assignmentId, (long) l.get(0));

		long margeId = userBean.create("marge", "marge@doh.com", "123");
		assignmentNode.addAssignmentAdmin(assignmentId, margeId);
		assertEquals(1, assignmentNode.getAssignmentsWhereIsAdmin().size());

		long tstId = assignmentNode.create("tst", "Test", new GregorianCalendar().getTime(), periodId);
		assignmentNode.addAssignmentAdmin(tstId, superId);
		assertEquals(2, assignmentNode.getAssignmentsWhereIsAdmin().size());
	}
}