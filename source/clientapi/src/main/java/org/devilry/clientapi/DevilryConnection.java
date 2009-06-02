package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.FileDataBlockLocal;
import org.devilry.core.daointerfaces.FileDataBlockRemote;
import org.devilry.core.daointerfaces.FileMetaLocal;
import org.devilry.core.daointerfaces.FileMetaRemote;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.daointerfaces.UserLocal;
import org.devilry.core.daointerfaces.UserRemote;

public interface DevilryConnection {
	
	public UserLocal getUser() throws NamingException;
		
	public NodeLocal getNode() throws NamingException;
	
	public CourseNodeLocal getCourseNode() throws NamingException;
	
	public PeriodNodeLocal getPeriodNode() throws NamingException;
	
	public AssignmentNodeLocal getAssignmentNode() throws NamingException;
	
	public DeliveryLocal getDelivery() throws NamingException;
	
	public DeliveryCandidateLocal getDeliveryCandidate() throws NamingException;
	
	public FileMetaLocal getFileMeta() throws NamingException;
	
	public FileDataBlockLocal getFileDataBlock() throws NamingException;
		
}
