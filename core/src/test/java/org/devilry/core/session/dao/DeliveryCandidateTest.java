package org.devilry.core.session.dao;

import java.util.List;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryCandidateTest extends AbstractSessionBeanTestHelper {

	DeliveryCandidateRemote remoteBean;
	long deliveryId;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		remoteBean = (DeliveryCandidateRemote) getRemoteBean(DeliveryCandidate.class);
		deliveryId = 0;
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test(expected=Exception.class)
	public void addFileWithoutInit() {
		remoteBean.addFile("not.txt");
	}

	@Test
	public void addFile() {
		remoteBean.init(deliveryId);
		long id1 = remoteBean.addFile("test.txt");
		long id2 = remoteBean.addFile("test2.txt");
		assertFalse(id1 == id2);
		List<Long> fileIds = remoteBean.getFileIds();
		assertEquals(2, fileIds.size());
	}


	@Test
	public void getFileIds() throws NamingException {
		remoteBean.init(deliveryId);
		remoteBean.addFile("b");
		remoteBean.addFile("a");
		remoteBean.addFile("c");
		List<Long> fileIds = remoteBean.getFileIds();
		assertEquals(3, fileIds.size());
	}
}