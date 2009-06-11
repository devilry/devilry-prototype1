package org.devilry.core.testhelpers;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.devilry.core.daointerfaces.UserLocal;
import org.devilry.core.daointerfaces.UserRemote;
import org.junit.Test;

public  class ExampleTest {

	@Test
	public void hmm() throws NamingException {
		EjbTestHelper a = new EjbTestHelper("homer", "doh");
		TullLocal t1 = a.getLocalBean(TullImpl.class);
		t1.tst();

		EjbTestHelper a2 = new EjbTestHelper("marge", "blue");
		TullLocal t2 = a2.getLocalBean(TullImpl.class);
		t2.tst();
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
			System.out.println("########## " + sessionCtx.getCallerPrincipal());
		}
	}
}
