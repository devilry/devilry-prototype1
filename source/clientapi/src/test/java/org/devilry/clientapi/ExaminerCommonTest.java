package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class ExaminerCommonTest extends UserCommonTest {

	Examiner homer;
	Examiner bart, lisa;
	
	// Create some test users
	@Before
	public void setUsers() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {

		homer = new Examiner(homerId, connection);
		bart = new Examiner(bartId, connection);
		lisa = new Examiner(lisaId, connection);
	}
	
	@Test
	public void getActivePeriods() {
			//		inf1000
		//public List getActivePeriods()
	}
	
	@Test
	public void getPeriods() throws NamingException, NoSuchObjectException, UnauthorizedException, NoSuchUserException {
	
		List<ExaminerPeriod> periods;
		
		periodNode.addExaminer(inf1000Spring09, homerId);
		periods = homer.getPeriods();
		assertEquals(1, periods.size());
		
		periodNode.addExaminer(inf1000Fall09, homerId);
		periods = homer.getPeriods();
		assertEquals(2, periods.size());
	}
	
}
