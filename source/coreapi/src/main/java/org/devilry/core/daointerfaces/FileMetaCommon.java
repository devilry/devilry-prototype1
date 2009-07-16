package org.devilry.core.daointerfaces;

import java.util.List;

public interface FileMetaCommon {

	/**
	 * Create new FileMeta with deliveryCandidate parent deliveryCandidateId
	 * @param deliveryCandidateId
	 * @param filePath
	 */
	long create(long deliveryCandidateId, String filePath);

	/**
	 * Get id of parent deliveryCandidate
	 * @param fileMetaId
	 * @return
	 */
	long getDeliveryCandidate(long fileMetaId);

	/**
	 * Get the filepath for the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return the path
	 */
	String getFilePath(long fileMetaId);

	/**
	 * Get size of all FileDataBlocks referencing the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return
	 */
	int getSize(long fileMetaId);

	/**
	 * Get list of ids of all FileDataBlocks refencing the FileMeta with id fileMetaId
	 * @param fileMetaId
	 * @return
	 */
	List<Long> getFileDataBlocks(long fileMetaId);

	/** Check if a file-meta with the given id exists. */
	boolean exists(long fileMetaId);

	/** Remove the file-meta with the given id. */
	public void remove(long fileMetaId);
}
