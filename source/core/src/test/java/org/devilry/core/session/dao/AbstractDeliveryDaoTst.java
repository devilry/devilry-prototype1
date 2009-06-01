package org.devilry.core.session.dao;


import javax.naming.NamingException;

import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.AssignmentNodeRemote;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.CourseNodeRemote;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.NodeRemote;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.dao.PeriodNodeRemote;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AbstractDeliveryDaoTst extends AbstractDaoTst {

	long uioId, matnatId;
		
	PeriodNodeRemote periodNode;
	long periodId;
	long periodId2;
	
	AssignmentNodeRemote assignmentNode;
	long assignmentId;
	long assignmentId2;
	
	
	protected void setupEjbContainer() throws NamingException {
		super.setupEjbContainer();
		
		// Nodes
		NodeRemote node = getRemoteBean(NodeImpl.class);
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		
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
		assignmentId = assignmentNode.create("Oblig1", "Obligatory assignment 1", deadline.getTime(), periodId);
		
		deadline = new GregorianCalendar(2009, 06, 17);
		assignmentId2 = assignmentNode.create("Oblig2", "Obligatory assignment 2", deadline.getTime(), periodId);
		
		deadline = new GregorianCalendar(2009, 07, 17);
		assignmentId2 = assignmentNode.create("Oblig1", "Obligatory assignment 1", deadline.getTime(), periodId2);
		
		
		
//		TreeManagerRemote tm = getRemoteBean(TreeManagerImpl.class);
		
//		long id = tm.addNode("uio", "Universitet i Oslo");
//		id = tm.addCourseNode("inf1000", "INF1000", "First programming course.", id);
//		id = tm.addPeriodNode("spring2009", new GregorianCalendar(2009, 1, 1).getTime(),
//				new GregorianCalendar(2009, 6, 15).getTime(), id);
		
//		assignmentId = tm.addAssignmentNode("Oblig1", "Obligatory assignment 1", id);
//		AssignmentNodeRemote assignment = getRemoteBean(AssignmentNodeImpl.class);
//		assignment.init(assignmentId);
//		delivery = getRemoteBean(DeliveryImpl.class);
//		delivery.init(assignment.addDelivery());
	}
}
