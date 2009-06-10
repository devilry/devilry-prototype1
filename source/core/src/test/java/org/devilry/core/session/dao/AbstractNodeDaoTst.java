package org.devilry.core.session.dao;

import javax.naming.NamingException;

import org.devilry.core.dao.NodeImpl;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.junit.After;
import org.junit.Before;


/** Base class for testcases <em>using</em> the node hierarchy. */
public class AbstractNodeDaoTst extends AbstractDaoTst {
	protected NodeRemote node;
	protected long uioId, matnatId;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		node = getRemoteBean(NodeImpl.class);
		uioId = node.create("uio", "UiO");
		node.addNodeAdmin(uioId, homerId);
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		node.addNodeAdmin(matnatId, homerId);
	}
	
	@After
	public void tearDown() {
		for(long nodeId: node.getToplevelNodes())
			node.remove(nodeId);
		for(long userId: userBean.getUsers())
			userBean.remove(userId);
	}
}
