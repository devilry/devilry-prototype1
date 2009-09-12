package org.devilry.clientapi;

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


public class SuperAdmin {

	DevilryConnection connection;
	
	UserCommon user;
	long userId;
	
	NodeCommon nodeBean;
	
	PeriodNodeCommon periodNode;
	
	SuperAdmin(long userId, DevilryConnection connection) {
		this.userId = userId;
		this.connection = connection;
	}
		
	protected UserCommon getUserBean() throws NamingException {
		return user == null ? user = connection.getUser() : user;
	}
	
	protected NodeCommon getNodeBean() throws NamingException {
		return nodeBean == null ? nodeBean = connection.getNode() : nodeBean;
	}
	
	public boolean isSuperAdmin(long userId) throws NamingException, UnauthorizedException {
		return getUserBean().isSuperAdmin(userId);
	}
	
	public void setIsSuperAdmin(long userId, boolean value) throws NamingException, UnauthorizedException {
		getUserBean().setIsSuperAdmin(userId, value);
	}

	
	public AdminNode addTopLevelNode(String name, String displayName) throws PathExistsException, UnauthorizedException, InvalidNameException, NamingException {
		long nodeId = getNodeBean().create(name, displayName);
		AdminNode admin = new AdminNode(nodeId, connection);
		return admin;
	}
	
	public void removeTopLevelNode(AdminNode node) throws NoSuchObjectException, NamingException, UnauthorizedException {
		getNodeBean().remove(node.nodeId);
	}
	
	public List<AdminNode> getTopLevelNodes() throws UnauthorizedException, NamingException {
		List<Long> rootNodes = getNodeBean().getToplevelNodes();
		List<AdminNode> adminNodes = new LinkedList<AdminNode>();
		
		for (long id : rootNodes) {
			adminNodes.add(new AdminNode(id, connection));
		}
		return adminNodes;
		
	}
	
	public void removeNonEmptyNode(AdminNode node) throws NoSuchObjectException, NamingException, UnauthorizedException {
		getNodeBean().remove(node.nodeId);
	}
	
	public long addUser(String name, String email, String phoneNumber) throws NamingException, UnauthorizedException {
		return getUserBean().create(name, email, phoneNumber);
	}
	
	public void addIdentity(long userId, String identity) throws NamingException, UnauthorizedException {
		getUserBean().addIdentity(userId, identity);
	}
	
	public void removeUser(long userId) throws NamingException, UnauthorizedException {
		getUserBean().remove(userId);
	}
	
	public boolean userExists(long userId) throws NamingException, UnauthorizedException {
		return getUserBean().userExists(userId);
	}
	
	public long findUser(String identity) throws NamingException, UnauthorizedException {
		return getUserBean().findUser(identity);
	}
}
