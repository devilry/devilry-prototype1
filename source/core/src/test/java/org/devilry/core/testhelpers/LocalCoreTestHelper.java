package org.devilry.core.testhelpers;

import javax.naming.NamingException;
import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;

public class LocalCoreTestHelper extends CoreTestHelper {

	public LocalCoreTestHelper(String username, String password) throws NamingException {
		super(username, password);
	}

	public NodeCommon getNodeCommon() throws NamingException {
		return getLocalBean(NodeImpl.class);
	}

	public CourseNodeCommon getCourseNodeCommon() throws NamingException {
		return getLocalBean(CourseNodeImpl.class);
	}

	public PeriodNodeCommon getPeriodNodeCommon() throws NamingException {
		return getLocalBean(PeriodNodeImpl.class);
	}

	public AssignmentNodeCommon getAssignmentNodeCommon() throws NamingException {
		return getLocalBean(AssignmentNodeImpl.class);
	}

	public UserCommon getUserCommon() throws NamingException {
		return getLocalBean(UserImpl.class);
	}
}
