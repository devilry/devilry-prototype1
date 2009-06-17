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
import org.junit.Test;

public abstract class AuthorizeNodeCommonTest {
	protected static CoreTestHelper testHelper;
	protected static CoreTestHelper superTestHelper;
	private NodeCommon node;
	private UserCommon user;
	private NodeCommon superNode;
	
	/** The id of the user with the logged-in identity. */
	private long margeId;
	
	private long uioId, matnatId;

	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		node = testHelper.getNodeCommon();
		user = testHelper.getUserCommon();
		superNode = superTestHelper.getNodeCommon();
		UserCommon superUser = superTestHelper.getUserCommon();

		long homerId = superUser.create("Homer Simpson", "homr@stuff.org", "123");
		superUser.setIsSuperAdmin(homerId, true);
		superUser.addIdentity(homerId, "homer");

		margeId = superUser.create("Marge Simpson", "marge@stuff.org", "123");
		superUser.addIdentity(margeId, "marge");

		uioId = superNode.create("uio", "UiO");
		matnatId = node.create("matnat", "Matnat", uioId);
	}

	@After
	public void tearDown() throws NamingException, NoSuchObjectException {
		testHelper.clearUsersAndNodes();
	}

	@Test(expected=UnauthorizedException.class)
	public void setName() throws Exception {
		
	}
}
