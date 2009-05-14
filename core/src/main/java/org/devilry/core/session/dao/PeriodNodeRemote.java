package org.devilry.core.session.dao;

import javax.ejb.Remote;
import javax.persistence.*;
import java.util.Date;

@Remote
public interface PeriodNodeRemote extends NodeRemote {
	public void setStartDate(Date start);
	public Date getStartDate();
	public void setEndDate(Date end);
	public Date getEndDate();
}

