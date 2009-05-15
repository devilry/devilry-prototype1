package org.devilry.core.session.dao;

import org.devilry.core.session.TreeManagerRemote;
import org.devilry.core.session.TreeManagerImpl;

import javax.naming.NamingException;
import java.util.GregorianCalendar;

public class AbstractDeliveryDaoTest extends AbstractDaoTest {
	DeliveryRemote delivery;

	protected void setupEjbContainer() throws NamingException {
		super.setupEjbContainer();

		TreeManagerRemote tm = getRemoteBean(TreeManagerImpl.class);
		long id = tm.addNode("uio", "Universitet i Oslo");
		id = tm.addCourseNode("inf1000", "INF1000", "First programming course.", id);
		id = tm.addPeriodNode("spring2009", new GregorianCalendar(2009, 1, 1).getTime(),
				new GregorianCalendar(2009, 6, 15).getTime(), id);
		id = tm.addAssignmentNode("Oblig1", "Obligatory assignment 1", id);
		AssignmentNodeRemote node = getRemoteBean(AssignmentNodeImpl.class);
		node.init(id);
		delivery = getRemoteBean(DeliveryImpl.class);
		delivery.init(node.addDelivery());
	}
}
