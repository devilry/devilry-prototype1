package org.devilry.core.session.dao;

import java.util.List;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.entity.*;
import org.devilry.core.session.*;

public class NodeImplTest extends AbstractSessionBeanTestHelper {
	NodeRemote node;
	TreeManagerRemote tm;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
		node = getRemoteBean(NodeImpl.class);

		tm.addNode("uio", "Universitetet i Oslo");
		tm.addNode("matnat", "Det matematisk-naturvitenskapelige fakultet", 
				tm.getNodeIdFromPath("uio"));
		tm.addNode("ifi", "Institutt for informatikk",
				tm.getNodeIdFromPath("uio.matnat"));

		tm.addNode("ifi2", "Institutt for informatikk (IFI2)",
				tm.getNodeIdFromPath("uio.matnat"));
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void getId() {
		node.init(tm.getNodeIdFromPath("uio.matnat"));
		assertEquals(tm.getNodeIdFromPath("uio.matnat"), node.getId());
	}

	@Test
	public void getName() {
		node.init(tm.getNodeIdFromPath("uio"));
		assertEquals("uio", node.getName());
	}

	@Test
	public void setName() {
		node.init(tm.getNodeIdFromPath("uio.matnat"));
		node.setName("ifi3");
		assertEquals("ifi3", node.getName());
		node.setName("ifi");
	}

	@Test
	public void getDisplayName() {
		node.init(tm.getNodeIdFromPath("uio.matnat"));
		assertEquals("Det matematisk-naturvitenskapelige fakultet", node.getDisplayName());
	}

	@Test
	public void setDisplayName() {
		node.init(tm.getNodeIdFromPath("uio.matnat"));
		node.setDisplayName("matnat2");
		assertEquals("matnat2", node.getDisplayName());
		node.setDisplayName("matnat");
	}

	@Test
	public void getPath() {
		node.init(tm.getNodeIdFromPath("uio.matnat"));
		assertEquals("uio.matnat", node.getPath());
	}

	@Test
	public void getChildren() {
		node.init(tm.getNodeIdFromPath("uio.matnat"));
		assertEquals(2, node.getChildren().size());
	}
}
