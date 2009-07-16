package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public abstract class AdminDeliveryCandidateCommonTest extends UserDeliveryCandidateCommonTest {
			
	Student homer;
	Student bart, lisa;
	
	AdminPeriod period;
	AdminPeriod period2;
	
	AdminAssignment assignment;
	AdminDelivery delivery;
	AdminDeliveryCandidate deliveryCandidate;
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		super.setUp();
				
		period = new AdminPeriod(inf1000Spring09, connection);
		period2 = new AdminPeriod(inf1000Fall09, connection);
				
		assignment = new AdminAssignment(assignmentId, connection); 
		
		delivery = new AdminDelivery(deliveryId, connection);
		deliveryCandidate = new AdminDeliveryCandidate(deliveryCandidateId, connection);
		
		// Create some test users
				
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
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
