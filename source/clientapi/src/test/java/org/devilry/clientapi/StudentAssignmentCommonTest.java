package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;
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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public abstract class StudentAssignmentCommonTest {
		
	protected static DevilryConnection connection;
	
	protected long uioId, matnatId, ifiId, inf1000, inf1000Spring09, inf1000Fall09;
	
	long assignmentId;
	
	protected long homerId;
	protected long bartId;
	protected long lisaId;
	
	UserCommon userBean;
	NodeCommon node;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	AssignmentNodeCommon assignmentNode;
	DeliveryCommon delivery;
	
	Student homer;
	Student bart, lisa;
	
	StudentPeriod period;
	StudentPeriod period2;
	
	StudentAssignment assignment;
	
	Calendar assignmentDeadline;
	
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
		assignmentNode = connection.getAssignmentNode();
		delivery = connection.getDelivery();
		
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
						
		inf1000 = courseNode.create("inf1000", "Programmering intro", ifiId);
		
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		inf1000Spring09 = periodNode.create("spring2009", "INF1000 spring2009", start.getTime(), end.getTime(), inf1000);
		inf1000Fall09 = periodNode.create("fall2009", "INf1000 fall 2009", start.getTime(), end.getTime(), inf1000);
				
		assignmentDeadline = new GregorianCalendar(2009, 00, 01, 10, 15);
		
		assignmentId = assignmentNode.create("oblig1", "Obligatory assignment 1", assignmentDeadline.getTime(), inf1000Spring09);
		
		period = new StudentPeriod(inf1000Spring09, connection);
		period2 = new StudentPeriod(inf1000Fall09, connection);
		
		
		assignment = new StudentAssignment(assignmentId, connection); 
		// Create some test users
				
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
		

	@After
	public void tearDown() throws NamingException, NoSuchObjectException {
						
		for(long nodeId: node.getToplevelNodes()) {
			node.remove(nodeId);
		}	
		
		for(long userId: userBean.getUsers()) {
			userBean.remove(userId);
		}
	}

	@Test
	public void getPath() throws NoSuchObjectException, NamingException {
		NodePath path = assignment.getPath();
				
		NodePath check = new NodePath(new String[]{"uio", "matnat", "ifi", "inf1000", "spring2009", "oblig1"});
		assertTrue(path.equals(check));
	}
	
	@Test
	public void getDeliveries() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
		
		// Add some deliveries
		
		assertEquals(0, assignment.getDeliveries().size());
				
		long deliveryId = delivery.create(assignmentId);
		delivery.addStudent(deliveryId, homerId);
		
		assertEquals(1, assignment.getDeliveries().size());
		assertEquals(deliveryId, assignment.getDeliveries().get(0).getDeliveryId());
				
		long delivery2Id = delivery.create(assignmentId);
		delivery.addStudent(delivery2Id, homerId);
		
		List<StudentDelivery> deliveries = assignment.getDeliveries();
		
		assertEquals(2, deliveries.size());
		
		for (StudentDelivery d : deliveries) {
			long val = d.getDeliveryId();
			assertTrue(val == deliveryId || val == delivery2Id);
		}
	}
	
	@Test
	public void getDeadline() throws NoSuchObjectException, NamingException {
		assertEquals(assignmentDeadline.getTime(), assignment.getDeadline());
	}
}
