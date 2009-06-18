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
import org.devilry.core.daointerfaces.FileMetaCommon;
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

public abstract class ExaminerDeliveryCandidateCommonTest extends UserDeliveryCandidateCommonTest {
			
	Examiner homer;
	Examiner bart, lisa;
	
	ExaminerPeriod period;
	ExaminerPeriod period2;
	
	ExaminerAssignment assignment;
	ExaminerDelivery delivery;
	ExaminerDeliveryCandidate deliveryCandidate;
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		super.setUp();
				
		period = new ExaminerPeriod(inf1000Spring09, connection);
		period2 = new ExaminerPeriod(inf1000Fall09, connection);
				
		assignment = new ExaminerAssignment(assignmentId, connection); 
		
		delivery = new ExaminerDelivery(deliveryId, connection);
		deliveryCandidate = new ExaminerDeliveryCandidate(deliveryCandidateId, connection);
		
		// Create some test users
				
		homer = new Examiner(homerId, connection);
		bart = new Examiner(bartId, connection);
		lisa = new Examiner(lisaId, connection);
	}
			
	
	@Test
	public void getDeliveryFiles() throws NoSuchObjectException, UnauthorizedException, NamingException, PathExistsException, InvalidNameException {
		
		// Add some deliveries
				
		assertEquals(0, deliveryCandidate.getFileCount());
				
		long fileMetaId = fileMetaBean.create(deliveryCandidateId, "Testing.txt");
		
		assertEquals(1, deliveryCandidate.getFileCount());
		assertEquals(fileMetaId, deliveryCandidate.getDeliveryFiles().get(0).fileMetaId);
		
		long fileMeta2Id = fileMetaBean.create(deliveryCandidateId, "Testing2.txt");
		
		List<DevilryInputStream> files = deliveryCandidate.getDeliveryFiles();
						
		assertEquals(2, files.size());
		
		for (DevilryInputStream s : files) {
			long val = s.fileMetaId;
			assertTrue(val == fileMetaId || val == fileMeta2Id);
		}
	}
}
