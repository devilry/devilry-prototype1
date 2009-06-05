package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

public interface AssignmentNodeCommon extends BaseNodeInterface {
	
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
	
	
	/** 
	 * Add a new administrator to the given assignment node.
	 * @param assignmentNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void addAssignmentAdmin(long assignmentNodeId, long userId);
	
	
	/** 
	 * Remove an administrator from the given assignment node.
	 * @param assignmentNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void removeAssignmentAdmin(long assignmentNodeId, long userId);

	/** 
	 * Get id of all administrators registered for the given assignment assignment.
	 * 
	 * @param baseNodeId The unique number identifying an existing assignment.
	 * @return A list with the id of all administrators for the given assignment.
	 */
	public List<Long> getAssignmentAdmins(long assignmentNodeId);

	/** 
	 * Check if a user is admin on the given assignment. 
	 * */
	public boolean isAssignmentAdmin(long assignmentNodeId, long userId);

}
