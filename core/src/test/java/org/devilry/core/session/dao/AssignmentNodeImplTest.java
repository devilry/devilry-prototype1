package org.devilry.core.session.dao;

import java.util.*;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.session.*;

public class AssignmentNodeImplTest extends NodeImplTest {
	AssignmentNodeRemote assignmentNode;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		assignmentNode = getRemoteBean(AssignmentNodeImpl.class);
	}

//	@Test
//	public void getDeliveryIds() {
//		List<Long> ids = node.getDeliveryIds();
//		assertEquals(3, ids.size());
//	}
}