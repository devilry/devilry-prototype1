package org.devilry.clientapi;


import javax.naming.NamingException;

import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.junit.Before;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AbstractDeliveryClientAPITst extends AbstractNodeClientAPITst {
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
		CourseNodeCommon courseNode = connection.getCourseNode();
		long inf1000Id = courseNode.create("inf1000", "Object oriented programming", matnatId);
		
		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		// Period
		periodNode = connection.getPeriodNode();
		periodId = periodNode.create("spring09", "Spring 2009", start.getTime(), end.getTime(), inf1000Id);
		periodId2 = periodNode.create("fall09", "Fall 2009", start.getTime(), end.getTime(), inf1000Id);
		
		Calendar deadline = new GregorianCalendar(2009, 05, 17);
		
		// Assignment
		assignmentNode = connection.getAssignmentNode();
		assignmentId = assignmentNode.create("Oblig1", "Obligatory assignment 1", deadline.getTime(), periodId);
		
		deadline = new GregorianCalendar(2009, 06, 17);
		assignmentId2 = assignmentNode.create("Oblig2", "Obligatory assignment 2", deadline.getTime(), periodId);
		
		deadline = new GregorianCalendar(2009, 07, 17);
		assignmentId2 = assignmentNode.create("Oblig1", "Obligatory assignment 1", deadline.getTime(), periodId2);
	}		
}
