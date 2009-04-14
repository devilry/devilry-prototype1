package org.devilry.cli;

import javax.naming.*;
import org.devilry.core.ClientRemote;

public class RemoteClient {
	public static void main(String args[]) throws Exception {
        Context ctx = new InitialContext();
        ClientRemote e = (ClientRemote) ctx.lookup("ClientBeanRemote");
        System.out.println(e.addFile("Hello world!").getPath());
	}
}