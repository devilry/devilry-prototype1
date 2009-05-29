/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devilry.cli;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.devilry.core.session.dao.DeliveryCandidateRemote;
import org.devilry.core.entity.FileMeta;
import org.devilry.core.session.TreeManagerImpl;
import org.devilry.core.session.TreeManagerRemote;
import org.devilry.core.session.dao.AssignmentNodeImpl;
import org.devilry.core.session.dao.AssignmentNodeRemote;
import org.devilry.core.session.dao.DeliveryCandidateImpl;
import org.devilry.core.session.dao.DeliveryImpl;
import org.devilry.core.session.dao.DeliveryRemote;
import org.devilry.core.session.dao.FileImpl;
import org.devilry.core.session.dao.FileMetaRemote;

/**
 *
 * @author bro
 */
public class DevilryCLILibrary {

    java.util.logging.Logger log = java.util.logging.Logger.getLogger(getClass().getName());
    protected InitialContext localCtx;
    Context serverConnection = null;

    private void initializeServerConnection() throws Exception {
        log.info("initializeServerConnection");
        serverConnection = getRemoteServerConnection();
    }

    private boolean isServerInitialized() {
        return serverConnection != null;
    }


    private DeliveryRemote getDelivery(String deliveryPath) throws Exception {

         if (!isServerInitialized()) {
            initializeServerConnection();
        }

       // System.err.println("addFile:" + deliveryPath + " file:" + filePath);

        TreeManagerRemote tm = getTreeManager();

        long deliveryId = tm.getNodeIdFromPath(deliveryPath);
        //System.err.println(deliveryPath + " id:" + deliveryId);


        AssignmentNodeRemote assignMent = getRemoteBean(AssignmentNodeImpl.class);
        assignMent.init(tm.getNodeIdFromPath("uio.inf1000.spring2009.oblig1"));

        // All deliveries for all users
        List<Long> deliveries = assignMent.getDeliveryIds();

        long deliveryID = -1;

        if (deliveries.size() == 0) {
            deliveryID = assignMent.addDelivery();
            System.err.println("No available deliveries, Created new delivery with id " + deliveryID);
        }
        else {
            deliveryID = deliveries.get(0);
        }

        DeliveryRemote delivery = getRemoteBean(DeliveryImpl.class);
        //System.err.println("deliveryID:" + deliveryID);

        delivery.init(deliveryID);

        return delivery;
    }


    public long addDeliveryFile(String deliveryPath, String filePath, byte[] fileData) throws Exception {

        DeliveryRemote delivery = getDelivery(deliveryPath);

        long deliveryCandidateId = delivery.addDeliveryCandidate();

        DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
        remoteBean.init(deliveryCandidateId);
        remoteBean.setDeliveryTime();

        long fileId = addFileToDelivery(remoteBean, filePath, fileData);

        return fileId;
    }

    
    public List<Long> addDeliveryDir(String deliveryPath, File dir) throws Exception {

        if (!dir.isDirectory()) {
            throw new FileNotFoundException("Directory " + dir + " could not be found!");
        }

        File[] files = dir.listFiles();

        if (files.length == 0) {
            return new ArrayList<Long>();
        }


        DeliveryRemote delivery = getDelivery(deliveryPath);

        System.err.println("New delivery registered to " + deliveryPath);
        System.err.println("Following files are added to new delivery id:" + delivery.getId());

        long deliveryCandidateId = delivery.addDeliveryCandidate();

        DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
        remoteBean.init(deliveryCandidateId);
        remoteBean.setDeliveryTime();

        ArrayList<Long> fileIDs = new ArrayList<Long>();

        Map <String, File> filesMap = getFileMap(dir);
        
        ArrayList<String> keys = new ArrayList<String>();

        Iterator<String> it = filesMap.keySet().iterator();

        while (it.hasNext()) {
            String filePath = it.next();
            keys.add(filePath);
        }

        Collections.sort(keys);

         for (int i = 0; i < keys.size(); i++) {

            String filePath = keys.get(i);
            File f = filesMap.get(filePath);

            byte[] fileData = FileUtil.getFileAsByteArray(f);

            long fileId = addFileToDelivery(remoteBean, filePath, fileData);

            //System.err.println("File id:" + fileId + " size:"+ fileData.length + " " + filePath);

            System.err.printf("File id:%5d size:%7d %s\n", + fileId, fileData.length, filePath);

            fileIDs.add(fileId);
         }

        return fileIDs;
    }

    

