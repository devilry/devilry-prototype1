package org.devilry.core.session.dao;

import javax.naming.NamingException;

import org.devilry.core.dao.NodeImpl;
import org.devilry.core.daointerfaces.NodeRemote;
import org.junit.After;
import org.junit.Before;

public class AbstractNodeDaoTst extends AbstractDaoTst {
	protected NodeRemote node;
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
		for(long id: node.getToplevelNodes())
			node.remove(id);
	}
}
