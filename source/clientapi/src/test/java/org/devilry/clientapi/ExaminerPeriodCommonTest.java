package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public abstract class ExaminerPeriodCommonTest extends UserPeriodCommonTest {

	Examiner homer;
	Examiner bart, lisa;
	
	ExaminerPeriod period;
	ExaminerPeriod period2;
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		
		period = new ExaminerPeriod(inf1000Spring09, connection);
		period2 = new ExaminerPeriod(inf1000Fall09, connection);
		
		// Create some test users
				
		homer = new Examiner(homerId, connection);
		bart = new Examiner(bartId, connection);
		lisa = new Examiner(lisaId, connection);
	}
	

	@Test
	public void getPath() throws NoSuchObjectException, NamingException {
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
				
		Collection<ExaminerAssignment> assignments = period.getAssignments();
		
		assertEquals(2, assignments.size());
		
		for (ExaminerAssignment s : assignments) {
			long val = s.assignmentId;
			assertTrue(val == ass1 || val == ass2);
		}
	}
}
