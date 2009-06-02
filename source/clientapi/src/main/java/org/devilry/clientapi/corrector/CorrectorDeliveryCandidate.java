package org.devilry.clientapi.corrector;

import org.devilry.clientapi.AbstractDeliveryCandidate;
import org.devilry.clientapi.DevilryConnection;

public class CorrectorDeliveryCandidate extends AbstractDeliveryCandidate {

	CorrectorDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		super(deliveryCandidateId, connection);
	}
}
