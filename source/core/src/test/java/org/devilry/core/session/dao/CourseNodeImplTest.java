package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.junit.Before;
import org.junit.Test;

public class CourseNodeImplTest extends AbstractNodeDaoTst {
	CourseNodeRemote courseNode;
	long courseId;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		courseNode = getRemoteBean(CourseNodeImpl.class);
		courseId = courseNode.create("inf1000", "Object oriented programming", matnatId);
	}

	@Test
	public void getAllCourses() {
		assertEquals(courseId, (long) courseNode.getAllCourses().get(0));
		courseNode.create("inf1010", "More object oriented programming", matnatId);
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
}
