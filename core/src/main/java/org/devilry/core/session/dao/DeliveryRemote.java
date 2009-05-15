package org.devilry.core.session.dao;

import javax.ejb.Remote;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Remote
public interface DeliveryRemote {
	public void init(long deliveryId);
	public long getId();
	public long getAssignmentId();
	public List<Long> getDeliveryCandidateIds();
	public long addDeliveryCandidate();
}