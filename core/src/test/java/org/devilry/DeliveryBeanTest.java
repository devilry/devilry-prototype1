package org.devilry;

import javax.naming.NamingException;
import org.devilry.core.entity.DeliveryCandidateEntity;
import org.devilry.core.session.DeliveryBeanRemote;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryBeanTest {

	DeliveryBeanRemote bean;

	@Before
	public void setUp() throws NamingException {
		bean = Helpers.loadBean(DeliveryBeanRemote.class);
	}

	@Ignore
	@Test
	public void add() {
		DeliveryCandidateEntity n = new DeliveryCandidateEntity();
		long id = bean.add(n);
		n = bean.get(id);
		assertNotNull(n);
	}


	@Ignore
	@Test
	public void get() {
		DeliveryCandidateEntity n = new DeliveryCandidateEntity();
		n.addFile("a.txt", "a".getBytes());
		long id = bean.add(n);
		n = bean.get(id);
		assertEquals(id, n.getId());
	}
}