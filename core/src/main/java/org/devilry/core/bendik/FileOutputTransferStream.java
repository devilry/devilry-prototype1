package org.devilry.core.bendik;

import java.io.IOException;
import java.io.Serializable;

public class FileOutputTransferStream implements FileOutputTransferStreamInterface, Serializable {
	
	protected String fileName;
   
    public FileOutputTransferStream(String name) {
        fileName = name;
    }
    
    public void write(byte [] fileData, int  offset, int len) throws IOException {
		System.out.println("writing");
    }

    public String getStatus() {
        return "hello";
    }
}