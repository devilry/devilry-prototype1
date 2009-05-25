package org.devilry.core.session.dao;

import javax.naming.*;
import javax.persistence.*;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.session.*;

public class PeriodNodeImplTest extends AbstractDaoTst {
	PeriodNodeRemote node;
	TreeManagerRemote tm;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
		node = getRemoteBean(PeriodNodeImpl.class);

		tm.addNode("uio", "Universitetet i Oslo");
		tm.addNode("matnat", "Det matematisk-naturvitenskapelige fakultet",
				tm.getNodeIdFromPath("uio"));
		tm.addNode("ifi", "Institutt for informatikk", 
				tm.getNodeIdFromPath("uio.matnat"));
		tm.addCourseNode("inf1000", "INF1000", "Grunnkurs i objektorientert programmering",
				tm.getNodeIdFromPath("uio.matnat.ifi"));

		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		tm.addPeriodNode("fall09", "Fall 2009", start.getTime(), end.getTime(),
				tm.getNodeIdFromPath("uio.matnat.ifi.inf1000"));
	}

	@After
	public void tearDown() {
		tm.delNode(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000.fall09"));
//		tm.delNode(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000"));
//		tm.delNode(tm.getNodeIdFromPath("uio.matnat.ifi"));
//		tm.delNode(tm.getNodeIdFromPath("uio.matnat"));
		tm.delNode(tm.getNodeIdFromPath("uio"));
	}

	@Test
	public void getStartDate() {
		node.init(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000.fall09"));
		assertEquals("Thu Jan 01 00:00:00 CET 2009", node.getStartDate().toString());
	}

	@Test
	public void setStartDate() {
		node.init(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000.fall09"));
		Calendar start = new GregorianCalendar(2000, 00, 01);
		node.setStartDate(start.getTime());
		assertEquals("Sat Jan 01 00:00:00 CET 2000", node.getStartDate().toString());
		start = new GregorianCalendar(2009, 00, 01);
		node.setStartDate(start.getTime());
	}

	@Test
	public void getEndDate() {
		node.init(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000.fall09"));
		assertEquals("Mon Jun 15 00:00:00 CEST 2009", node.getEndDate().toString());
	}

	@Test
	public void setEndDate() {
		node.init(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000.fall09"));
		Calendar end = new GregorianCalendar(2008, 05, 01);
		node.setEndDate(end.getTime());
		assertEquals("Sun Jun 01 00:00:00 CEST 2008", node.getEndDate().toString());
		end = new GregorianCalendar(2009, 05, 15);
		node.setEndDate(end.getTime());
	}
}

