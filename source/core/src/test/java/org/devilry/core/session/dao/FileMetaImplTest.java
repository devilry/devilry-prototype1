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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileMetaImplTest extends AbstractDeliveryDaoTst {

	DeliveryRemote delivery;
	long deliveryId;
	DeliveryCandidateRemote deliveryCandidate;
	long deliveryCandidateId;
	FileMetaRemote fileMeta;
	FileDataBlockRemote fileDataBlock;
	long fileMetaId;

	@Before
	@Override
	public void setUp() throws NamingException {
		super.setUp();

		// Set up a delivery
		delivery = getRemoteBean(DeliveryImpl.class);
		deliveryId = delivery.create(assignmentId);

		fileDataBlock = getRemoteBean(FileDataBlockImpl.class);


		// Add delivery candidate
		deliveryCandidate = getRemoteBean(DeliveryCandidateImpl.class);
		deliveryCandidateId = deliveryCandidate.create(deliveryId);

		// Add file meta data
		fileMeta = getRemoteBean(FileMetaImpl.class);
		fileMetaId = fileMeta.create(deliveryCandidateId, "test.txt");
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

		fileDataBlock = getRemoteBean(FileDataBlockImpl.class);
		long id1 = fileDataBlock.create(fileMetaId, "Laban".getBytes());
		long id2 = fileDataBlock.create(fileMetaId, "Tullball".getBytes());
		long id3 = fileDataBlock.create(fileMetaId, "Skrot".getBytes());

		List<Long> ids = fileMeta.getFileDataBlocks(fileMetaId);

		assertEquals(3, ids.size());
		assertTrue(ids.contains(id1));
		assertTrue(ids.contains(id2));
		assertTrue(ids.contains(id3));
		
		assertEquals(id1, (long) ids.get(0));
		assertEquals(id2, (long) ids.get(1));
		assertEquals(id3, (long) ids.get(2));
	}

	@Test
	public void getSize() throws NamingException {

		byte[] data1 = "sdflhkfjskdfbaskdfbasdkfbadsklf".getBytes();
		byte[] data2 = "aadbfaskdfbaskdfbasdfdddddddddd".getBytes();
		byte[] data3 = "igasduyasdasdiuiibhassbadabiabdkfbadsklf".getBytes();

		fileDataBlock.create(fileMetaId, data1);
		fileDataBlock.create(fileMetaId, data2);
		fileDataBlock.create(fileMetaId, data3);

		int size = fileMeta.getSize(fileMetaId);

		assertEquals(size, data1.length + data2.length + data3.length);
	}

	@Test
	public void remove() {

		// Remove a single file-meta and it's children.
		long dataId1 = fileDataBlock.create(fileMetaId, "test".getBytes());
		long dataId2 = fileDataBlock.create(fileMetaId, "ing".getBytes());
		fileMeta.remove(fileMetaId);
		assertFalse(fileMeta.exists(fileMetaId));
		assertFalse(fileDataBlock.exists(dataId1));
		assertFalse(fileDataBlock.exists(dataId2));

		// Remove node works to?
		long id = fileMeta.create(deliveryCandidateId, "hello.txt");
		long id2 = fileMeta.create(deliveryCandidateId, "hello2.txt");
		long dataId3 = fileDataBlock.create(id, "test".getBytes());
		node.remove(uioId);
		assertFalse(fileMeta.exists(id));
		assertFalse(fileMeta.exists(id2));
		assertFalse(fileDataBlock.exists(dataId3));
	}

	@Test
	public void exists() {
		assertTrue(fileMeta.exists(fileMetaId));
		assertFalse(fileMeta.exists(fileMetaId + 1));
	}
}
