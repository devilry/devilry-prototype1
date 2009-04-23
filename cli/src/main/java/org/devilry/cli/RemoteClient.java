package org.devilry.cli;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class RemoteClient {

    Logger log = Logger.getLogger(RemoteClient.class.getName());

	public static void main (String args[]) throws Exception {

        if (args.length == 0) {
             new RemoteClient();
        }
        else if (args.length < 2) {
            System.err.println("Too few arguments");
            System.err.println("USAGE:" +
                    "add filename"
            );
           return;
        }
        else
            new RemoteClient(args);
    }

    RemoteClient() {
        runDevilryShell();
    }

    public void runDevilryShell() {

        try {

            Scanner scan = new Scanner(System.in);

            for (;;) {

                System.out.print("Devilry >");

                while (!scan.hasNextLine()) {
                    System.err.println("slettping");
                    Thread.sleep(200);
                }
               
                String line = scan.nextLine();
                String[] args = line.split("\\s");

                System.err.println("cmd:" + line);
                handleCommand(args);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     RemoteClient(String args []) throws FileNotFoundException {
         handleCommand(args);
     }

    public void handleCommand(String [] args) {
        
  // Expect valid file in args[1]
        if (args[0].equalsIgnoreCase("add")) {

            if (!new File(args[1]).isFile()) {
               log.warning("File named " + args[1] + " could not be found!");
               return;
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

           DevilryCLILibrary lib = new DevilryCLILibrary();

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