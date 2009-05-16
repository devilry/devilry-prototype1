package org.devilry.core.session;

import java.util.*;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.session.dao.*;

public class TreeManagerImplTest extends AbstractDaoTst {
	TreeManagerRemote tm;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void addNode() {
		long id = tm.addNode("uio", "Universitetet i Oslo");
		assertTrue(0 < id);
		assertTrue(0 < tm.getNodeIdFromPath("uio"));
		assertEquals(tm.getNodeIdFromPath("uio"), id);
	}

	@Test
	public void addCourseNode() {
		long id = tm.addNode("uio", "Universitetet i Oslo");
		id = tm.addCourseNode("inf1000", "INF1000", 
				"Grunnkurs i objektorientert programmering",  id);

		assertTrue(0 < id);
		assertTrue(0 < tm.getNodeIdFromPath("uio.inf1000"));
		assertEquals(tm.getNodeIdFromPath("uio.inf1000"), id);
	}

	@Test
	public void addPeriodNode() {
		long id = tm.addNode("uio", "Universitetet i Oslo");
		id = tm.addCourseNode("inf1000", "INF1000", "Grunnkurs i objektorientert programmering", id);

		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		id = tm.addPeriodNode("fall09", "Fall 2009", start.getTime(), end.getTime(), id);

		assertTrue(0 < id);
		assertTrue(0 < tm.getNodeIdFromPath("uio.inf1000.fall09"));
		assertEquals(tm.getNodeIdFromPath("uio.inf1000.fall09"), id);
	}

	@Test
	public void addAssignmentNode() {
		long id = tm.addNode("uio", "Universitetet i Oslo");
		id = tm.addCourseNode("inf1000", "INF1000", "Grunnkurs i objektorientert programmering", id);

		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		id = tm.addPeriodNode("fall09", "Fall 2009", start.getTime(), end.getTime(), id);
		id = tm.addAssignmentNode("oblig1", "O1", id);

		assertTrue(0 < id);
		assertTrue(0 < tm.getNodeIdFromPath("uio.inf1000.fall09.oblig1"));
		assertEquals(tm.getNodeIdFromPath("uio.inf1000.fall09.oblig1"), id);
	}

	@Test
	public void delNode() {
		long id = tm.addNode("uio", "Universitetet i Oslo");
		
		assertEquals(id, tm.getNodeIdFromPath("uio"));
		tm.delNode(id);
		assertEquals(-1, tm.getNodeIdFromPath("uio"));
	}

	@Test
	public void getNodeIdFromPath() {
		tm.addNode("uio", "Universitetet i Oslo");
		assertTrue(0 < tm.getNodeIdFromPath("uio"));
	}
}

