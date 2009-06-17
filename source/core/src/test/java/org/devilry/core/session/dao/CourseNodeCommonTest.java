package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for all methods in CourseNodeLocal.
 * 
 * @see CourseNodeLocal
 * */
public abstract class CourseNodeCommonTest {
	
	protected static CoreTestHelper testHelper;
	
	protected NodeCommon node;
	protected UserCommon userBean;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	
	long superId;
	
	long uioId;
	long matnatId;
	long courseId;

	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
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
	}

	/*
	@Test
	public void getAllCourses() {
		assertEquals(courseId, (long) courseNode.getAllCourses().get(0));
		assertEquals(1, courseNode.getAllCourses().size());
		courseNode.create("inf1010", "More object oriented programming",
				matnatId);
		List<Long> ids = courseNode.getAllCourses();
		assertEquals(2, ids.size());
	}
*/
	
	@Test
	public void remove() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		long id = courseNode.create("tst", "TsT", uioId);
		courseNode.remove(id);
		node.remove(uioId);
		assertFalse(courseNode.exists(courseId));
	}

	@Test
	public void exists() throws NoSuchObjectException {
		assertTrue(courseNode.exists(courseId));
		assertFalse(courseNode.exists(uioId));
	}
	
	@Test
	public void getPath() throws NoSuchObjectException {
		assertEquals(new NodePath("uio.matnat.inf1000", "\\."), courseNode.getPath(courseId));
	}

	@Test
	public void getIdFromPath() throws NoSuchObjectException {		
		assertEquals(courseId, courseNode.getIdFromPath(new NodePath("uio.matnat.inf1000", "\\.")));
	}
	
	@Test
	public void getCoursesWhereIsAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, PathExistsException, InvalidNameException {
		courseNode.addCourseAdmin(courseId, superId);
		List<Long> l = courseNode.getCoursesWhereIsAdmin();
		assertEquals(1, l.size());
		assertEquals(courseId, (long) l.get(0));

		long margeId = userBean.create("marge", "marge@doh.com", "123");
		courseNode.addCourseAdmin(courseId, margeId);
		assertEquals(1, courseNode.getCoursesWhereIsAdmin().size());

		long tstId = courseNode.create("tst", "Test", uioId);
		courseNode.addCourseAdmin(tstId, superId);
		assertEquals(2, courseNode.getCoursesWhereIsAdmin().size());
	}

	@Test
	public void getPeriods() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
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
	public void createDuplicate() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		courseNode.create("unique", "Unique", uioId);
		courseNode.create("unique", "Unique", uioId);
	}
	
	@Test
	public void isCourseAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException {
		courseNode.addCourseAdmin(courseId, superId);
		assertTrue(courseNode.isCourseAdmin(courseId));
	}

	@Test
	public void addCourseAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException {
		courseNode.addCourseAdmin(courseId, superId);
		assertTrue(courseNode.isCourseAdmin(courseId));
	
		int adminCount = courseNode.getCourseAdmins(courseId).size();
		
		// Test duplicates
		courseNode.addCourseAdmin(courseId, superId);
		assertEquals(adminCount, courseNode.getCourseAdmins(courseId).size());
	}
		
	@Test
	public void removeCourseAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException {
		courseNode.addCourseAdmin(courseId, superId);
		courseNode.removeCourseAdmin(courseId, superId);
		assertFalse(courseNode.isCourseAdmin(courseId));
	}
}
