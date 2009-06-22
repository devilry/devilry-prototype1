package org.devilry.clientapi;

import static org.junit.Assert.*;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public abstract class SuperAdminCommonTest {
		
	protected static DevilryConnection connection;
		
	protected long homerId;
	protected long bartId;
	protected long lisaId;
	
	SuperAdmin superAdmin;
	
	NodeCommon node;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	
	UserCommon userBean;

	
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> identity = new ArrayList<String>();
	ArrayList<String> emails = new ArrayList<String>();
	ArrayList<String> phoneNumbers = new ArrayList<String>();
			
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		node = connection.getNode();
		courseNode = connection.getCourseNode();
		userBean = connection.getUser();
		periodNode = connection.getPeriodNode();
				
		// Add users
		names.add("Homer Simpson");
		names.add("Bart Simpson");
		names.add("Lisa Simpson");

		identity.add("homer");
		identity.add("bart");
		identity.add("lisa");
		
		emails.add("homer@doh.com");
		emails.add("bart@doh.com");
		emails.add("lisa@doh.com");
		
		phoneNumbers.add("1111111");
		phoneNumbers.add("9999999");
		phoneNumbers.add("6666666");
				
		homerId = userBean.create(names.get(0), emails.get(0), phoneNumbers.get(0));
		userBean.addIdentity(homerId, identity.get(0));
		userBean.setIsSuperAdmin(homerId, true);
		
		superAdmin = new SuperAdmin(homerId, connection);
		
		bartId = userBean.create(names.get(1), emails.get(1), phoneNumbers.get(1));
		userBean.addIdentity(bartId, identity.get(1));
		
		lisaId = userBean.create(names.get(2), emails.get(2), phoneNumbers.get(2));
		userBean.addIdentity(lisaId, identity.get(2));
	}
		

	@After
	public void tearDown() throws NamingException, NoSuchObjectException, UnauthorizedException {
						
		for(long nodeId: node.getToplevelNodes()) {
			node.remove(nodeId);
		}	
		
		for(long userId: userBean.getUsers()) {
			userBean.remove(userId);
		}
	}
	
	@Test
	public void isSuperAdmin() throws NamingException {
		assertTrue(superAdmin.isSuperAdmin(homerId));
	}
	
	@Test
	public void setIsSuperAdmin() throws NamingException {
		superAdmin.setIsSuperAdmin(lisaId, true);
		assertTrue(superAdmin.isSuperAdmin(lisaId));
		
		superAdmin.setIsSuperAdmin(lisaId, false);
		assertFalse(superAdmin.isSuperAdmin(lisaId));
	}
	
	@Test
	public void addTopLevelNode() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		AdminNode topLevelNode = superAdmin.addTopLevelNode("testTopLevel", "Just for testing top level nodes");
				
		long nodeId = node.getIdFromPath(new NodePath(new String[]{"testTopLevel"}));
		assertEquals(topLevelNode.nodeId, nodeId);
	}
	
	@Test(expected = NoSuchObjectException.class) 
	public void removeTopLevelNode() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		AdminNode topLevelNode = superAdmin.addTopLevelNode("testTopLevel", "Just for testing top level nodes");
		superAdmin.removeTopLevelNode(topLevelNode);
				
		// Object does not exist and should produce NoSuchObjectException
		long nodeId = node.getIdFromPath(new NodePath(new String[]{"testTopLevel"}));
	}
	
	@Test
	public void getTopLevelNodes() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		AdminNode topLevelNode = superAdmin.addTopLevelNode("testTopLevel", "Just for testing top level nodes");
		AdminNode topLevelNode2 = superAdmin.addTopLevelNode("testTopLevel2", "Just for testing top level nodes2");
		
		List<AdminNode> topLevelNodes = superAdmin.getTopLevelNodes();
		
		for (AdminNode node : topLevelNodes) {
			assertTrue(node.nodeId == topLevelNode.nodeId || node.nodeId == topLevelNode2.nodeId);
		}		
	}
	
	@Test
	public void addUser() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		long userId = superAdmin.addUser("Test1", "Email2", "2342342342");
		String test1Identity = "test1Ientity";
		superAdmin.addIdentity(userId, test1Identity);
		
		assertEquals(userId, superAdmin.findUser(test1Identity));
	}
	
	@Test
	public void removeUser() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		long userId = superAdmin.addUser("Test1", "Email2", "2342342342");
		superAdmin.removeUser(userId);
		
		assertFalse(superAdmin.userExists(userId));
	}
		
	@Test
	public void userExists() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		long userId = superAdmin.addUser("Test1", "Email2", "2342342342");		
		assertTrue(superAdmin.userExists(userId));
	}
	
	
	@Test
	public void findUser() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		long userId = superAdmin.addUser("Test1", "Email2", "2342342342");
		String test1Identity = "test1Ientity";
		superAdmin.addIdentity(userId, test1Identity);
		
		assertEquals(userId, superAdmin.findUser(test1Identity));
	}
}
