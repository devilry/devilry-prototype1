package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public abstract class StudentCommonTest extends UserCommonTest {

	Student homer;
	Student bart, lisa;
	
	// Create some test users
	@Before
	public void setUsers() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {

		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
	}

	@Test
	public void getActivePeriods() {
			//		inf1000
		//public List getActivePeriods()
	}
	
	@Test
	public void getPeriods() throws NamingException, NoSuchObjectException, UnauthorizedException, NoSuchUserException {
	
		List<StudentPeriod> periods;
		
		periodNode.addStudent(inf1000Spring09, homerId);
		periods = homer.getPeriods();
		assertEquals(1, periods.size());
		
		periodNode.addStudent(inf1000Fall09, homerId);
		periods = homer.getPeriods();
		assertEquals(2, periods.size());
	}
	
}
