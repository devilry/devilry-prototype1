package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public abstract class AdminAssignmentCommonTest extends UserAssignmentCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	AdminPeriod period;
	AdminPeriod period2;
	
	AdminAssignment assignment;
				
	
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		super.setUp();
				
		period = new AdminPeriod(inf1000Spring09, connection);
		period2 = new AdminPeriod(inf1000Fall09, connection);
				
		assignment = new AdminAssignment(assignmentId, connection); 
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
		
	@Test
	public void getPath() throws NoSuchObjectException, NamingException, InvalidNameException {
		NodePath path = assignment.getPath();
				
		NodePath check = new NodePath(new String[]{"uio", "matnat", "ifi", "inf1000", "spring2009", "oblig1"});
		assertTrue(path.equals(check));
	}
	
	@Test
	public void getDeliveries() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
				
		assertEquals(0, assignment.getDeliveries().size());
				
		long deliveryId = delivery.create(assignmentId);
		delivery.addStudent(deliveryId, homerId);
		
		// Not connected to student
		long delivery2Id = delivery.create(assignmentId);
		
		assertEquals(2, assignment.getDeliveries().size());
				
		long delivery3Id = delivery.create(assignmentId);
		delivery.addStudent(delivery3Id, homerId);
		
		List<AdminDelivery> deliveries = assignment.getDeliveries();
		
		assertEquals(3, deliveries.size());
		
		for (AdminDelivery d : deliveries) {
			long val = d.deliveryId;
			assertTrue(val == deliveryId || val == delivery2Id || val == delivery3Id);
		}
	}
	
	@Test
	public void addDelivery() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
				
		assertEquals(0, assignment.getDeliveries().size());
				
		AdminDelivery adminDelivery = assignment.addDelivery();
		adminDelivery.addStudent(homerId);
		
		assertEquals(1, assignment.getDeliveries().size());
		assertEquals(adminDelivery.deliveryId, assignment.getDeliveries().get(0).deliveryId);
		
		AdminDelivery adminDelivery2 = assignment.addDelivery();
		adminDelivery2.addStudent(homerId);
		
		List<AdminDelivery> deliveries = assignment.getDeliveries();
		
		assertEquals(2, deliveries.size());
		
		for (AdminDelivery d : deliveries) {
			long val = d.deliveryId;
			assertTrue(val == adminDelivery.deliveryId || val == adminDelivery2.deliveryId);
		}
	}
	
	
	
	@Test
	public void getDeadline() throws NoSuchObjectException, NamingException {
		assertEquals(assignmentDeadline.getTime(), assignment.getDeadline());
	}
}
