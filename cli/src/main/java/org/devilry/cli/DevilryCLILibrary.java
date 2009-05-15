/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devilry.cli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.devilry.core.entity.DeliveryCandidate;
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
        log.info("Initializing");
        System.err.println("initializeDeliveryBean");
        serverConnection = getLocalServerConnection();
    }

    private boolean isServerInitialized() {
        return serverConnection != null;
    }



    public long addDelivery(String deliveryPath, String filePath, byte[] fileData) throws Exception {

        if (!isServerInitialized()) {
            initializeServerConnection();
        }

        System.err.println("addFile:" + deliveryPath + " file:" + filePath);

        TreeManagerRemote tm = getTreeManager();

        System.err.println("deliveryPath:" + deliveryPath);

        long deliveryId = tm.getNodeIdFromPath(deliveryPath);
        System.err.println(deliveryPath + " id:" + deliveryId);

        /*
        deliveryPath = "uio";
        deliveryId = tm.getNodeIdFromPath(deliveryPath);
        System.err.println(deliveryPath + " id:" + deliveryId);
        
        deliveryPath = "uio.inf1000";
        deliveryId = tm.getNodeIdFromPath(deliveryPath);
        System.err.println(deliveryPath + " id:" + deliveryId);

        deliveryPath = "uio.inf1000.spring2009";
        deliveryId = tm.getNodeIdFromPath(deliveryPath);
        System.err.println(deliveryPath + " id:" + deliveryId);

        deliveryPath = "uio.inf1000.spring2009.oblig1";
        deliveryId = tm.getNodeIdFromPath(deliveryPath);
        System.err.println(deliveryPath + " id:" + deliveryId);
*/
        
       // AssignmentNodeRemote node = getRemoteBean(AssignmentNodeImpl.class);
        //node.init(tm.getNodeIdFromPath("uio.inf1000.spring2009.oblig1"));

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
        System.err.println("delivery id:" + deliveryId);

        delivery.init(deliveryID);

        long deliveryCandidateId = delivery.addDeliveryCandidate();

        DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
        remoteBean.init(deliveryCandidateId);

        
        long fileId = remoteBean.addFile(filePath);
        FileMetaRemote fileMeta = getRemoteBean(FileImpl.class);
        fileMeta.init(fileId);


        fileMeta.write(fileData);
        fileMeta.resetReadState();

        return -1;
    }

    List<String> getDeliveryCandidateFiles(String nodePath) throws Exception {

        if (!isServerInitialized()) {
            initializeServerConnection();
        }

        System.err.println("getDeliveryCandidateFiles:" + nodePath);

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


        List<String> fileNames = new ArrayList<String>();

        for (long cID : candidateIDs) {

            System.err.println("CandiateID:" + cID);

            DeliveryCandidateRemote remoteBean = getRemoteBean(DeliveryCandidateImpl.class);
            remoteBean.init(cID);

            List<Long> fileIDs = remoteBean.getFileIds();

            for (long fID : fileIDs) {

                System.err.println("FileID:" + fID);

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

    public Context getLocalServerConnection() throws NamingException {
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
}
