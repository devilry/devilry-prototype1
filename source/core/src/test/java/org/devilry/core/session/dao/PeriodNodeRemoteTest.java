package org.devilry.core.session.dao;

import javax.naming.NamingException;
import org.devilry.core.testhelpers.RemoteCoreTestHelper;
import org.junit.BeforeClass;

public class PeriodNodeRemoteTest extends PeriodNodeCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		testHelper = new RemoteCoreTestHelper("homer", "doh");
	}
}
