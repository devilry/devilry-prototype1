package org.devilry.core.session.dao;

import javax.naming.*;
import javax.persistence.*;
import java.util.Properties;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.session.*;

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
		// TODO
		assertFalse(courseNode.exists(courseId));
	}

	@Test
	public void exists() {
		assertTrue(courseNode.exists(courseId));
		assertFalse(courseNode.exists(uioId));
	}
}
