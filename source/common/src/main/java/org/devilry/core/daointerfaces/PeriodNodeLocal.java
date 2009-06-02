package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

public interface PeriodNodeLocal extends BaseNodeInterface {
	public void setStartDate(long periodNodeId, Date start);
	public Date getStartDate(long periodNodeId);
	public void setEndDate(long periodNodeId, Date end);
	public Date getEndDate(long periodNodeId);
	public long create(String name, String displayName, Date start, Date end, long parentId);
	public List<Long> getAssignments(long periodNodeId);
	
	/** Get id of all students registered for the given node.
	 * 
	 * @param nodeId The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 */
	List<Long> getStudents(long nodeId);

	/** Check if a user is student on the given node. */
	boolean isStudent(long nodeId, long userId);

	/** Add a new student to the given node.
	 * 
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	void addStudent(long nodeId, long userId);

	/** Remove an student from the given node.
	 * 
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	void removeStudent(long nodeId, long userId);


	/** Get a list of nodes where the given user is admin. */
	List<Long> getNodesWhereIsStudent(long userId);
}
