package org.devilry.core.daointerfaces;

import java.util.List;

public interface DeliveryLocal {
	/**
	 * Create a new Delivery in the database.
	 * 
	 * @param assignmentId
	 *            The id of the (parent) assignment.
	 * @return The id of the newly created delivery.
	 */
	long create(long assignmentId);

	/**
	 * Get parent (Assignment) node
	 * 
	 * @return
	 */
	long getAssignment(long deliveryId);

	/**
	 * Get status of the delivery
	 * 
	 * @return
	 */
	int getStatus(long deliveryId);

	/**
	 * Set status of the delivery
	 * 
	 * @param deliveryId
	 * @param status
	 */
	void setStatus(long deliveryId, int status);

	/**
	 * Get IDs of all delivery candidates for the delivery
	 * 
	 * @return
	 */
	List<Long> getDeliveryCandidates(long deliveryId);

	/**
	 * Get the Id of the delivery that was delivered latest (in time)
	 * 
	 * @param deliveryId
	 * @return
	 */
	long getLastDeliveryCandidate(long deliveryId);

	/**
	 * Get the delivery that was delivered latest (in time) that was before the
	 * deadline of the assignment.
	 * 
	 * @param deliveryId
	 * @return
	 */
	long getLastValidDeliveryCandidate(long deliveryId);

	/**
	 * Get id of all correctors assigned for this delivery
	 * 
	 * @param deliveryIs
	 * @return
	 */
	List<Long> getCorrectors(long deliveryId);

	/**
	 * Get id of all students registered for this delivery
	 * 
	 * @param deliveryId
	 * @return
	 */
	List<Long> getStudents(long deliveryId);

	/** Check if a delivery with the given id exists. */
	boolean exists(long deliveryId);
	
	/** Remove the delivery with the given id. */
	public void remove(long deliveryId);
}
