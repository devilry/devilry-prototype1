package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.*;


public interface DevilryConnection {
	
	public UserCommon getUser() throws NamingException;
		
	public NodeCommon getNode() throws NamingException;
	
	public CourseNodeCommon getCourseNode() throws NamingException;
	
	public PeriodNodeCommon getPeriodNode() throws NamingException;
	
	public AssignmentNodeCommon getAssignmentNode() throws NamingException;
	
	public DeliveryCommon getDelivery() throws NamingException;
	
	public DeliveryCandidateCommon getDeliveryCandidate() throws NamingException;
	
	public FileMetaCommon getFileMeta() throws NamingException;
	
	public FileDataBlockCommon getFileDataBlock() throws NamingException;
		
}
