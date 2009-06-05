package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

public interface PeriodNodeCommon extends BaseNodeInterface {
	public void setStartDate(long periodperiodId, Date start);
	public Date getStartDate(long periodperiodId);
	public void setEndDate(long periodperiodId, Date end);
	public Date getEndDate(long periodperiodId);
	public long create(String name, String displayName, Date start, Date end, long parentId);
	
	public long getCourse(long periodId);
	public List<Long> getAssignments(long periodperiodId);
	
	/** Get id of all students registered for the given period.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @return A list with the id of all administrators for the given node.
	 */
	public List<Long> getStudents(long periodId);

	/** Check if a user is student on the given node. */
	public boolean isStudent(long periodId, long userId);

	/** Add a new student to the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	public void addStudent(long periodId, long userId);

	/** Remove an student from the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	public void removeStudent(long periodId, long userId);


	/** Get a list of nodes where the authenticated user is student. */
	public List<Long> getPeriodsWhereIsStudent();


	/** Get id of all Examiners registered for the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @return A list with the id of all administrators for the given node.
	 */
	public List<Long> getExaminers(long periodId);

	
	/** Check if a user is Examiner on the given node. */
	public boolean isExaminer(long periodId, long userId);

	/** Add a new Examiner to the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	public void addExaminer(long periodId, long userId);

	/** Remove an Examiner from the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	public void removeExaminer(long periodId, long userId);
	
	/** Get a list of nodes where the authenticated user is examiner. */
	List<Long> getPeriodsWhereIsExaminer();


	/** Get a list of periods where the authenticated user is admin.
	 * 
	 * @return List of period-ids.
	 * */
	public List<Long> getPeriodsWhereIsAdmin();


	/** 
	 * Check if a user is admin on the given period node. 
	 * */
	public boolean isPeriodAdmin(long periodNodeId, long userId);
	
	/** 
	 * Add a new administrator to the given period node.
	 * @param periodNodeId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	public void addPeriodAdmin(long periodNodeId, long userId);
	
	
	/** 
	 * Remove an administrator from the given period node.
	 * @param periodNodeId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	public void removePeriodAdmin(long periodNodeId, long userId);
	
	
	/** 
	 * Get id of all administrators registered for the given period node.
	 * 
	 * @param baseNodeId The unique number identifying an existing period.
	 * @return A list with the id of all administrators for the given node.
	 */
	public List<Long> getPeriodAdmins(long periodNodeId);

	public long getNodeIdFromPath(String [] nodePath, long parentNodeId);
}
