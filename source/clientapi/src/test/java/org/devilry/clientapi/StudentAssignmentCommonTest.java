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

public abstract class StudentAssignmentCommonTest extends UserAssignmentCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	StudentPeriod period;
	StudentPeriod period2;
	
	StudentAssignment assignment;
				
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		super.setUp();
				
		period = new StudentPeriod(inf1000Spring09, connection);
		period2 = new StudentPeriod(inf1000Fall09, connection);
				
		assignment = new StudentAssignment(assignmentId, connection); 
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
		
	@Test
	public void getPath() throws NoSuchObjectException, NamingException, InvalidNameException, UnauthorizedException {
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
		delivery.create(assignmentId);
		
		assertEquals(1, assignment.getDeliveries().size());
		assertEquals(deliveryId, assignment.getDeliveries().get(0).deliveryId);
				
		long delivery2Id = delivery.create(assignmentId);
		delivery.addStudent(delivery2Id, homerId);
		
		List<StudentDelivery> deliveries = assignment.getDeliveries();
		
		assertEquals(2, deliveries.size());
		
		for (StudentDelivery d : deliveries) {
			long val = d.deliveryId;
			assertTrue(val == deliveryId || val == delivery2Id);
		}
	}
	
	@Test
	public void getDeadline() throws NoSuchObjectException, NamingException, UnauthorizedException {
		assertEquals(assignmentDeadline.getTime(), assignment.getDeadline());
	}
}
