package org.devilry.cli;

import javax.naming.*;
import org.devilry.core.ExampleRemote;

public class ExampleClient {
	public static void main(String args[]) throws Exception {
        Context ctx = new InitialContext();
        ExampleRemote e = (ExampleRemote) ctx.lookup("Example");
        System.out.println(e.test());
	}
}