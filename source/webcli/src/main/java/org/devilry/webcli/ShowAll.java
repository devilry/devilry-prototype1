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
		b.append(String.format("<dt class='%s'>%s</dt>", t, info));
		b.append(String.format("<dd class='%s'><dl>", t));

		if (assignment.exists(nodeId)) {
			for(long id: assignment.getDeliveries(nodeId))
				addDelivery(b, id);
		} else {
			for (long id : node.getChildren(nodeId))
				addNode(b, id);
		}
		b.append("</dl></dd>");
	}

	private void addDelivery(StringBuffer b, long deliveryId) {
		b.append("<dt class='delivery'>delivery</dt>");
		b.append("<dd class='delivery'><dl>");

		createUserList(b, delivery.getStudents(deliveryId), "Students");
		createUserList(b, delivery.getExaminers(deliveryId), "Examiners");
		b.append("<dt>Delivery candidates</dt>");
		b.append("<dd><dl>");
		for(long id: delivery.getDeliveryCandidates(deliveryId))
			addDeliveryCandidate(b, id);
		b.append("</dl></dd>");

		b.append("</dl></dd>");
	}

	private void addDeliveryCandidate(StringBuffer b, long deliveryCandidateId) {
		String info = deliveryCandidate.getTimeOfDelivery(deliveryCandidateId).toString();
		b.append("<dt class='deliveryCandidate'>" + info + "</dt>");
		b.append("<dd class='deliveryCandidate'><dl>");
		for(long id: deliveryCandidate.getFiles(deliveryCandidateId))
			addFileMeta(b, id);
		b.append("</dl></dd>");
	}

	private void addFileMeta(StringBuffer b, long fileMetaId) {
		String info = fileMeta.getFilePath(fileMetaId);
		b.append("<dt class='fileMeta'>" + info + "</dt>");
		b.append("<dd class='fileMeta'>");
		for(long id: fileMeta.getFileDataBlocks(fileMetaId))
			addFileDataBlock(b, id);
		b.append("</dd>");		
	}

	private void addFileDataBlock(StringBuffer b, long fileDataBlockId) {
		String info = new String(fileDataBlock.getFileData(fileDataBlockId));
		b.append(info);
	}

	private void createUserList(StringBuffer b, List<Long> students, String head) {
		b.append("<dt>" + head + ":</dt>");
		b.append("<dd>");
		String sep = "";
		for(long userId: students) {
			b.append(sep + user.getName(userId));
			sep = ", ";
		}
		if(sep.equals(""))
			b.append("none");
		b.append("</dd>");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		StringBuffer b = new StringBuffer();
		b.append("<dl>");
		for (long nodeId : node.getToplevelNodes()) {
			addNode(b, nodeId);
		}
		b.append("</dl>");
		request.setAttribute("all", b.toString());
		request.getRequestDispatcher("Main").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}