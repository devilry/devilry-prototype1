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

public abstract class StudentDeliveryCandidateCommonTest extends UserDeliveryCandidateCommonTest {
			
	Student homer;
	Student bart, lisa;
	
	StudentPeriod period;
	StudentPeriod period2;
	
	StudentAssignment assignment;
	StudentDelivery delivery;
	StudentDeliveryCandidate deliveryCandidate;
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		super.setUp();
				
		period = new StudentPeriod(inf1000Spring09, connection);
		period2 = new StudentPeriod(inf1000Fall09, connection);
				
		assignment = new StudentAssignment(assignmentId, connection); 
		
		delivery = new StudentDelivery(deliveryId, connection);
		deliveryCandidate = new StudentDeliveryCandidate(deliveryCandidateId, connection);
		
		// Create some test users
				
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}
			
	@Test
	public void addFile() throws NamingException, NoSuchObjectException, UnauthorizedException {
			
		assertEquals(0, deliveryCandidate.getFileCount());
		
		DevilryOutputStream out = deliveryCandidate.addFile("Testing2.txt");
		
		assertEquals(1, deliveryCandidate.getFileCount());
		
		List<DevilryInputStream> files = deliveryCandidate.getDeliveryFiles();
		assertEquals(out.fileMetaId, files.get(0).fileMetaId);
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
