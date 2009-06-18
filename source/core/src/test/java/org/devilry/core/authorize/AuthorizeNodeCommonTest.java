package org.devilry.core.authorize;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AuthorizeNodeCommonTest {
	protected static CoreTestHelper testHelper;
	protected static CoreTestHelper superTestHelper;

	/** The id of the users with the logged-in identity. */
	private long userId, superId;

	private long uioId, matnatId;

	@Before
	public void setUp() throws NamingException, PathExistsException,
			UnauthorizedException, InvalidNameException, NoSuchObjectException {
		NodeCommon superNode = superTestHelper.getNodeCommon();
		UserCommon superUser = superTestHelper.getUserCommon();

		superId = superUser.create("Homer Simpson", "homr@stuff.org", "123");
		superUser.setIsSuperAdmin(superId, true);
		superUser.addIdentity(superId, "homer");
		System.out.printf("******* s:%d,%s%n", superId,
				superUser.getAuthenticatedIdentity());

		userId = superUser.create("Marge Simpson", "marge@stuff.org", "123");
		superUser.addIdentity(userId, "marge");

		uioId = superNode.create("uio", "UiO");
		matnatId = superNode.create("matnat", "Matnat", uioId);
	}

	@After
	public void tearDown() throws NamingException, NoSuchObjectException {
		superTestHelper.clearUsersAndNodes();
	}

	/** Test that a SuperAdmin can set the name of a toplevel node. */
	@Test
	public void setNameToplevel() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, superId);
		superTestHelper.getNodeCommon().setName(uioId, "u");
	}

	/** Test that not even an admin on a toplevel node cat set the the name. */
	@Test(expected = UnauthorizedException.class)
	public void setNameToplevelUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().setName(uioId, "u");
	}

	/** Test that setName works when admin on the parent-node. */
	@Test
	public void setName() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().setName(matnatId, "u");
	}

	/** Make sure that even an Admin on a node cannot set the name. */
	@Test(expected = UnauthorizedException.class)
	public void setNameUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(matnatId, userId);
		testHelper.getNodeCommon().setName(matnatId, "u");
	}
	


	/** Test that a SuperAdmin can set the diplayname of a toplevel node. */
	@Test
	public void setDisplayNameToplevel() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, superId);
		superTestHelper.getNodeCommon().setDisplayName(uioId, "u");
	}

	/** Test that not even an admin on a toplevel node cat set the the
	 * displayname. */
	@Test(expected = UnauthorizedException.class)
	public void setToplevelNameUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().setDisplayName(uioId, "u");
	}

	/** Test that setDisplayName works when admin on the parent-node. */
	@Test
	public void setDisplayName() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().setDisplayName(matnatId, "u");
	}

	/** Make sure that even an Admin on a node cannot set the displayname. */
	@Test(expected = UnauthorizedException.class)
	public void setDisplayNameUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(matnatId, userId);
		testHelper.getNodeCommon().setDisplayName(matnatId, "u");
	}



	/** Test that none of the unprotected methods fails as a normal user
	 * on both toplevel and normal nodes. */
	@Test
	public void noAuthMethods() throws Exception {
		NodeCommon node = testHelper.getNodeCommon();
		node.getName(uioId);
		node.getName(matnatId);

		node.getDisplayName(uioId);
		node.getDisplayName(matnatId);

		node.exists(uioId);
		node.exists(matnatId);

		node.getPath(uioId);
		node.getPath(matnatId);

		node.getNodesWhereIsAdmin();

		node.isNodeAdmin(uioId);
		node.isNodeAdmin(matnatId);

		node.getParentNode(matnatId);

		node.getChildnodes(uioId);
		node.getChildnodes(matnatId);

		node.getChildcourses(uioId);
		node.getChildcourses(matnatId);
	}
}