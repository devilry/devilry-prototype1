package org.devilry.core.bendik.filestuff.MDB;

import javax.persistence.EntityManager;
import org.devilry.core.bendik.filestuff.entity.DevilryFile;
import javax.persistence.PersistenceContext;
import javax.ejb.EJBException;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType",
								  propertyValue = "javax.jms.Queue")
	}, mappedName="FileTransferJMSQueue")


public class ProcessFileTransferBean implements MessageListener {

    java.util.logging.Logger log = java.util.logging.Logger.getLogger(getClass().getName());

	@PersistenceContext(unitName="FileTransferService")
	private EntityManager em;
	
	public void onMessage(Message message) {
		try {
            if (message instanceof ObjectMessage) {
                   ObjectMessage objmsg = (ObjectMessage) message;
                   DevilryFile cust = (DevilryFile) objmsg.getObject();
                   DevilryFile mergedCust = em.merge(cust);
                   log.info("MDB: Customer persisted");
			} else {
				log.info("Wrong type of message");
			}
		} catch(Exception e) {
			throw new EJBException(e);
		}
	}
} 






