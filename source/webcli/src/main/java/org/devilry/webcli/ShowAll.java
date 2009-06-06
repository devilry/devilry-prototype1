package org.devilry.webcli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

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
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.daointerfaces.UserRemote;

public class ShowAll extends HttpServlet {

	protected static final String LOGNAME = ShowAll.class.getName();

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

	private void addNode(StringBuffer b, long nodeId) {
		String t = "Node";
		if (course.exists(nodeId))
			t = "CourseNode";
		if (period.exists(nodeId))
			t = "PeriodNode";
		if (assignment.exists(nodeId))
			t = "AssignmentNode";

		String info = String.format("%s:%s (%s)", node.getName(nodeId), node
				.getDisplayName(nodeId), t);
		b.append(String.format("<div class='%s'>", t));
		b.append(String.format("<h2>%s</h2>", info));
		
		if(period.exists(nodeId)) {
			createUserList(b, period.getStudents(nodeId), "Students");
			createUserList(b, period.getExaminers(nodeId), "Examiners");

			b.append("<div class='subsection'>");
			b.append("<h3>Assignments:</h3>");
			for (long id : node.getChildcourses(nodeId))
				addNode(b, id);			
		} else if (assignment.exists(nodeId)) {
			for (long id : assignment.getDeliveries(nodeId))
				addDelivery(b, id);
		} else {
			for (long id : node.getChildcourses(nodeId))
				addNode(b, id);
		}
		b.append("</div>");
	}

	private void addDelivery(StringBuffer b, long deliveryId) {
		b.append("<div class='delivery'>");
		b.append("<h2>Delivery</h2>");

		createUserList(b, delivery.getStudents(deliveryId), "Students");
		createUserList(b, delivery.getExaminers(deliveryId), "Examiners");

		b.append("<div class='subsection'>");
		b.append("<h3>Delivery candidates:</h3>");
		List<Long> dc = delivery.getDeliveryCandidates(deliveryId);
		if (dc.isEmpty())
			b.append("none");
		else {
			for (long id : dc)
				addDeliveryCandidate(b, id);
		}
		b.append("</div>");

		b.append("</div>");
	}

	private void addDeliveryCandidate(StringBuffer b, long deliveryCandidateId) {
		String info = deliveryCandidate.getTimeOfDelivery(deliveryCandidateId)
				.toString();
		b.append("<div class='deliveryCandidate'>");
		b.append("<h2>" + info + "</h2>");
		for (long id : deliveryCandidate.getFiles(deliveryCandidateId))
			addFileMeta(b, id);
		b.append("</div>");
	}

	private void addFileMeta(StringBuffer b, long fileMetaId) {
		String info = fileMeta.getFilePath(fileMetaId);
		b.append("<div class='fileMeta'>");
		b.append("<h2>" + info + "</h2><pre>");
		for (long id : fileMeta.getFileDataBlocks(fileMetaId))
			addFileDataBlock(b, id);
		b.append("</pre></div>");
	}

	private void addFileDataBlock(StringBuffer b, long fileDataBlockId) {
		String info = new String(fileDataBlock.getFileData(fileDataBlockId));
		b.append(info);
	}

	private void createUserList(StringBuffer b, List<Long> students, String head) {
		b.append("<div class='subsection'>");
		b.append("<h3>" + head + ":</h3>");
		String sep = "";
		for (long userId : students) {
			b.append(sep + user.getName(userId));
			sep = ", ";
		}
		if (sep.equals(""))
			b.append("none");
		b.append("</div>");
	}
	
	private void addUsers(StringBuffer b) {
		b.append("<div class='users'>");
		b.append("<h2>Users</h2>");
		for(long userId: user.getUsers())
			addUser(b, userId);
		b.append("</div>");
	}

	private void addUser(StringBuffer b, long userId) {
		b.append(String.format("<div class='user'><h2>%s</h2>", user.getName(userId)));
		b.append(String.format("<div class='subsection'><h3>Email</h3>%s</div>", user.getEmail(userId)));
		b.append(String.format("<div class='subsection'><h3>Phone number</h3>%s</div>", user.getPhoneNumber(userId)));
		
		String sep = "";
		String identities = "";
		for(String id: user.getIdentities(userId)) {
			identities += sep + id;
			sep = ", ";
		}
		if(identities.equals(""))
			identities = "none";
		b.append(String.format("<div class='subsection'><h3>Identities</h3>%s</div>", identities));
		b.append("</div>");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		StringBuffer b = new StringBuffer();
		b.append("<div id='tree'>");
		addUsers(b);
		for (long nodeId : node.getToplevelNodes()) {
			addNode(b, nodeId);
		}
		b.append("</div>");
		request.setAttribute("all", b.toString());
		request.getRequestDispatcher("Main").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}