package org.devilry.core.session.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AssignmentNodeRemote extends NodeRemote {
	
	/**
	 * Get deliveries 
	 * @param nodeId
	 * @return list of ids for the deliveries
	 */
	public List<Long> getDeliveries(long assignmentId);
	
	/**
	 * Get the deadline for this assignment
	 * @param assignmentId
	 * @return the deadline in Date format
	 */
	public Date getDeadline(long assignmentId);
	
	/**
	 * Set the deadline for this assignment
	 * @param assignmentId
	 * @param date
	 */
	public void setDeadline(long assignmentId, Date date);
}