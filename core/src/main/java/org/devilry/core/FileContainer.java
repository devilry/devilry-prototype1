package org.devilry.core;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Entity
public class FileContainer extends RemoteFile {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected long id;

	protected FileContainer() {
		super(null);
	}

	FileContainer(String path) {
		super(path);
	}
}