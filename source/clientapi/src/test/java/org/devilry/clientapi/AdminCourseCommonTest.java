package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class AdminCourseCommonTest extends CourseCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	//AdminPeriod period;
	//AdminPeriod period2;
				
	long inf1000Spring09, inf1000Fall09;
	
	AdminCourse adminCourse;
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		// Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		// Calendar end = new GregorianCalendar(2009, 05, 15);
		// inf1000Spring09 = periodNode.create("spring2009", "INF1000 spring2009", start.getTime(), end.getTime(), inf1000);
		// inf1000Fall09 = periodNode.create("fall2009", "INf1000 fall 2009", start.getTime(), end.getTime(), inf1000);
		
		
		//period = new AdminPeriod(inf1000Spring09, connection);
		//period2 = new AdminPeriod(inf1000Fall09, connection);
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
		
		adminCourse = new AdminCourse(inf1000, connection);
	}
	
	@Test
	public void getPath() throws NoSuchObjectException, NamingException, InvalidNameException, UnauthorizedException {
		NodePath path = adminCourse.getPath();
		
		NodePath check = new NodePath(new String[]{"uio", "matnat", "ifi", "inf1000"});
		assertTrue(path.equals(check));
	}
	
	@Test
	public void getCourseWhereIsAdmin() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException, NoSuchUserException {
		
		List<AdminCourse> adminCourses = adminCourse.getCoursesWhereIsAdmin();
		
		assertEquals(0, adminCourses.size());
		
		long inf1010 = courseNode.create("inf1010", "Programmering intro", ifiId);
		
		courseNode.addCourseAdmin(inf1000, homerId);
		courseNode.addCourseAdmin(inf1010, homerId);
		
		adminCourses = adminCourse.getCoursesWhereIsAdmin();
		assertEquals(2, adminCourses.size());		
	}
	
	@Test
	public void addPeriod() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		AdminPeriod newPeriod = adminCourse.addPeriod("summmer09", "Summer games", start.getTime(), end.getTime());

		List<AdminPeriod> periods = adminCourse.getPeriods();
		assertEquals(newPeriod.periodId, periods.get(0).periodId);
		
		AdminPeriod newPeriod2 = adminCourse.addPeriod("winter09", "Winter games", start.getTime(), end.getTime());
		assertEquals(2, adminCourse.getPeriods().size());
		
	}
	
	@Test
	public void removePeriod() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		
		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);
		
		AdminPeriod newPeriod = adminCourse.addPeriod("summmer09", "Summer games", start.getTime(), end.getTime());
		AdminPeriod newPeriod2 = adminCourse.addPeriod("winter09", "Winter games", start.getTime(), end.getTime());
		
		adminCourse.removePeriod(newPeriod2);
		assertEquals(1, adminCourse.getPeriods().size());
		
		adminCourse.removePeriod(newPeriod);
		assertEquals(0, adminCourse.getPeriods().size());
	}
	
	@Test
	public void setCourseName() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		String newCourseName = "inf0001";
		adminCourse.setCourseName(newCourseName);
		assertEquals(newCourseName, adminCourse.getCourseName());
	}
	
	@Test
	public void setCourseDisplayName() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		String newCourseDisplayName = "skrot";
		adminCourse.setCourseDisplayName(newCourseDisplayName);
		assertEquals(newCourseDisplayName, adminCourse.getCourseDisplayName());
	}	
}
