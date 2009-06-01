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
import org.devilry.core.dao.CourseNodeRemote;
import org.devilry.core.session.*;

public class CourseNodeImplTest extends NodeImplTest {
	CourseNodeRemote courseNode;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		courseNode = getRemoteBean(CourseNodeImpl.class);
	}

	@Test
	public void getAllCourses() {
		long id = courseNode.create("inf1000", "Object oriented programming", matnatId);
		assertEquals(id, (long) courseNode.getAllCourses().get(0));
		courseNode.create("inf1010", "More object oriented programming", matnatId);
		List<Long> ids = courseNode.getAllCourses();
		assertEquals(2, ids.size());
	}
	
	@Test
	public void remove() {
		long id = courseNode.create("inf1000", "Object oriented programming", matnatId);
		super.remove();
		assertFalse(node.exists(id));
	}
}
