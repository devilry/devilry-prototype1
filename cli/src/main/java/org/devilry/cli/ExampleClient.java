package org.devilry.cli;


import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.DeliveryRemote;


public class ExampleClient {

	public static void main(String args[]) throws Exception {
		Context ctx = new InitialContext();
		DeliveryRemote dm = (DeliveryRemote) ctx.lookup("DeliveryBean");

		String filename = args[0];

		DeliveryCandidateNode dir = new DeliveryCandidateNode();
		dir.addFile(filename, "Hello world".getBytes());
		long id = dm.add(dir);
		System.out.println(id);
	}
}