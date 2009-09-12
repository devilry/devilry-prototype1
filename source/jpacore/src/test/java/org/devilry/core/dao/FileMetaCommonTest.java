package org.devilry.core.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.dao.DeliveryCandidateImpl;
import org.devilry.core.dao.DeliveryImpl;
import org.devilry.core.dao.FileDataBlockImpl;
import org.devilry.core.dao.FileMetaImpl;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.FileDataBlockCommon;
import org.devilry.core.daointerfaces.FileDataBlockRemote;
import org.devilry.core.daointerfaces.FileMetaCommon;
import org.devilry.core.daointerfaces.FileMetaRemote;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public abstract class FileMetaCommonTest {
	
	protected static CoreTestHelper testHelper;
	
	NodeCommon node;
	UserCommon userBean;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	AssignmentNodeCommon assignmentNode;
	
	DeliveryCommon delivery;
	DeliveryCandidateCommon deliveryCandidate;
		
	FileMetaCommon fileMeta;
	FileDataBlockCommon fileDataBlock;
	
	long superId;
	
	long uioId;
	long matnatId;
	long courseId;

	long periodId;
	long periodId2;
		
	long assignmentId, assignmentId2, assignmentId3;
	Calendar deadline;
		
	long deliveryId;
	//long deliveryId2;

	long deliveryCandidateId;
		
	long fileMetaId;

	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		node = testHelper.getNodeCommon();
		userBean = testHelper.getUserCommon();
		courseNode = testHelper.getCourseNodeCommon();
		periodNode = testHelper.getPeriodNodeCommon();
		assignmentNode = testHelper.getAssignmentNodeCommon();
		delivery = testHelper.getDeliveryCommon();
		deliveryCandidate = testHelper.getDeliveryCandidateCommon();
		fileMeta = testHelper.getFileMetaCommon();
		fileDataBlock = testHelper.getFileDataBlockCommon();
		
		superId = userBean.create("Homer Simpson", "homr@stuff.org", "123");
		userBean.setIsSuperAdmin(superId, true);
		userBean.addIdentity(superId, "homer");
		
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		courseId = courseNode.create("inf1000", "Object oriented programming", matnatId);
				
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		periodId = periodNode.create("fall09", "Fall 2009", start.getTime(),
				end.getTime(), courseId);
		periodId2 = periodNode.create("spring09", "Spring 2009", start
				.getTime(), end.getTime(), courseId);
		
		deadline = new GregorianCalendar(2009, 07, 20);
		
		// Add assignment
		assignmentId = assignmentNode.create("oblig1", "Obligatory assignemnt 1", deadline.getTime(), periodId);
		
		deliveryId = delivery.create(assignmentId);
		//deliveryId2 = delivery.create(assignmentId);
		
		// Add delivery candidate
		deliveryCandidateId = deliveryCandidate.create(deliveryId);

		// Add file meta data
		fileMetaId = fileMeta.create(deliveryCandidateId, "test.txt");
		
		
	}

	@After
	public void tearDown() throws Exception {
		testHelper.clearUsersAndNodes();
	}
	

	@Test
	public void getDeliveryCandidate() throws UnauthorizedException {
		assertEquals(deliveryCandidateId, fileMeta.getDeliveryCandidate(fileMetaId));
	}

	@Test
	public void getFilePath() throws UnauthorizedException {
		assertEquals("test.txt", fileMeta.getFilePath(fileMetaId));
	}

	@Test
	public void getFileDataBlocks() throws NamingException, UnauthorizedException {

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
	public void getSize() throws NamingException, UnauthorizedException {

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
	public void remove() throws NoSuchObjectException, UnauthorizedException {

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
	public void exists() throws UnauthorizedException {
		assertTrue(fileMeta.exists(fileMetaId));
		assertFalse(fileMeta.exists(fileMetaId + 1));
	}
}
