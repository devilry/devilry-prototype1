package org.devilry;

import com.bm.testsuite.BaseSessionBeanFixture;
import java.util.Collection;
import org.devilry.core.DeliveryBean;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.FileNode;


public class DeliveryBeanTest extends BaseSessionBeanFixture<DeliveryBean> {
	private static final Class<?>[] USED_ENTITIES = {
		DeliveryBean.class, DeliveryCandidateNode.class, FileNode.class
	};

	public DeliveryBeanTest() {
		super(DeliveryBean.class, USED_ENTITIES);
	}


	public void testBaunWithPreloadedData() {
		DeliveryBean d = this.getBeanToTest();
		DeliveryCandidateNode dc = new DeliveryCandidateNode();
		dc.addFile("a", "aa".getBytes());
		dc.addFile("b", "bb".getBytes());
		long id = d.add(dc);

		/*
		Collection<FileNode> c = dc.getFiles();
		assertEquals(c.size(), 2);

		DeliveryCandidateNode dcFromDb = d.getFull(id);
		assertCollectionsEqual(c, dcFromDb.getFiles());
		 */
	}
}
