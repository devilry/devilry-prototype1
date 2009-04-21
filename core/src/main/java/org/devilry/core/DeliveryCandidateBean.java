package org.devilry.core;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.naming.*;
import org.devilry.core.bendik.ClientRemote;
import javax.ejb.Stateless;

@Stateful(mappedName="DeliveryCandidateBeanRemote")
public class DeliveryCandidateBean implements DeliveryCandidate {

    String fileName;
    byte [] data;

    public void addFile(String path, byte [] fileData) {
        this.fileName = fileName;
        data = fileData;
    }

    public byte [] getData(String path) {
        return data;
    }

    public Collection getFilePaths() {
        return null;
    }
}
