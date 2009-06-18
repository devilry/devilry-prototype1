package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
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

public abstract class ExaminerDeliveryCommonTest extends UserDeliveryCommonTest {
	
	Examiner homer;
	Examiner bart, lisa;
	
	ExaminerPeriod period;
	ExaminerPeriod period2;
	
	ExaminerAssignment assignment;
	ExaminerDelivery delivery;
		
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		
		period = new ExaminerPeriod(inf1000Spring09, connection);
		period2 = new ExaminerPeriod(inf1000Fall09, connection);
				
		assignment = new ExaminerAssignment(assignmentId, connection); 
		
		delivery = new ExaminerDelivery(deliveryId, connection);
		
		// Create some test users
		homer = new Examiner(homerId, connection);
		bart = new Examiner(bartId, connection);
		lisa = new Examiner(lisaId, connection);
	}
	
	
	@Test
	public void getDeliveryCandidates() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
		
		// Add some deliveries
		
		assertEquals(0, delivery.getDeliveryCandidates().size());
				
		long candidateId = deliveryCandidateBean.create(deliveryId);
		
		assertEquals(1, delivery.getDeliveryCandidates().size());
		assertEquals(candidateId, delivery.getDeliveryCandidates().get(0).deliveryCandidateId);
		
		long candidate2Id = deliveryCandidateBean.create(deliveryId);
		
		List<ExaminerDeliveryCandidate> candidates = delivery.getDeliveryCandidates();
				
		assertEquals(2, candidates.size());
		
		for (ExaminerDeliveryCandidate c : candidates) {
			long val = c.deliveryCandidateId;
			assertTrue(val == candidateId || val == candidate2Id);
		}
	}
	
	@Test
	public void getStatus() throws NamingException {
		deliveryBean.setStatus(deliveryId, 2);
		assertEquals(2, delivery.getStatus());
	}
	
	@Test
	public void setStatus() throws NamingException {
		// Ignored
	}
}
