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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class ExaminerAssignmentCommonTest extends UserAssignmentCommonTest {
	
	Examiner homer;
	Examiner bart, lisa;
	
	ExaminerPeriod period;
	ExaminerPeriod period2;
	
	ExaminerAssignment assignment;
				
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		super.setUp();
				
		period = new ExaminerPeriod(inf1000Spring09, connection);
		period2 = new ExaminerPeriod(inf1000Fall09, connection);
				
		assignment = new ExaminerAssignment(assignmentId, connection); 
		// Create some test users
				
		homer = new Examiner(homerId, connection);
		bart = new Examiner(bartId, connection);
		lisa = new Examiner(lisaId, connection);
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
		delivery.addExaminer(deliveryId, homerId);
		
		assertEquals(1, assignment.getDeliveries().size());
		assertEquals(deliveryId, assignment.getDeliveries().get(0).deliveryId);
				
		long delivery2Id = delivery.create(assignmentId);
		delivery.addExaminer(delivery2Id, homerId);
		
		List<ExaminerDelivery> deliveries = assignment.getDeliveries();
		
		assertEquals(2, deliveries.size());
		
		for (ExaminerDelivery d : deliveries) {
			long val = d.deliveryId;
			assertTrue(val == deliveryId || val == delivery2Id);
		}
	}
	
	@Test
	public void getDeadline() throws NoSuchObjectException, NamingException {
		assertEquals(assignmentDeadline.getTime(), assignment.getDeadline());
	}
}
