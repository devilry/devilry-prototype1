package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.daointerfaces.UserLocal;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class StudentTest extends AbstractNodeClientAPITst {
	protected UserCommon userBean;
	protected long testUser0, testUser1, testUser2;

	Student bart, lisa, maggie;
	
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> emails = new ArrayList<String>();
	ArrayList<String> phoneNumbers = new ArrayList<String>();
	
	Student homer;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		userBean = connection.getUser();
				
		names.add("Bart");
		names.add("Lisa");
		names.add("Maggie");
		
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
		
		bart = new Student(testUser0, connection);
		lisa = new Student(testUser1, connection);
		maggie = new Student(testUser2, connection);
	}
	

	@After
	public void tearDown() {
		for(long userId: userBean.getUsers()) {
			userBean.remove(userId);
		}
		
		names.clear();
		emails.clear();
		phoneNumbers.clear();
	}

	@Test
	public void getActivePeriods() {
	
	//public List getActivePeriods()
	}
	
	@Test
	public void getPeriods() {
	
	//public List<StudentPeriod> getPeriods()
	}
	
}
