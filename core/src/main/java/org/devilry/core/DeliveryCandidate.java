/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.core;

import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author bro
 */
@Remote
public interface DeliveryCandidate {

    public void setId(long id);
    public long getId();
    public void addFile(String path, byte [] fileData);
    public byte [] getData(String path);
    public Collection getFilePaths();

 //   public org.devilry.core.bendik.FileOutputTransferStream getFileOutputStream();
}
