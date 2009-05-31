package org.devilry.core.session.dao;

import java.util.List;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryCandidateImplTest extends AbstractDeliveryDaoTst {

	DeliveryCandidateRemote deliveryCandidate;
	long deliveryCandidateId;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		deliveryCandidate = getRemoteBean(DeliveryCandidateImpl.class);
		deliveryCandidateId = deliveryCandidate.create();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void addFile() {
		
		long id1 = deliveryCandidate.addFile(deliveryCandidateId, "test.txt");
		long id2 = deliveryCandidate.addFile(deliveryCandidateId, "test2.txt");
		assertFalse(id1 == id2);
		
		List<Long> fileIds = deliveryCandidate.getFiles(deliveryCandidateId);
		assertEquals(2, fileIds.size());
	}


	@Test
	public void getFiles() throws NamingException {
	
		long id1 = deliveryCandidate.addFile(deliveryCandidateId, "b");
		long id2 = deliveryCandidate.addFile(deliveryCandidateId, "a");
		long id3 = deliveryCandidate.addFile(deliveryCandidateId, "c");
		List<Long> fileIds = deliveryCandidate.getFiles(deliveryCandidateId);
		assertEquals(3, fileIds.size());
		
		assertTrue(fileIds.contains(id1));
		assertTrue(fileIds.contains(id2));
		assertTrue(fileIds.contains(id3));
	}
	
	
	@Test
	public void getAndSetStatus() {
		deliveryCandidate.setStatus(deliveryCandidateId, 0);
		assertEquals(0, deliveryCandidate.getStatus(deliveryCandidateId));
	}
	
}