package org.devilry.core.session.dao;

import javax.ejb.Remote;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Remote
public interface DeliveryRemote {
	public long create();
	public long getAssignment();
	public List<Long> getDeliveryCandidates();
}