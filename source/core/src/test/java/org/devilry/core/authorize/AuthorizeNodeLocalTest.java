package org.devilry.core.authorize;

import javax.naming.NamingException;

import org.devilry.core.testhelpers.LocalCoreTestHelper;
import org.junit.BeforeClass;

public class AuthorizeNodeLocalTest extends AuthorizeNodeCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		superTestHelper = new LocalCoreTestHelper("homer", "doh");
		testHelper = new LocalCoreTestHelper("marge", "blue");
	}
}
