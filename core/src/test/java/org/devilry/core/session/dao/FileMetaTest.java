package org.devilry.core.session.dao;

import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class FileMetaTest extends AbstractSessionBeanTestHelper {

	FileMetaRemote fileMeta;
	DeliveryCandidateRemote deliveryCandidate;
	long fileId;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		fileMeta = (FileMetaRemote) getRemoteBean(FileMeta.class);
		deliveryCandidate = (DeliveryCandidateRemote) getRemoteBean(DeliveryCandidate.class);
		deliveryCandidate.init(0);
		fileId = deliveryCandidate.addFile("test.txt");
		fileMeta.init(fileId);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void getId() {
		assertEquals(fileId, fileMeta.getId());
	}

	@Test
	public void getDeliveryCandidateId() {
		assertEquals(deliveryCandidate.getId(), fileMeta.getDeliveryCandidateId());
	}

	@Test
	public void getFilePath() {
		assertEquals("test.txt", fileMeta.getFilePath());
	}
}