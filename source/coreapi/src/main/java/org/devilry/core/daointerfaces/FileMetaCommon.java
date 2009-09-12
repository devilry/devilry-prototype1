package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.UnauthorizedException;

public interface FileMetaCommon {

	/**
	 * Create new FileMeta with deliveryCandidate parent deliveryCandidateId
	 * @param deliveryCandidateId
	 * @param filePath
	 */
	long create(long deliveryCandidateId, String filePath) throws UnauthorizedException;

	/**
	 * Get id of parent deliveryCandidate
	 * @param fileMetaId
	 * @return
	 */
	long getDeliveryCandidate(long fileMetaId) throws UnauthorizedException;

	/**
	 * Get the filepath for the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return the path
	 */
	String getFilePath(long fileMetaId) throws UnauthorizedException;

	/**
	 * Get size of all FileDataBlocks referencing the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return
	 */
	int getSize(long fileMetaId) throws UnauthorizedException;

	/**
	 * Get list of ids of all FileDataBlocks refencing the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return
	 */
	List<Long> getFileDataBlocks(long fileMetaId) throws UnauthorizedException;

	/** 
	 * Check if a file-meta with the given id exists. 
	 * */
	boolean exists(long fileMetaId) throws UnauthorizedException;

	/** 
	 * Remove the file-meta with the given id. 
	 * */
	public void remove(long fileMetaId) throws UnauthorizedException;
}
