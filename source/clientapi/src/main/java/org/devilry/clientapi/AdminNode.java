package org.devilry.clientapi;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public class AdminNode {

	DevilryConnection connection;
	
	UserCommon user;
	
	long nodeId;
	
	NodeCommon nodeBean;
	PeriodNodeCommon periodNodeBean;
	CourseNodeCommon courseNodeBean;
	
	AdminNode(long nodeId, DevilryConnection connection) {
		this.nodeId = nodeId;
		this.connection = connection;
	}
		
	protected UserCommon getUserBean() throws NamingException {
		return user == null ? user = connection.getUser() : user;
	}
	
	protected NodeCommon getNodeBean() throws NamingException {
		return nodeBean == null ? nodeBean = connection.getNode() : nodeBean;
	}
	
	protected CourseNodeCommon getCourseNodeBean() throws NamingException {
		return courseNodeBean == null ? courseNodeBean = connection.getCourseNode() : courseNodeBean;
	}
	
	public String getNodeName() throws NoSuchObjectException, NamingException, UnauthorizedException {
		return getNodeBean().getName(nodeId);
	}
	
	public String getNodeDisplayName() throws NoSuchObjectException, NamingException, UnauthorizedException {
		return getNodeBean().getDisplayName(nodeId);
	}	
		
	public void setNodeName(String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getNodeBean().setName(nodeId, newName);
	}

	public void setNodeDisplayName(String newDisplayName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getNodeBean().setDisplayName(nodeId, newDisplayName);
	}

	
	public List<AdminNode> getNodesWhereIsAdmin() throws UnauthorizedException, NamingException {
		
		List<Long> nodes = getNodeBean().getNodesWhereIsAdmin();
	
		List<AdminNode> adminNodes = new LinkedList<AdminNode>();
		
		for (long id : nodes) {
			adminNodes.add(new AdminNode(id, connection));
		}
		return adminNodes;
	}
	
	
	public List<AdminNode> getSubnodes() throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<Long> nodes = getNodeBean().getChildNodes(nodeId);
		
		List<AdminNode> subnodes = new LinkedList<AdminNode>();
		
		for (long id : nodes) {
			subnodes.add(new AdminNode(id, connection));
		}
		return subnodes;
	}
	
	public List<AdminCourse> getCourses() throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<Long> courses = getNodeBean().getChildCourses(nodeId);
		
		List<AdminCourse> courseList = new LinkedList<AdminCourse>();
		
		for (long id : courses) {
			courseList.add(new AdminCourse(id, connection));
		}
		return courseList;
	}
	
	public AdminNode addSubnode(String name, String displayName) throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		long subnodeId = getNodeBean().create(name, displayName, nodeId);
		return new AdminNode(subnodeId, connection);
	}

	
	public void removeSubnode(AdminNode node) throws NoSuchObjectException, NamingException, UnauthorizedException {
		getNodeBean().remove(node.nodeId);
	}

	public AdminCourse addCourse(String courseName, String courseDisplayName) throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		long courseId = getCourseNodeBean().create(courseName, courseDisplayName, nodeId);
		return new AdminCourse(courseId, connection);
	}

	public void removeCourse(AdminCourse course) throws NoSuchObjectException, NamingException, UnauthorizedException {
		getCourseNodeBean().remove(course.courseId);
	}
	
	public void addNodeAdmin(long userId) throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, NamingException {
		getNodeBean().addNodeAdmin(nodeId, userId);
	}
	
	public void removeNodeAdmin(long userId) throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, NamingException {
		getNodeBean().removeNodeAdmin(nodeId, userId);
	}
	
	public List<Long> getNodeAdmins() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> admins = getNodeBean().getNodeAdmins(nodeId);
		return admins;
	}
		
	public NodePath getPath() throws NamingException, NoSuchObjectException, InvalidNameException, UnauthorizedException {
		return getNodeBean().getPath(nodeId);
	}
}
