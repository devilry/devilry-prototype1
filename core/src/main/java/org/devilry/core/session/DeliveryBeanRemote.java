package org.devilry.core.session;

import org.devilry.core.entity.FileMetaEntity;
import org.devilry.core.entity.DeliveryCandidateEntity;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface DeliveryBeanRemote {
	public long add(DeliveryCandidateEntity dc);
	public DeliveryCandidateEntity get(long id);
	public DeliveryCandidateEntity getFull(long id);
	public FileMetaEntity getFile(DeliveryCandidateEntity deliveryCandidate, String path);
	public List<String> getFilePaths(DeliveryCandidateEntity deliveryCandidate);
}