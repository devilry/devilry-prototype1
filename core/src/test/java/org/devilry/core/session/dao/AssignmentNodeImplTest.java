package org.devilry.core.session.dao;

import java.util.*;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.entity.*;
import org.devilry.core.session.*;

public class AssignmentNodeImplTest extends AbstractSessionBeanTestHelper {
	AssignmentNodeRemote node;
	TreeManagerRemote tm;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		long id;
		tm = getRemoteBean(TreeManagerImpl.class);
		tm.addNode("uio", "Universitet i Oslo");
		tm.addCourseNode("inf1000", "INF1000", "First programming course.",
				tm.getNodeIdFromPath("uio"));
		id = tm.addPeriodNode("spring2009", new GregorianCalendar(2009, 1, 1).getTime(),
				new GregorianCalendar(2009, 6, 15).getTime(),
				tm.getNodeIdFromPath("uio.inf1010"));
		id = tm.addAssignmentNode("Oblig1", "Obligatory assignment 1", id);
		node = getRemoteBean(AssignmentNodeImpl.class);
		node.init(id);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void testPlaceholder() {
		// TODO: add some extra attributes to AssignmentNode and test these.
	}
}