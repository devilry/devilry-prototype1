package org.devilry.core.session.dao;

import javax.naming.*;
import javax.persistence.*;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.session.*;
import org.devilry.core.testhelpers.CoreTestHelper;

public abstract class PeriodNodeCommonTest {
	
	protected static CoreTestHelper testHelper;
	
	NodeCommon node;
	UserCommon userBean;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	
	long superId;
	
	long uioId;
	long matnatId;
	long courseId;

	long periodId;
	long periodId2;
		
	@Before
	public void setUp() throws Exception {
		
		node = testHelper.getNodeCommon();
		userBean = testHelper.getUserCommon();
		courseNode = testHelper.getCourseNodeCommon();
		periodNode = testHelper.getPeriodNodeCommon();
		
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
	}

	@After
	public void tearDown() throws Exception {
		testHelper.clearUsersAndNodes();
	}
	

	@Test
	public void getAssignments() throws NamingException, NoSuchObjectException, UnauthorizedException, PathExistsException, InvalidNameException {

		List<Long> assignments = periodNode.getAssignments(periodId);
		assertEquals(0, assignments.size());

		AssignmentNodeCommon assignmentNode = testHelper.getAssignmentNodeCommon();

		Calendar deadline = new GregorianCalendar(2009, 8, 15);
		long assignmentId = assignmentNode.create("Oblig 1",
				"Obligatory assignment 1", deadline.getTime(), periodId);
		long assignmentId2 = assignmentNode.create("Oblig 2",
				"Obligatory assignment 2", deadline.getTime(), periodId);
		long assignmentId3 = assignmentNode.create("Oblig 3",
				"Obligatory assignment 3", deadline.getTime(), periodId);

		assignments = periodNode.getAssignments(periodId);

		assertEquals(3, assignments.size());
		assertTrue(assignments.contains(assignmentId));
		assertTrue(assignments.contains(assignmentId2));
		assertTrue(assignments.contains(assignmentId3));
	}

	@Test
	public void getStartDate() throws NoSuchObjectException, UnauthorizedException {
		Date expected = new GregorianCalendar(2009, 00, 01, 10, 15).getTime();
		Date actual = periodNode.getStartDate(periodId);
		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void setStartDate() throws UnauthorizedException, NoSuchObjectException {
		Calendar start = new GregorianCalendar(2000, 00, 01);
		periodNode.setStartDate(periodId, start.getTime());
		assertEquals(start.getTime().toString(), periodNode.getStartDate(
				periodId).toString());
	}

	@Test
	public void getEndDate() throws NoSuchObjectException, UnauthorizedException {
		assertEquals(new GregorianCalendar(2009, 05, 15).getTime().toString(),
				periodNode.getEndDate(periodId).toString());

	}

	@Test
	public void setEndDate() throws NoSuchObjectException, UnauthorizedException {
		Calendar end = new GregorianCalendar(2000, 00, 01);
		periodNode.setEndDate(periodId, end.getTime());
		assertEquals(end.getTime().toString(), periodNode.getEndDate(periodId)
				.toString());
	}

	@Test
	public void remove() throws NoSuchObjectException {
		node.remove(uioId);
		assertFalse(node.exists(periodId));
	}

	@Test
	public void isStudent() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		assertFalse(periodNode.isStudent(periodId));
		periodNode.addStudent(periodId, superId);
		assertTrue(periodNode.isStudent(periodId));
	}

	@Test
	public void addStudent() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		periodNode.addStudent(periodId, superId);
		assertTrue(periodNode.isStudent(periodId));

		assertEquals(1, periodNode.getStudents(periodId).size());
		periodNode.addStudent(periodId, superId);

