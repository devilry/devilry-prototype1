package org.devilry.core.session.dao;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.naming.NamingException;

public class DeliveryImplTest extends AbstractDeliveryDaoTest {

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
	}

	@Test
	public void getId() {
		assertTrue(delivery.getId() > 0);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}
}
