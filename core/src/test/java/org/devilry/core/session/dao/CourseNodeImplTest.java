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

		tm.addNode("ifi", "Universitetet i Oslo");
		tm.addCourseNode("inf1000", "INF1000", "Grunnkurs i objektorientert programmering",
				tm.getNodeIdFromPath("ifi"));
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void setCourseCode() {
		node.init(tm.getNodeIdFromPath("ifi.inf1000"));
		node.setCourseCode("INF1010");
		assertEquals("INF1010", node.getCourseCode());
		node.setCourseCode("INF1000");
	}

	@Test
	public void getCourseCode() {
		node.init(tm.getNodeIdFromPath("ifi.inf1000"));
		assertEquals("INF1000", node.getCourseCode());
	}
}

