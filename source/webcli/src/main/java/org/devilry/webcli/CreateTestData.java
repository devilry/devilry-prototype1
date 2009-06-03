package org.devilry.webcli;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.FileDataBlockRemote;
import org.devilry.core.daointerfaces.FileMetaRemote;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.daointerfaces.UserRemote;

public class CreateTestData extends HttpServlet {

	protected static final String LOGNAME = CreateTestData.class.getName();

	@EJB
	protected UserRemote user;

	@EJB
	protected NodeRemote node;

	@EJB
	protected CourseNodeRemote course;

	@EJB
	protected PeriodNodeRemote period;

	@EJB
	protected AssignmentNodeRemote assignment;

	@EJB
	protected DeliveryRemote delivery;

	@EJB
	protected DeliveryCandidateRemote deliveryCandidate;

	@EJB
	protected FileMetaRemote fileMeta;
	
	@EJB
	protected FileDataBlockRemote fileDataBlock;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		long ednaId = user.create("Edna Krabappel", "edna@teacher.com", "13231241");
		long elisabethId = user.create("Elizabeth Hoover", "hoover@teacher.com", "4342321123213");
		long homerId = user.create("Homer J. Simpson", "homr@doh.com", "11223344");
		long margeId = user.create("Marge Simpson", "marge@doh.com", "11223344");
		long bartId = user.create("Bart Simpson", "bartman@doh.com", "11223344");
		long lisaId = user.create("Lisa Simpson", "lisa@president.com", "11223344");

		long uioId = node.create("uio", "Universitetet i Oslo");
		long matnatId = node.create("matnat",
				"Matematisk naturvitenskapelig fakultet", uioId);
		long ifiId = node.create("ifi", "Institutt for informatikk", matnatId);

		long inf1010Id = course.create("inf1010",
				"Object oriented programming", ifiId);

		long periodId = period.create("spring2009", "spring2009",
				new GregorianCalendar(2009, 0, 15).getTime(),
				new GregorianCalendar(2009, 0, 15).getTime(), inf1010Id);
		long a1 = assignment.create("oblig1", "Obligatory assignment 1",
				new GregorianCalendar(2009, 1, 5).getTime(), periodId);
		long d1 = delivery.create(a1);
		delivery.addStudent(d1, bartId);
		delivery.addExaminer(d1, ednaId);
		delivery.addExaminer(d1, elisabethId);

		long d2 = delivery.create(a1);
		delivery.addStudent(d2, lisaId);
		delivery.addExaminer(d2, ednaId);

		long d3 = delivery.create(a1);
		delivery.addStudent(d3, homerId);
		delivery.addStudent(d3, margeId);
		delivery.addExaminer(d3, elisabethId);
		long dc = deliveryCandidate.create(d3);
		long fm1 = fileMeta.create(dc, "/homers secret notes.txt");
		fileDataBlock.create(fm1, "look at bart!".getBytes());
		long fm2 = fileMeta.create(dc, "/the_answer.txt");
		fileDataBlock.create(fm2, "Lisa probably knows.. Or 42.".getBytes());

		request.getRequestDispatcher("ShowAll").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}