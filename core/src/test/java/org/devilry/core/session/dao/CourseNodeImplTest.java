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

import org.devilry.core.session.*;

public class CourseNodeImplTest extends NodeImplTest {
	CourseNodeRemote courseNode;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		courseNode = getRemoteBean(CourseNodeImpl.class);
	}

	@Test
	public void getAllCourseIds() {
		long id = courseNode.create("inf1000", "Object oriented programming", matnatId);
		assertEquals(id, (long) courseNode.getAllCourseIds().get(0));
		courseNode.create("inf1010", "More object oriented programming", matnatId);
		List<Long> ids = courseNode.getAllCourseIds();
		assertEquals(2, ids.size());
	}
}
