package org.devilry.core.bendik.filestuff.session;

import javax.ejb.Remote;

import org.devilry.core.bendik.filestuff.entity.DevilryFile;

@Remote
public interface FileTransferService {
    void addFile(int custId, String path, byte [] data);
    DevilryFile findFile(int custId);
    void updateFile(DevilryFile file);
}
