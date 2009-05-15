package org.devilry.core.session.dao;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.naming.NamingException;
import java.util.List;

public class DeliveryImplTest extends AbstractDeliveryDaoTst {

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
	}

	@Test
	public void getId() {
		assertTrue(delivery.getId() > 0);
	}

	@Test
	public void getAssignmentId() {
		assertEquals(assignmentId, delivery.getAssignmentId());
	}

	@Test
	public void getDeliveryCandidateIds() {
		delivery.addDeliveryCandidate();
		delivery.addDeliveryCandidate();
		delivery.addDeliveryCandidate();
		assertEquals(3, delivery.getDeliveryCandidateIds().size());
	}

	@Test
	public void addDeliveryCandidate() throws NamingException {
		long id = delivery.addDeliveryCandidate();

		List<Long> c = delivery.getDeliveryCandidateIds();
		assertEquals(1, c.size());
		assertEquals(id, (long)c.get(0));
		DeliveryCandidateRemote d = getRemoteBean(DeliveryCandidateImpl.class);
		d.init(id);
		assertEquals(id, d.getId());
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}
}
