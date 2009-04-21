package org.devilry.core;

import javax.ejb.Remote;

@Remote
public interface DeliveryRemote {
	public long add(DeliveryCandidateNode dc);
}