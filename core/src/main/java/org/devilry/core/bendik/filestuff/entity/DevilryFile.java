package org.devilry.core.bendik.filestuff.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class DevilryFile implements java.io.Serializable {
    private int id;
    private String path;
    private byte [] data;
    private int version;

    public DevilryFile() {}
    @Id
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
   
    public String getFilePath() { return path; }
    public void setFilePath(String path) { this.path = path; }
    
    public byte [] getFileData() { return data; }
    public void setFileData(byte [] data) { this.data = data; }

    @Version
    protected int getVersion() { return version; }
    protected void setVersion(int version) { this.version = version; }

    public String toString() {
      return "Filepath:" + path + ", data length:" + data.length;
   }

}
