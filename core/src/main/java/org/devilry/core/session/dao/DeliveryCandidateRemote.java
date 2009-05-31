package org.devilry.core.session.dao;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface DeliveryCandidateRemote {
	
	/**
	 * Get parent (Delivery) node
	 * @return
	 */
	public long getDelivery(long deliveryCandiateId);
	
	/**
	 * Creates a delivery candidate in the database
	 * @return the database id of the delivery candidate
	 */
	public long create();
	
	/**
	 * Add a file to the delivery candidate with ID deliveryCandiateId
	 * @param filePath
	 * @return the ID of the file that was added
	 */
	public long addFile(long deliveryCandiateId, String filePath);
		
	/**
	 * Get all the files in the delivery candidate.
	 * @param deliveryCandiateID
	 * @return
	 */
	public List<Long> getFiles(long deliveryCandiateID);
	
	/**
	 * Set the status of the delivery
	 * @param deliveryCandiateID
	 * @param status
	 */
	public void setStatus(long deliveryCandiateID, short status);
	
	/**
	 * Get the status of the delivery.
	 * @param deliveryCandiateID
	 * @return
	 */
	public short getStatus(long deliveryCandiateID);
	
}