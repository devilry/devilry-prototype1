package org.devilry.cli;


import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.DeliveryRemote;
import org.devilry.core.FileNode;


public class ExampleClient {

	public static void main(String args[]) throws Exception {
		Context ctx = new InitialContext();

		// https://glassfish.dev.java.net/javaee5/ejb/EJB_FAQ.html#SessionBeanGlobalJNDINameAssignment
		DeliveryRemote dm = (DeliveryRemote) ctx.lookup("org.devilry.core.DeliveryRemote");

		String filename = args[0];

		DeliveryCandidateNode dir = new DeliveryCandidateNode();
		dir.addFile(filename, "Hello world".getBytes());
		long id = dm.add(dir);
		System.out.printf("Added DeliveryCandidate with id %d.%n", id);


		DeliveryCandidateNode d = dm.getFull(id);
		for(FileNode n: d.getFiles())
			System.out.printf("   - %s:%s%n", n.getPath(), new String(n.getData()));

		FileNode n = dm.getFile(d, filename);
		System.out.printf(">>> %s:%s%n", n.getPath(), new String(n.getData()));
	}
}