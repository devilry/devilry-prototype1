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
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void getId() {

	}

	@Test
	public void getName() {

	}

	@Test
	public void setName() {

	}

	@Test
	public void getDisplayName() {

	}

	@Test
	public void setDisplayName() {

	}

	@Test
	public void getPath() {

	}
}
