package org.devilry.core;


 public class RemoteFile implements java.io.Serializable{
 	protected String filePath;

 	RemoteFile(String path) {
 		filePath = path;
 	}

 	public String getPath() {
 		return filePath;
 	}
 }