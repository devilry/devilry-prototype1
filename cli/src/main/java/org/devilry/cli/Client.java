package org.devilry.cli;

import javax.naming.*;
import java.util.Properties;
import com.sun.appserv.security.ProgrammaticLogin;
import org.devilry.core.ConnectionRemote;	

public class Client {
	public static void main(String args[]) throws Exception {
		ProgrammaticLogin pl = new ProgrammaticLogin();
		pl.login("morteoh", "morteoh");

		try {
			Properties p = new Properties();
			p.setProperty("org.omg.CORBA.ORBInitialHost", "macfoo");
			p.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

			InitialContext ctx = new InitialContext(p);
			ConnectionRemote cr = (ConnectionRemote) ctx.lookup("ConnectionImplRemote");

			System.out.println(cr.test());
		} catch (Exception e) {
			System.err.println("Got exception... not allowed to access object.");
			System.exit(-1);
		}

		pl.logout();
	}
}