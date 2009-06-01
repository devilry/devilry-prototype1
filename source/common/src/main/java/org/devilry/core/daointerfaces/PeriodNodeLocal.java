package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

public interface PeriodNodeLocal extends BaseNodeInterface {
	public void setStartDate(long nodeId, Date start);
	public Date getStartDate(long nodeId);
	public void setEndDate(long nodeId, Date end);
	public Date getEndDate(long nodeId);
	public long create(String name, String displayName, Date start, Date end, long parentId);
	public List<Long> getAssignments(long parentId);
}
