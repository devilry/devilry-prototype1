package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;

public interface DeliveryCommon {
	/**
	 * Create a new Delivery in the database.
	 *
	 * @param assignmentId The id of the (parent) assignment.
	 * @return The id of the newly created delivery.
	 * @throws UnauthorizedException If the authenticated user is not authorized for this method.
	 * @throws NoSuchObjectException If no assignment-node with the given id exists.
	 */
	long create(long assignmentId) throws UnauthorizedException,
			NoSuchObjectException;

	/**
	 * Get parent (Assignment) node.
	 *
	 * @return The id of the parent-node.
	 */
	long getAssignment(long deliveryId) throws UnauthorizedException;

	/**
	 * Get status of the delivery
	 *
	 * @return
	 */
	int getStatus(long deliveryId) throws UnauthorizedException;

	/**
	 * Set status of the delivery
	 *
	 * @param deliveryId
	 * @param status
	 */
	void setStatus(long deliveryId, int status) throws UnauthorizedException;

	/**
	 * Get IDs of all delivery candidates for the delivery.
	 *
	 * @param deliveryId The id of an existing delivery.
	 * @return A list containing the ID of all delivery candidates for the given
	 *         delivery.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	List<Long> getDeliveryCandidates(long deliveryId)
			throws UnauthorizedException;

	/**
	 * Get the Id of the delivery that was delivered latest (in time).
	 *
	 * @param deliveryId The id of an existing delivery.
	 * @return The id of a delivery-candidate.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	long getLastDeliveryCandidate(long deliveryId) throws UnauthorizedException;

	/**
	 * Get the delivery that was delivered latest (in time) that was before the
	 * deadline of the assignment.
	 *
	 * @param deliveryId The id of an existing delivery.
	 * @return The id of the last valid deliverycandidate.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	long getLastValidDeliveryCandidate(long deliveryId)
			throws UnauthorizedException;

	/**
	 * Check if a delivery with the given id exists.
	 */
	boolean exists(long deliveryId) throws UnauthorizedException;

	/**
	 * Remove the delivery with the given id.
	 *
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	public void remove(long deliveryId) throws UnauthorizedException;

	/**
	 * Get id of all students registered for the given delivery.
	 *
	 * @param deliveryId The unique number identifying an existing delivery.
	 * @return A list with the id of all administrators for the given delivery.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	List<Long> getStudents(long deliveryId) throws UnauthorizedException;

	/**
	 * Check if the authenticated user is student on the given delivery.
	 *
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	boolean isStudent(long deliveryId)
			throws UnauthorizedException;

	/**
	 * Add a new student to the given delivery.
	 *
	 * @param deliveryId The unique number identifying an existing delivery.
	 * @param userId	 The unique number identifying an existing user.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	void addStudent(long deliveryId, long userId) throws UnauthorizedException;

	/**
	 * Remove an student from the given delivery.
	 *
	 * @param deliveryId The unique number identifying an existing delivery.
	 * @param userId	 The unique number identifying an existing user.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	void removeStudent(long deliveryId, long userId)
			throws UnauthorizedException;

	/**
	 * Get a list of deliveries where the authenticated user is student.
	 */
	List<Long> getDeliveriesWhereIsStudent() throws UnauthorizedException;

	/**
	 * Get id of all Examiners registered for the given delivery.
	 *
	 * @param deliveryId The unique number identifying an existing delivery.
	 * @return A list with the id of all administrators for the given delivery.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	List<Long> getExaminers(long deliveryId) throws UnauthorizedException;

	/**
	 * Check if the authenticated user is Examiner on the given delivery.
	 *
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	boolean isExaminer(long deliveryId) throws UnauthorizedException;

	/**
	 * Add a new Examiner to the given delivery.
	 *
	 * @param deliveryId The unique number identifying an existing delivery.
	 * @param userId	 The unique number identifying an existing user.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	void addExaminer(long deliveryId, long userId) throws UnauthorizedException;

	/**
	 * Remove an Examiner from the given delivery.
	 *
	 * @param deliveryId The unique number identifying an existing delivery.
	 * @param userId	 The unique number identifying an existing user.
	 * @throws UnauthorizedException If the authenticated user is not authorized
	 *                               for this method.
	 */
	void removeExaminer(long deliveryId, long userId)
			throws UnauthorizedException;

	/**
	 * Get a list of deliveries where the authenticated user is examiner.
	 */
	List<Long> getDeliveriesWhereIsExaminer() throws UnauthorizedException;
}
