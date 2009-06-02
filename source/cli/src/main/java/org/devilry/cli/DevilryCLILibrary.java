/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devilry.cli;

import java.util.*;
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
import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.FileDataBlockImpl;
import org.devilry.core.dao.FileMetaImpl;
import org.devilry.core.dao.NodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.dao.UserImpl;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.FileDataBlockLocal;
import org.devilry.core.daointerfaces.FileMetaLocal;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.UserLocal;
import org.devilry.core.entity.FileMeta;

import org.devilry.core.daointerfaces.*;

/**
 *
 * @author bro
 */
public class DevilryCLILibrary {

    java.util.logging.Logger log = java.util.logging.Logger.getLogger(getClass().getName());
    protected InitialContext localCtx;
    Context serverConnection = null;
    
    public DevilryCLILibrary(String user, String password) throws Exception {
    	initializeServerConnection(user, password);
    }
    
    private void initializeServerConnection(String user, String password) throws Exception {
        log.info("initializeServerConnection");
        serverConnection = getRemoteServerConnection(user, password);
    }

    private long getDeliveryId(String deliveryPath) throws Exception {
         
         System.err.println("deliveryPath:" + deliveryPath);

       // System.err.println("addFile:" + deliveryPath + " file:" + filePath);

         NodeRemote tm = getTreeManager();

        long deliveryId = tm.getNodeIdFromPath(deliveryPath);


        AssignmentNodeRemote assignMent = getAssignmentNode();
        long assignmentId = tm.getNodeIdFromPath("uio.inf1000.spring2009.oblig1");
        
        // All deliveries for all users
        List<Long> deliveries = assignMent.getDeliveriesWhereIsStudent(assignmentId);

        long deliveryID = -1;

        if (deliveries.size() == 0) {
        	
        	DeliveryRemote delivery = getDelivery();
        	
            deliveryID = delivery.create(assignmentId);
            System.err.println("No available deliveries, Created new delivery with id " + deliveryID);
        }
        else {
            deliveryID = deliveries.get(0);
        }

        return deliveryID;
    }
    


    public long addDeliveryFile(String assignmentPath, String filePath, byte[] fileData) throws Exception {

    	System.err.println("assignmentPath:" + assignmentPath);
    	
    	long assignmentId = getTreeManager().getNodeIdFromPath(assignmentPath);
    	
    	System.err.println("assignmentId:" + assignmentId);
    	    	
    	DeliveryRemote delivery = getDelivery();
    	long deliveryId = getDeliveryId(assignmentPath);

    	System.err.println("deliveryId:" + deliveryId);
    	    	
    	DeliveryCandidateRemote deliveryCandidate = getDeliveryCandidate();
        long deliveryCandidateId = deliveryCandidate.create(deliveryId);

        long fileId = addFileToDelivery(deliveryCandidateId, filePath, fileData);

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


        DeliveryRemote delivery = getDelivery();
        long deliveryId = getDeliveryId(deliveryPath);

        System.err.println("New delivery registered to " + deliveryPath);
        System.err.println("Following files are added to new delivery id:" + deliveryId);

        DeliveryCandidateRemote deliveryCandidate = getDeliveryCandidate();
        long deliveryCandidateId = deliveryCandidate.create(deliveryId);
                
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

            long fileId = addFileToDelivery(deliveryCandidateId, filePath, fileData);
            //System.err.println("File id:" + fileId + " size:"+ fileData.length + " " + filePath);
            System.err.printf("File id:%5d size:%7d %s\n", + fileId, fileData.length, filePath);
            fileIDs.add(fileId);
         }

