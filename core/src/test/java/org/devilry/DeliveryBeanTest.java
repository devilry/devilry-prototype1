package org.devilry;

import javax.naming.NamingException;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.DeliveryBeanRemote;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryBeanTest {

	DeliveryBeanRemote bean;

	@Before
	public void setUp() throws NamingException {
		bean = Helpers.loadBean(DeliveryBeanRemote.class);
	}

	@Test
	public void add() {
		DeliveryCandidateNode n = new DeliveryCandidateNode();
		long id = bean.add(n);
		n = bean.get(id);
		assertNotNull(n);
	}


	@Test
	public void get() {
		DeliveryCandidateNode n = new DeliveryCandidateNode();
		n.addFile("a.txt", "a".getBytes());
		long id = bean.add(n);
		n = bean.get(id);
		assertEquals(id, n.getId());
	}
}