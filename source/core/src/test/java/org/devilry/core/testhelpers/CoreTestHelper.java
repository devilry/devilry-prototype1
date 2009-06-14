package org.devilry.core.testhelpers;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;

public abstract class CoreTestHelper extends EjbTestHelper {

	public CoreTestHelper(String username, String password)
			throws NamingException {
		super(username, password);
	}


	public void clearUsersAndNodes()
			throws NamingException, NoSuchObjectException {
		for(long nodeId: getNodeCommon().getToplevelNodes())
			getNodeCommon().remove(nodeId);
		for(long userId: getUserCommon().getUsers())
			getUserCommon().remove(userId);
	}

	public abstract NodeCommon getNodeCommon() throws NamingException;

	public abstract CourseNodeCommon getCourseNodeCommon()
			throws NamingException;

	public abstract PeriodNodeCommon getPeriodNodeCommon()
			throws NamingException;

	public abstract AssignmentNodeCommon getAssignmentNodeCommon()
			throws NamingException;

	public abstract UserCommon getUserCommon() throws NamingException;
}
