package org.devilry.core.session.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.naming.NamingException;

import org.devilry.core.dao.NodeImpl;
import org.devilry.core.daointerfaces.NodeCommon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractNodeDaoTst extends AbstractDaoTst {
	protected NodeCommon node;
	protected long uioId, matnatId;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		node = getRemoteBean(NodeImpl.class);
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
	}
	
	@After
	public void tearDown() {
		for(long nodeId: node.getToplevelNodes())
			node.remove(nodeId);
		for(long userId: userBean.getUsers())
			userBean.remove(userId);
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
	public void exists() {
		assertTrue(node.exists(uioId));
		assertTrue(node.exists(matnatId));
		assertFalse(node.exists(uioId + matnatId));
	}

	

	@Test
	public void isAdmin() {
		assertFalse(node.isNodeAdmin(uioId, homerId));
		node.addNodeAdmin(uioId, homerId);
		assertTrue(node.isNodeAdmin(uioId, homerId));
	}

	@Test
	public void addAdmin() {
		node.addNodeAdmin(uioId, homerId);
		assertTrue(node.isNodeAdmin(uioId, homerId));

		assertEquals(1, node.getNodeAdmins(uioId).size());
		node.addNodeAdmin(uioId, homerId);
		assertEquals(1, node.getNodeAdmins(uioId).size());
	}

	@Test
	public void removeAdmin() {
		node.addNodeAdmin(uioId, homerId);
		node.removeNodeAdmin(uioId, homerId);
		assertFalse(node.isNodeAdmin(uioId, homerId));
		assertTrue(userBean.userExists(homerId)); // make sure the user is not removed from the system as well!
	}
}
