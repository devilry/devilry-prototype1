package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class NodeImplTest extends AbstractNodeDaoTst {

	@Test
	public void getPath() {
		assertEquals("uio.matnat", node.getPath(matnatId));
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

	
	@Test
	public void getNodesWhereIsAdmin() {
		node.addAdmin(uioId, homerId);
		List<Long> l = node.getNodesWhereIsAdmin();
		assertEquals(1, l.size());
		assertEquals(uioId, (long) l.get(0));

		long margeId = userBean.create("marge", "marge@doh.com", "123");
		node.addAdmin(uioId, margeId);
		assertEquals(1, node.getNodesWhereIsAdmin().size());

		node.addAdmin(matnatId, homerId);
		assertEquals(2, node.getNodesWhereIsAdmin().size());
	}
	
	@Test(expected=Exception.class)
	public void createDuplicateChild() {
		node.create("matnat", "aaaa", uioId);
	}

	@Test(expected=Exception.class)
	public void createDuplicateToplevel() {
		node.create("uio", "aaaa");
	}
}
