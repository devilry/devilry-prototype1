package org.devilry.core.digest;

import java.io.Serializable;
import org.devilry.core.entity.FileMetaEntity;

/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
public class FileMetaDigest implements Serializable {

	private long id;
	private String filePath;

	public FileMetaDigest(FileMetaEntity f) {
		this.id = f.getId();
		this.filePath = f.getFilePath();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}