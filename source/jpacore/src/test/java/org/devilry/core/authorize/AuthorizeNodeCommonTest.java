package org.devilry.core.authorize;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
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
	private long userId, userId2, superId;

	private long uioId, matnatId;

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
		matnatId = superNode.create("matnat", "Matnat", uioId);
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

		node.getChildNodes(uioId);
		node.getChildNodes(matnatId);

		node.getChildCourses(uioId);
		node.getChildCourses(matnatId);
		
		NodePath x = new NodePath("uio", ".");
		System.out.printf(x.toString());
//		node.getIdFromPath(new NodePath("uio", ".") );
//		node.getIdFromPath(new NodePath("uio.matnat", ".") );
	}
	
	
	//
	//
	// create()
	//
	//
	
	/** Test that a SuperAdmin can create toplevel nodes. */
	@Test
	public void createToplevel() throws Exception {
		superTestHelper.getNodeCommon().create("a", "A");
	}

	/** Test that a normal user cannot create a toplevel node. */
	@Test(expected=UnauthorizedException.class)
	public void createToplevelUnauthorized() throws Exception {
		testHelper.getNodeCommon().create("a", "A");
	}

	/** Test that a user can create a node if Admin on the parent node. */
	@Test
	public void create() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().create("a", "A", uioId);
	}
	
	/** Test that a user cannot create a node if not Admin on the parent
	 * node. */
	@Test(expected=UnauthorizedException.class)
	public void createUnauthorized() throws Exception {
		testHelper.getNodeCommon().create("a", "A");
	}
	

	//
	//
	// setName()
	//
	//

	/** Test that a SuperAdmin can set the name of a toplevel node. */
	@Test
	public void setNameToplevel() throws Exception {
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

	//
	//
	// setDisplayName()
	//
	//

	/** Test that a SuperAdmin can set the diplayname of a toplevel node. */
	@Test
	public void setDisplayNameToplevel() throws Exception {
		superTestHelper.getNodeCommon().setDisplayName(uioId, "u");
	}

	/**
	 * Test that not even an admin on a toplevel node cat set the the
	 * displayname.
	 */
	@Test(expected = UnauthorizedException.class)
	public void setDisplayNameToplevelUnauthorized() throws Exception {
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

	//
	//
	// remove()
	//
	//
	
	/** Test that a SuperAdmin can remove a toplevel node. */
	@Test
	public void removeToplevel() throws Exception {
		superTestHelper.getNodeCommon().remove(uioId);
	}
	
	/**
	 * Test that not even an admin on a toplevel node can remove a node.
	 */
	@Test(expected = UnauthorizedException.class)
	public void removeToplevelUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().remove(uioId);
	}
	
	/** Test that remove works when admin on the parent-node. */
	@Test
	public void remove() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().remove(matnatId);
	}
	
	/** Make sure that even an Admin on a node can not remove the node. */
	@Test(expected = UnauthorizedException.class)
	public void removeUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(matnatId, userId);
		testHelper.getNodeCommon().remove(matnatId);
	}

	//
	//
	// addNodeAdmin()
	//
	//
	
	/** Test that addNodeAdmin on toplevel node works when SuperAdmin. */
	@Test
	public void addNodeAdminTolpevel() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
	}

	/**
	 * Test that addNodeAdmin on toplevel does not even work when admin on the
	 * node.
	 */
	@Test(expected = UnauthorizedException.class)
	public void addNodeAdminTolpevelUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().addNodeAdmin(uioId, userId);
	}

	/** Test that addNodeAdmin works when admin on the parent-node. */
	@Test
	public void addNodeAdmin() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().addNodeAdmin(matnatId, userId2);
	}

	/** Test that addNodeAdmin do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void addNodeAdminUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(matnatId, userId);
		testHelper.getNodeCommon().addNodeAdmin(matnatId, userId2);
	}

	//
	//
	// removeNodeAdmin()
	//
	//
	
	/** Test that removeNodeAdmin on toplevel node works when SuperAdmin. */
	@Test
	public void removeNodeAdminTolpevel() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
	}

	/**
	 * Test that removeNodeAdmin on toplevel does not even work when admin on the
	 * node.
	 */
	@Test(expected = UnauthorizedException.class)
	public void removeNodeAdminTolpevelUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().removeNodeAdmin(uioId, userId);
	}

	/** Test that removeNodeAdmin works when admin on the parent-node. */
	@Test
	public void removeNodeAdmin() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().removeNodeAdmin(matnatId, userId2);
	}

	/** Test that removeNodeAdmin do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void removeNodeAdminUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(matnatId, userId);
		testHelper.getNodeCommon().removeNodeAdmin(matnatId, userId2);
	}

	//
	//
	// getNodeAdmins()
	//
	//
	
	/** Test that getNodeAdmins on toplevel node works when SuperAdmin. */
	@Test
	public void getNodeAdminsTolpevel() throws Exception {
		superTestHelper.getNodeCommon().getNodeAdmins(uioId);
	}

	/**
	 * Test that getNodeAdmins on toplevel does not even work when admin on the
	 * node.
	 */
	@Test(expected = UnauthorizedException.class)
	public void getNodeAdminsTolpevelUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().getNodeAdmins(uioId);
	}

	/** Test that getNodeAdmins works when admin on the parent-node. */
	@Test
	public void getNodeAdmins() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(uioId, userId);
		testHelper.getNodeCommon().getNodeAdmins(matnatId);
	}

	/** Test that getNodeAdmins do not even work when admin on the node. */
	@Test(expected = UnauthorizedException.class)
	public void getNodeAdminsUnauthorized() throws Exception {
		superTestHelper.getNodeCommon().addNodeAdmin(matnatId, userId);
		testHelper.getNodeCommon().getNodeAdmins(matnatId);
	}
}