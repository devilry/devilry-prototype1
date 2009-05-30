package org.devilry.core.session.dao;

import javax.naming.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.session.*;

import java.util.List;

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
	public void getDisplayName() {
		assertEquals("Faculty of Mathematics", node.getDisplayName(matnatId));
	}

	@Test
	public void setName() {
		node.setName(matnatId, "newname");
		assertEquals("newname", node.getName(matnatId));
	}

	@Test
	public void setDisplayName() {
		node.setDisplayName(matnatId, "newdisp");
		assertEquals("newdisp", node.getDisplayName(matnatId));
	}


	@Test
	public void getPath() {
		assertEquals("uio.matnat", node.getPath(matnatId));
	}

	@Test
	public void getChildren() {
		List<Long> children = node.getChildren(uioId);
		assertEquals(1, children.size());
		assertEquals(matnatId, (long) children.get(0));

		node.create("hf", "Huma....", uioId);
		assertEquals(2, node.getChildren(uioId).size());
	}

	@Test
	public void getSiblings() {
		assertEquals(0, node.getSiblings(matnatId).size());
		node.create("hf", "Huma....", uioId);
		assertEquals(1, node.getSiblings(matnatId).size());
	}


	@Test
	public void exists() {
		assertTrue(node.exists(uioId));
		assertTrue(node.exists(matnatId));
		assertFalse(node.exists(uioId + matnatId));
	}


	@Test
	public void remove() {
		node.remove(uioId);
		assertFalse(node.exists(uioId));
		assertFalse(node.exists(matnatId));
	}


	@Test
	public void getNodeIdFromPath() {
		assertEquals(uioId, node.getNodeIdFromPath("uio"));
		assertEquals(matnatId, node.getNodeIdFromPath("uio.matnat"));
	}

	@Test
	public void getToplevelNodeIds() {
		List<Long> toplevel = node.getToplevelNodeIds();
		assertEquals(1, toplevel.size());
		assertEquals(uioId, (long) toplevel.get(0));
		node.create("a", "A");
		assertEquals(2, node.getToplevelNodeIds().size());
	}
}
