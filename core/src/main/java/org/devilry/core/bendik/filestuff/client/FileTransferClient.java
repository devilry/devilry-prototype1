package org.devilry.core.bendik.filestuff.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.bendik.filestuff.session.*;
import org.devilry.core.bendik.filestuff.entity.DevilryFile;
import javax.ejb.EJBException;
import javax.ejb.EJB; 

    public class FileTransferClient {
    @EJB
    private static FileTransferService fileTransferService;
    
    public static void main(String[] args) {

        try {
            int custId = 0;

            String filePath = "laban/file.txt";
            byte [] data = "Data".getBytes();
            
            try {
                custId = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.err.println("Invalid arguments entered, try again");
                System.exit(0);
            }

            fileTransferService.addFile(custId, filePath, data); //  add customer to database
            try
            {
              Thread.sleep(20000);
            } catch (Exception e) {
            }
       
            DevilryFile file = fileTransferService.findFile(custId);
            System.out.println("File:" + file);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

}


