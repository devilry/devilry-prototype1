package org.devilry.clientapi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.devilry.core.dao.AssignmentNodeImpl;
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.DeliveryCandidateImpl;
import org.devilry.core.dao.DeliveryImpl;
import org.devilry.core.dao.FileDataBlockImpl;
import org.devilry.core.dao.FileMetaImpl;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.FileDataBlockCommon;
import org.devilry.core.daointerfaces.FileMetaCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public class DevilryConnectionOpenEJB implements DevilryConnection {
	
	InitialContext context;
	
	DevilryConnectionOpenEJB(String username, String password) throws NamingException {
		setUpInitialContext(username, password);
	}
	
	private void setUpInitialContext(String username, String password) throws NamingException {
		
		if (context == null) {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.openejb.client.LocalInitialContextFactory");
			p.put("openejb.deploymentId.format",
					"{ejbName}{interfaceType.annotationName}");
			p.put("openejb.jndiname.format",
					"{ejbName}{interfaceType.annotationName}");
			p.put(Context.SECURITY_PRINCIPAL, username);
			p.put(Context.SECURITY_CREDENTIALS, password);
			
			context = new InitialContext(p);
		}
	}
	

	@SuppressWarnings("unchecked")
	protected <E> E getRemoteBean(String className)
			throws NamingException {
		return (E) context.lookup(className + "Remote");
	}	
	

	@SuppressWarnings("unchecked")
	public <E> E getRemoteBean(Class<E> beanImplClass)
			throws NamingException {
		return (E) context.lookup(beanImplClass.getSimpleName() + "Remote");
	}
	
	public NodeCommon getNode() throws NamingException {
		return getRemoteBean(NodeImpl.class);
	}

	public CourseNodeCommon getCourseNode() throws NamingException {
		return getRemoteBean(CourseNodeImpl.class);
	}

	public PeriodNodeCommon getPeriodNode() throws NamingException {
		return getRemoteBean(PeriodNodeImpl.class);
	}

	public AssignmentNodeCommon getAssignmentNode() throws NamingException {
		return getRemoteBean(AssignmentNodeImpl.class);
	}

	public UserCommon getUser() throws NamingException {
		return getRemoteBean(UserImpl.class);
	}

	public DeliveryCommon getDelivery() throws NamingException {
		return getRemoteBean(DeliveryImpl.class);
	}

	public DeliveryCandidateCommon getDeliveryCandidate()
			throws NamingException {
		return getRemoteBean(DeliveryCandidateImpl.class);
	}

	public FileMetaCommon getFileMeta() throws NamingException {
		return getRemoteBean(FileMetaImpl.class);
	}
	
	public FileDataBlockCommon getFileDataBlock() throws NamingException {
		return getRemoteBean(FileDataBlockImpl.class);
	}

}
