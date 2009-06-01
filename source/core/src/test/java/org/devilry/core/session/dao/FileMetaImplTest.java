package org.devilry.core.session.dao;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.dao.DeliveryCandidateImpl;
import org.devilry.core.dao.DeliveryImpl;
import org.devilry.core.dao.FileDataBlockImpl;
import org.devilry.core.dao.FileMetaImpl;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.FileDataBlockRemote;
import org.devilry.core.daointerfaces.FileMetaRemote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class FileMetaImplTest extends AbstractDeliveryDaoTst {

	DeliveryRemote delivery;
	long deliveryId;
	
	DeliveryCandidateRemote deliveryCandidate;
	long deliveryCandidateId;
	
	FileMetaRemote fileMeta;
	long fileMetaId;
	
	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		
		// Set up a delivery
		delivery = getRemoteBean(DeliveryImpl.class);
		deliveryId = delivery.create(assignmentId);
		
		// Add delivery candidate
		deliveryCandidate = getRemoteBean(DeliveryCandidateImpl.class);
		deliveryCandidateId = deliveryCandidate.create(deliveryId);
		
		// Add file meta data
		fileMeta = getRemoteBean(FileMetaImpl.class);
		fileMetaId = fileMeta.create(deliveryCandidateId, "test.txt");
	}

	@After
	public void tearDown() {
		periodNode.remove(uioId);
	}

	@Test
	public void getDeliveryCandidate() {
		assertEquals(deliveryCandidateId, fileMeta.getDeliveryCandidate(fileMetaId));
	}

	@Test
	public void getFilePath() {
		assertEquals("test.txt", fileMeta.getFilePath(fileMetaId));
	}


	@Test
	public void getFileDataBlocks() throws NamingException {
		
		FileDataBlockRemote fileDataBlock = getRemoteBean(FileDataBlockImpl.class);
		long id1 = fileDataBlock.create(fileMetaId, "Laban".getBytes());
		long id2 = fileDataBlock.create(fileMetaId, "Tullball".getBytes());
		long id3 = fileDataBlock.create(fileMetaId, "Skrot".getBytes());
		
		List<Long> ids = fileMeta.getFileDataBlocks(fileMetaId);
		
		assertEquals(3, ids.size());
		assertTrue(ids.contains(id1));
		assertTrue(ids.contains(id2));
		assertTrue(ids.contains(id3));
	}
	

	@Test
	public void getSize() throws NamingException {

		byte [] data1 = "sdflhkfjskdfbaskdfbasdkfbadsklf".getBytes();
		byte [] data2 = "aadbfaskdfbaskdfbasdfdddddddddd".getBytes();
		byte [] data3 = "igasduyasdasdiuiibhassbadabiabdkfbadsklf".getBytes();
		
		FileDataBlockRemote fileDataBlock = getRemoteBean(FileDataBlockImpl.class);
		long id1 = fileDataBlock.create(fileMetaId, data1);
		long id2 = fileDataBlock.create(fileMetaId, data2);
		long id3 = fileDataBlock.create(fileMetaId, data3);
		
		int size = fileMeta.getSize(fileMetaId);
	
		assertEquals(size, data1.length + data2.length + data3.length);
	}
	
}
