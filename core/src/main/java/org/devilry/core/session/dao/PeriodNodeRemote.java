package org.devilry.core.session.dao;

import javax.ejb.Remote;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Remote
public interface PeriodNodeRemote extends NodeRemote {
	public void setStartDate(long nodeId, Date start);
	public Date getStartDate(long nodeId);
	public void setEndDate(long nodeId, Date end);
	public Date getEndDate(long nodeId);
	public long create(String name, String displayName, Date start, Date end, long parentId);
	public List<Long> getAllPeriodIds();
}

