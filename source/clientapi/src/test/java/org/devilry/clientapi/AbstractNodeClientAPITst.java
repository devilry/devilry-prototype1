package org.devilry.clientapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.daointerfaces.UserLocal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractNodeClientAPITst extends AbstractClientAPITst {
	protected NodeLocal node;
	protected long uioId, matnatId, ifiId;
	
	CourseNodeCommon courseNode;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		node = connection.getNode();
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		ifiId = node.create("ifi", "Department of Informatics", matnatId);
		
		courseNode = connection.getCourseNode();
		courseNode.create("INF1000", "Programmering intro", ifiId);
		
	}
	
	@After
	public void tearDown() {
		for(long nodeId: node.getToplevelNodes())
			node.remove(nodeId);
		
		for(long userId: userBean.getUsers())
			userBean.remove(userId);
	}

}
