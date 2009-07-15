package org.devilry.core.authorize;

import javax.naming.NamingException;

import org.devilry.core.testhelpers.RemoteCoreTestHelper;
import org.junit.BeforeClass;

public class AuthorizeCourseNodeRemoteTest extends
		AuthorizeCourseNodeCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		superTestHelper = new RemoteCoreTestHelper("homer", "doh");
		testHelper = new RemoteCoreTestHelper("marge", "blue");
	}
}
