package org.devilry.core.session.dao;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface DeliveryCandidateRemote {
	
	/**
	 * Creates a delivery candidate in the database
	 * @param deliveryId
	 * @return the database id of the delivery candidate
	 */
	public long create(long deliveryId);
		
	/**
	 * Get parent (Delivery) node
	 * @return
	 */
	public long getDelivery(long deliveryCandidateId);
		
	/**
	 * Add a file to the delivery candidate with ID deliveryCandiateId
	 * @param filePath
	 * @return the ID of the file that was added
	 */
	public long addFile(long deliveryCandidateId, String filePath);
		
	/**
	 * Get all the files in the delivery candidate.
	 * @param deliveryCandiateId
	 * @return
	 */
	public List<Long> getFiles(long deliveryCandidateId);
	
	/**
	 * Set the status of the delivery
	 * @param deliveryCandiateId
	 * @param status
	 */
	public void setStatus(long deliveryCandidateId, int status);
	
	/**
	 * Get the status of the delivery.
	 * @param deliveryCandiateId
	 * @return
	 */
	public int getStatus(long deliveryCandidateId);
	
}