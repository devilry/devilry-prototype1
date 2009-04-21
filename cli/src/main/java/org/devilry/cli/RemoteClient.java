package org.devilry.cli;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.naming.*;
import org.devilry.core.bendik.ClientRemote;
import org.devilry.core.DeliveryCandidate;
import org.devilry.core.bendik.RemoteFileBean;
import org.devilry.core.bendik.FileOutputTransferStream;
import org.devilry.core.bendik.FileOutputTransferStreamInterface;

public class RemoteClient {

	public static void main (String args[]) throws Exception {

        if (args.length < 2) {
            System.err.println("Too few arguments");
            System.err.println("USAGE:" +
                    "add filename"
                 );
           return;
        }

        new RemoteClient(args);
    }

     RemoteClient(String args []) throws FileNotFoundException {

        // Expect valid file in args[1]
        if (args[0].equalsIgnoreCase("add")) {

            if (!new File(args[1]).isFile()) {
               throw new FileNotFoundException("File named " + args[1] + " could not be found!");
            }

            byte [] data = getFileAsByteArray(new File(args[1]));

            addFile(data);
         }
        else {
             System.err.println("Invalid command " + args[0]);
             return;
        }

    }

    public void addFile(byte [] fileData) {
         try {

            DevilyCLILibrary lib = new DevilyCLILibrary();

            lib.addFile("NewFileToSave", fileData);

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }

    }


     public static byte [] getFileAsByteArray(File file) {

        try {

            if (!file.isFile()) {
                throw new FileNotFoundException("File " + file.getAbsolutePath() + " could not be found!");
            }

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bufferedInputStream.available());

            int buffer;
            while ((buffer = bufferedInputStream.read()) != -1)
                byteStream.write(buffer);

            bufferedInputStream.close();
            byteStream.close();

            return byteStream.toByteArray();

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage()); //$NON-NLS-1$
        }
        return null;
    }

}