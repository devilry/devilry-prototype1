package org.devilry.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.UnauthorizedException;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.daointerfaces.UserRemote;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class UserCommonTest {
	
	protected static CoreTestHelper testHelper;
	
	protected UserCommon userBean;
	
	long superId;
	
	protected long testUser0, testUser1, testUser2;

	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> emails = new ArrayList<String>();
	ArrayList<String> phoneNumbers = new ArrayList<String>();
	
	@Before
	public void setUp() throws NamingException, UnauthorizedException {
		
		userBean = testHelper.getUserCommon();
		
		superId = userBean.create("Homer Simpson", "homr@doh.com", "123");
		userBean.addIdentity(superId, "homer");
				
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
		testUser0 = userBean.create(names.get(0), emails.get(0), phoneNumbers.get(0));
		testUser1 = userBean.create(names.get(1),  emails.get(1), phoneNumbers.get(1));
		testUser2 = userBean.create(names.get(2), emails.get(2), phoneNumbers.get(2));
	}

	@After
	public void tearDown() throws UnauthorizedException {
		for(long userId: userBean.getUsers()) {
			userBean.remove(userId);
		}
		
		names.clear();
		emails.clear();
		phoneNumbers.clear();
	}

	@Test
	public void getName() throws UnauthorizedException {
		assertEquals(names.get(0), userBean.getName(testUser0));
		assertEquals(names.get(1), userBean.getName(testUser1));
		assertEquals(names.get(2), userBean.getName(testUser2));
	}

	@Test
	public void getEmail() throws UnauthorizedException {
		assertEquals(emails.get(0), userBean.getEmail(testUser0));
		assertEquals(emails.get(1), userBean.getEmail(testUser1));
		assertEquals(emails.get(2), userBean.getEmail(testUser2));
	}

	@Test
	public void getPhoneNumber() throws UnauthorizedException {
		assertEquals(phoneNumbers.get(0), userBean.getPhoneNumber(testUser0));
		assertEquals(phoneNumbers.get(1), userBean.getPhoneNumber(testUser1));
		assertEquals(phoneNumbers.get(2), userBean.getPhoneNumber(testUser2));
	}

	
	@Test
	public void setName() throws UnauthorizedException {
		userBean.setName(testUser0, "newname");
		assertEquals("newname", userBean.getName(testUser0));
	}

	@Test
	public void setEmail() throws UnauthorizedException {
		userBean.setEmail(testUser0, "newemail");
		assertEquals("newemail", userBean.getEmail(testUser0));
	}

	@Test(expected=Exception.class)
	public void setExistingEmail() throws UnauthorizedException {
		userBean.setEmail(testUser0, "newemail");
		userBean.setEmail(testUser1, "newemail");
	}

	@Test
	public void setPhoneNumber() throws UnauthorizedException {
		userBean.setPhoneNumber(testUser0, "newphone");
		assertEquals("newphone", userBean.getPhoneNumber(testUser0));
	}
	
	
	@Test
	public void remove() throws UnauthorizedException {
		List<Long> users = userBean.getUsers();
		assertTrue(users.size() == names.size()+1); // +1 because of the user created in super
		
		userBean.remove(testUser0);
		
		List<Long> users2 = userBean.getUsers();
		assertTrue(users2.size() == names.size()); // not -1 because of the user created in super
	}
	
	

	@Test
	public void addIdentity() throws UnauthorizedException {
		userBean.addIdentity(testUser0, "laban");
		
		long id = userBean.findUser("laban");
		assertTrue(testUser0 == id);
	}
	
	@Test
	public void removeIdentity() throws UnauthorizedException {
		userBean.addIdentity(testUser0, "laban");
		userBean.addIdentity(testUser0, "tull");
		
		userBean.removeIdentity("laban");
		assertFalse(userBean.identityExists("laban"));
		
		userBean.removeIdentity("tull");
		assertFalse(userBean.identityExists("tull"));
		
		List<String> identities = userBean.getIdentities(testUser0);
		assertTrue(identities.size() == 0);
	}
	
	
	@Test
	public void identityExists() throws UnauthorizedException {
		userBean.addIdentity(testUser0, "laban");
		userBean.addIdentity(testUser0, "tull");
		
		assertTrue(userBean.identityExists("laban"));
		assertTrue(userBean.identityExists("tull"));
		assertFalse(userBean.identityExists("nonexistantidentity"));
	}
	
	
	@Test
	public void emailExists() throws UnauthorizedException {
		assertTrue(userBean.emailExists(emails.get(0)));
		assertTrue(userBean.emailExists(emails.get(1)));
		assertFalse(userBean.emailExists("nonexistantemail"));
	}

	@Test
	public void userExists() throws UnauthorizedException {
		assertTrue(userBean.userExists(testUser0));
		assertFalse(userBean.userExists(testUser0+testUser1+testUser2));
	}

	
	@Test
	public void getIdentities() throws UnauthorizedException {
		userBean.addIdentity(testUser0, "laban");
		userBean.addIdentity(testUser0, "tull");
		
		List<String> identities = userBean.getIdentities(testUser0);
		
		assertTrue(identities.size() == 2);
		assertTrue(identities.contains("laban"));
		assertTrue(identities.contains("tull"));
	}
	
	
	@Test
	public void findUser() throws UnauthorizedException {
		userBean.addIdentity(testUser0, "laban");

		long id = userBean.findUser("laban");
		assertTrue(testUser0 == id);
	}

	
	@Test
	public void getUsers() throws UnauthorizedException {
		List<Long> users = userBean.getUsers();
	
		assertTrue(users.size() == names.size() + 1); // +1 because of the user created in super
		assertTrue(users.contains(testUser0));
		assertTrue(users.contains(testUser1));
		assertTrue(users.contains(testUser2));
	}

	
	@Test
	public void getAuthenticatedUser() throws UnauthorizedException {
		assertEquals(superId, userBean.getAuthenticatedUser());
	}

	@Test
	public void getAuthenticatedIdentity() throws UnauthorizedException {
		assertEquals("homer", userBean.getAuthenticatedIdentity());
	}

	@Test
	public void setIsSuperAdmin() throws UnauthorizedException {
		assertFalse(userBean.isSuperAdmin(testUser0));
		userBean.setIsSuperAdmin(testUser0, true);
		assertTrue(userBean.isSuperAdmin(testUser0));
		userBean.setIsSuperAdmin(testUser0, false);
		assertFalse(userBean.isSuperAdmin(testUser0));
	}

	@Test
	public void getSuperAdmins() throws UnauthorizedException {
		assertEquals(0, userBean.getSuperAdmins().size());
		userBean.setIsSuperAdmin(testUser0, true);
		List<Long> l = userBean.getSuperAdmins();
		assertEquals(1, l.size());
		assertEquals(testUser0, (long) l.get(0));		
		userBean.setIsSuperAdmin(testUser1, true);
		assertEquals(2, userBean.getSuperAdmins().size());
	}
}
