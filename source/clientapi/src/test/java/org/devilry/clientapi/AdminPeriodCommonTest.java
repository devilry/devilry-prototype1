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

public abstract class AdminPeriodCommonTest extends UserPeriodCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	AdminPeriod period;
	AdminPeriod period2;
				
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		
		period = new AdminPeriod(inf1000Spring09, connection);
		period2 = new AdminPeriod(inf1000Fall09, connection);
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
	
	@Test
	public void getPath() throws NoSuchObjectException, NamingException, InvalidNameException {
		NodePath path = period.getPath();
		
		NodePath check = new NodePath(new String[]{"uio", "matnat", "ifi", "inf1000", "spring2009"});
		assertTrue(path.equals(check));
	}
	
	@Test
	public void getAssignments() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
	
		// Add some assignments
		
		assertEquals(0, period.getAssignments().size());
		
		Calendar deadline = new GregorianCalendar(2009, 00, 01, 10, 15);
		
		long ass1 = assignmentNode.create("oblig1", "Obligatory assignment 1", deadline.getTime(), inf1000Spring09);
		long ass2 = assignmentNode.create("oblig2", "Obligatory assignment 2", deadline.getTime(), inf1000Spring09);
				
		Collection<AdminAssignment> assignments = period.getAssignments();
		
		assertEquals(2, assignments.size());
		
		for (AdminAssignment s : assignments) {
			long val = s.assignmentId;
			assertTrue(val == ass1 || val == ass2);
		}
	}
	
	@Test
	public void setPeriodName() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		long periodId = periodNode.create("summmer09", "Summer games", start.getTime(), end.getTime(), inf1000);
		AdminPeriod adminPeriod = new AdminPeriod(periodId, connection);		
		
		String newPeriodName = "summer10";
		adminPeriod.setPeriodName(newPeriodName);
		
		assertEquals(newPeriodName, adminPeriod.getName());
	}
	
	@Test
	public void setPeriodDisplayName() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		long periodId = periodNode.create("summmer09", "Summer games", start.getTime(), end.getTime(), inf1000);
		AdminPeriod adminPeriod = new AdminPeriod(periodId, connection);		
		
		String newPeriodDisplayName = "summer10";
		adminPeriod.setPeriodDisplayName(newPeriodDisplayName);
		
		assertEquals(newPeriodDisplayName, adminPeriod.getDisplayName());
	}
	
	
	
	@Test
	public void changePeriodStartDate() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		long periodId = periodNode.create("summmer09", "Summer games", start.getTime(), end.getTime(), inf1000);
		AdminPeriod adminPeriod = new AdminPeriod(periodId, connection);
		
		Calendar newStartTime = new GregorianCalendar(2010, 00, 02, 04, 15);
		adminPeriod.setPeriodStartDate(newStartTime.getTime());
		assertEquals(newStartTime.getTime(), adminPeriod.getPeriodStartDate());
	}
	
	@Test
	public void changePeriodEndDate() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		long periodId = periodNode.create("summmer09", "Summer games", start.getTime(), end.getTime(), inf1000);
		AdminPeriod adminPeriod = new AdminPeriod(periodId, connection);
		
		Calendar newEndTime = new GregorianCalendar(2010, 00, 02, 04, 15);
		adminPeriod.setPeriodEndDate(newEndTime.getTime());
		assertEquals(newEndTime.getTime(), adminPeriod.getPeriodEndDate());
	}
	
}
