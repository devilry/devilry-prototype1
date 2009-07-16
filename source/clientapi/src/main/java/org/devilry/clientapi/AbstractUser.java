package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public abstract class AbstractUser<E extends AbstractPeriod<?>> extends AbstractCourse<E> {
	
	UserCommon user;
	long userId;
	
	PeriodNodeCommon periodNode;
	
	AbstractUser(long userId, DevilryConnection connection) {
		super(connection);
		this.userId = userId;
	}
		
	protected PeriodNodeCommon getPeriodNodeBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
		
}
