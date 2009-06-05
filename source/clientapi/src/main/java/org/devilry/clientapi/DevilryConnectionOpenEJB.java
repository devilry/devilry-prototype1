package org.devilry.clientapi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.FileDataBlockLocal;
import org.devilry.core.daointerfaces.FileDataBlockRemote;
import org.devilry.core.daointerfaces.FileMetaLocal;
import org.devilry.core.daointerfaces.FileMetaRemote;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.daointerfaces.UserLocal;
import org.devilry.core.daointerfaces.UserRemote;

public class DevilryConnectionOpenEJB implements DevilryConnection {
	
	protected InitialContext localCtx;

	public static InitialContext CTX = null;

	public static InitialContext getCtx() throws NamingException {
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

	protected void setupEjbContainer() throws NamingException {
		localCtx = getCtx();
	}

	@SuppressWarnings("unchecked")
	protected <E> E getLocalBean(Class<E> beanImplClass) throws NamingException {
		return (E) localCtx.lookup(beanImplClass.getSimpleName() + "Local");
	}

	@SuppressWarnings("unchecked")
	protected <E> E getRemoteBean(String className)
			throws NamingException {
		return (E) localCtx.lookup(className + "Remote");
	}	
	
	public AssignmentNodeLocal getAssignmentNode() throws NamingException {
		return getRemoteBean("AssignmentNodeImpl");
	}
	

	public CourseNodeLocal getCourseNode() throws NamingException {
		return getRemoteBean("CourseNodeImpl");
	}

	public DeliveryCandidateLocal getDeliveryCandidate() throws NamingException {
		return getRemoteBean("DeliveryCandidateImpl");
	}

	public DeliveryLocal getDelivery() throws NamingException {
		return getRemoteBean("DeliveryImpl");
	}

	public FileDataBlockLocal getFileDataBlock() throws NamingException {
		return getRemoteBean("FileDataBlockImpl");
	}

	public FileMetaLocal getFileMeta() throws NamingException {
		return getRemoteBean("FileMetaImpl");
	}

	public NodeLocal getNode() throws NamingException {
		return getRemoteBean("NodeImpl");
	}

	public PeriodNodeLocal getPeriodNode() throws NamingException {
		return getRemoteBean("PeriodNodeImpl");
	}

	public UserLocal getUser() throws NamingException {
		return getRemoteBean("UserImpl");
	}

}
