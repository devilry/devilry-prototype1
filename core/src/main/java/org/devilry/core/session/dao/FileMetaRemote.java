package org.devilry.core.session.dao;

import javax.ejb.Remote;


/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Remote
public interface FileMetaRemote {
	void init(long fileId);
	long getId();
	long getDeliveryCandidateId();
	String getFilePath();
	byte[] read();
	void resetReadState();
	void write(byte[] dataBlock);
}