package org.devilry.core.authorize;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AuthorizePeriodNodeCommonTest {
	protected static CoreTestHelper testHelper;
	protected static CoreTestHelper superTestHelper;

	/** The id of the users with the logged-in identity. */
	private long userId, userId2, superId;

	private long uioId, inf1000Id, fall09;

	Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
	Calendar end = new GregorianCalendar(2009, 00, 02, 10, 15);
	
	@Before
	public void setUp() throws NamingException, PathExistsException,
			UnauthorizedException, InvalidNameException, NoSuchObjectException {
		NodeCommon superNode = superTestHelper.getNodeCommon();
		UserCommon superUser = superTestHelper.getUserCommon();

		superId = superUser.create("Homer Simpson", "homr@stuff.org", "123");
		superUser.setIsSuperAdmin(superId, true);
		superUser.addIdentity(superId, "homer");

		userId = superUser.create("Marge Simpson", "marge@stuff.org", "123");
		superUser.addIdentity(userId, "marge");

		userId2 = superUser.create("Bart Simpson", "bart@stuff.org", "123");
		superUser.addIdentity(userId2, "bart");

		uioId = superNode.create("uio", "UiO");
		CourseNodeCommon course = superTestHelper.getCourseNodeCommon();
		inf1000Id = course.create("inf1000", "INF1000", uioId);
				
		PeriodNodeCommon period = superTestHelper.getPeriodNodeCommon();
		fall09 = period.create("fall09", "Fall semester", start.getTime(), end.getTime(), inf1000Id);
	}

	@After
	public void tearDown() throws Exception {
		superTestHelper.clearUsersAndNodes();
	}

	/**
	 * Test that none of the unprotected methods fails as a normal user on both
	 * toplevel and normal nodes.
	 */
	@Test
	public void noAuthMethods() throws Exception {
		PeriodNodeCommon period = superTestHelper.getPeriodNodeCommon();
		period.getName(fall09);
		period.getDisplayName(fall09);
		period.exists(fall09);
		period.getPath(fall09);
		period.getPeriodsWhereIsAdmin();
		period.isPeriodAdmin(fall09);
		period.getParentCourse(fall09);
		period.getAssignments(fall09);
	}
	
	
	//
	//
	// create()
	//
	//

	/** Test that a user can create a course if Admin on the parent node. */
	@Test
	public void create() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getPeriodNodeCommon().create("test09", "Test", start.getTime(), end.getTime(), inf1000Id);
	}
	
	/** Test that a user cannot create a course if not Admin on the parent
	 * node. */
	@Test(expected=UnauthorizedException.class)
	public void createUnauthorized() throws Exception {
		testHelper.getPeriodNodeCommon().create("test09", "Test", start.getTime(), end.getTime(), inf1000Id);
	}
	

	//
	//
	// setName()
	//
	//

	/** Test that setName works when admin on the parent-node. */
	@Test
	public void setName() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getPeriodNodeCommon().setName(fall09, "u");
	}

	/** Make sure that even an Admin on a period cannot set the name. */
	@Test(expected = UnauthorizedException.class)
	public void setNameUnauthorized() throws Exception {
		superTestHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId);
		testHelper.getPeriodNodeCommon().setName(fall09, "u");
	}

	//
	//
	// setDisplayName()
	//
	//

	/** Test that setDisplayName works when admin on the parent-node. */
	@Test
	public void setDisplayName() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getPeriodNodeCommon().setDisplayName(fall09, "u");
	}

	/** Make sure that even an Admin on a node cannot set the displayname. */
	@Test(expected = UnauthorizedException.class)
	public void setDisplayNameUnauthorized() throws Exception {
		superTestHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId);
		testHelper.getPeriodNodeCommon().setDisplayName(fall09, "u");
	}

	//
	//
	// addPeriodAdmin()
	//
	//


	/** Test that addCourseAdmin works when admin on the parent-node. */
	@Test
	public void addPeriodAdmin() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId2);
	}

	/** Test that addCourseAdmin do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void addPeriodAdminUnauthorized() throws Exception {
		superTestHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId);
		testHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId2);
	}

	//
	//
	// removePeriod	Admin()
	//
	//

	/** Test that removeCourseNodeAdmin works when admin on the parent-node. */
	@Test
	public void removePeriodAdmin() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getPeriodNodeCommon().removePeriodAdmin(fall09, userId2);
	}

	/** Test that removeCourseAdmin do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void removeCourseAdminUnauthorized() throws Exception {
		superTestHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId);
		testHelper.getPeriodNodeCommon().removePeriodAdmin(fall09, userId2);
	}

	//
	//
	// getPeriodAdmins()
	//
	//

	/** Test that getCourseAdmins works when admin on the parent-node. */
	@Test
	public void getPeriodAdmins() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getPeriodNodeCommon().getPeriodAdmins(fall09);
	}

	/** Test that getCourseAdmins do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void getCourseAdminsUnauthorized() throws Exception {
		superTestHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId);
		testHelper.getPeriodNodeCommon().getPeriodAdmins(fall09);
	}
	
	//
	// setStartDate
	//
	 
	/** Test that setStartDate works when admin on the parent-node. */
	@Test
	public void setStartDate() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getPeriodNodeCommon().setStartDate(fall09, start.getTime());
	}
	
	/** Test that setStartDate do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void setStartDateUnauthorized() throws Exception {
		superTestHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId);
		testHelper.getPeriodNodeCommon().setStartDate(fall09, start.getTime());
	}
	
	//
	// setEndDate
	//
	
	/** Test that setEndDate works when admin on the parent-node. */
	@Test
	public void setEndDate() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getPeriodNodeCommon().setEndDate(fall09, end.getTime());
	}
	
	/** Test that setStartDate do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void setEndDateUnauthorized() throws Exception {
		superTestHelper.getPeriodNodeCommon().addPeriodAdmin(fall09, userId);
		testHelper.getPeriodNodeCommon().setEndDate(fall09, start.getTime());
	}
}