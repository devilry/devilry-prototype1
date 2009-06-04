package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

public interface AssignmentNodeLocal extends BaseNodeInterface {
	
	/** Create a new assignment node.
	 * 
	 * @param name
	 * @param displayName
	 * @param deadline
	 * @param parentId
	 * @return
	 */
	public long create(String name, String displayName, Date deadline, long parentId);

	/** Get the period owning this assignment. */
	long getPeriod(long nodeId);
	
	/**
	 * Get the deadline for this assignment
	 * @param nodeId
	 * @return the date/time of the deadline.
	 */
	public Date getDeadline(long nodeId);
	
	/**
	 * Set the deadline for this assignment
	 * @param nodeId
	 * @param deadline The date/time of the deadline.
	 */
	public void setDeadline(long nodeId, Date deadline);
	
	/**
	 * Get deliveries 
	 * @param nodeId
	 * @return list of IDs for the deliveries
	 */
	public List<Long> getDeliveries(long assignmentId);

	/**
	 * Get deliveries where the user is student
	 * @param assignmentId
	 * @return id the of the delivery
	 */
	public List<Long> getDeliveriesWhereIsStudent(long assignmentId);
	
	/**
	 * Get deliveries where the user is examiner
	 * @param assignmentId
	 * @return
	 */
	public List<Long> getDeliveriesWhereIsExaminer(long assignmentId);


	/** Get a list of assignments where the authenticated user is admin.
	 * 
	 * @return List of assignment-ids.
	 * */
	List<Long> getAssignmentsWhereIsAdmin();
}
