package org.devilry.core.authorize;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AuthorizeCourseNodeCommonTest {
	protected static CoreTestHelper testHelper;
	protected static CoreTestHelper superTestHelper;

	/** The id of the users with the logged-in identity. */
	private long userId, userId2, superId;

	private long uioId, inf1000Id;

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
		CourseNodeCommon course = testHelper.getCourseNodeCommon();
		course.getName(inf1000Id);
		course.getDisplayName(inf1000Id);
		course.exists(inf1000Id);
		course.getPath(inf1000Id);
		course.getCoursesWhereIsAdmin();
		course.isCourseAdmin(inf1000Id);
		course.getParentNode(inf1000Id);
		course.getPeriods(inf1000Id);
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
		testHelper.getCourseNodeCommon().create("a", "A", uioId);
	}
	
	/** Test that a user cannot create a course if not Admin on the parent
	 * node. */
	@Test(expected=UnauthorizedException.class)
	public void createUnauthorized() throws Exception {
		testHelper.getCourseNodeCommon().create("a", "A", uioId);
	}
	

	//
	//
	// setName()
	//
	//

	/** Test that setName works when admin on the parent-node. */
	@Test
	public void setName() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getCourseNodeCommon().setName(inf1000Id, "u");
	}

	/** Make sure that even an Admin on a course cannot set the name. */
	@Test(expected = UnauthorizedException.class)
	public void setNameUnauthorized() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getCourseNodeCommon().setName(inf1000Id, "u");
	}

	//
	//
	// setDisplayName()
	//
	//

	/** Test that setDisplayName works when admin on the parent-node. */
	@Test
	public void setDisplayName() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getCourseNodeCommon().setDisplayName(inf1000Id, "u");
	}

	/** Make sure that even an Admin on a node cannot set the displayname. */
	@Test(expected = UnauthorizedException.class)
	public void setDisplayNameUnauthorized() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getCourseNodeCommon().setDisplayName(inf1000Id, "u");
	}

	//
	//
	// addCourseAdmin()
	//
	//


	/** Test that addCourseAdmin works when admin on the parent-node. */
	@Test
	public void addCourseAdmin() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId2);
	}

	/** Test that addCourseAdmin do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void addCourseAdminUnauthorized() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId2);
	}

	//
	//
	// removeCourseAdmin()
	//
	//

	/** Test that removeCourseNodeAdmin works when admin on the parent-node. */
	@Test
	public void removeCourseAdmin() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getCourseNodeCommon().removeCourseAdmin(inf1000Id, userId2);
	}

	/** Test that removeCourseAdmin do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void removeCourseAdminUnauthorized() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getCourseNodeCommon().removeCourseAdmin(inf1000Id, userId2);
	}

	//
	//
	// getCourseAdmins()
	//
	//

	/** Test that getCourseAdmins works when admin on the parent-node. */
	@Test
	public void getCourseAdmins() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getCourseNodeCommon().getCourseAdmins(inf1000Id);
	}

	/** Test that getCourseAdmins do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void getCourseAdminsUnauthorized() throws Exception {
		superTestHelper.getCourseNodeCommon().addCourseAdmin(inf1000Id, userId);
		testHelper.getCourseNodeCommon().getCourseAdmins(inf1000Id);
	}
}