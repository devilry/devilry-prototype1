package org.devilry.core.session.dao;

import javax.ejb.Remote;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Remote
public interface DeliveryRemote {
	
	/**
	 * Get parent (Assignment) node
	 * @return
	 */
	public long getAssignment(long deliveryID);
	
	/** 
	 * Create a new Delivery in the database.
	 */
	public long create();
		
	/**
	 * Set status of the delivery
	 * @param deliveryID
	 * @param status
	 */
	public void setStatus(long deliveryID, short status);
	
	/**
	 * Get status of the delivery
	 * @return
	 */
	public short getStatus(long deliveryID);
	
	/**
	 * Get IDs of all delivery candidates for the delivery
	 * @return
	 */
	public List<Long> getDeliveryCandidates(long deliveryID);
	
	/**
	 * Get the Id of the delivery that was delivered latest (in time) 
	 * @param deliveryID
	 * @return
	 */
	public long getLastDeliveryCandidate(long deliveryID);
	
	/**
	 * Get the delivery that was delivered latest (in time) that was before the deadline of the assignment.
	 * @param deliveryID
	 * @return
	 */
	public long getLastValidDeliveryCandidate(long deliveryID);
		
}