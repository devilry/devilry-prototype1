package org.devilry.cli;

import java.io.FileNotFoundException;
import java.io.File;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import org.apache.commons.cli.PosixParser;
import org.devilry.core.entity.FileMeta;

public class RemoteClient {

    static Logger log = Logger.getLogger(RemoteClient.class.getName());

    String newLine = System.getProperty("line.separator");

     String defaultNodePath = "uio.inf1000.spring2009.oblig1";

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

            	//System.err.println(new Date());
            	
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
         } else if (args[0].equalsIgnoreCase("pwd")) {
             printPWD();
         } else if (args[0].equalsIgnoreCase("ls")) {
             printLS();
         } else if (args[0].equalsIgnoreCase("add")) {
             add(args);
         } else if (args[0].equalsIgnoreCase("devil")) {
             printDevil();
         } else if (args[0].equalsIgnoreCase("addtestnodes")) {
             lib.addTestNodes();
         }
         else if (args[0].equalsIgnoreCase("listCandidateFiles")) {
            
            if (args.length < 2) {
                System.out.println("id is missing, using default: uio.inf1000.spring2009.oblig1");

                getCandidateFiles("uio.inf1000.spring2009.oblig1");
            }
            else {
                
                try {
                    String path = args[1];
                    getCandidateFiles(path);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid id:" + args[1]);
                }
            }
         }
         else if (args[0].equalsIgnoreCase("listCandidates")) {

            if (args.length < 2) {
                System.out.println("id is missing, using default: uio.inf1000.spring2009.oblig1");

                getCandidates("uio.inf1000.spring2009.oblig1");
            }
            else {

                try {
                    String path = args[1];
                    getCandidates(path);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid id:" + args[1]);
                }
            }
         }
      else if (args[0].equalsIgnoreCase("getlastCandidatefile")) {

             String nodePath = defaultNodePath;
             String defaultOutputDir = "output";

             if (args.length < 2) {
                 System.out.println("node path is missing, using default " + nodePath);
                 System.out.println("outputdir is missing, using default " + defaultOutputDir);

             } else {
                 nodePath = args[1];
                 defaultOutputDir = args[2];
             }

             getLastDelivery(nodePath, defaultOutputDir);

         } else {
            System.err.println("Invalid command:" + args[0]);
            System.out.println("Type 'help' for available commands.");
        }

        return true;
    }

    /*
    public void parseOptions(String[] args) {

        // create the command line parser
        CommandLineParser parser = new PosixParser();

        // create the Options
        Options options = new Options();
        //options.addOption("add", "all", false, "do not hide entries starting with .");

        options.addOption(OptionBuilder.withLongOpt("add").withDescription("add a delivery").hasArgs(2).create());

        options.addOption(OptionBuilder.withLongOpt("listCandiateFiles").withDescription("List all the candiate files").hasArgs(2).create());


        //options.addOption("A", "almost-all", false, "do not list implied . and ..");
        //options.addOption("b", "escape", false, "print octal escapes for nongraphic " + "characters");
        //options.addOption(OptionBuilder.withLongOpt("block-size").withDescription("use SIZE-byte blocks").hasArg().withArgName("SIZE").create());
        //options.addOption("B", "ignore-backups", false, "do not list implied entried " + "ending with ~");
        //options.addOption("c", false, "with -lt: sort by, and show, ctime (time of last " + "modification of file status information) with " + "-l:show ctime and sort by name otherwise: sort " + "by ctime");
        //options.addOption("C", false, "list entries by columns");

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            // validate that block-size has been set
            if (line.hasOption("block-size")) {
                // print the value of block-size
                System.out.println(line.getOptionValue("block-size"));
            }

            if (line.hasOption("add")) {
                // print the value of block-size
                System.out.println("option add found");
            }

            if (line.hasOption("listCandiateFiles")) {
                // print the value of block-size
                System.out.println("option add found");
            }



        } catch (org.apache.commons.cli.ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());
        }

    }
*/
        
    void add(String[] args) {

        if (args.length < 3) {
            System.out.println("To few arguments for option add");
            System.out.println("Valid nodepath: uio.inf1000.spring2009.oblig1");
            return;
        }

        String nodePath = args[1];

        File f = new File(args[2]);

        if (f.isFile()) {
            byte[] fileData = FileUtil.getFileAsByteArray(f);
            addFile(nodePath, f, fileData);
        }
        else if (f.isDirectory()) {
            addDirectory(nodePath, f);
        }
        else {
            log.warning("File named " + args[2] + " could not be found!");
            return;
        }

    }

    void printPWD() {
         String curDir = System.getProperty("user.dir");
         System.out.println(curDir);
    }

    void printLS() {
         String curDir = System.getProperty("user.dir");

         File curD = new File(curDir);

         String [] files = curD.list();

         for (String s : files)
            System.out.println(s);
    }

    void printDevil() {
        System.out.println(getDevil());
    }

    public void help() {
         System.out.println("Available commands:" + newLine +
                                "    - add nodeTath      filename" + newLine +
                                "    - listCandidates    nodePath" + newLine +
                                "    - listCandiateFiles nodePath" + newLine +
                                "    - getlastCandidatefile nodePath outputdir" + newLine +
                                "    - addtestnodes" + newLine +
                                "    - pwd" + newLine +
                                "    - ls" + newLine +
                                "    - exit"
                     );
    }


    public String getDevil() {

        String devil =
                "  (\\-\"````\"-/)" + newLine +
                "  //^\\    /^\\\\" + newLine +
                " ;/ ~_\\  /_~ \\;" + newLine +
                " |  / \\\\// \\  |" + newLine +
                "(,  \\0/  \\0/  ,)" + newLine +
                " |   /    \\   |" + newLine +
                " | (_\\.__./_) |" + newLine +
                "  \\ \\-v..v-/ /" + newLine +
                "   \\ `====' /" + newLine +
                "    `\\\\\\///'" + newLine +
                "     '\\\\//'" + newLine +
                "       \\/";

        return devil;
    }
    
    public void getLastDelivery(String nodePath, String outputDir) {

        try {
            File outputDirFile = new File(outputDir);

            if (outputDirFile.isFile()) {
                System.err.println("outputDir is not a directory!");
                return;
            } else if (!outputDirFile.isDirectory()) {
                outputDirFile.mkdir();
            }

            //FileMetaRemote remoteFile = lib.getLastDeliveryCandidateFile(nodePath);d
            //DeliveryCandidateRemote remoteBean = lib.getLastDeliveryCandidate(nodePath);
            org.devilry.core.daointerfaces.DeliveryCandidateRemote deliveryCandidate = lib.getDeliveryCandidate();
            long deliveryCandidateId = lib.getLastDeliveryCandidateId(nodePath);

            List<Long> fileIDs = deliveryCandidate.getFiles(deliveryCandidateId);

            for (int i = 0; i < fileIDs.size(); i++) {
                long fID = fileIDs.get(i);

                //FileMetaRemote remoteFileBean = lib.getRemoteBean(FileImpl.class);
                //remoteFileBean.init(fID);
                org.devilry.core.daointerfaces.FileMetaRemote fileMetaBean = lib.getFileMeta();
                String filePath = fileMetaBean.getFilePath(fID);
                List<Long> fileBlocksId = fileMetaBean.getFileDataBlocks(fID);
                
                org.devilry.core.daointerfaces.FileDataBlockRemote fileDataBlockBean = lib.getFileDataBlock();
                byte[] data = fileDataBlockBean.getFileData(fileBlocksId.get(0));
                                
                FileUtil.writeToFile(data, new File(outputDirFile, filePath));
            }

            //fileNames.add("Candiate " + cID + ":" + remoteFileBean.getFilePath());

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
    



     public void getCandidates(String nodePath) {

        try {

        	System.err.println("lib:" + lib);
        	
            List<String> files = lib.getDeliveryCandidatesList(nodePath);

            if (files.size() == 0) {
                System.out.println("No files in delivery for path " + nodePath);
            }
            else {
                System.out.println("Available deliveries for path " + nodePath + ":");
            }

            for (String file : files) {
                System.out.println(file);
            }

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
     

    public void getCandidateFiles(String nodePath) {

        try {

            List<String> files =lib.getDeliveryCandidateFileList(nodePath);

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

            long id = lib.addDeliveryFile(deliveryPath, f.getName(), fileData);

            log.info("Added file " + f.getName() + " with id:" + id);

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

     public void addDirectory(String deliveryPath, File dir) {

        try {

            List<Long> fileIDs = lib.addDeliveryDir(deliveryPath, dir);

           // long id = lib.addDeliveryFile(deliveryPath, f.getName(), fileData);

            log.info("Added directory " + dir.getName());

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

  


  
}