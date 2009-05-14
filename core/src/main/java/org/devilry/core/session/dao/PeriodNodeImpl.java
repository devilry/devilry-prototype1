package org.devilry.core.session.dao;

import javax.ejb.Stateful;
import java.util.Date;

import org.devilry.core.entity.*;

@Stateful
public class PeriodNodeImpl extends NodeImpl implements PeriodNodeRemote {
	public void setStartDate(Date start) {
		((PeriodNode) node).setStartDate(start);
		em.merge(node);
	}

	public Date getStartDate() {
		return ((PeriodNode) node).getStartDate();
	}

	public void setEndDate(Date end) {
		((PeriodNode) node).setEndDate(end);
		em.merge(node);
	}
	
	public Date getEndDate() {
		return ((PeriodNode) node).getEndDate();
	}
}

