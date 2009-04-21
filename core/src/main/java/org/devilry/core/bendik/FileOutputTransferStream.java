
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
