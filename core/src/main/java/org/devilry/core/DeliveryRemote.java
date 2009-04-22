package org.devilry.core;

import java.util.Collection;
import javax.ejb.Remote;

@Remote
public interface DeliveryRemote {
	public long add(DeliveryCandidateNode dc);
	public DeliveryCandidateNode get(long id);
	public DeliveryCandidateNode getFull(long id);
}