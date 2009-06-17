package org.devilry.core.authorize;

import javax.naming.NamingException;

import org.devilry.core.testhelpers.RemoteCoreTestHelper;
import org.junit.BeforeClass;

public class AuthorizeNodeRemoteTest extends AuthorizeNodeCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		testHelper = new RemoteCoreTestHelper("homer", "doh");
		superTestHelper = new RemoteCoreTestHelper("marge", "blue");
	}
}
