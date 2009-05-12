package org.devilry.core;

import javax.ejb.Remote;

/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Remote
public interface FileReaderBeanRemote {
	public void open(FileNode fileNode);
	public byte[] read();
}