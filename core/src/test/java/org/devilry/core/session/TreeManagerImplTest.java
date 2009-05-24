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
	long rootnodeId;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
		rootnodeId = tm.addNode("uio", "Universitetet i Oslo");
	}

	@After
	public void tearDown() {
		tm.delNode(rootnodeId);
	}

	@Test
	public void addNode() {
		assertTrue(0 < rootnodeId);
		assertTrue(0 < tm.getNodeIdFromPath("uio"));
		assertEquals(tm.getNodeIdFromPath("uio"), rootnodeId);
	}

	@Test
	public void addCourseNode() {
		rootnodeId = tm.addCourseNode("inf1000", "INF1000",
				"Grunnkurs i objektorientert programmering", rootnodeId);

		assertTrue(0 < rootnodeId);
		assertTrue(0 < tm.getNodeIdFromPath("uio.inf1000"));
		assertEquals(tm.getNodeIdFromPath("uio.inf1000"), rootnodeId);
	}

	@Test
	public void addPeriodNode() {
		rootnodeId = tm.addCourseNode("inf1000", "INF1000", "Grunnkurs i objektorientert programmering", rootnodeId);

		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		rootnodeId = tm.addPeriodNode("fall09", "Fall 2009", start.getTime(), end.getTime(), rootnodeId);

		assertTrue(0 < rootnodeId);
		assertTrue(0 < tm.getNodeIdFromPath("uio.inf1000.fall09"));
		assertEquals(tm.getNodeIdFromPath("uio.inf1000.fall09"), rootnodeId);
	}

	@Test
	public void addAssignmentNode() {
		rootnodeId = tm.addCourseNode("inf1000", "INF1000", "Grunnkurs i objektorientert programmering", rootnodeId);

		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		rootnodeId = tm.addPeriodNode("fall09", "Fall 2009", start.getTime(), end.getTime(), rootnodeId);
		rootnodeId = tm.addAssignmentNode("oblig1", "O1", rootnodeId);

		assertTrue(0 < rootnodeId);
		assertTrue(0 < tm.getNodeIdFromPath("uio.inf1000.fall09.oblig1"));
		assertEquals(tm.getNodeIdFromPath("uio.inf1000.fall09.oblig1"), rootnodeId);
	}

	@Test
	public void delNode() {
		assertEquals(rootnodeId, tm.getNodeIdFromPath("uio"));
		tm.delNode(rootnodeId);
		assertEquals(-1, tm.getNodeIdFromPath("uio"));
	}

	@Test
	public void getNodeIdFromPath() {
		assertTrue(0 < tm.getNodeIdFromPath("uio"));
	}
}