package org.devilry.core.session.dao;

import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class FileDataBlockImplTest extends AbstractDeliveryDaoTst {

	DeliveryRemote delivery;
	long deliveryId;
	
	DeliveryCandidateRemote deliveryCandidate;
	long deliveryCandidateId;
	
	FileMetaRemote fileMeta;
	long fileMetaId;
	
	FileDataBlockRemote fileDataBlock;	
	long fileDataBlockId;
	long fileDataBlockId2;
	
	byte [] data = "ausduudfudsfusfusfusdusuf".getBytes();
	byte [] data2 = "uuququqqwquwqwqvqvnqbqxxxxxxx".getBytes();
	
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
		
		// Add data blocks
		fileDataBlock = getRemoteBean(FileDataBlockImpl.class);
		fileDataBlockId = fileDataBlock.create(fileMetaId, data);
		fileDataBlockId2 = fileDataBlock.create(fileMetaId, data2);
				
	}

	@After
	public void tearDown() {
		periodNode.remove(deliveryId);
	}
	
	@Test
	public void getFileMeta() {
		assertEquals(fileMetaId, fileDataBlock.getFileMeta(fileDataBlockId));
	}
		
	
	@Test
	public void getFileData() {
		assertTrue(Arrays.equals(data, fileDataBlock.getFileData(fileDataBlockId)));
		assertTrue(Arrays.equals(data2, fileDataBlock.getFileData(fileDataBlockId2)));
	}
		
	
	@Test
	public void getSize() {
		assertEquals(data.length, fileDataBlock.getSize(fileDataBlockId));
	}
	
	
	/*
	@Test
	public void readwrite() {
		fileMeta.write("a".getBytes());
		fileMeta.write("b".getBytes());
		fileMeta.write("c".getBytes());
		assertEquals("a", new String(fileMeta.read()));
		assertEquals("b", new String(fileMeta.read()));
		assertEquals("c", new String(fileMeta.read()));

		fileMeta.resetReadState();
		assertEquals("a", new String(fileMeta.read()));
	}
	*/
	
}
