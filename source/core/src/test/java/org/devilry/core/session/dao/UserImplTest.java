package org.devilry.core.session.dao;

import javax.naming.*;

import org.devilry.core.dao.UserImpl;
import org.devilry.core.dao.UserRemote;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class UserImplTest extends AbstractDaoTst {
	protected UserRemote node;
	protected long testUser0, testUser1, testUser2;

	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> emails = new ArrayList<String>();
	ArrayList<String> phoneNumbers = new ArrayList<String>();
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		node = getRemoteBean(UserImpl.class);
		
		names.add("Bendik");
		names.add("Espen");
		names.add("Morten");
		
		emails.add("bendik@universe.com");
		emails.add("xxx@pornstar.com");
		emails.add("machoguy@gayparade.com");
		
		phoneNumbers.add("9999999");
		phoneNumbers.add("6666666");
		phoneNumbers.add("80012345");
		
		// Create some test users
		testUser0 = node.create(names.get(0), emails.get(0), phoneNumbers.get(0));
		testUser1 = node.create(names.get(1),  emails.get(1), phoneNumbers.get(1));
		testUser2 = node.create(names.get(2), emails.get(2), phoneNumbers.get(2));
	}

	@After
	public void tearDown() {
		node.remove(testUser0);
		node.remove(testUser1);
		node.remove(testUser2);
		
		names.clear();
		emails.clear();
		phoneNumbers.clear();
	}

	@Test
	public void getName() {
		assertEquals(names.get(0), node.getName(testUser0));
		assertEquals(names.get(1), node.getName(testUser1));
		assertEquals(names.get(2), node.getName(testUser2));
	}

	@Test
	public void getEmail() {
		assertEquals(emails.get(0), node.getEmail(testUser0));
		assertEquals(emails.get(1), node.getEmail(testUser1));
		assertEquals(emails.get(2), node.getEmail(testUser2));
	}

	@Test
	public void getPhoneNumber() {
		assertEquals(phoneNumbers.get(0), node.getPhoneNumber(testUser0));
		assertEquals(phoneNumbers.get(1), node.getPhoneNumber(testUser1));
		assertEquals(phoneNumbers.get(2), node.getPhoneNumber(testUser2));
	}

	
	@Test
	public void setName() {
		node.setName(testUser0, "newname");
		assertEquals("newname", node.getName(testUser0));
	}

	@Test
	public void setEmail() {
		node.setEmail(testUser0, "newemail");
		assertEquals("newemail", node.getEmail(testUser0));
	}

	@Test
	public void setPhoneNumber() {
		node.setPhoneNumber(testUser0, "newphone");
		assertEquals("newphone", node.getPhoneNumber(testUser0));
	}
	
	
	@Test
	public void remove() {
		List<Long> users = node.getUsers();
		assertTrue(users.size() == names.size());
		
		node.remove(testUser0);
		
		List<Long> users2 = node.getUsers();
		assertTrue(users2.size() == names.size()-1);
	}
	
	

	@Test
	public void addIdentity() {
		node.addIdentity(testUser0, "laban");
		
		long id = node.findUser("laban");
		assertTrue(testUser0 == id);
	}
	
	@Test
	public void removeIdentity() {
		node.addIdentity(testUser0, "laban");
		node.addIdentity(testUser0, "tull");
		
		node.removeIdentity("laban");
		assertFalse(node.identityExists("laban"));
		
		node.removeIdentity("tull");
		assertFalse(node.identityExists("tull"));
		
		List<String> identities = node.getIdentities(testUser0);
		assertTrue(identities.size() == 0);
	}
	
	
	@Test
	public void identityExists() {
		node.addIdentity(testUser0, "laban");
		node.addIdentity(testUser0, "tull");
		
		assertTrue(node.identityExists("laban"));
		assertTrue(node.identityExists("tull"));
		assertFalse(node.identityExists("nonexistantidentity"));
	}
	
	
	@Test
	public void emailExists() {
		assertTrue(node.emailExists(emails.get(0)));
		assertTrue(node.emailExists(emails.get(1)));
		
		assertFalse(node.emailExists("nonexistantemail"));
	}
		
	
	@Test
	public void getIdentities() {
		node.addIdentity(testUser0, "laban");
		node.addIdentity(testUser0, "tull");
		
		List<String> identities = node.getIdentities(testUser0);
		
		assertTrue(identities.size() == 2);
		assertTrue(identities.contains("laban"));
		assertTrue(identities.contains("tull"));
	}
	
	
	@Test
	public void findUser() {
		node.addIdentity(testUser0, "laban");

		long id = node.findUser("laban");
		assertTrue(testUser0 == id);
		
		//long id2 = node.findUser("nonexistantuser");
		//assertTrue(id2 == -1);
	}

	
	@Test
	public void getUsers() {
		List<Long> users = node.getUsers();
	
		assertTrue(users.size() == names.size());
		assertTrue(users.contains(testUser0));
		assertTrue(users.contains(testUser1));
		assertTrue(users.contains(testUser2));
	}

}
