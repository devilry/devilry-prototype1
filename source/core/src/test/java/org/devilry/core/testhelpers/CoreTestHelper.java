package org.devilry.core.testhelpers;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.FileDataBlockCommon;
import org.devilry.core.daointerfaces.FileMetaCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;

public abstract class CoreTestHelper extends EjbTestHelper {

	public CoreTestHelper(String username, String password)
			throws NamingException {
		super(username, password);
	}


	public void clearUsersAndNodes()
			throws NamingException, NoSuchObjectException,
			UnauthorizedException {
		for(long nodeId: getNodeCommon().getToplevelNodes())
			getNodeCommon().remove(nodeId);
		for(long userId: getUserCommon().getUsers())
			getUserCommon().remove(userId);
	}

	public abstract UserCommon getUserCommon() throws NamingException;
	
	public abstract NodeCommon getNodeCommon() throws NamingException;

	public abstract CourseNodeCommon getCourseNodeCommon()
			throws NamingException;

	public abstract PeriodNodeCommon getPeriodNodeCommon()
			throws NamingException;

	public abstract AssignmentNodeCommon getAssignmentNodeCommon()
			throws NamingException;

	public abstract DeliveryCommon getDeliveryCommon()
			throws NamingException;

	public abstract DeliveryCandidateCommon getDeliveryCandidateCommon()
			throws NamingException;

	public abstract FileMetaCommon getFileMetaCommon()
			throws NamingException;

	public abstract FileDataBlockCommon getFileDataBlockCommon()
			throws NamingException;

}
