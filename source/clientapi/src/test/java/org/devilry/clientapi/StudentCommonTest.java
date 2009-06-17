package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public abstract class StudentCommonTest {
		
	protected static DevilryConnection connection;
	
	protected long uioId, matnatId, ifiId, inf1000, inf1000Spring09, inf1000Fall09;
	
	protected long homerId;
	protected long bartId;
	protected long lisaId;
	
	protected NodeCommon node;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	
	protected UserCommon userBean;
	//protected long bartId, lisaId;

	Student homer;
	Student bart, lisa;
	
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
		
		bartId = userBean.create(names.get(1), emails.get(1), phoneNumbers.get(1));
		userBean.addIdentity(bartId, identity.get(1));
		
		lisaId = userBean.create(names.get(2), emails.get(2), phoneNumbers.get(2));
		userBean.addIdentity(lisaId, identity.get(2));
		
		
		// Add nodes
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		ifiId = node.create("ifi", "Department of Informatics", matnatId);
		
		System.err.println("ifiId:" + ifiId);
				
		long testId = courseNode.getIdFromPath(new NodePath("uio.matnat.ifi.inf1000", "\\."));
		
		System.err.println("inf1000 id:" + testId);
		
		//inf1000 = courseNode.create("inf1000", "Programmering intro", ifiId);
		inf1000 = courseNode.create("inf1000", "Programmering intro", matnatId);
		
		
		inf1000Spring09 = courseNode.create("spring2009", "INF1000 spring2009", inf1000);
		inf1000Fall09 = courseNode.create("fall2009", "INf1000 fall 2009", inf1000);
		
		
		
		
		// Create some test users
				
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
		

	@After
	public void tearDown() throws NamingException, NoSuchObjectException {
						
		for(long nodeId: node.getToplevelNodes()) {
			System.err.println("Remove toplevel node:" + nodeId);
			node.remove(nodeId);
		}
		
		for(long userId: userBean.getUsers()) {
			System.err.println("Remove user:" + userId);
			userBean.remove(userId);
		}
	}


	@Test
	public void getActivePeriods() {
			//		inf1000
		//public List getActivePeriods()
	}
	
	@Test
	public void getPeriods() throws NamingException, NoSuchObjectException, UnauthorizedException, NoSuchUserException {
	
		List<StudentPeriod> periods;
				
		periodNode.addStudent(inf1000Spring09, homerId);
		periods = homer.getPeriods();
		assertEquals(1, periods.size());
		
		periodNode.addStudent(inf1000Fall09, homerId);
		periods = homer.getPeriods();
		assertEquals(2, periods.size());
	}
	
}
