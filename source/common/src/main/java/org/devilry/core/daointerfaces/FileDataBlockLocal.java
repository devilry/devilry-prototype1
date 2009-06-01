package org.devilry.core.daointerfaces;

public interface FileDataBlockLocal {
	/**
	 * Create new FileDataBlock with parent id fileMetaId and data fileData
	 * @param fileMetaId
	 * @param fileData
	 * @return id of the new FileDataBlock
	 */
	long create(long fileMetaId, byte [] fileData);

	/**
	 * Get id of parent FileMeta
	 * @param fileDataId
	 * @return id of FileMeta
	 */
	long getFileMeta(long fileDataBlockId);

	/**
	 * Get data from datablock with id fileDataId
	 * @param fileDataId
	 * @return the data in bytes
	 */
	byte [] getFileData(long fileDataBlockId);

	/**
	 * Get size of data of this datablock
	 * @param fileDataId
	 * @return the size of data in number of bytes
	 */
	int getSize(long fileDataBlockId);
}
