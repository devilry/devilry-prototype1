package org.devilry.core.session.dao;

import org.devilry.core.UnauthorizedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import javax.naming.NamingException;

import org.devilry.core.NodePath;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/** Tests all methods in the NodeCommon interface.
 @see NodeCommon
 */
public abstract class NodeCommonTest {

	protected static CoreTestHelper testHelper;
	protected NodeCommon node;
	protected UserCommon userBean;

	/** The id of the user with the logged-in identity. */
	protected long homerId;

	@BeforeClass
	public static void initBeans() throws NamingException {
		throw new NotImplementedException();
	}

	@Before
	public void setUp() throws NamingException {
		node = testHelper.getNodeCommon();
		userBean = testHelper.getUserCommon();

		homerId = userBean.create("Homer Simpson", "homr@stuff.org", "123");
		userBean.addIdentity(homerId, "homer");
	}

	@After
	public void tearDown() throws NamingException {
		testHelper.clearUsersAndNodes();
	}

	@Test
	public void exists() throws NamingException {
		long uioId = node.create("uio", "UiO");
		long matnatId = node.create("matnat", "Matnat", uioId);
		assertTrue(node.exists(uioId));
		assertTrue(node.exists(matnatId));
		assertFalse(node.exists(uioId + matnatId));
	}

	@Test
	public void getPath() {
		long matnatId = node.create("matnat", "Matnat",
				node.create("uio", "UiO"));
		assertEquals(new NodePath("uio.matnat", "\\."), node.getPath(matnatId));
	}

	@Test
	public void getIdFromPath() {
		long uioId = node.create("uio", "UiO");
		long matnatId = node.create("matnat", "Matnat", uioId);
		assertEquals(uioId, node.getIdFromPath(new NodePath(new String[]{"uio"})));
		assertEquals(matnatId, node.getIdFromPath(new NodePath("uio.matnat", "\\.")));
	}

	@Test
	public void getToplevelNodeIds() {
		long aId = node.create("a", "A");
		List<Long> toplevel = node.getToplevelNodes();
		assertEquals(1, toplevel.size());
		assertEquals(aId, (long) toplevel.get(0));

		node.create("b", "B");
		assertEquals(2, node.getToplevelNodes().size());
	}

	@Test
	public void getNodesWhereIsAdmin() {
		long uioId = node.create("uio", "UiO");
		assertEquals(0, node.getNodesWhereIsAdmin().size());

		node.addNodeAdmin(uioId, homerId);
		List<Long> l = node.getNodesWhereIsAdmin();
		assertEquals(1, l.size());
		assertEquals(uioId, (long) l.get(0));

		// Adding another admin does not affect the result?
		long margeId = userBean.create("marge", "marge@doh.com", "123");
		node.addNodeAdmin(uioId, margeId);
		assertEquals(1, node.getNodesWhereIsAdmin().size());

		// Add as admin to another node works?
		long nodeId = node.create("tst", "Test");
		node.addNodeAdmin(nodeId, homerId);
		assertEquals(2, node.getNodesWhereIsAdmin().size());
	}

	@Test(expected = Exception.class)
	public void createDuplicateChild() {
		long uioId = node.create("uio", "UiO");
		node.create("unique", "aaaa", uioId);
		node.create("unique", "aaaa", uioId);
	}

	@Test(expected = Exception.class)
	public void createDuplicateToplevel() {
		node.create("unique", "aaaa");
		node.create("unique", "aaaa");
	}

	@Test
	public void getChildnodes() throws UnauthorizedException {
		long uioId = node.create("uio", "UiO");
		node.addNodeAdmin(uioId, homerId);
		long matnatId = node.create("matnat", "Mat...", uioId);

		List<Long> children = node.getChildnodes(uioId);
		assertEquals(1, children.size());
		assertEquals(matnatId, (long) children.get(0));

		node.create("hf", "Huma....", uioId);
		assertEquals(2, node.getChildnodes(uioId).size());
	}


	@Test(expected = UnauthorizedException.class)
	public void getChildnodesUnauthorized() throws UnauthorizedException {
		long tstId = node.create("tst", "Test");
		node.getChildnodes(tstId);
	}

	@Test
	public void getChildcourses() throws UnauthorizedException, NamingException {
		CourseNodeCommon course = testHelper.getCourseNodeCommon();
		long uioId = node.create("uio", "UiO");
		node.addNodeAdmin(uioId, homerId);
		long exphilId = course.create("exphil", "Exphil", uioId);

		List<Long> children = node.getChildcourses(uioId);
		assertEquals(1, children.size());
		assertEquals(exphilId, (long) children.get(0));

		course.create("a", "Aaaaa", uioId);
		assertEquals(2, node.getChildcourses(uioId).size());


	}

	@Test(expected = UnauthorizedException.class)
	public void getChildcoursesUnauthorized() throws UnauthorizedException {
		long tstId = node.create("tst", "Test");
		node.getChildcourses(tstId);
	}

	@Test
	public void remove() {
		long uioId = node.create("uio", "UiO");
		long matnatId = node.create("matnat", "Mat...", uioId);

		node.remove(uioId);
		assertFalse(node.exists(uioId));
		assertFalse(node.exists(matnatId));
	}

	@Test
	public void isNodeAdmin() {
		long uioId = node.create("uio", "UiO");
		long matnatId = node.create("matnat", "Mat...", uioId);
		node.addNodeAdmin(uioId, homerId);

		assertTrue(node.isNodeAdmin(uioId));
		assertTrue("NodeAdmin on a supernode should make the user " +
				"NodeAdmin on a subnode.",
				node.isNodeAdmin(matnatId));
	}

	@Test
	public void addNodeAdmin() {
		long uioId = node.create("uio", "UiO");

		node.addNodeAdmin(uioId, homerId);
		assertTrue(node.isNodeAdmin(uioId));

		int adminCount = node.getNodeAdmins(uioId).size();

		// Test duplicates
		node.addNodeAdmin(uioId, homerId);
		assertEquals(adminCount, node.getNodeAdmins(uioId).size());
	}

	@Test
	public void removeNodeAdmin() {
		long uioId = node.create("uio", "UiO");
		node.addNodeAdmin(uioId, homerId);

		assertTrue(node.isNodeAdmin(uioId));
		node.removeNodeAdmin(uioId, homerId);
		assertFalse(node.isNodeAdmin(uioId));
	}
}
