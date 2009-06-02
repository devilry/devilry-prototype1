package org.devilry.cli;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class ExampleClient {

	public static void main(String args[]) throws NamingException {
		new ExampleClient();
	}

	Context serverConnection;

	public ExampleClient() throws NamingException {
		serverConnection = getEmbeddedServerConnection();

		/*
		// Get the tree mananger bean
		// same as:
		// TreeManagerRemote tm = (TreeManagerRemote) serverConnection.lookup("TreeManagerImplRemote");
		TreeManagerRemote tm = getRemoteBean(TreeManagerImpl.class);

		// Add some nodes
		tm.addNode("uio", "Universitetet i Oslo");
		tm.addNode("matnat", "Matematisk...", tm.getNodeIdFromPath("uio"));
		tm.addNode("ifi", "Institutt for informatikk", tm.getNodeIdFromPath("uio.matnat"));

		// List nodes
		NodeRemote uio = getRemoteBean(NodeImpl.class);
		uio.init(tm.getNodeIdFromPath("uio"));
		for (long facultyId : uio.getChildren()) {
			NodeRemote faculty = getRemoteBean(NodeImpl.class);
			faculty.init(facultyId);
			System.out.println(faculty.getPath());
		}
		*/
	}

	public Context getEmbeddedServerConnection() throws NamingException {
		Properties p = new Properties();

		// Embed openejb
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");

		// Set bean naming (JNDI) format
		p.put("openejb.deploymentId.format",
				"{ejbName}{interfaceType.annotationName}");
		p.put("openejb.jndiname.format",
				"{ejbName}{interfaceType.annotationName}");

		return new InitialContext(p);
	}

	public Context getRemoteServerConnection() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.RemoteInitialContextFactory");
		p.put(Context.PROVIDER_URL, "ejbd://127.0.0.1:4201");

		return new InitialContext(p);
	}

	@SuppressWarnings("unchecked")
	protected <E> E getRemoteBean(Class<E> beanImplClass)
			throws NamingException {
		return (E) serverConnection.lookup(beanImplClass.getSimpleName() + "Remote");
	}
}