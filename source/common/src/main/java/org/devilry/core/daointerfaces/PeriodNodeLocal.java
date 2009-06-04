package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

public interface PeriodNodeLocal extends BaseNodeInterface {
	public void setStartDate(long periodperiodId, Date start);
	public Date getStartDate(long periodperiodId);
	public void setEndDate(long periodperiodId, Date end);
	public Date getEndDate(long periodperiodId);
	public long create(String name, String displayName, Date start, Date end, long parentId);
	
	long getCourse(long periodId);
	public List<Long> getAssignments(long periodperiodId);
	
	/** Get id of all students registered for the given period.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @return A list with the id of all administrators for the given node.
	 */
	List<Long> getStudents(long periodId);

	/** Check if a user is student on the given node. */
	boolean isStudent(long periodId, long userId);

	/** Add a new student to the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	void addStudent(long periodId, long userId);

	/** Remove an student from the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	void removeStudent(long periodId, long userId);


	/** Get a list of nodes where the authenticated user is student. */
	List<Long> getPeriodsWhereIsStudent();


	/** Get id of all Examiners registered for the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @return A list with the id of all administrators for the given node.
	 */
	List<Long> getExaminers(long periodId);

	
	/** Check if a user is Examiner on the given node. */
	boolean isExaminer(long periodId, long userId);

	/** Add a new Examiner to the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	void addExaminer(long periodId, long userId);

	/** Remove an Examiner from the given node.
	 * 
	 * @param periodId The unique number identifying an existing period.
	 * @param userId The unique number identifying an existing user.
	 */
	void removeExaminer(long periodId, long userId);
	
	/** Get a list of nodes where the authenticated user is examiner. */
	List<Long> getPeriodsWhereIsExaminer();

	/** Get a list of periods where the authenticated user is admin.
	 * 
	 * @return List of period-ids.
	 * */
	List<Long> getPeriodsWhereIsAdmin();
}
