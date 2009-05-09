package org.devilry.core;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface DeliveryBeanRemote {
	public long add(DeliveryCandidateNode dc);
	public DeliveryCandidateNode get(long id);
	public DeliveryCandidateNode getFull(long id);
	public FileNode getFile(DeliveryCandidateNode deliveryCandidate, String path);
	public List<String> getFilePaths(DeliveryCandidateNode deliveryCandidate);
}