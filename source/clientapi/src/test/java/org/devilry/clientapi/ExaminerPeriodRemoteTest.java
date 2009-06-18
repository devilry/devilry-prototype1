package org.devilry.clientapi;

import javax.naming.NamingException;
import org.devilry.core.testhelpers.RemoteCoreTestHelper;
import org.junit.BeforeClass;

public class ExaminerPeriodRemoteTest extends ExaminerPeriodCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		connection = new DevilryConnectionOpenEJB("homer", "doh");
	}
}	
