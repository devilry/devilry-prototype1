package org.devilry.core.bendik;

import javax.ejb.Stateless;

@Stateless(mappedName="ClientBeanRemote")
public class ClientBean implements ClientRemote {

    /*
   public FileOutputTransferStreamInterface getRemoteFile(String path) {
       FileOutputTransferStream fileStream = new FileOutputTransferStream(path);
       return fileStream;
   }
*/

    public String getStatus() {
         return "skrot";
     }

    public void addFile(String fileName, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
