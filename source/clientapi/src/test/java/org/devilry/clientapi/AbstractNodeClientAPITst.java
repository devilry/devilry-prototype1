package org.devilry.clientapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import javax.naming.NamingException;

import org.devilry.core.NodePath;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractNodeClientAPITst extends AbstractClientAPITst {
	protected NodeCommon node;
	protected long uioId, matnatId, ifiId, inf1000, inf1000Spring09, inf1000Fall09;
	
	protected UserCommon userBean;
	protected long homerId;
	protected long bartId;
	protected long lisaId;
	
	CourseNodeCommon courseNode;
	
	PeriodNodeCommon periodNode;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		node = connection.getNode();
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		ifiId = node.create("ifi", "Department of Informatics", matnatId);
		
		System.err.println("ifiId:" + ifiId);
		
		courseNode = connection.getCourseNode();
		
		long testId = courseNode.getIdFromPath(new NodePath("uio.matnat.ifi.inf1000", "\\."));
		
		System.err.println("inf1000 id:" + testId);
		
		//inf1000 = courseNode.create("inf1000", "Programmering intro", ifiId);
		inf1000 = courseNode.create("inf1000", "Programmering intro", matnatId);
		
		periodNode = connection.getPeriodNode();
		inf1000Spring09 = courseNode.create("spring2009", "INF1000 spring2009", inf1000);
		inf1000Fall09 = courseNode.create("fall2009", "INf1000 fall 2009", inf1000);
		
		addTestUsers();
	}
	
	@After
	public void tearDown() throws NamingException {
		System.err.println("AbstractNodeClientAPITst teardown");
		
		node = connection.getNode();
		userBean = connection.getUser();
		
		System.err.println("uioId:" + uioId);
		
		for(long nodeId: node.getToplevelNodes()) {
			System.err.println("Remove toplevel node:" + nodeId);
			node.remove(nodeId);
		}
		
		for(long userId: userBean.getUsers()) {
			System.err.println("Remove user:" + userId);
			userBean.remove(userId);
		}
	}

	
	protected void addTestUsers() throws NamingException {
		//userBean = getRemoteBean(UserImpl.class);
		userBean = connection.getUser();
		
		homerId = userBean.create("Homer Simpson", "homr@doh.com", "123");
		userBean.addIdentity(homerId, "homer");
		
		//homerNodeAdmin = 
				
		bartId = userBean.create("Bart Simpson", "bart@doh.com", "1234");
		userBean.addIdentity(bartId, "bart");
		
		lisaId = userBean.create("Lisa Simpson", "lisa@doh.com", "1234");
		userBean.addIdentity(lisaId, "lisa");
				
		//bartStudent = new Student(bartId, connection);
		//lisaStudent = new Student(lisaId, connection);
	}
	
}
