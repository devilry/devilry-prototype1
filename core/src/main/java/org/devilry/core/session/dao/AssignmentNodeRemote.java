package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AssignmentNodeRemote extends NodeRemote {
	public List<Long> getDeliveryIds();
	public long addDelivery();
}