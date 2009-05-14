package org.devilry.core.session.dao;

import java.util.List;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.entity.*;
import org.devilry.core.session.*;

public class CourseNodeImplTest extends AbstractSessionBeanTestHelper {
	CourseNodeRemote node;
	TreeManagerRemote tm;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
		node = getRemoteBean(CourseNodeImpl.class);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void setCourseCode() {

	}

	@Test
	public void getCourseCode() {

	}
}

