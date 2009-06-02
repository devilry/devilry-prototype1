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
}
