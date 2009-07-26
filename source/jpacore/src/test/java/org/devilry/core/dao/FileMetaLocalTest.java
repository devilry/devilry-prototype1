package org.devilry.core.dao;

import javax.naming.NamingException;
import org.devilry.core.testhelpers.LocalCoreTestHelper;
import org.junit.BeforeClass;

public class FileMetaLocalTest extends FileMetaCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		testHelper = new LocalCoreTestHelper("homer", "doh");
	}
}