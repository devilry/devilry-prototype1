package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;

public interface DeliveryCommon {
	/**
	 * Create a new Delivery in the database.
	 * 
	 * @param assignmentId
	 *            The id of the (parent) assignment.
	 * @return The id of the newly created delivery.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @throws NoSuchObjectException
	 *             If no assignment-node with the given id exists.
	 */
	long create(long assignmentId) throws UnauthorizedException,
		NoSuchObjectException;

	/**
	 * Get parent (Assignment) node.
	 * 
	 * @return The id of the parent-node.
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
	 * Get IDs of all delivery candidates for the delivery.
	 * 
	 * @param deliveryId The id of an existing delivery. 
	 * 
	 * @return A list containing the ID of all delivery candidates for the given
	 *         delivery.
	 */
	List<Long> getDeliveryCandidates(long deliveryId);

	/**
	 * Get the Id of the delivery that was delivered latest (in time).
	 * 
	 * @param deliveryId The id of an existing delivery. 
	 * @return The id of a delivery-candidate.
	 */
	long getLastDeliveryCandidate(long deliveryId);

	/**
	 * Get the delivery that was delivered latest (in time) that was before the
	 * deadline of the assignment.
	 * 
	 * @param deliveryId The id of an existing delivery. 
	 * @return
	 */
	long getLastValidDeliveryCandidate(long deliveryId);

	/** Check if a delivery with the given id exists. */
	boolean exists(long deliveryId);

	/** Remove the delivery with the given id. */
	public void remove(long deliveryId);

	/**
	 * Get id of all students registered for the given delivery.
	 * 
	 * @param deliveryId
	 *            The unique number identifying an existing delivery.
	 * @return A list with the id of all administrators for the given delivery.
	 */
	List<Long> getStudents(long deliveryId);

	/** Check if a user is student on the given delivery. */
	boolean isStudent(long deliveryId, long userId);

	/**
	 * Add a new student to the given delivery.
	 * 
	 * @param deliveryId
	 *            The unique number identifying an existing delivery.
	 * @param userId
	 *            The unique number identifying an existing user.
	 */
	void addStudent(long deliveryId, long userId);

	/**
	 * Remove an student from the given delivery.
	 * 
	 * @param deliveryId
	 *            The unique number identifying an existing delivery.
	 * @param userId
	 *            The unique number identifying an existing user.
	 */
	void removeStudent(long deliveryId, long userId);

	/** Get a list of deliveries where the authenticated user is student. */
	List<Long> getDeliveriesWhereIsStudent();

	/**
	 * Get id of all Examiners registered for the given delivery.
	 * 
	 * @param deliveryId
	 *            The unique number identifying an existing delivery.
	 * @return A list with the id of all administrators for the given delivery.
	 */
	List<Long> getExaminers(long deliveryId);

	/** Check if a user is Examiner on the given delivery. */
	boolean isExaminer(long deliveryId, long userId);

	/**
	 * Add a new Examiner to the given delivery.
	 * 
	 * @param deliveryId
	 *            The unique number identifying an existing delivery.
	 * @param userId
	 *            The unique number identifying an existing user.
	 */
	void addExaminer(long deliveryId, long userId);

	/**
	 * Remove an Examiner from the given delivery.
	 * 
	 * @param deliveryId
	 *            The unique number identifying an existing delivery.
	 * @param userId
	 *            The unique number identifying an existing user.
	 */
	void removeExaminer(long deliveryId, long userId);

	/** Get a list of deliveries where the authenticated user is examiner. */
	List<Long> getDeliveriesWhereIsExaminer();
}
