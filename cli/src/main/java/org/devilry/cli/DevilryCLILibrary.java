/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.cli;

import java.util.Collection;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.entity.DeliveryCandidateEntity;
import org.devilry.core.session.dao.DeliveryCandidateRemote;
import org.devilry.core.entity.FileMetaEntity;

/**
 *
 * @author bro
 */
public class DevilryCLILibrary {

    java.util.logging.Logger log = java.util.logging.Logger.getLogger(getClass().getName());

   DeliveryCandidateRemote deliveryManager = null;
        
    private void initializeDeliveryBean() throws Exception {
        Context ctx = new InitialContext();
    /*
        Object obj = ctx.lookup("DeliveryCandidateBeanRemote");
        System.out.println("obj:" + obj);
        delivery = (DeliveryCandidate) obj;
*/

        //deliveryManager = (DeliveryRemote) ctx.lookup("Delivery");
        deliveryManager = (DeliveryCandidateRemote) ctx.lookup("org.devilry.core.DeliveryRemote");

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

		DeliveryCandidateEntity dir = new DeliveryCandidateEntity();
		dir.addFile(path, fileData);
		long id = deliveryManager.add(dir);

        return id;
    }

    Collection<FileMetaEntity> getFiles(long id) throws Exception {

        System.err.println("deliveryManager:" + deliveryManager);

        if (!isDeliveryBeanInitialized())
            initializeDeliveryBean();

        DeliveryCandidateEntity d = deliveryManager.getFull(id);
		return d == null ? null : d.getFiles();
    }
    
}
