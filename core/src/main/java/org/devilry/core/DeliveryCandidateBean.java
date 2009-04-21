package org.devilry.core;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.naming.*;
import org.devilry.core.bendik.ClientRemote;
import javax.ejb.Stateless;
import org.devilry.core.bendik.FileOutputTransferStream;

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

    public FileOutputTransferStream getFileOutputStream() {
       return null;
    }
}