        return fileIDs;
    }
    

    private long addFileToDelivery(long deliveryCandidateId, String filePath, byte[] fileData) throws Exception {
        
    	DeliveryCandidateRemote deliveryCandidateNode = getDeliveryCandidate();
    	
    	System.err.println("deliveryCandidateId:" + deliveryCandidateId);
    	System.err.println("filePath:" + filePath);
    	long fileMetaId = deliveryCandidateNode.addFile(deliveryCandidateId, filePath);
    	
    	FileDataBlockRemote dataBlock = getFileDataBlock();
    	long blockId = dataBlock.create(fileMetaId, fileData);
    	
        return blockId;
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


    /**
     * Retrieves the DeliveryCandidateRemote for the last candidate for a delivery
     * @param nodePath
     * @return
     * @throws java.lang.Exception
     */
    long getLastDeliveryCandidateId(String nodePath) throws Exception {

    	System.err.println("getLastDeliveryCandidateFile:" + nodePath);

    	NodeRemote tm = getTreeManager();
    	AssignmentNodeRemote assignMent = getAssignmentNode();

    	List<Long> ids = assignMent.getDeliveriesWhereIsStudent(tm.getNodeIdFromPath(nodePath));

    	if (ids.size() == 0) {
    		System.err.println("No deliveries exist!");
    		return -1;
    	}

    	DeliveryRemote delivery = getDelivery();
    	System.err.println("delivery id:" + ids.get(0));

    	List<Long> candidateIDs = delivery.getDeliveryCandidates(ids.get(0));

    	if (candidateIDs.size() < 0) {
    		System.err.println("No delivery candidates exist!");
    		return -1;
    	}

    	long cID = candidateIDs.get(candidateIDs.size() -1);

    	System.err.println("CandidateID:" + cID);

    	return cID;
    }


    /**
     * Retrieves the DeliveryCandidateRemote for the candidate with id candidateID
     * @param nodePath
     * @return
     * @throws java.lang.Exception
     */
    long getDeliveryCandidateId(String nodePath, long candidateID) throws Exception {

    	System.err.println("getLastDeliveryCandidateFile:" + nodePath);

    	NodeRemote tm = getTreeManager();
    	AssignmentNodeRemote assignMent = getAssignmentNode();

    	List<Long> IDs = assignMent.getDeliveriesWhereIsStudent(tm.getNodeIdFromPath(nodePath));

    	if (IDs.size() < 0) {
    		System.err.println("No deliveries exist!");
    		return -1;
    	}

    	DeliveryRemote delivery = getDelivery();
    	System.err.println("delivery id:" + IDs.get(0));

    	List<Long> candidateIDs = delivery.getDeliveryCandidates(IDs.get(0));

    	if (candidateIDs.size() < 0) {
    		System.err.println("No delivery candidates exist!");
    		return -1;
    	}

    	if (!candidateIDs.contains(candidateID)) {
    		System.err.println("The provided ID (" + candidateID + ") is not valid!");
    		return -1;
    	}

    	System.err.println("CandidateID:" + candidateID);

    	return candidateID;
    }


      /**
       * Returns a list of all delivery candidates for an assignment
       * @param nodePath
       * @return
       * @throws java.lang.Exception
       */
      List<String> getDeliveryCandidatesList(String nodePath) throws Exception {

        System.err.println("getDeliveryCandidatesList:" + nodePath);

        NodeRemote tm = getTreeManager();

        AssignmentNodeRemote assignMent = getAssignmentNode();
        System.err.println("tm.getNodeIdFromPath(nodePath):" + tm.getNodeIdFromPath(nodePath));

        long nodeID = tm.getNodeIdFromPath(nodePath);

        if (nodeID == -1) {
            log.warning("No valid id for nodepath " + nodePath);
            return new ArrayList<String>();
        }

        List<Long> ids = assignMent.getDeliveriesWhereIsStudent(nodeID);

        if (ids.size() == 0) {
            System.err.println("No deliveries exist!");
            return new ArrayList<String>();
        }

        DeliveryRemote delivery = getDelivery();
        System.err.println("delivery id:" + ids.get(0));

        List<Long> candidateIDs = delivery.getDeliveryCandidates(ids.get(0));

        if (candidateIDs.size() < 0) {
            System.err.println("No delivery candidates exist!");
            return new ArrayList<String>();
        }


        List<String> fileNames = new ArrayList<String>();

        for (long cID : candidateIDs) {

           // System.err.println("CandidateID:" + cID);

        	DeliveryCandidateRemote remoteBean = getDeliveryCandidate();
            List<Long> fileIDs = remoteBean.getFiles(cID);

            fileNames.add("Candidate " + cID + " with " +fileIDs.size()+ " files  handed in " + remoteBean.getTimeOfDelivery(cID) );
                       
            //System.err.println("Time:" + remoteBean.getTimeOfDelivery(cID).getTime());
        }

        return fileNames;

    }


    /**
     * Returns a list of all the files for all delivery candidates
     * @param nodePath
     * @return
     * @throws java.lang.Exception
     */
    List<String> getDeliveryCandidateFileList(String nodePath) throws Exception {

        System.err.println("getDeliveryCandidateFiles:" + nodePath);

        NodeRemote tm = getTreeManager();

        long nodeID = tm.getNodeIdFromPath(nodePath);
        
        System.err.println("tm.getNodeIdFromPath(nodePath):" + nodeID);

        if (nodeID == -1) {
            log.warning("No valid id for nodepath " + nodePath);
            return new ArrayList<String>();
        }

        AssignmentNodeRemote assignMent = getAssignmentNode();

        List<Long> ids = assignMent.getDeliveriesWhereIsStudent(nodeID);

        if (ids.size() == 0) {
            System.err.println("No deliveries exist!");
            return new ArrayList<String>();
        }

        DeliveryRemote delivery = getDelivery();
        System.err.println("delivery id:" + ids.get(0));

        List<Long> candidateIDs = delivery.getDeliveryCandidates(ids.get(0));

        if (candidateIDs.size() < 0) {
            System.err.println("No delivery candidates exist!");
            return new ArrayList<String>();
        }


        List<String> fileNames = new ArrayList<String>();

        for (long cID : candidateIDs) {

           // System.err.println("CandidateID:" + cID);

        	DeliveryCandidateRemote remoteBean = getDeliveryCandidate();
        	
            List<Long> fileIDs = remoteBean.getFiles(cID);

            for (long fID : fileIDs) {

                //System.err.println("Candidate " +cID + " FileID:" + fID);
            	FileMetaRemote remoteFileBean = getFileMeta();
            	fileNames.add("Candidate " + cID + ":" + remoteFileBean.getFilePath(fID));
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
        return null;
    }




    
    public void addTestNodes() {

        try {

        	NodeRemote tm = getTreeManager();

            String nodeName = "";
            
            try {
                nodeName = "uio";
                tm.create(nodeName, "Universitetet i Oslo");
            } catch (Exception e) {
                System.err.println("Exception when adding test node " + nodeName);
            }

            try {
                nodeName = "inf1000";
                CourseNodeLocal courseNode = getCourseNode();
                long courseId = courseNode.create(nodeName, "First programming course.", tm.getNodeIdFromPath("uio"));
                
            } catch (Exception e) {
             System.err.println("Exception when adding test node " + nodeName);
            }
            
            try {
                nodeName = "spring2009";
                PeriodNodeLocal periodNode = getPeriodNode();
                long periodId = periodNode.create(nodeName, nodeName, 
                		new GregorianCalendar(2009, 1, 1).getTime(), 
                		new GregorianCalendar(2009, 6, 15).getTime(), 
                		tm.getNodeIdFromPath("uio.inf1000"));
            } catch (Exception e) {
                 System.err.println("Exception when adding test node " + nodeName);
            }

            try {
                nodeName = "oblig1";
                AssignmentNodeLocal assignmentNode = getAssignmentNode();
                long assignmentId = assignmentNode.create(nodeName, "Obligatory assignment 1", 
                		new GregorianCalendar(2009, 1, 1).getTime(), 
                		tm.getNodeIdFromPath("uio.inf1000.spring2009"));
                
            } catch (Exception e) {
                 System.err.println("Exception when adding test node " + nodeName);
            }


        } catch (Exception ex) {
            Logger.getLogger(DevilryCLILibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public NodeRemote setUpTestTreeManager() throws NamingException, Exception {

    	NodeRemote tm = getTreeManager();

        tm.create("uio", "Universitetet i Oslo");

        CourseNodeLocal courseNode = getCourseNode();
        long courseId = courseNode.create("inf1000", "First programming course.", tm.getNodeIdFromPath("uio"));
       

        PeriodNodeLocal periodNode = getPeriodNode();
        long periodId = periodNode.create("spring2009", "spring2009", 
        		new GregorianCalendar(2009, 1, 1).getTime(), 
        		new GregorianCalendar(2009, 6, 15).getTime(), 
        		tm.getNodeIdFromPath("uio.inf1000"));
      
        
        AssignmentNodeLocal assignmentNode = getAssignmentNode();
        long assignmentId = assignmentNode.create("oblig1", "Obligatory assignment 1", 
        		new GregorianCalendar(2009, 1, 1).getTime(), 
        		tm.getNodeIdFromPath("uio.inf1000.spring2009"));
                
        return tm;
    }
    

    private NodeRemote getTreeManager() throws NamingException, Exception {
        NodeRemote tm = getRemoteBean(NodeImpl.class);
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
    protected <E> E getRemoteBean(Class<E> beanImplClass) throws NamingException, Exception {
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
    	return getRemoteServerConnection("homer", "doh");
    }
    	
    public Context getRemoteServerConnection(String user, String password) throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.RemoteInitialContextFactory");
		p.put(Context.PROVIDER_URL, "ejbd://127.0.0.1:4201");
		
		p.put(Context.SECURITY_PRINCIPAL, user);
		p.put(Context.SECURITY_CREDENTIALS, password);
		
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
    
   
    
    public AssignmentNodeRemote getAssignmentNode() throws Exception {
		return getRemoteBean(org.devilry.core.dao.AssignmentNodeImpl.class);
	}
	
	public CourseNodeRemote getCourseNode() throws Exception {
		return getRemoteBean(CourseNodeImpl.class);
	}

	public DeliveryCandidateRemote getDeliveryCandidate() throws Exception {
		return getRemoteBean(org.devilry.core.dao.DeliveryCandidateImpl.class);
	}

	public DeliveryRemote getDelivery() throws Exception {
		return getRemoteBean(org.devilry.core.dao.DeliveryImpl.class);
	}

	public FileDataBlockRemote getFileDataBlock() throws Exception {
		return getRemoteBean(FileDataBlockImpl.class);
	}

	public FileMetaRemote getFileMeta() throws Exception {
		return getRemoteBean(FileMetaImpl.class);
	}

	public NodeRemote getNode() throws Exception {
		return getRemoteBean(NodeImpl.class);
	}

	public PeriodNodeRemote getPeriodNode() throws Exception {
		return getRemoteBean(PeriodNodeImpl.class);
	}

	public UserRemote getUser() throws Exception {
		return getRemoteBean(UserImpl.class);
	}
}
