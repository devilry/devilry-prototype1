package org.devilry.cli;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Logger;
import org.devilry.core.entity.FileMeta;

public class RemoteClient {

    Logger log = Logger.getLogger(RemoteClient.class.getName());

    String newLine = System.getProperty("line.separator");

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

            help();

            for (;;) {

                System.out.print("Devilry>");

                while (!scan.hasNextLine()) {
                    System.err.println("sleeping");
                    Thread.sleep(200);
                }
               
                String line = scan.nextLine();
                String[] args = line.split("\\s");

                if (!handleCommand(args)) {
                    System.out.println("Exiting Devilry shell");
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     RemoteClient(String args []) throws FileNotFoundException {
         handleCommand(args);
     }

    public boolean handleCommand(String [] args) {

        if (args.length == 0)
            return true;
 
         if (args[0].equalsIgnoreCase("exit")) {
                return false;
         } 
         else if (args[0].equalsIgnoreCase("help")) {
            help();
         }
        else if (args[0].equalsIgnoreCase("pwd")) {
            printPWD();
        }

         else if (args[0].equalsIgnoreCase("add")) {

             File f = new File(args[1]);

            if (!f.isFile()) {
               log.warning("File named " + args[1] + " could not be found!");
               return true;
            }

            byte [] fileData = getFileAsByteArray(f);

            addFile(f, fileData);
            
        } else if (args[0].equalsIgnoreCase("listfiles")) {
            
            if (args.length < 2) {
                System.out.println("id is missing");
            }
            else {
                
                try {
                    long id = (long) Integer.parseInt(args[1]);
                    getFiles(id);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid id:" + args[1]);
                }
            }
        } else {
            System.err.println("Invalid command:" + args[0]);
            System.out.println("Type 'help' for available commands.");
        }

        return true;
    }

    void printPWD() {
         String curDir = System.getProperty("user.dir");
         System.out.println(curDir);
    }

    public void help() {
         System.out.println("Available commands:" + newLine +
                                "    - add       filename" + newLine +
                                "    - listfiles id" + newLine +
                                "    - pwd" + newLine +
                                "    - exit"
                     );
    }

    public void getFiles(long id) {

        try {

            DevilryCLILibrary lib = new DevilryCLILibrary();

            Collection<FileMeta> files = lib.getFiles(id);

            if (files.size() == 0) {
                System.out.print("No files in delivery with id " + id);
            }
            else {
                System.out.println("Files in delivery with id " + id + ":");
            }

            for (FileMeta file : files) {
             //   System.out.println(file.getPath());
            }
            
        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void addFile(File f, byte[] fileData) {

        try {

            DevilryCLILibrary lib = new DevilryCLILibrary();

            long id = lib.addFile(f.getName(), fileData);

            //log.info("Added file " + path + " with id:" + id);
            System.out.println("New file added with delivery id:" + id);

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static byte[] getFileAsByteArray(File file) {

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