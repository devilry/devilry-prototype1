package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NodePath;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for all methods in CourseNodeLocal.
 * 
 * @see CourseNodeLocal
 * */
public class CourseNodeImplTest extends BaseNodeTst {
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	
	long courseId;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		courseNode = getRemoteBean(CourseNodeImpl.class);
		periodNode = getRemoteBean(PeriodNodeImpl.class);
		courseId = courseNode.create("inf1000", "Object oriented programming",
				matnatId);
	}

	@Test
	public void getAllCourses() {
		assertEquals(courseId, (long) courseNode.getAllCourses().get(0));
		assertEquals(1, courseNode.getAllCourses().size());
		courseNode.create("inf1010", "More object oriented programming",
				matnatId);
		List<Long> ids = courseNode.getAllCourses();
		assertEquals(2, ids.size());
	}

	@Test
	public void remove() {
		long id = courseNode.create("tst", "TsT", uioId);
		courseNode.remove(id);
		node.remove(uioId);
		assertFalse(courseNode.exists(courseId));
	}

	@Test
	public void exists() {
		assertTrue(courseNode.exists(courseId));
		assertFalse(courseNode.exists(uioId));
	}
	
	@Test
	public void getPath() {
		assertEquals(new NodePath("uio.matnat.inf1000", "\\."), courseNode.getPath(courseId));
	}

	@Test
	public void getIdFromPath() {
		System.err.println("CourseNodeImplTest");
		System.err.println("courseId:" + courseId);
		System.err.println("getIdFromPath:" + courseNode.getIdFromPath(new NodePath("uio.matnat.inf1000", "\\.")));
		
		assertEquals(courseId, courseNode.getIdFromPath(new NodePath("uio.matnat.inf1000", "\\.")));
	}
	
	@Test
	public void getCoursesWhereIsAdmin() {
		courseNode.addCourseAdmin(courseId, homerId);
		List<Long> l = courseNode.getCoursesWhereIsAdmin();
		assertEquals(1, l.size());
		assertEquals(courseId, (long) l.get(0));

		long margeId = userBean.create("marge", "marge@doh.com", "123");
		courseNode.addCourseAdmin(courseId, margeId);
		assertEquals(1, courseNode.getCoursesWhereIsAdmin().size());

		long tstId = courseNode.create("tst", "Test", uioId);
		courseNode.addCourseAdmin(tstId, homerId);
		assertEquals(2, courseNode.getCoursesWhereIsAdmin().size());
	}

	@Test
	public void getPeriods() {
		long p1 = periodNode.create("spring2009", "Spring 2009",
				new GregorianCalendar().getTime(), new GregorianCalendar()
						.getTime(), courseId);
		List<Long> l = courseNode.getPeriods(courseId);
		assertEquals(p1, (long) l.get(0));
		assertEquals(1, l.size());

		periodNode.create("spring2010", "Spring 2009", new GregorianCalendar()
				.getTime(), new GregorianCalendar().getTime(), courseId);
		l = courseNode.getPeriods(courseId);
		assertEquals(2, l.size());
	}

	@Test(expected=Exception.class)
	public void createDuplicate() {
		courseNode.create("unique", "Unique", uioId);
		courseNode.create("unique", "Unique", uioId);
	}
	
	@Test
	public void isCourseAdmin() {
		courseNode.addCourseAdmin(courseId, homerId);
		assertTrue(courseNode.isCourseAdmin(courseId, homerId));
	}
	
	@Test
	public void addCourseAdmin() {
		courseNode.addCourseAdmin(courseId, homerId);
		assertTrue(courseNode.isCourseAdmin(courseId, homerId));
	
		int adminCount = courseNode.getCourseAdmins(courseId).size();
		
		// Test duplicates
		courseNode.addCourseAdmin(courseId, homerId);
		assertEquals(adminCount, courseNode.getCourseAdmins(courseId).size());
	}
		
	@Test
	public void removeCourseAdmin() {
		courseNode.removeCourseAdmin(courseId, homerId);
		assertFalse(courseNode.isCourseAdmin(courseId, homerId));
	}
}
