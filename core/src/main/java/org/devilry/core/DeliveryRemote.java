package org.devilry.core;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface DeliveryRemote {
	public long add(DeliveryCandidateNode dc);
	public DeliveryCandidateNode get(FileNodeId id);
	public DeliveryCandidateNode getFull(FileNodeId id);
	public FileNode getFile(DeliveryCandidateNode deliveryCandidate, String path);
	public List<String> getFilePaths(DeliveryCandidateNode deliveryCandidate);
}