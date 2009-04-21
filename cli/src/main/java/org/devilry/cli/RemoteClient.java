package org.devilry.cli;

import java.io.FileInputStream;
import javax.naming.*;
import org.devilry.core.bendik.ClientRemote;
import org.devilry.core.DeliveryCandidate;
import org.devilry.core.bendik.RemoteFileBean;
import org.devilry.core.bendik.FileOutputTransferStream;
import org.devilry.core.bendik.FileOutputTransferStreamInterface;

public class RemoteClient {

	public static void main (String args[]) throws Exception {
        Context ctx = new InitialContext();
        Object obj = ctx.lookup("DeliveryCandidateBeanRemote");
        System.out.println("obj:" + obj);

        DeliveryCandidate delivery = (DeliveryCandidate) obj;
        //System.out.println(e.addFile("Hello world!").getPath());

       // FileOutputTransferStreamInterface filebean = e.getRemoteFile("path");

        byte [] data = new byte [] {1,2,3,4,5,6,7,8,9};

        delivery.addFile("NewFileToSave", data);

        // filebean.write(data, 0, data.length);
      
    //  System.err.println("Status:" + e.getStatus());
    }

}