		// Check that duplicate value isn't added
		assertEquals(1, periodNode.getStudents(periodId).size());
	}

	@Test
	public void removeStudent() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		periodNode.addStudent(periodId, superId);
		periodNode.removeStudent(periodId, superId);
		assertFalse(periodNode.isStudent(periodId));
		assertTrue(userBean.userExists(superId)); // make sure the user is not
		// removed from the system
		// as well!
	}

	@Test
	public void getPeriodsWhereIsStudent() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		periodNode.addStudent(periodId, superId);
		List<Long> l = periodNode.getPeriodsWhereIsStudent();
		assertEquals(1, l.size());
		assertEquals(periodId, (long) l.get(0));

		// Make sure adding another student does not affect the authenticated
		// student.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		periodNode.addStudent(periodId, margeId);
		assertEquals(1, periodNode.getPeriodsWhereIsStudent().size());

		// It works with more than one period?
		periodNode.addStudent(periodId2, superId);
		assertEquals(2, periodNode.getPeriodsWhereIsStudent().size());
	}

	@Test
	public void isExaminer() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		assertFalse(periodNode.isExaminer(periodId));
		periodNode.addExaminer(periodId, superId);
		assertTrue(periodNode.isExaminer(periodId));
	}

	@Test
	public void addExaminer() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		periodNode.addExaminer(periodId, superId);
		assertTrue(periodNode.isExaminer(periodId));

		assertEquals(1, periodNode.getExaminers(periodId).size());
		periodNode.addExaminer(periodId, superId);

		// Check that duplicate value isn't added
		assertEquals(1, periodNode.getExaminers(periodId).size());
	}

	@Test
	public void removeExaminer() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		periodNode.addExaminer(periodId, superId);
		periodNode.removeExaminer(periodId, superId);
		assertFalse(periodNode.isExaminer(periodId));
		assertTrue(userBean.userExists(superId)); // make sure the user is not
		// removed from the system
		// as well!
	}

	@Test
	public void getPeriodsWhereIsExaminer() throws UnauthorizedException, NoSuchUserException {
		periodNode.addExaminer(periodId, superId);
		List<Long> l = periodNode.getPeriodsWhereIsExaminer();
		assertEquals(1, l.size());
		assertEquals(periodId, (long) l.get(0));

		// Make sure adding another Examiner does not affect the authenticated
		// Examiner.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		periodNode.addExaminer(periodId, margeId);
		assertEquals(1, periodNode.getPeriodsWhereIsExaminer().size());

		// It works with more than one period?
		periodNode.addExaminer(periodId2, superId);
		assertEquals(2, periodNode.getPeriodsWhereIsExaminer().size());
	}

	@Test
	public void exists() throws NoSuchObjectException {
		assertTrue(periodNode.exists(periodId));
		assertFalse(periodNode.exists(uioId));
	}

	@Test
	public void getPath() throws NoSuchObjectException {
		assertEquals(new NodePath("uio.matnat.inf1000.fall09", "\\."), periodNode.getPath(periodId));
	}
	
	@Test(expected = Exception.class)
	public void createDuplicate() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		periodNode.create("unique", "Unique",
				new GregorianCalendar().getTime(), new GregorianCalendar()
						.getTime(), courseId);
		periodNode.create("unique", "Unique",
				new GregorianCalendar().getTime(), new GregorianCalendar()
						.getTime(), courseId);
	}

	@Test
	public void isPeriodAdmin() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		assertFalse(periodNode.isPeriodAdmin(periodId));
		periodNode.addPeriodAdmin(periodId, superId);
		assertTrue(periodNode.isPeriodAdmin(periodId));
	}

	@Test
	public void addPeriodAdmin() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		periodNode.addPeriodAdmin(periodId, superId);
		assertTrue(periodNode.isPeriodAdmin(periodId));

		int adminCount = periodNode.getPeriodAdmins(periodId).size();

		// Test duplicates
		periodNode.addPeriodAdmin(periodId, superId);
		assertEquals(adminCount, periodNode.getPeriodAdmins(periodId).size());
	}

	@Test
	public void removePeriodAdmin() throws NoSuchObjectException, UnauthorizedException, NoSuchUserException {
		periodNode.removePeriodAdmin(periodId, superId);
		assertFalse(periodNode.isPeriodAdmin(periodId));
	}

	@Test
	public void getPeriodsWhereIsAdmin() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NoSuchUserException {
		periodNode.addPeriodAdmin(periodId, superId);
		List<Long> l = periodNode.getPeriodsWhereIsAdmin();
		assertEquals(1, l.size());
		assertEquals(periodId, (long) l.get(0));

		long margeId = userBean.create("marge", "marge@doh.com", "123");
		periodNode.addPeriodAdmin(periodId, margeId);
		assertEquals(1, periodNode.getPeriodsWhereIsAdmin().size());

		long tstId = periodNode.create("tst", "Test", new GregorianCalendar()
				.getTime(), new GregorianCalendar().getTime(), courseId);
		periodNode.addPeriodAdmin(tstId, superId);
		assertEquals(2, periodNode.getPeriodsWhereIsAdmin().size());
	}
	
	
	
}