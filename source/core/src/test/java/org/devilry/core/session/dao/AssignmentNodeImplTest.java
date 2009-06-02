package org.devilry.core.session.dao;

import java.util.*;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.DeliveryImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.session.*;

public class AssignmentNodeImplTest extends NodeImplTest {
	
	PeriodNodeRemote periodNode;
	long periodId;
	
	AssignmentNodeRemote assignmentNode;
	long assignmentId;
	Calendar deadline;
		
	@Before
	public void setUp() throws NamingException {
		super.setUp();

		// Add course
		CourseNodeRemote courseNode = getRemoteBean(CourseNodeImpl.class);
		long inf1000Id = courseNode.create("inf1000", "Object oriented programming", matnatId);

		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		// Add period
		periodNode = getRemoteBean(PeriodNodeImpl.class);
		periodId = periodNode.create("fall09", "Fall 2009", start.getTime(), end.getTime(), inf1000Id);
		
		deadline = new GregorianCalendar(2009, 07, 20);
				
		// Add assignment
		assignmentNode = getRemoteBean(AssignmentNodeImpl.class);
		assignmentId = assignmentNode.create("Oblig1", "Obligatory assignemnt 1", deadline.getTime(), periodId);
	}

	@Test
	public void getDeliveries() throws NamingException {
		
		DeliveryRemote delivery = getRemoteBean(DeliveryImpl.class);
		long deliveryId = delivery.create(assignmentId);
		long deliveryId2 = delivery.create(assignmentId);
		long deliveryId3 = delivery.create(assignmentId);
		
		List<Long> ids = assignmentNode.getDeliveries(assignmentId);
		assertEquals(3, ids.size());
		assertTrue(ids.contains(deliveryId));
		assertTrue(ids.contains(deliveryId2));
		assertTrue(ids.contains(deliveryId3));
	}
	
	@Test
	public void getDeliveriesWhereIsStudent() throws NamingException {
		
		DeliveryRemote delivery = getRemoteBean(DeliveryImpl.class);
		long deliveryId = delivery.create(assignmentId);
		long deliveryId2 = delivery.create(assignmentId);
				
		delivery.addStudent(deliveryId, homerId);
		delivery.addStudent(deliveryId2, homerId);
		
		long bartId = userBean.create("Bart Simpson", "bart@doh.com", "1232");
		userBean.addIdentity(bartId, "Bart");
		
		delivery.addStudent(deliveryId, bartId);
		
		List<Long> ids = assignmentNode.getDeliveriesWhereIsStudent(assignmentId);
		assertEquals(2, ids.size());
		assertTrue(ids.contains(deliveryId));
		assertTrue(ids.contains(deliveryId2));
	}
	
	@Test
	public void getDeliveriesWhereIsExaminer() throws NamingException {
		
		DeliveryRemote delivery = getRemoteBean(DeliveryImpl.class);
		long deliveryId = delivery.create(assignmentId);
		long deliveryId2 = delivery.create(assignmentId);
				
		delivery.addExaminer(deliveryId, homerId);
		delivery.addExaminer(deliveryId2, homerId);
		
		long bartId = userBean.create("Bart Simpson", "bart@doh.com", "1232");
		userBean.addIdentity(bartId, "Bart");
		
		delivery.addStudent(deliveryId, bartId);
		
		List<Long> ids = assignmentNode.getDeliveriesWhereIsExaminer(assignmentId);
		assertEquals(2, ids.size());
		assertTrue(ids.contains(deliveryId));
		assertTrue(ids.contains(deliveryId2));
	}
	
	
	@Test
	public void getDeadline() throws NamingException {
		assertEquals(deadline.getTime(), assignmentNode.getDeadline(assignmentId));
	}
	
	@Test
	public void setDeadline() throws NamingException {
		Calendar newDeadline = new GregorianCalendar(2009, 05, 16);
		
		assignmentNode.setDeadline(assignmentId, newDeadline.getTime());
		assertEquals(newDeadline.getTime(), assignmentNode.getDeadline(assignmentId));
	}
	
	@Test
	public void remove() {
		super.remove();
		assertFalse(node.exists(assignmentId));
	}
}