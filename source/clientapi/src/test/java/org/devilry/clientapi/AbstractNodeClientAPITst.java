package org.devilry.clientapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import javax.naming.NamingException;

import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractNodeClientAPITst extends AbstractClientAPITst {
	protected NodeCommon node;
	protected long uioId, matnatId, ifiId, inf1000, inf1000Spring09, inf1000Fall09;
	
	CourseNodeCommon courseNode;
	
	PeriodNodeCommon periodNode;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		node = connection.getNode();
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		ifiId = node.create("ifi", "Department of Informatics", matnatId);
		
		courseNode = connection.getCourseNode();
		inf1000 = courseNode.create("INF1000", "Programmering intro", ifiId);
		
		periodNode = connection.getPeriodNode();
		inf1000Spring09 = courseNode.create("spring2009", "INF1000 spring2009", inf1000);
		inf1000Fall09 = courseNode.create("fall2009", "INf1000 fall 2009", inf1000);
		
	}
	
	@After
	public void tearDown() {
		for(long nodeId: node.getToplevelNodes())
			node.remove(nodeId);
		
		for(long userId: userBean.getUsers())
			userBean.remove(userId);
	}

}
