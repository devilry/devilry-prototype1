package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;


import org.junit.Test;


/** Base class for testcases <em>testing classes</em> in the node hierarchy. */
public abstract class BaseNodeTst extends AbstractNodeDaoTst {
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
}
