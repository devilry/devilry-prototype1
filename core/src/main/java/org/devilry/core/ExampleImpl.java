package org.devilry.core;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName="Example")
public class ExampleImpl implements ExampleRemote {
    //@PersistenceContext(unitName="DevilryCore")
    private EntityManager em;

	public long test() {
		/*DeliveryCandidateNode n = new DeliveryCandidateNode();
		em.persist(n);
		return f.getId();*/
		return 0;
	}

	public String getData(long id) {
		//DeliveryCandidateNode n = new DeliveryCandidateNode();
		//em.persist(n);
		//n.addFile(new FileNode(n, "stuff", "assa".getBytes()));
		return "hei";
	}
}