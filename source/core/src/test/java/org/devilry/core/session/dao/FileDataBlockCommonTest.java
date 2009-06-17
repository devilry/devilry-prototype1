package org.devilry.core.session.dao;

import java.util.Arrays;
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


public abstract class FileDataBlockCommonTest {

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
	
	long fileDataBlockId;
	long fileDataBlockId2;
	
	byte [] data = "ausduudfudsfusfusfusdusuf".getBytes();
	byte [] data2 = "uuququqqwquwqwqvqvnqbqxxxxxxx".getBytes();
	
	
	
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
				
		// Add data blocks
		fileDataBlockId = fileDataBlock.create(fileMetaId, data);
		fileDataBlockId2 = fileDataBlock.create(fileMetaId, data2);
		
	}

	@After
	public void tearDown() throws NamingException, NoSuchObjectException {
		testHelper.clearUsersAndNodes();
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
	
	@Test
	public void exists() {
		assertTrue(fileDataBlock.exists(fileDataBlockId2));
		assertFalse(fileDataBlock.exists(fileDataBlockId2 + 1));
	}
}
