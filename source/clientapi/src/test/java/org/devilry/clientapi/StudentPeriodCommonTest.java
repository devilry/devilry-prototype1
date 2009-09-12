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

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public abstract class StudentPeriodCommonTest extends UserPeriodCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	StudentPeriod period;
	StudentPeriod period2;
				
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		
		period = new StudentPeriod(inf1000Spring09, connection);
		period2 = new StudentPeriod(inf1000Fall09, connection);
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
	
	@Test
	public void getPath() throws NoSuchObjectException, NamingException, InvalidNameException, UnauthorizedException {
		NodePath path = period.getPath();
		
		NodePath check = new NodePath(new String[]{"uio", "matnat", "ifi", "inf1000", "spring2009"});
		assertTrue(path.equals(check));
	}
	
	@Test
	public void getAssignments() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
		// Collection<StudentAssignment> getAssignments()
		
		// Add some assignments
		
		assertEquals(0, period.getAssignments().size());
		
		Calendar deadline = new GregorianCalendar(2009, 00, 01, 10, 15);
		
		long ass1 = assignmentNode.create("oblig1", "Obligatory assignment 1", deadline.getTime(), inf1000Spring09);
		long ass2 = assignmentNode.create("oblig2", "Obligatory assignment 2", deadline.getTime(), inf1000Spring09);
				
		Collection<StudentAssignment> assignments = period.getAssignments();
		
		assertEquals(2, assignments.size());
		
		for (StudentAssignment s : assignments) {
			long val = s.assignmentId;
			assertTrue(val == ass1 || val == ass2);
		}
	}
}
