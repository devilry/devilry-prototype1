package org.devilry.core.session;

import java.util.List;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryBeanTest extends AbstractSessionBeanTestHelper {

	DeliveryCandidateRemote remoteBean;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		remoteBean = (DeliveryCandidateRemote) getRemoteBean(DeliveryCandidate.class);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void addFile() {
		remoteBean.init(0);
		remoteBean.addFile("test.txt");
		List<Long> fileIds = remoteBean.getFileIds();
		assertEquals(1, fileIds.size());
	}


	@Test
	public void getFileIds() throws NamingException {
		remoteBean.init(0);
		remoteBean.addFile("b");
		remoteBean.addFile("a");
		remoteBean.addFile("c");
		List<Long> fileIds = remoteBean.getFileIds();
		assertEquals(3, fileIds.size());
	}
}