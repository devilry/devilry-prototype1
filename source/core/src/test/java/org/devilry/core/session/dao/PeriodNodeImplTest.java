package org.devilry.core.session.dao;

import javax.naming.*;
import javax.persistence.*;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.dao.CourseNodeImpl;
import org.devilry.core.dao.PeriodNodeImpl;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.session.*;

public class PeriodNodeImplTest extends NodeImplTest {
	PeriodNodeRemote periodNode;
	long periodId;

	@Before
	public void setUp() throws NamingException {
		super.setUp();
		periodNode = getRemoteBean(PeriodNodeImpl.class);
		CourseNodeRemote courseNode = getRemoteBean(CourseNodeImpl.class);
		long inf1000Id = courseNode.create("inf1000", "Object oriented programming", matnatId);

		Calendar start = new GregorianCalendar(2009, 00, 01);
		Calendar end = new GregorianCalendar(2009, 05, 15);

		periodId = periodNode.create("fall09", "Fall 2009", start.getTime(), end.getTime(), inf1000Id);
	}


	@Test
	public void getStartDate() {
		assertEquals(new GregorianCalendar(2009, 00, 01).getTime().toString(),
				periodNode.getStartDate(periodId).toString());
	}

	@Test
	public void setStartDate() {
		Calendar start = new GregorianCalendar(2000, 00, 01);
		periodNode.setStartDate(periodId, start.getTime());
		assertEquals(start.getTime().toString(), periodNode.getStartDate(periodId).toString());
	}

	@Test
	public void getEndDate() {
		assertEquals(new GregorianCalendar(2009, 05, 15).getTime().toString(),
				periodNode.getEndDate(periodId).toString());

	}

	@Test
	public void setEndDate() {
		Calendar end = new GregorianCalendar(2000, 00, 01);
		periodNode.setEndDate(periodId, end.getTime());
		assertEquals(end.getTime().toString(), periodNode.getEndDate(periodId).toString());
	}

	@Test
	public void remove() {
		super.remove();
		assertFalse(node.exists(periodId));
	}
}

