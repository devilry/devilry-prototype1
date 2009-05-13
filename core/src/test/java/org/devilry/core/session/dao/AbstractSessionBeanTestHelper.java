package org.devilry.core.session.dao;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.devilry.core.entity.DeliveryCandidateEntity;
import org.devilry.core.entity.FileMetaEntity;

/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
public abstract class AbstractSessionBeanTestHelper {
	protected static Class[] ENTITIES = {DeliveryCandidateEntity.class, FileMetaEntity.class};

	protected EntityManager entityManager;
	protected InitialContext localCtx;
	private TestBeanLocal testBean;

	protected void setupEjbContainer() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		p.put("openejb.deploymentId.format", "{ejbName}{interfaceType.annotationName}");
		p.put("openejb.jndiname.format", "{ejbName}{interfaceType.annotationName}");
		localCtx = new InitialContext(p);



		testBean = (TestBeanLocal) getLocalBean(TestBean.class);
		entityManager = testBean.getEntityManager();
	}


	protected void destroyEjbContainer() {
		testBean.clearDatabase();
	}

	protected Object getLocalBean(Class beanImplClass) throws NamingException {
		return localCtx.lookup(beanImplClass.getSimpleName() + "Local");
	}


	protected Object getRemoteBean(Class beanImplClass) throws NamingException {
		return localCtx.lookup(beanImplClass.getSimpleName() + "Remote");
	}



	@Stateless
	public static class TestBean implements TestBeanLocal {

		@PersistenceContext(unitName = "DevilryCore")
		private EntityManager entityManager;

		public EntityManager getEntityManager() {
			return entityManager;
		}

		@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
		public void clearDatabase() {
			for(Class entity: ENTITIES) {
				entityManager.createQuery(
						"DELETE FROM " + entity.getSimpleName()).executeUpdate();
			}
		}
	}

	public static interface TestBeanLocal {

		EntityManager getEntityManager();

		void clearDatabase();
	}
}
