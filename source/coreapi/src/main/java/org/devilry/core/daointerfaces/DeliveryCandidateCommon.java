package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

import org.devilry.core.UnauthorizedException;

public interface DeliveryCandidateCommon {
	
	/**
	 * Creates a delivery candidate in the database
	 * @param deliveryId
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 * @return the database id of the delivery candidate
	 */
	public long create(long deliveryId) throws UnauthorizedException;
		
	/**
	 * Get parent (Delivery) node
	 * 
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 * @return
	 */
	public long getDelivery(long deliveryCandidateId) throws UnauthorizedException;
		
	/**
	 * Add a file to the delivery candidate with ID deliveryCandiateId
	 * @param filePath
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 * @return the ID of the file that was added
	 */
	public long addFile(long deliveryCandidateId, String filePath) throws UnauthorizedException;
		
	/**
	 * Get all the files in the delivery candidate.
	 * @param deliveryCandidateId
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 * @return
	 */
	public List<Long> getFiles(long deliveryCandidateId) throws UnauthorizedException;
	
	/**
	 * Set the status of the delivery
	 * @param deliveryCandidateId
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 * @param status
	 */
	public void setStatus(long deliveryCandidateId, int status) throws UnauthorizedException;
	
	/**
	 * Get the status of the delivery.
	 * @param deliveryCandidateId
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 * @return
	 */
	public int getStatus(long deliveryCandidateId) throws UnauthorizedException;
	
	
	/** 
	 * Get the server-time when the delivery was created. 
	 *
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	public Date getTimeOfDelivery(long deliveryCandidateId) throws UnauthorizedException;
	
	
	/** 
	 * Remove a delivery candidate.
	 * 
	 * @param deliveryCandidateId The id of an existing delviery-candidate.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	public void remove(long deliveryCandidateId) throws UnauthorizedException;

	/** 
	 * Check if a delivery-candidate with the given id exists. 
	 * 
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	boolean exists(long deliveryCandidateId) throws UnauthorizedException;
}