    Map <String, File> getFileMap(File dir) {

        ArrayList <File> fileList = new ArrayList<File>();

        addToFileMap(dir, fileList);

        Map <String, File> fileMap = new HashMap <String, File>();

        for (int i = 0; i < fileList.size(); i++) {
            
            File f = fileList.get(i);

            String path = f.getAbsolutePath();
            path = path.substring(dir.getAbsolutePath().length());
            
            fileMap.put(path, f);
        }

        return fileMap;
    }

    private void addToFileMap(File dir, ArrayList <File> fileList) {

        if (dir.isDirectory()) {
            File [] files = dir.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {

                     if (!files[i].getName().startsWith("."))
                        addToFileMap(files[i], fileList);
                }
                else {

                    if (!files[i].getName().startsWith("."))
                         fileList.add(files[i]);
                }
            }
        }
    }

    private long addFileToDelivery(DeliveryCandidateRemote remoteBean, String filePath, byte[] fileData) throws Exception {
               
        long fileId = remoteBean.addFile(filePath);
        FileMetaRemote fileMeta = getRemoteBean(FileImpl.class);
        fileMeta.init(fileId);

        fileMeta.write(fileData);
        fileMeta.resetReadState();

        return fileId;
    }

    /*
    FileMetaRemote getLastDeliveryCandidateFile(String nodePath) throws Exception {

        List<Long> fileIDs = remoteBean.getFileIds();

        long fID = fileIDs.get(0);

        System.err.println("FileID:" + fID);

        FileMetaRemote remoteFileBean = getRemoteBean(FileImpl.class);
        remoteFileBean.init(fID);

        fileNames.add("Candiate " + cID + ":" + remoteFileBean.getFilePath());

        return remoteFileBean;
    }
 */
    

     DeliveryCandidateRemote getLastDeliveryCandidate(String nodePath) throws Exception {

        if (!isServerInitialized()) {
            initializeServerConnection();
        }

        System.err.println("getLastDeliveryCandidateFile:" + nodePath);

        TreeManagerRemote tm = getTreeManager();

        AssignmentNodeRemote assignMent = getRemoteBean(AssignmentNodeImpl.class);
        assignMent.init(tm.getNodeIdFromPath(nodePath));

        List<Long> ids = assignMent.getDeliveryIds();

        if (ids.size() < 0) {
            System.err.println("No deliveries exist!");
            return null;
        }

        DeliveryRemote delivery = getRemoteBean(DeliveryImpl.class);
        delivery.init(ids.get(0));
        System.err.println("delivery id:" + ids.get(0));


        List<Long> candidateIDs = delivery.getDeliveryCandidateIds();

        if (candidateIDs.size() < 0) {
            System.err.println("No delivery candidates exist!");
            return null;
        }

         long cID = candidateIDs.get(candidateIDs.size() -1);

         System.err.println("CandiateID:" + cID);

         DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
         remoteBean.init(cID);

         return remoteBean;
     }


      List<String> getDeliveryCandidatesList(String nodePath) throws Exception {

        if (!isServerInitialized()) {
            initializeServerConnection();
        }

        System.err.println("getDeliveryCandidatesList:" + nodePath);

        TreeManagerRemote tm = getTreeManager();

        AssignmentNodeRemote assignMent = getRemoteBean(AssignmentNodeImpl.class);
        System.err.println("tm.getNodeIdFromPath(nodePath):" + tm.getNodeIdFromPath(nodePath));

        long nodeID = tm.getNodeIdFromPath(nodePath);

        if (nodeID == -1) {
            log.warning("No valid id for nodepath " + nodePath);
            return null;
        }

        assignMent.init(nodeID);

        List<Long> ids = assignMent.getDeliveryIds();

        if (ids.size() < 0) {
            System.err.println("No deliveries exist!");
            return null;
        }

        DeliveryRemote delivery = getRemoteBean(DeliveryImpl.class);
        delivery.init(ids.get(0));
        System.err.println("delivery id:" + ids.get(0));


        List<Long> candidateIDs = delivery.getDeliveryCandidateIds();

        if (candidateIDs.size() < 0) {
            System.err.println("No delivery candidates exist!");
            return null;
        }


        List<String> fileNames = new ArrayList<String>();

        for (long cID : candidateIDs) {

           // System.err.println("CandiateID:" + cID);

            DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
            remoteBean.init(cID);

            List<Long> fileIDs = remoteBean.getFileIds();

            fileNames.add("Candiate " + cID + " with " +fileIDs.size()+ " files  handed in " + remoteBean.getDeliveryTime() );
            
        }

        return fileNames;

    }



    List<String> getDeliveryCandidateFileList(String nodePath) throws Exception {

        if (!isServerInitialized()) {
            initializeServerConnection();
        }

        System.err.println("getDeliveryCandidateFiles:" + nodePath);

        TreeManagerRemote tm = getTreeManager();

        AssignmentNodeRemote assignMent = getRemoteBean(AssignmentNodeImpl.class);
        System.err.println("tm.getNodeIdFromPath(nodePath):" + tm.getNodeIdFromPath(nodePath));

        long nodeID = tm.getNodeIdFromPath(nodePath);

        if (nodeID == -1) {
            log.warning("No valid id for nodepath " + nodePath);
            return null;
        }

        assignMent.init(nodeID);

        List<Long> ids = assignMent.getDeliveryIds();

        if (ids.size() < 0) {
            System.err.println("No deliveries exist!");
            return null;
        }

        DeliveryRemote delivery = getRemoteBean(DeliveryImpl.class);
        delivery.init(ids.get(0));
        System.err.println("delivery id:" + ids.get(0));


        List<Long> candidateIDs = delivery.getDeliveryCandidateIds();

        if (candidateIDs.size() < 0) {
            System.err.println("No delivery candidates exist!");
            return null;
        }


        List<String> fileNames = new ArrayList<String>();

        for (long cID : candidateIDs) {

           // System.err.println("CandiateID:" + cID);

            DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
            remoteBean.init(cID);

            List<Long> fileIDs = remoteBean.getFileIds();

            for (long fID : fileIDs) {

                //System.err.println("Candiate " +cID + " FileID:" + fID);

                FileMetaRemote remoteFileBean = getRemoteBean(FileImpl.class);
                remoteFileBean.init(fID);

                fileNames.add("Candiate " + cID + ":" + remoteFileBean.getFilePath());
            }

        }

        return fileNames;

    }



    /**
     *
     * @param deliveryId    id of a delivery
     * @return
     * @throws java.lang.Exception
     */
    /*
    List <Long> getDeliveryCandidateIDs(long deliveryId) throws Exception {

    if (!isServerInitialized()) {
    initializeServerConnection();
    }

    DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
    remoteBean.init(deliveryId);


    List<Long> ids = remoteBean.getFileIds();

    return ids;
    }
     */

    Collection<FileMeta> getFiles(long id) throws Exception {

        if (!isServerInitialized()) {
            initializeServerConnection();
        }

        /*
        DeliveryCandidate d = deliveryManager.getFull(id);
        return d == null ? null : d.getFiles();
         */


        //getFileIds

        return null;
    }




    
    public void addTestNodes() {

        try {

            TreeManagerRemote tm = getTreeManager();

            String nodeName = "";
            
            try {
                nodeName = "uio";
                tm.addNode(nodeName, "Universitetet i Oslo");
            } catch (Exception e) {
                System.err.println("Exception when adding test node " + nodeName);
            }

            try {
                nodeName = "inf1000";
                tm.addCourseNode(nodeName, "INF1000", "First programming course.",
                        tm.getNodeIdFromPath("uio"));
            } catch (Exception e) {
             System.err.println("Exception when adding test node " + nodeName);
            }
            
            try {
                nodeName = "spring2009";
                tm.addPeriodNode(nodeName, new GregorianCalendar(2009, 1, 1).getTime(),
                        new GregorianCalendar(2009, 6, 15).getTime(),
                        tm.getNodeIdFromPath("uio.inf1000"));
            } catch (Exception e) {
                 System.err.println("Exception when adding test node " + nodeName);
            }

            try {
                nodeName = "oblig1";
                tm.addAssignmentNode(nodeName, "Obligatory assignment 1",
                        tm.getNodeIdFromPath("uio.inf1000.spring2009"));
            } catch (Exception e) {
                 System.err.println("Exception when adding test node " + nodeName);
            }

            try {
                AssignmentNodeRemote node = getRemoteBean(AssignmentNodeImpl.class);
                node.init(tm.getNodeIdFromPath("uio.inf1000.spring2009.oblig1"));
            } catch (Exception e) {
                 System.err.println("Exception when adding test node " + nodeName);
            }


        } catch (Exception ex) {
            Logger.getLogger(DevilryCLILibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TreeManagerRemote setUpTestTreeManager() throws NamingException, Exception {

        TreeManagerRemote tm = getTreeManager();


        tm.addNode("uio", "Universitetet i Oslo");
        tm.addNode("matnat", "Matematisk...", tm.getNodeIdFromPath("uio"));
        tm.addNode("ifi", "Institutt for informatikk", tm.getNodeIdFromPath("uio.matnat"));

        tm.addCourseNode("inf1000", "INF1000", "First programming course.",
                tm.getNodeIdFromPath("uio.matnat.ifi"));

        tm.addPeriodNode("spring2009", new GregorianCalendar(2009, 1, 1).getTime(),
                new GregorianCalendar(2009, 6, 15).getTime(),
                tm.getNodeIdFromPath("uio.inf1000"));

        tm.addAssignmentNode("oblig1", "Obligatory assignment 1",
                tm.getNodeIdFromPath("uio.inf1000.spring2009"));

        AssignmentNodeRemote node = getRemoteBean(AssignmentNodeImpl.class);

        node.init(tm.getNodeIdFromPath("uio.inf1000.spring2009.oblig1"));
        return tm;
    }

    private TreeManagerRemote getTreeManager() throws NamingException, Exception {
        TreeManagerRemote tm = getRemoteBean(TreeManagerImpl.class);
        return tm;
    }

    /*
    @SuppressWarnings("unchecked")
    protected <E> E getRemoteBean(Class<E> beanImplClass)
    throws NamingException {
    return (E) localCtx.lookup(beanImplClass.getSimpleName() + "Remote");
    }
     */
    @SuppressWarnings("unchecked")
    protected <E> E getRemoteBean(Class<E> beanImplClass)
            throws NamingException, Exception {

        if (!isServerInitialized()) {
            initializeServerConnection();
        }

        return (E) serverConnection.lookup(beanImplClass.getSimpleName() + "Remote");
    }

    public Context getRemoteServerConnection1() throws NamingException {
        Properties p = new Properties();
//
//		// Embed openejb
//		p.put(Context.INITIAL_CONTEXT_FACTORY,
//				"org.apache.openejb.client.LocalInitialContextFactory");
//
//		// Set bean naming (JNDI) format
//		p.put("openejb.deploymentId.format",
//				"{ejbName}{interfaceType.annotationName}");
//		p.put("openejb.jndiname.format",
//				"{ejbName}{interfaceType.annotationName}");
        p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.openejb.client.RemoteInitialContextFactory");
        p.put(Context.PROVIDER_URL, "ejbd://127.0.0.1:4201");
        p.put("java.naming.security.principal", "jonathan");
        p.put("java.naming.security.credentials", "secret");

        return new InitialContext(p);
    }

    public Context getRemoteServerConnection() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.RemoteInitialContextFactory");
		p.put(Context.PROVIDER_URL, "ejbd://127.0.0.1:4201");

		return new InitialContext(p);
	}

    public Context getEmbeddedServerConnection() throws NamingException {
		Properties p = new Properties();

		// Embed openejb
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");

		// Set bean naming (JNDI) format
		p.put("openejb.deploymentId.format",
				"{ejbName}{interfaceType.annotationName}");
		p.put("openejb.jndiname.format",
				"{ejbName}{interfaceType.annotationName}");

		return new InitialContext(p);
	}
}
