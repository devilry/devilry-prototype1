package org.devilry.core.authorize;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthorizeDeliveryCommonTest {

	protected static CoreTestHelper testHelper;
	protected static CoreTestHelper superTestHelper;
	private long userId, userId2, superId;
	private long oblig1Id, delivery1Id;

	@Before
	public void setUp() throws Exception {
		NodeCommon superNode = superTestHelper.getNodeCommon();
		UserCommon superUser = superTestHelper.getUserCommon();

		superId = superUser.create("Homer Simpson", "homr@stuff.org", "123");
		superUser.setIsSuperAdmin(superId, true);
		superUser.addIdentity(superId, "homer");

		userId = superUser.create("Marge Simpson", "marge@stuff.org", "123");
		superUser.addIdentity(userId, "marge");

		userId2 = superUser.create("Bart Simpson", "bart@stuff.org", "123");
		superUser.addIdentity(userId2, "bart");

		long uioId = superNode.create("uio", "UiO");
		CourseNodeCommon course = superTestHelper.getCourseNodeCommon();
		long inf1000Id = course.create("inf1000", "INF1000", uioId);

		Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		PeriodNodeCommon period = superTestHelper.getPeriodNodeCommon();
		long spring09Id = period.create("spring09", "spring09",
			start.getTime(), end.getTime(), inf1000Id);

		Calendar deadline = new GregorianCalendar(2009, 05, 05);
		AssignmentNodeCommon assignment =
			superTestHelper.getAssignmentNodeCommon();
		oblig1Id = assignment.create("oblig1", "Oblig1",
			deadline.getTime(), spring09Id);

//		DeliveryCommon delivery = superTestHelper.getDeliveryCommon();
//		delivery1Id = delivery.create(oblig1Id);
	}

	@After
	public void tearDown() throws Exception {
		superTestHelper.clearUsersAndNodes();
	}

	@Test
	public void noAuthMethods() throws Exception {
//		DeliveryCommon delivery = testHelper.getDeliveryCommon();
//		delivery.exists(delivery1Id);
//		delivery.getAssignment(delivery1Id);
//		delivery.getDeliveriesWhereIsExaminer();
//		delivery.getDeliveriesWhereIsStudent();
	}

	/** Test that a user can create a Delivery if Admin on the assignment. */
//	@Test
//	public void create() throws Exception {
//		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
//			oblig1Id, userId);
//		testHelper.getDeliveryCommon().create(oblig1Id);
//	}

	/** Test that a user cannot create a node if not Admin on the parent
	 * node. */
//	@Test(expected=UnauthorizedException.class)
//	public void createUnauthorized() throws Exception {
//		testHelper.getDeliveryCommon().create(oblig1Id);
//	}
}
