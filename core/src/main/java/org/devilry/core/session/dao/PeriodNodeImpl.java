package org.devilry.core.session.dao;

import javax.ejb.Stateful;
import java.util.Date;

@Stateful
public class PeriodNodeImpl extends NodeImpl implements PeriodNodeRemote {
	public void setStartDate(Date start) {
		((org.devilry.core.entity.PeriodNode) node).setStartDate(start);
		em.merge(node);
	}

	public Date getStartDate() {
		return ((org.devilry.core.entity.PeriodNode) node).getStartDate();
	}

	public void setEndDate(Date end) {
		((org.devilry.core.entity.PeriodNode) node).setEndDate(end);
		em.merge(node);
	}
	
	public Date getEndDate() {
		return ((org.devilry.core.entity.PeriodNode) node).getEndDate();
	}
}

