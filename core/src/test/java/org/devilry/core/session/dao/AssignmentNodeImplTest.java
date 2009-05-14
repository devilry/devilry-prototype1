package org.devilry.core.session.dao;

import java.util.*;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.entity.*;
import org.devilry.core.session.*;

public class AssignmentNodeImplTest extends AbstractSessionBeanTestHelper {
	AssignmentNodeRemote node;
	TreeManagerRemote tm;

	@Before
	public void setUp() throws NamingException {
		setupEjbContainer();
		tm = getRemoteBean(TreeManagerImpl.class);
		node = getRemoteBean(AssignmentNodeImpl.class);
	}

	@After
	public void tearDown() {
		destroyEjbContainer();
	}

	// cant have empty tests, remove this when AssignmentNode get implemented
	@Test
	public void test1() {}
}

