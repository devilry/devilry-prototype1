package org.devilry.core.session;

import org.devilry.core.entity.FileMetaEntity;
import org.devilry.core.*;
import javax.ejb.Remote;

/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Remote
public interface FileReaderBeanRemote {
	public void open(FileMetaEntity fileNode);
	public byte[] read();
}