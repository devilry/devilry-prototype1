package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public class AdminNode {

	DevilryConnection connection;
	
	UserCommon user;
	
	long nodeId;
	
	NodeCommon nodeBean;
	
	PeriodNodeCommon periodNode;
	
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
	
	public void changeSubnodeName(AdminNode node, String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<AdminNode> subnodes = getSubnodes();
		
		if (subnodes.contains(node)) {
			getNodeBean().setName(node.nodeId, newName);
		}
		else {
			System.err.println("Does not contain subnode with id:" + node.nodeId);
		}
	}

	public void changeSubnodeDisplayName(AdminNode node, String newDisplayName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<AdminNode> subnodes = getSubnodes();
		
		if (subnodes.contains(node)) {
			getNodeBean().setDisplayName(node.nodeId, newDisplayName);
		}
		else {
			System.err.println("Does not contain subnode with id:" + node.nodeId);
		}
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
	
	
	public AdminNode addSubnode(String name, String displayName) throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		long subnodeId = getNodeBean().create(name, displayName, nodeId);
		return new AdminNode(subnodeId, connection);
	}

	
	public void removeSubnode(AdminNode node) throws NoSuchObjectException, NamingException {
		getNodeBean().remove(node.nodeId);
	}

	public void addCourse(String courseName, String courseDisplayName) {
		
	}

	public void removeCourse() {
		
	}

	public void changeCourseName() {
		
	}

	public void changeCourseDisplayName() {
		
	}


}
