package org.devilry.core;

import java.util.Collection;

import javax.ejb.Stateful;

import javax.naming.Context;
import javax.naming.InitialContext;

@Stateful(mappedName="DeliveryCandidateBeanRemote")
public class DeliveryCandidateBean implements DeliveryCandidate {

    String path;
    byte [] data;

    public void setId(long id) {
        
    }

    public long getId() {
        return -1;
    }

    // "kat1/src/file.java"
    public void addFile(String path, byte [] fileData) {
        this.path = path;
        data = fileData;

     //   System.err.println("Added file " + path);
    }

    public byte [] getData(String path) {
        return data;
    }

    
    public Collection getFilePaths() {
        return null;
    }

    
    public org.devilry.core.bendik.FileOutputTransferStream getFileOutputStream() {

    try {
        Context ctx = new InitialContext();

      
        Object obj = ctx.lookup("FileOutputTransferStreamImplRemote");
      //  System.out.println("obj:" + obj);
        org.devilry.core.bendik.FileOutputTransferStream fileStream =
                (org.devilry.core.bendik.FileOutputTransferStream) obj;

        return fileStream;
          
    } catch (Exception e) {
        //Systme.err.println("Exception:" + e.getMessage());

    }
       return null;
    }
     
}
