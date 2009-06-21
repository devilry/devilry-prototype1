package org.devilry.clientapi;

import javax.naming.NamingException;
import org.junit.BeforeClass;

public class ExaminerAssignmentRemoteTest extends ExaminerAssignmentCommonTest {
	@BeforeClass
	public static void initBeans() throws NamingException {
		connection = new DevilryConnectionOpenEJB("homer", "doh");
	}
}	
