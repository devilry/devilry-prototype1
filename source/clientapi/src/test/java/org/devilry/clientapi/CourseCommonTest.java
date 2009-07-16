package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class CourseCommonTest {
		
	protected static DevilryConnection connection;
	
	protected long uioId, matnatId, ifiId, inf1000;
	
	protected long homerId;
	protected long bartId;
	protected long lisaId;
	
	NodeCommon node;
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	
	protected UserCommon userBean;
	
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> identity = new ArrayList<String>();
	ArrayList<String> emails = new ArrayList<String>();
	ArrayList<String> phoneNumbers = new ArrayList<String>();
			
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		node = connection.getNode();
		courseNode = connection.getCourseNode();
		userBean = connection.getUser();
		periodNode = connection.getPeriodNode();
		
		// Add users
		names.add("Homer Simpson");
		names.add("Bart Simpson");
		names.add("Lisa Simpson");

		identity.add("homer");
		identity.add("bart");
		identity.add("lisa");
		
		emails.add("homer@doh.com");
		emails.add("bart@doh.com");
		emails.add("lisa@doh.com");
		
		phoneNumbers.add("1111111");
		phoneNumbers.add("9999999");
		phoneNumbers.add("6666666");
				
		homerId = userBean.create(names.get(0), emails.get(0), phoneNumbers.get(0));
		userBean.addIdentity(homerId, identity.get(0));
		userBean.setIsSuperAdmin(homerId, true);
		
		bartId = userBean.create(names.get(1), emails.get(1), phoneNumbers.get(1));
		userBean.addIdentity(bartId, identity.get(1));
		
		lisaId = userBean.create(names.get(2), emails.get(2), phoneNumbers.get(2));
		userBean.addIdentity(lisaId, identity.get(2));
		
		
		// Add nodes
		uioId = node.create("uio", "UiO");
		matnatId = node.create("matnat", "Faculty of Mathematics", uioId);
		ifiId = node.create("ifi", "Department of Informatics", matnatId);
						
		inf1000 = courseNode.create("inf1000", "Programmering intro", ifiId);
	}
		

	@After
	public void tearDown() throws NamingException, NoSuchObjectException, UnauthorizedException {
						
		for(long nodeId: node.getToplevelNodes()) {
			node.remove(nodeId);
		}	
		
		for(long userId: userBean.getUsers()) {
			userBean.remove(userId);
		}
	}
}
