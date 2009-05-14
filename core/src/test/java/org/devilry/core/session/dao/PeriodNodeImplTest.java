package org.devilry.core.session.dao;

import java.util.*;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.entity.*;
import org.devilry.core.session.*;

public class PeriodNodeImplTest extends AbstractSessionBeanTestHelper {
	PeriodNodeRemote node;
	TreeManagerRemote tm;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
		node = getRemoteBean(PeriodNodeImpl.class);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	@Test
	public void getStartDate() {

	}

	@Test
	public void setStartDate() {

	}

	@Test
	public void getEndDate() {

	}

	@Test
	public void setEndDate() {

	}
}

