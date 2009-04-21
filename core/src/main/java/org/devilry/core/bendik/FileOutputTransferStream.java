/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.core.bendik;

import javax.ejb.Remote;

/**
 *
 * @author bro
 */
@Remote
public interface FileOutputTransferStream {

    public void write(byte [] fileData, int  offset, int len);
    public String getStatus();
}
