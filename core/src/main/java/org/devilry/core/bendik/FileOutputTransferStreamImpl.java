package org.devilry.core.bendik;

//import java.io.Serializable;
import javax.ejb.Stateful;

@Stateful(mappedName="FileOutputTransferStreamImplRemote")
public class FileOutputTransferStreamImpl implements FileOutputTransferStream {
	
	protected String fileName;
   
   public void setFileName(String name) {
       fileName = name;
   }
    
    public void write(byte [] fileData, int  offset, int len) {
		//System.out.println("writing");
    }

    public String getStatus() {
        return null;
    }
}