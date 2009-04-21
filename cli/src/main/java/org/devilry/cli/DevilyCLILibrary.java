/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.cli;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.devilry.core.DeliveryCandidate;

/**
 *
 * @author bro
 */
public class DevilyCLILibrary {

    DeliveryCandidate delivery = null;
        
    private void initializeDeliveryBean() throws Exception {
        Context ctx = new InitialContext();
        Object obj = ctx.lookup("DeliveryCandidateBeanRemote");
        System.out.println("obj:" + obj);
        delivery = (DeliveryCandidate) obj;
    }

    private boolean isDeliveryBeanInitialized() {
        return delivery != null;
    }

    public void addFile(String path, byte [] fileData) throws Exception {

        if (!isDeliveryBeanInitialized())
            initializeDeliveryBean();

            delivery.addFile(path, fileData);
    }

}
