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
		DeliveryRemote delivery = (DeliveryRemote) ctx.lookup("org.devilry.core.DeliveryRemote");

		// Create a delivery candidate and add it to the delivery
		DeliveryCandidateNode deliveryCand = new DeliveryCandidateNode();
		deliveryCand.addFile("/hello/world.txt", "Hello world!".getBytes());
		deliveryCand.addFile("/hello/norsk.txt", "Hei verden!".getBytes());
		deliveryCand.addFile("/test", "This is a test".getBytes());
		long dcId = delivery.add(deliveryCand); // uploads it to the server

		// Get the newly uploaded deliveryCandidate from the server
		deliveryCand = delivery.getFull(dcId);
		System.out.println("Created files (delivery-candidate-id::path::contents)");
		for (FileNode f : deliveryCand.getFiles()) {
			System.out.printf("   %s::%s::%s%n", dcId, f.getPath(), new String(f.getData()));
		}

		// List files without fetching file-contents from the server
		DeliveryCandidateNode deliveryCandSmall = delivery.get(dcId);
		System.out.println("Another listing.. Only paths this time:)");
		for (String filePath : delivery.getFilePaths(deliveryCandSmall)) {
			System.out.println("   " + filePath);
		}

		// You can retreive only a single file,,
		FileNode n = delivery.getFile(deliveryCandSmall, "/hello/world.txt");
		System.out.printf("The contents of '%s' in delivery candidate '%d':%n   %n",
				n.getPath(), deliveryCandSmall.getId(), new String(n.getData()));

	}
}