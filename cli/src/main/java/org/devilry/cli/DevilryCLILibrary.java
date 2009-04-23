/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.cli;

import java.util.Collection;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.DeliveryRemote;
import org.devilry.core.FileNode;

/**
 *
 * @author bro
 */
public class DevilryCLILibrary {

   DeliveryRemote deliveryManager = null;
        
    private void initializeDeliveryBean() throws Exception {
        Context ctx = new InitialContext();
    /*
        Object obj = ctx.lookup("DeliveryCandidateBeanRemote");
        System.out.println("obj:" + obj);
        delivery = (DeliveryCandidate) obj;
*/

        //deliveryManager = (DeliveryRemote) ctx.lookup("Delivery");
        deliveryManager = (DeliveryRemote) ctx.lookup("org.devilry.core.DeliveryRemote");

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

        System.err.println("Added file " + path + " with id:" + id);

        return id;
    }

    Collection<FileNode> getFiles(long id) {
        DeliveryCandidateNode d = deliveryManager.getFull(id);
		return d.getFiles();
    }
    
}
