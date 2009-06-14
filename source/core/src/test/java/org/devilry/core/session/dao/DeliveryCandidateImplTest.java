package org.devilry.core.session.dao;

import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

import org.devilry.core.dao.DeliveryCandidateImpl;
import org.devilry.core.dao.DeliveryImpl;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryCandidateImplTest extends AbstractDeliveryDaoTst {

	DeliveryRemote delivery;
	long deliveryId;
	
	DeliveryCandidateRemote deliveryCandidate;
	long deliveryCandidateId;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		
		// Set up a delivery
		delivery = getRemoteBean(DeliveryImpl.class);
		deliveryId = delivery.create(assignmentId);
		
		// Add delivery candidate
		deliveryCandidate = getRemoteBean(DeliveryCandidateImpl.class);
		deliveryCandidateId = deliveryCandidate.create(deliveryId);
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
	
	
	@Test
	public void getTimeOfDelivery() throws InterruptedException {
		Thread.sleep(10); // To avoid now and delivery from having same timestamp with the presision provided by sql timestamp. 
		Date now = new Date();
		Date delivery = deliveryCandidate.getTimeOfDelivery(deliveryCandidateId);
		assertTrue(now.after(delivery));
	}

	@Test
	public void remove() {
		deliveryCandidate.remove(deliveryCandidateId);
		assertFalse(deliveryCandidate.exists(deliveryCandidateId));

		long did = delivery.create(assignmentId);
		long dcid = deliveryCandidate.create(deliveryId);
		node.remove(uioId);
		assertFalse(assignmentNode.exists(assignmentId));
		assertFalse(deliveryCandidate.exists(did));
		assertFalse(deliveryCandidate.exists(dcid));
	}

	@Test
	public void exists() {
		assertTrue(deliveryCandidate.exists(deliveryCandidateId));
		assertFalse(deliveryCandidate.exists(deliveryCandidateId + 1));
	}
}