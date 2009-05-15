package org.devilry.cli;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.devilry.core.entity.FileMeta;
import org.devilry.core.session.TreeManagerRemote;

public class RemoteClient {

    Logger log = Logger.getLogger(RemoteClient.class.getName());

    String newLine = System.getProperty("line.separator");

    DevilryCLILibrary lib = new DevilryCLILibrary();

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

        //org.apache.commons.cli

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

             add(args);
         }
         else if (args[0].equalsIgnoreCase("addtestnodes")) {
            lib.addTestNodes();
         }
         else if (args[0].equalsIgnoreCase("listCandidateFiles")) {
            
            if (args.length < 2) {
                System.out.println("id is missing, running default");

                getFiles("uio.inf1000.spring2009.oblig1");
            }
            else {
                
                try {
                    String path = args[1];
                    getFiles(path);
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

    boolean add(String [] args) {

        if (args.length < 3) {
            System.out.println("To few arguments for option add");
            return false;
        }

          String deliveryPath = args[1];

          File f = new File(args[2]);

            if (!f.isFile()) {
               log.warning("File named " + args[2] + " could not be found!");
               return true;
            }

            byte [] fileData = getFileAsByteArray(f);

            addFile(deliveryPath, f, fileData);

            return false;
    }

    void printPWD() {
         String curDir = System.getProperty("user.dir");
         System.out.println(curDir);
    }

    public void help() {
         System.out.println("Available commands:" + newLine +
                                "    - add       filename" + newLine +
                                "    - listCandiateFiles nodePath" + newLine +
                                "    - pwd" + newLine +
                                "    - exit"
                     );
    }

    public void getFiles(String nodePath) {

        try {

            DevilryCLILibrary lib = new DevilryCLILibrary();

            List<String> files =lib.getDeliveryCandidateFiles(nodePath);

            if (files.size() == 0) {
                System.out.print("No files in delivery for path " + nodePath);
            }
            else {
                System.out.println("Files in delivery with path " + nodePath + ":");
            }

            for (String file : files) {
                System.out.println(file);
            }
            
        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void addFile(String deliveryPath, File f, byte[] fileData) {

        try {

            long id = lib.addDelivery(deliveryPath, f.getName(), fileData);

            log.info("Added file " + f.getName() + " with id:" + id);
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