package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.junit.Before;
import org.junit.Test;

public class NodeImplTest extends AbstractNodeDaoTst {
	CourseNodeRemote courseNode;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		courseNode = getRemoteBean(CourseNodeImpl.class);
	}
	
	@Test
	public void getPath() {
		assertEquals("uio.matnat", node.getPath(matnatId));
	}


	@Test
	public void getNodeIdFromPath() {
		assertEquals(uioId, node.getIdFromPath("uio"));
		assertEquals(matnatId, node.getIdFromPath("uio.matnat"));
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
		node.addNodeAdmin(uioId, homerId);
		List<Long> l = node.getNodesWhereIsAdmin();
		assertEquals(1, l.size());
		assertEquals(uioId, (long) l.get(0));

		long margeId = userBean.create("marge", "marge@doh.com", "123");
		node.addNodeAdmin(uioId, margeId);
		assertEquals(1, node.getNodesWhereIsAdmin().size());

		node.addNodeAdmin(matnatId, homerId);
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

	@Test
	public void getChildnodes() {
		List<Long> children = node.getChildnodes(uioId);
		assertEquals(1, children.size());
		assertEquals(matnatId, (long) children.get(0));

		node.create("hf", "Huma....", uioId);
		assertEquals(2, node.getChildnodes(uioId).size());
	}
	
	@Test
	public void getChildcourses() {
		long exphilId = courseNode.create("exphil", "Exphil", uioId);
		List<Long> children = node.getChildcourses(uioId);
		assertEquals(1, children.size());
		assertEquals(exphilId, (long) children.get(0));

		courseNode.create("a", "Aaaaa", uioId);
		assertEquals(2, node.getChildcourses(uioId).size());
	}

	@Test
	public void remove() {
		node.remove(uioId);
		assertFalse(node.exists(uioId));
		assertFalse(node.exists(matnatId));
	}
}
