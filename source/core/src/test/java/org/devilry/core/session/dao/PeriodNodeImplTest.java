package org.devilry.core.session.dao;

import javax.naming.*;
import javax.persistence.*;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.session.*;

public class PeriodNodeImplTest extends AbstractNodeDaoTst {
	PeriodNodeRemote periodNode;
	long periodId;
	long periodId2;
	long inf1000Id;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		periodNode = getRemoteBean(PeriodNodeImpl.class);
		CourseNodeRemote courseNode = getRemoteBean(CourseNodeImpl.class);
		inf1000Id = courseNode.create("inf1000", "Object oriented programming",
				matnatId);

		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		periodId = periodNode.create("fall09", "Fall 2009", start.getTime(),
				end.getTime(), inf1000Id);
		periodId2 = periodNode.create("spring09", "Spring 2009", start
				.getTime(), end.getTime(), inf1000Id);
	}

	@Test
	public void getAssignments() throws NamingException {

		List<Long> assignments = periodNode.getAssignments(periodId);
		assertEquals(0, assignments.size());

		AssignmentNodeRemote assignmentNode = getRemoteBean(AssignmentNodeImpl.class);

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
	public void getStartDate() {
		Date expected = new GregorianCalendar(2009, 00, 01, 10, 15).getTime();
		Date actual = periodNode.getStartDate(periodId);
		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void setStartDate() {
		Calendar start = new GregorianCalendar(2000, 00, 01);
		periodNode.setStartDate(periodId, start.getTime());
		assertEquals(start.getTime().toString(), periodNode.getStartDate(
				periodId).toString());
	}

	@Test
	public void getEndDate() {
		assertEquals(new GregorianCalendar(2009, 05, 15).getTime().toString(),
				periodNode.getEndDate(periodId).toString());

	}

	@Test
	public void setEndDate() {
		Calendar end = new GregorianCalendar(2000, 00, 01);
		periodNode.setEndDate(periodId, end.getTime());
		assertEquals(end.getTime().toString(), periodNode.getEndDate(periodId)
				.toString());
	}

	@Test
	public void remove() {
		node.remove(uioId);
		assertFalse(node.exists(periodId));
	}

	@Test
	public void isStudent() {
		assertFalse(periodNode.isStudent(periodId, homerId));
		periodNode.addStudent(periodId, homerId);
		assertTrue(periodNode.isStudent(periodId, homerId));
	}

	@Test
	public void addStudent() {
		periodNode.addStudent(periodId, homerId);
		assertTrue(periodNode.isStudent(periodId, homerId));

		assertEquals(1, periodNode.getStudents(periodId).size());
		periodNode.addStudent(periodId, homerId);

		// Check that duplicate value isn't added
		assertEquals(1, periodNode.getStudents(periodId).size());
	}

	@Test
	public void removeStudent() {
		periodNode.addStudent(periodId, homerId);
		periodNode.removeStudent(periodId, homerId);
		assertFalse(periodNode.isStudent(periodId, homerId));
		assertTrue(userBean.userExists(homerId)); // make sure the user is not
		// removed from the system
		// as well!
	}

	@Test
	public void getPeriodsWhereIsStudent() {
		periodNode.addStudent(periodId, homerId);
		List<Long> l = periodNode.getPeriodsWhereIsStudent();
		assertEquals(1, l.size());
		assertEquals(periodId, (long) l.get(0));

		// Make sure adding another student does not affect the authenticated
		// student.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		periodNode.addStudent(periodId, margeId);
		assertEquals(1, periodNode.getPeriodsWhereIsStudent().size());

		// It works with more than one period?
		periodNode.addStudent(periodId2, homerId);
		assertEquals(2, periodNode.getPeriodsWhereIsStudent().size());
	}

	@Test
	public void isExaminer() {
		assertFalse(periodNode.isExaminer(periodId, homerId));
		periodNode.addExaminer(periodId, homerId);
		assertTrue(periodNode.isExaminer(periodId, homerId));
	}

	@Test
	public void addExaminer() {
		periodNode.addExaminer(periodId, homerId);
		assertTrue(periodNode.isExaminer(periodId, homerId));

		assertEquals(1, periodNode.getExaminers(periodId).size());
		periodNode.addExaminer(periodId, homerId);

		// Check that duplicate value isn't added
		assertEquals(1, periodNode.getExaminers(periodId).size());
	}

	@Test
	public void removeExaminer() {
		periodNode.addExaminer(periodId, homerId);
		periodNode.removeExaminer(periodId, homerId);
		assertFalse(periodNode.isExaminer(periodId, homerId));
		assertTrue(userBean.userExists(homerId)); // make sure the user is not
		// removed from the system
		// as well!
	}

	@Test
	public void getPeriodsWhereIsExaminer() {
		periodNode.addExaminer(periodId, homerId);
		List<Long> l = periodNode.getPeriodsWhereIsExaminer();
		assertEquals(1, l.size());
		assertEquals(periodId, (long) l.get(0));

		// Make sure adding another Examiner does not affect the authenticated
		// Examiner.
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		periodNode.addExaminer(periodId, margeId);
		assertEquals(1, periodNode.getPeriodsWhereIsExaminer().size());

		// It works with more than one period?
		periodNode.addExaminer(periodId2, homerId);
		assertEquals(2, periodNode.getPeriodsWhereIsExaminer().size());
	}

	@Test
	public void exists() {
		assertTrue(periodNode.exists(periodId));
		assertFalse(periodNode.exists(uioId));
	}

	@Test(expected = Exception.class)
	public void createDuplicate() {
		periodNode.create("unique", "Unique",
				new GregorianCalendar().getTime(), new GregorianCalendar()
						.getTime(), inf1000Id);
		periodNode.create("unique", "Unique",
				new GregorianCalendar().getTime(), new GregorianCalendar()
						.getTime(), inf1000Id);
	}
}