package org.devilry.core.session.dao;


import javax.naming.NamingException;

import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.junit.Before;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AbstractDeliveryDaoTst extends AbstractNodeDaoTst {
	PeriodNodeCommon periodNode;
	long periodId;
	long periodId2;
	
	AssignmentNodeCommon assignmentNode;
	long assignmentId;
	long assignmentId2;
	
	
	@Before
	public void setUp() throws NamingException {
		super.setUp();
		// Course
		CourseNodeRemote courseNode = getRemoteBean(CourseNodeImpl.class);
		long inf1000Id = courseNode.create("inf1000", "Object oriented programming", matnatId);
		
		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		// Period
		periodNode = getRemoteBean(PeriodNodeImpl.class);
		periodId = periodNode.create("spring09", "Spring 2009", start.getTime(), end.getTime(), inf1000Id);
		periodId2 = periodNode.create("fall09", "Fall 2009", start.getTime(), end.getTime(), inf1000Id);
		
		Calendar deadline = new GregorianCalendar(2009, 05, 17);
		
		// Assignment
		assignmentNode = getRemoteBean(AssignmentNodeImpl.class);
		assignmentId = assignmentNode.create("oblig1", "Obligatory assignment 1", deadline.getTime(), periodId);
		
		deadline = new GregorianCalendar(2009, 06, 17);
		assignmentId2 = assignmentNode.create("oblig2", "Obligatory assignment 2", deadline.getTime(), periodId);
		
		deadline = new GregorianCalendar(2009, 07, 17);
		assignmentId2 = assignmentNode.create("oblig1", "Obligatory assignment 1", deadline.getTime(), periodId2);
	}		
}
