package org.devilry.core.daointerfaces;

import org.devilry.core.UnauthorizedException;

public interface FileDataBlockCommon {
	/**
	 * Create new FileDataBlock with parent id fileMetaId and data fileData
	 * @param fileMetaId
	 * @param fileData
	 * @return id of the new FileDataBlock
	 */
	long create(long fileMetaId, byte [] fileData) throws UnauthorizedException;

	/**
	 * Get id of parent FileMeta
	 * @param fileDataId
	 * @return id of FileMeta
	 */
	long getFileMeta(long fileDataBlockId) throws UnauthorizedException;

	/**
	 * Get data from datablock with id fileDataId
	 * @param fileDataId
	 * @return the data in bytes
	 */
	byte [] getFileData(long fileDataBlockId) throws UnauthorizedException;

	/**
	 * Get size of data of this datablock
	 * @param fileDataId
	 * @return the size of data in number of bytes
	 */
	int getSize(long fileDataBlockId) throws UnauthorizedException;

	/** 
	 * Check if a file data block with the given id exists. 
	 */
	boolean exists(long fileDataBlockId) throws UnauthorizedException;
}
