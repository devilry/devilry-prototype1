package org.devilry.core.session.dao;

import java.util.*;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.session.*;

//public class AssignmentNodeImplTest extends AbstractDaoTst {
//	AssignmentNodeRemote node;
//	TreeManagerRemote tm;
//
//	@Before
//	public void setUp() throws NamingException {
//		setupEjbContainer();
//		long id;
//		tm = getRemoteBean(TreeManagerImpl.class);
//		tm.addNode("uio", "Universitet i Oslo");
//		tm.addCourseNode("inf1000", "INF1000", "First programming course.",
//				tm.getNodeIdFromPath("uio"));
//		tm.addPeriodNode("spring2009", new GregorianCalendar(2009, 1, 1).getTime(),
//				new GregorianCalendar(2009, 6, 15).getTime(),
//				tm.getNodeIdFromPath("uio.inf1000"));
////		tm.addAssignmentNode("oblig1", "Obligatory assignment 1",
////				tm.getNodeIdFromPath("uio.inf1000.spring2009"));
////		node = getRemoteBean(AssignmentNodeImpl.class);
////		node.init(tm.getNodeIdFromPath("uio.inf1000.spring2009.oblig1"));
//	}
//
//	@After
//	public void tearDown() {
////		tm.delNode(tm.getNodeIdFromPath("uio.inf1000.spring2009.oblig1"));
////		tm.delNode(tm.getNodeIdFromPath("uio.inf1000.spring2009"));
////		tm.delNode(tm.getNodeIdFromPath("uio"));
//	}
//
//	@Test
//	public void addDelivery() {
//		long id = node.addDelivery();
//		List<Long> ids = node.getDeliveryIds();
//		assertEquals(1, ids.size());
//		assertEquals(id, (long)ids.get(0));
//	}
//
//	@Test
//	public void getDeliveryIds() {
//		node.addDelivery();
//		node.addDelivery();
//		node.addDelivery();
//
//		List<Long> ids = node.getDeliveryIds();
//		assertEquals(3, ids.size());
//	}
//}