package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.entity.CourseNode;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for all methods in CourseNodeLocal.
 * 
 * @see CourseNodeLocal
 * */
public class CourseNodeImplTest extends AbstractNodeDaoTst {
	CourseNodeRemote courseNode;
	PeriodNodeRemote periodNode;
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
	public void addCourseAdmin() {
		
		courseNode.addCourseAdmin(courseId, homerId);
		
		courseNode.is
		
	/*
	addCourseAdmin(long courseNodeId, long userId) {
		CourseNode node = getCourseNode(courseNodeId);
		addAdmin(node, userId);
	
	*/
	}
	
	@Test
	public void removeCourseAdmin(long courseNodeId, long userId) {
		CourseNode node = getCourseNode(courseNodeId);
		removeAdmin(node, userId);
	}
}
