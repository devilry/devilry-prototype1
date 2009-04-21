/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.cli;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.DeliveryCandidate;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.DeliveryRemote;

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

        deliveryManager = (DeliveryRemote) ctx.lookup("Delivery");
    }

    private boolean isDeliveryBeanInitialized() {
        return deliveryManager != null;
    }

    public void addFile(String path, byte [] fileData) throws Exception {

        if (!isDeliveryBeanInitialized())
            initializeDeliveryBean();

		DeliveryCandidateNode dir = new DeliveryCandidateNode();
		dir.addFile(path, fileData);
		long id = deliveryManager.add(dir);
  
        System.err.println("Added file " + path + " with id:" + id);
           
    }

}
