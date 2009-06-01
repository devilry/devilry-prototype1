/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.devilry.cli;

import java.util.logging.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 * @author bro
 */
public class FileUtil {

    static Logger log = Logger.getLogger(FileUtil.class.getName());

      public static byte[] getFileAsByteArray(File file) {

        try {

            if (!file.isFile()) {
                throw new FileNotFoundException("File " + file.getAbsolutePath() + " could not be found!");
            }

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bufferedInputStream.available());

            int buffer;
            while ((buffer = bufferedInputStream.read()) != -1)
                byteStream.write(buffer);

            bufferedInputStream.close();
            byteStream.close();

            return byteStream.toByteArray();

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage()); //$NON-NLS-1$
        }
        return null;
    }


         public static boolean writeToFile(byte [] data, File file) {
    	return writeToFile(new ByteArrayInputStream(data), file);
    }

    public static boolean writeToFile(InputStream data, File file) {

        System.err.println("writing file:" + file.getAbsolutePath());


        try {
        	if (!file.getParentFile().isDirectory()) {
        		if (!file.getParentFile().mkdirs()) {
                    log.log(Level.WARNING, "Failed to create new file: " + file);
        			return false;
        		}
        	}

        	int bufferSize = 8192;

        	BufferedInputStream inputStream = new BufferedInputStream(data);
        	FileOutputStream fileStream = new FileOutputStream(file);

        	byte buffer [] = new byte[bufferSize];
        	BufferedOutputStream dest = new BufferedOutputStream(fileStream, bufferSize);
        	int count;

        	while ((count = inputStream.read(buffer, 0, bufferSize)) != -1) {
        		dest.write(buffer, 0, count);
        	}

        	inputStream.close();
        	dest.close();

        } catch (Exception e) {
           log.log(Level.SEVERE, "Exception:"+ e.getMessage());
            return false;
        }
        return true;
    }


}
