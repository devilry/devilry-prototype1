package org.devilry.core.authorize;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.*;
import org.devilry.core.testhelpers.CoreTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AuthorizeDeliveryCommonTest {

	protected static CoreTestHelper testHelper;
	protected static CoreTestHelper superTestHelper;
	private long userId, unAuthUser, superId;
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

		unAuthUser = superUser.create("Bart Simpson", "bart@stuff.org", "123");
		superUser.addIdentity(unAuthUser, "bart");

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

		DeliveryCommon delivery = superTestHelper.getDeliveryCommon();
		delivery1Id = delivery.create(oblig1Id);

		// Required for testing of the functions returning a single delivery
		// candidate.
		DeliveryCandidateCommon deliveryCandidate =
				superTestHelper.getDeliveryCandidateCommon();
		deliveryCandidate.create(delivery1Id);
	}

	@After
	public void tearDown() throws Exception {
		superTestHelper.clearUsersAndNodes();
	}

	@Test
	public void noAuthMethods() throws Exception {
		DeliveryCommon delivery = testHelper.getDeliveryCommon();
		delivery.exists(delivery1Id);
		delivery.getAssignment(delivery1Id);
		delivery.getDeliveriesWhereIsExaminer();
		delivery.getDeliveriesWhereIsStudent();
	}


	/////////////////////////////////////////////////////
	// assignmentAdminMethods
	/////////////////////////////////////////////////////

	@Test
	public void addExaminer() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		testHelper.getDeliveryCommon().addExaminer(delivery1Id, userId);
	}

	@Test(expected = UnauthorizedException.class)
	public void addExaminerUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().addExaminer(delivery1Id, userId);
	}


	@Test
	public void addStudent() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		testHelper.getDeliveryCommon().addStudent(delivery1Id, userId);
	}

	@Test(expected = UnauthorizedException.class)
	public void addStudentUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().addStudent(delivery1Id, userId);
	}


	@Test
	public void create() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		testHelper.getDeliveryCommon().create(oblig1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void createUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().create(oblig1Id);
	}


	@Test
	public void getExaminers() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		testHelper.getDeliveryCommon().getExaminers(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void getExaminersUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().getExaminers(delivery1Id);
	}


	@Test
	public void getStudents() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		testHelper.getDeliveryCommon().getStudents(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void getStudentsUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().getStudents(delivery1Id);
	}


	@Test
	public void remove() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		testHelper.getDeliveryCommon().remove(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void removeUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().remove(delivery1Id);
	}


	@Test
	public void removeExaminer() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		superTestHelper.getDeliveryCommon()
				.addExaminer(delivery1Id, unAuthUser);
		testHelper.getDeliveryCommon().removeExaminer(delivery1Id, unAuthUser);
	}

	@Test(expected = UnauthorizedException.class)
	public void removeExaminerUnauthorized() throws Exception {
		superTestHelper.getDeliveryCommon()
				.addExaminer(delivery1Id, unAuthUser);
		testHelper.getDeliveryCommon().removeExaminer(delivery1Id, unAuthUser);
	}


	@Test
	public void removeStudent() throws Exception {
		superTestHelper.getAssignmentNodeCommon().addAssignmentAdmin(
				oblig1Id, userId);
		superTestHelper.getDeliveryCommon()
				.addExaminer(delivery1Id, unAuthUser);
		testHelper.getDeliveryCommon().removeStudent(delivery1Id, unAuthUser);
	}

	@Test(expected = UnauthorizedException.class)
	public void removeStudentUnauthorized() throws Exception {
		superTestHelper.getDeliveryCommon()
				.addExaminer(delivery1Id, unAuthUser);
		testHelper.getDeliveryCommon().removeStudent(delivery1Id, unAuthUser);
	}


	////////////////////////////////////////////
	// examinerMethods
	////////////////////////////////////////////

	@Test
	public void isExaminer() throws Exception {
		superTestHelper.getDeliveryCommon().addExaminer(
				delivery1Id, userId);
		testHelper.getDeliveryCommon().isExaminer(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void isExaminerUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().isExaminer(delivery1Id);
	}


	////////////////////////////////////////////
	// studentMethods
	////////////////////////////////////////////

	@Test
	public void isStudent() throws Exception {
		superTestHelper.getDeliveryCommon().addStudent(delivery1Id, userId);
		testHelper.getDeliveryCommon().isStudent(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void isStudentUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().isStudent(delivery1Id);
	}


	///////////////////////////////////////////
	// anyMethods
	///////////////////////////////////////////

	@Test
	public void getDeliveryCandidatesAsStudent() throws Exception {
		superTestHelper.getDeliveryCommon().addStudent(delivery1Id, userId);
		testHelper.getDeliveryCommon().getDeliveryCandidates(delivery1Id);
	}

	@Test
	public void getDeliveryCandidatesAsExaminer() throws Exception {
		superTestHelper.getDeliveryCommon().addExaminer(delivery1Id, userId);
		testHelper.getDeliveryCommon().getDeliveryCandidates(delivery1Id);
	}

	@Test
	public void getDeliveryCandidatesAsAssignmentAdmin() throws Exception {
		superTestHelper.getAssignmentNodeCommon()
				.addAssignmentAdmin(oblig1Id, userId);
		testHelper.getDeliveryCommon().getDeliveryCandidates(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void getDeliveryCandidatesAsStudentUnauthorized() throws Exception {
		testHelper.getDeliveryCommon().getDeliveryCandidates(delivery1Id);
	}


	@Test
	public void getLastDeliveryCandidateAsStudent() throws Exception {
		superTestHelper.getDeliveryCommon().addStudent(delivery1Id, userId);
		testHelper.getDeliveryCommon().getLastDeliveryCandidate(delivery1Id);
	}

	@Test
	public void getLastDeliveryCandidateAsExaminer() throws Exception {
		superTestHelper.getDeliveryCommon().addExaminer(delivery1Id, userId);
		testHelper.getDeliveryCommon().getLastDeliveryCandidate(delivery1Id);
	}

	@Test
	public void getLastDeliveryCandidateAsAssignmentAdmin() throws Exception {
		superTestHelper.getAssignmentNodeCommon()
				.addAssignmentAdmin(oblig1Id, userId);
		testHelper.getDeliveryCommon().getLastDeliveryCandidate(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void getLastDeliveryCandidateAsStudentUnauthorized()
			throws Exception {
		testHelper.getDeliveryCommon().getLastDeliveryCandidate(delivery1Id);
	}


	@Test
	public void getLastValidDeliveryCandidateAsStudent() throws Exception {
		superTestHelper.getDeliveryCommon().addStudent(delivery1Id, userId);
		testHelper.getDeliveryCommon()
				.getLastValidDeliveryCandidate(delivery1Id);
	}

	@Test
	public void getLastValidDeliveryCandidateAsExaminer() throws Exception {
		superTestHelper.getDeliveryCommon().addExaminer(delivery1Id, userId);
		testHelper.getDeliveryCommon()
				.getLastValidDeliveryCandidate(delivery1Id);
	}

	@Test
	public void getLastValidDeliveryCandidateAsAssignmentAdmin()
			throws Exception {
		superTestHelper.getAssignmentNodeCommon()
				.addAssignmentAdmin(oblig1Id, userId);
		testHelper.getDeliveryCommon()
				.getLastValidDeliveryCandidate(delivery1Id);
	}

	@Test(expected = UnauthorizedException.class)
	public void getLastValidDeliveryCandidateAsStudentUnauthorized()
			throws Exception {
		testHelper.getDeliveryCommon()
				.getLastValidDeliveryCandidate(delivery1Id);
	}
}