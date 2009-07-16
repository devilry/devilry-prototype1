package org.devilry.clientapi;

import javax.naming.NamingException;
import org.junit.BeforeClass;

public class ExaminerDeliveryRemoteTest extends ExaminerDeliveryCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		connection = new DevilryConnectionOpenEJB("homer", "doh");
	}
}	
