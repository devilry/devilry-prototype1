/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.core.bendik;

import java.io.IOException;

/**
 *
 * @author bro
 */
public interface FileOutputTransferStreamInterface {

    public void write(byte [] fileData, int  offset, int len) throws IOException;
    public String getStatus();
}
