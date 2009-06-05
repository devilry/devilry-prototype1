package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.daointerfaces.UserCommon;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class StudentTest extends AbstractNodeClientAPITst {
	protected UserCommon userBean;
	protected long bartId, lisaId;

	Student homer;
	Student bart, lisa;
	
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> emails = new ArrayList<String>();
	ArrayList<String> phoneNumbers = new ArrayList<String>();
	
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		userBean = connection.getUser();
		
		names.add("Bart");
		names.add("Lisa");

		emails.add("bart@doh.com");
		emails.add("lisa@doh.com");
		
		phoneNumbers.add("9999999");
		phoneNumbers.add("6666666");
		
		// Create some test users
		bartId = userBean.create(names.get(0), emails.get(0), phoneNumbers.get(0));
		lisaId = userBean.create(names.get(1),  emails.get(1), phoneNumbers.get(1));
		
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
				
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
	
		
		
//		inf1000
		
		//public List getActivePeriods()
	}
	
	@Test
	public void getPeriods() throws NamingException {
	
		List<StudentPeriod> periods;
		
		periodNode.addStudent(inf1000Spring09, homerId);
		periods = homer.getPeriods();
		assertEquals(1, periods.size());
		
		periodNode.addStudent(inf1000Fall09, homerId);
		periods = homer.getPeriods();
		assertEquals(2, periods.size());
	}
	
}
