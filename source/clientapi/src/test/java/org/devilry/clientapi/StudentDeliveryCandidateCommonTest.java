package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.FileMetaCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class StudentDeliveryCandidateCommonTest {
		
	protected static DevilryConnection connection;
	
	protected long uioId, matnatId, ifiId, inf1000, inf1000Spring09, inf1000Fall09;
	
	long assignmentId;
	long deliveryId;
	long deliveryCandidateId;
	
	protected long homerId;
	protected long bartId;
	protected long lisaId;
	
	UserCommon userBean;
	NodeCommon node;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	AssignmentNodeCommon assignmentNode;
	DeliveryCommon deliveryBean;
	DeliveryCandidateCommon deliveryCandidateBean;
	FileMetaCommon fileMetaBean;
	
	

	Student homer;
	Student bart, lisa;
	
	StudentPeriod period;
	StudentPeriod period2;
	
	StudentAssignment assignment;
	StudentDelivery delivery;
	StudentDeliveryCandidate deliveryCandidate;
	
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
		deliveryBean = connection.getDelivery();
		deliveryCandidateBean = connection.getDeliveryCandidate();
		fileMetaBean = connection.getFileMeta();
		
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
				
		Calendar deadline = new GregorianCalendar(2009, 00, 01, 10, 15);
		
		assignmentId = assignmentNode.create("oblig1", "Obligatory assignment 1", deadline.getTime(), inf1000Spring09);
		
		deliveryId = deliveryBean.create(assignmentId);
		deliveryBean.addStudent(deliveryId, homerId);
		
		deliveryCandidateId = deliveryCandidateBean.create(deliveryId);
		
		
		period = new StudentPeriod(inf1000Spring09, connection);
		period2 = new StudentPeriod(inf1000Fall09, connection);
				
		assignment = new StudentAssignment(assignmentId, connection); 
		
		delivery = new StudentDelivery(deliveryId, connection);
		deliveryCandidate = new StudentDeliveryCandidate(deliveryCandidateId, connection);
		
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
	public void addFile() throws NamingException, NoSuchObjectException, UnauthorizedException {
			
		assertEquals(0, deliveryCandidate.getFileCount());
		
		DevilryOutputStream out = deliveryCandidate.addFile("Testing2.txt");
		
		assertEquals(1, deliveryCandidate.getFileCount());
		
		List<DevilryInputStream> files = deliveryCandidate.getDeliveryFiles();
		assertEquals(out.fileMetaId, files.get(0).fileMetaId);
	}
		
	
	@Test
	public void getDeliveryFiles() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
		
		// Add some deliveries
				
		assertEquals(0, deliveryCandidate.getFileCount());
				
		long fileMetaId = fileMetaBean.create(deliveryCandidateId, "Testing.txt");
		
		assertEquals(1, deliveryCandidate.getFileCount());
		assertEquals(fileMetaId, deliveryCandidate.getDeliveryFiles().get(0).fileMetaId);
		
		long fileMeta2Id = fileMetaBean.create(deliveryCandidateId, "Testing2.txt");
		
		List<DevilryInputStream> files = deliveryCandidate.getDeliveryFiles();
						
		assertEquals(2, files.size());
		
		for (DevilryInputStream s : files) {
			long val = s.fileMetaId;
			assertTrue(val == fileMetaId || val == fileMeta2Id);
		}
	}
	
	
}
