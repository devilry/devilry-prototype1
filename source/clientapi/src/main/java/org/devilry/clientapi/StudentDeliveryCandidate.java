package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.StudentDelivery.StudentDeliveryCandidateIterator;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.FileMetaCommon;


public class StudentDeliveryCandidate extends AbstractDeliveryCandidate {
	
	FileMetaCommon fileMeta;
	
	StudentDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		super(deliveryCandidateId, connection);
	}
	
	protected FileMetaCommon getFileMetaBean() throws NamingException {
		return fileMeta == null ? fileMeta = connection.getFileMeta() : fileMeta;
	}
	
	public DevilryOutputStream addFile(String filePath) throws NamingException {
		
		long fileMetaId = getFileMetaBean().create(deliveryCandidateId, filePath);
		
		DevilryOutputStream outputStream = new DevilryOutputStream(fileMetaId, connection);
		return outputStream;
	}
	
	
	class DevilryInputStreamIterator implements Iterable<DevilryInputStream>, Iterator<DevilryInputStream> {

		Iterator<Long> fileMetaIdIterator;
		
		DevilryInputStreamIterator(List<Long> ids) {
			fileMetaIdIterator = ids.iterator();
		}
		
		public Iterator<DevilryInputStream> iterator() {
			return this;
		}

		public boolean hasNext() {
			return fileMetaIdIterator.hasNext();
		}

		public DevilryInputStream next() {
			return new DevilryInputStream(fileMetaIdIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
		
	Iterator<DevilryInputStream> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> fileIds = getDeliveryCandidateBean().getFiles(deliveryCandidateId);
		return new DevilryInputStreamIterator(fileIds).iterator();
	}
	
	
	public List<DevilryInputStream> getDeliveryFiles() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<DevilryInputStream> candidateList = new LinkedList<DevilryInputStream>();
		Iterator<DevilryInputStream> iter = deliveries();
		
		while (iter.hasNext()) {
			candidateList.add(iter.next());
		}
		return candidateList;
	}
	

}
