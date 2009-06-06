package org.devilry.clientapi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
	
	protected InitialContext localCtx;

	public static InitialContext CTX = null;

	public static InitialContext createCtx() throws NamingException {
		if (CTX == null) {
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.openejb.client.LocalInitialContextFactory");
			p.put("openejb.deploymentId.format",
					"{ejbName}{interfaceType.annotationName}");
			p.put("openejb.jndiname.format",
					"{ejbName}{interfaceType.annotationName}");
			p.put(Context.SECURITY_PRINCIPAL, "homer");
			p.put(Context.SECURITY_CREDENTIALS, "doh");
			
			CTX = new InitialContext(p);
		}
		return CTX;
	}

	protected InitialContext setupEjbContainer() throws NamingException {
		return localCtx = createCtx();
	}

	InitialContext getCtx() throws NamingException {
		return localCtx != null ? localCtx : setupEjbContainer();
	}
	
	@SuppressWarnings("unchecked")
	protected <E> E getLocalBean(Class<E> beanImplClass) throws NamingException {
		return (E) getCtx().lookup(beanImplClass.getSimpleName() + "Local");
	}

	@SuppressWarnings("unchecked")
	protected <E> E getRemoteBean(String className)
			throws NamingException {
		return (E) getCtx().lookup(className + "Remote");
	}	
	
	public AssignmentNodeCommon getAssignmentNode() throws NamingException {
		return getRemoteBean("AssignmentNodeImpl");
	}
	

	public CourseNodeCommon getCourseNode() throws NamingException {
		return getRemoteBean("CourseNodeImpl");
	}

	public DeliveryCandidateCommon getDeliveryCandidate() throws NamingException {
		return getRemoteBean("DeliveryCandidateImpl");
	}

	public DeliveryCommon getDelivery() throws NamingException {
		return getRemoteBean("DeliveryImpl");
	}

	public FileDataBlockCommon getFileDataBlock() throws NamingException {
		return getRemoteBean("FileDataBlockImpl");
	}

	public FileMetaCommon getFileMeta() throws NamingException {
		return getRemoteBean("FileMetaImpl");
	}

	public NodeCommon getNode() throws NamingException {
		return getRemoteBean("NodeImpl");
	}

	public PeriodNodeCommon getPeriodNode() throws NamingException {
		return getRemoteBean("PeriodNodeImpl");
	}

	public UserCommon getUser() throws NamingException {
		return getRemoteBean("UserImpl");
	}

}
