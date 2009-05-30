package org.devilry.core.session.dao;

import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


//public class FileImplTest extends AbstractDeliveryDaoTst {
//
//	FileMetaRemote fileMeta;
//	DeliveryCandidateRemote deliveryCandidate;
//	long fileId;
//
//	@Before
//	public void setUp() throws NamingException {
//		setupEjbContainer();
//		fileMeta = getRemoteBean(FileImpl.class);
//		deliveryCandidate = getRemoteBean(DeliveryCandidateImpl.class);
//		long deliveryCandidateId = delivery.addDeliveryCandidate();
//		deliveryCandidate.init(deliveryCandidateId);
//		fileId = deliveryCandidate.addFile("test.txt");
//		fileMeta.init(fileId);
//	}
//
//	@After
//	public void tearDown() {
//	}
//
//	@Test
//	public void getId() {
//		assertEquals(fileId, fileMeta.getId());
//	}
//
//	@Test
//	public void getDeliveryCandidateId() {
//		assertEquals(deliveryCandidate.getId(), fileMeta.getDeliveryCandidateId());
//	}
//
//	@Test
//	public void getFilePath() {
//		assertEquals("test.txt", fileMeta.getFilePath());
//	}
//
//	@Test
//	public void readwrite() {
//		fileMeta.write("a".getBytes());
//		fileMeta.write("b".getBytes());
//		fileMeta.write("c".getBytes());
//		assertEquals("a", new String(fileMeta.read()));
//		assertEquals("b", new String(fileMeta.read()));
//		assertEquals("c", new String(fileMeta.read()));
//
//		fileMeta.resetReadState();
//		assertEquals("a", new String(fileMeta.read()));
//	}
//}
