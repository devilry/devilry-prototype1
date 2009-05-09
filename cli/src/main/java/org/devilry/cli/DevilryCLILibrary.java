/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.cli;

import java.util.Collection;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.DeliveryBeanRemote;
import org.devilry.core.FileNode;

/**
 *
 * @author bro
 */
public class DevilryCLILibrary {

    java.util.logging.Logger log = java.util.logging.Logger.getLogger(getClass().getName());

   DeliveryBeanRemote deliveryManager = null;
        
    private void initializeDeliveryBean() throws Exception {
        Context ctx = new InitialContext();
    /*
        Object obj = ctx.lookup("DeliveryCandidateBeanRemote");
        System.out.println("obj:" + obj);
        delivery = (DeliveryCandidate) obj;
*/

        //deliveryManager = (DeliveryRemote) ctx.lookup("Delivery");
        deliveryManager = (DeliveryBeanRemote) ctx.lookup("org.devilry.core.DeliveryRemote");

    }

    private boolean isDeliveryBeanInitialized() {
        return deliveryManager != null;
    }

    /**
     *
     * @param path
     * @param fileData
     * @return id
     * @throws java.lang.Exception
     */
    public long addFile(String path, byte [] fileData) throws Exception {

        if (!isDeliveryBeanInitialized())
            initializeDeliveryBean();

		DeliveryCandidateNode dir = new DeliveryCandidateNode();
		dir.addFile(path, fileData);
		long id = deliveryManager.add(dir);

        return id;
    }

    Collection<FileNode> getFiles(long id) throws Exception {

        System.err.println("deliveryManager:" + deliveryManager);

        if (!isDeliveryBeanInitialized())
            initializeDeliveryBean();

        DeliveryCandidateNode d = deliveryManager.getFull(id);
		return d == null ? null : d.getFiles();
    }
    
}
