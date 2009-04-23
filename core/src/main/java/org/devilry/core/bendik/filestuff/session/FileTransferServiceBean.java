package org.devilry.core.bendik.filestuff.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import org.devilry.core.bendik.filestuff.entity.DevilryFile;
import javax.persistence.PersistenceContext;
import javax.ejb.EJBException;
import javax.jms.*;
import javax.annotation.Resource;


@Stateless
public class FileTransferServiceBean implements FileTransferService {
   
@Resource(mappedName="FileTransferConnectionFactory")
private ConnectionFactory cf;

@Resource(mappedName="FileTransferJMSQueue")
private Queue queue;

   @PersistenceContext(unitName="FileTransferService")
   private EntityManager em;

    public DevilryFile findFile(int custId) {
        return ((DevilryFile) em.find(DevilryFile.class, custId));
    }

     public void addFile(int custId, String path, byte[] data) {
     try{
        DevilryFile file = new DevilryFile();
        file.setId(custId);
        file.setFilePath(path);
        file.setFileData(data);
        
        Connection conn = cf.createConnection();
        Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer mp = sess.createProducer(queue);
        ObjectMessage objmsg = sess.createObjectMessage();
        objmsg.setObject(file);
        mp.send(objmsg);
        conn.close();
     } catch (JMSException e) {
        e.printStackTrace();
     }
    }

    public void updateFile(DevilryFile cust) {
           DevilryFile mergedCust = em.merge(cust);
    }
            
}

