package org.devilry.core.session.dao;

import javax.naming.NamingException;
import org.devilry.core.testhelpers.LocalCoreTestHelper;
import org.junit.BeforeClass;

public class DeliveryLocalTest extends DeliveryCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		testHelper = new LocalCoreTestHelper("homer", "doh");
	}
}