package org.devilry.core.testhelpers;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import org.devilry.core.authorize.AuthorizeNode;
import org.junit.Test;

public  class EjbExampleTest {

	private final static Logger log = LoggerFactory.getLogger(EjbExampleTest.class);
	
	@Test
	public void hmm() throws NamingException {
		EjbTestHelper a = new EjbTestHelper("homer", "doh");
		TullLocal t1 = a.getLocalBean(TullImpl.class);
		t1.tst();

		EjbTestHelper a2 = new EjbTestHelper("marge", "blue");
		TullLocal t2 = a2.getLocalBean(TullImpl.class);
		t2.tst();
		
		t1 = a.getLocalBean(TullImpl.class);
		t1.tst();
	}

	@Local
	public static interface TullLocal {
		void tst();
	}

	@Stateless
	public static class TullImpl implements TullLocal {

		@Resource
		protected SessionContext sessionCtx;

		public TullImpl() {
			
		}

		public void tst() {
			log.info("Authenticated user: {}", sessionCtx.getCallerPrincipal());
		}
	}
}
