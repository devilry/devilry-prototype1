package org.devilry.core.session.dao;

import javax.naming.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.session.*;

public class NodeImplTest extends AbstractDaoTst {
	NodeRemote node;
	TreeManagerRemote tm;
	long uioId, matnatId;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
		node = getRemoteBean(NodeImpl.class);
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);

	}

	@After
	public void tearDown() {
		node.remove(uioId);
	}

	@Test
	public void getName() {
		assertEquals("uio", node.getName(uioId));
		assertEquals("matnat", node.getName(matnatId));
	}

	@Test
	public void setName() {
		node.setName(matnatId, "newname");
		assertEquals("newname", node.getName(matnatId));
	}

	@Test
	public void getDisplayName() {
		assertEquals("Faculty of Mathematics", node.getDisplayName(matnatId));
	}

	@Test
	public void setDisplayName() {
		node.setDisplayName(matnatId, "newdisp");
		assertEquals("newdisp", node.getDisplayName(matnatId));
	}


	/*
	@Test
	public void getPath() {
		node.init(tm.getNodeIdFromPath("uio.matnat.ifi"));
		assertEquals("uio.matnat.ifi", node.getPath());
	}

	@Test
	public void getChildren() {
		node.init(tm.getNodeIdFromPath("uio.matnat"));
		assertEquals(1, node.getChildren().size());
		tm.addNode("ifi2", "ifi2", tm.getNodeIdFromPath("uio.matnat"));
		assertEquals(2, node.getChildren().size());
	}

	@Test
	public void getSiblings() {
		node.init(tm.getNodeIdFromPath("uio.matnat.ifi"));
		assertEquals(0, node.getSiblings().size());
		tm.addNode("ifi2", "ifi2", tm.getNodeIdFromPath("uio.matnat"));
		assertEquals(1, node.getSiblings().size());
	}

	@Test
	public void remove() {
		node.init(tm.getNodeIdFromPath("uio"));
		node.remove();
		assertEquals(-1, tm.getNodeIdFromPath("uio"));
		assertEquals(-1, tm.getNodeIdFromPath("uio.matnat"));
		assertEquals(-1, tm.getNodeIdFromPath("uio.matnat.ifi"));
	}
	*/
}
