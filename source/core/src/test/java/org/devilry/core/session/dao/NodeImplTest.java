package org.devilry.core.session.dao;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class NodeImplTest extends AbstractNodeDaoTst {
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
		List<Long> toplevel = node.getToplevelNodes();
		assertEquals(1, toplevel.size());
		assertEquals(uioId, (long) toplevel.get(0));
		node.create("a", "A");
		assertEquals(2, node.getToplevelNodes().size());
	}
}