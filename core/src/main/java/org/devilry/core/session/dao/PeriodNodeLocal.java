package org.devilry.core.session.dao;

import java.util.Date;

public interface PeriodNodeLocal extends BaseNodeInterface {
	public void setStartDate(long nodeId, Date start);
	public Date getStartDate(long nodeId);
	public void setEndDate(long nodeId, Date end);
	public Date getEndDate(long nodeId);
	public long create(String name, String displayName, Date start, Date end, long parentId);
}
