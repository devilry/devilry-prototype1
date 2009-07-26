package org.devilry.core.dao;

import javax.naming.NamingException;
import org.devilry.core.testhelpers.RemoteCoreTestHelper;
import org.junit.BeforeClass;

public class UserRemoteTest extends UserCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		testHelper = new RemoteCoreTestHelper("homer", "doh");
	}
}
