package org.devilry.clientapi.corrector;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.AbstractAssignment;
import org.devilry.clientapi.DevilryConnection;

public class CorrectorAssignment extends AbstractAssignment {
	
	CorrectorAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
		
	public Collection<CorrectorDelivery> getDeliveries() throws NamingException {
		
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
				
		List<CorrectorDelivery> deliveries = new LinkedList<CorrectorDelivery>();
		CorrectorDelivery deliveryTmp;
		
		for (long id : ids) {
			deliveryTmp = new CorrectorDelivery(id, connection);
			deliveries.add(deliveryTmp);
		}
		
		return deliveries;
	}
	
}