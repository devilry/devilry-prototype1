package org.devilry.clientapi;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class AdminNodeCommonTest extends NodeCommonTest {
	
	Student homer;
	Student bart, lisa;
	
	//AdminPeriod period;
	//AdminPeriod period2;
				
	long inf1000Spring09, inf1000Fall09;
	
	AdminNode adminNode;
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
				
		super.setUp();
		// Calendar start = new GregorianCalendar(2009, 00, 01, 10, 15);
		// Calendar end = new GregorianCalendar(2009, 05, 15);
		// inf1000Spring09 = periodNode.create("spring2009", "INF1000 spring2009", start.getTime(), end.getTime(), inf1000);
		// inf1000Fall09 = periodNode.create("fall2009", "INf1000 fall 2009", start.getTime(), end.getTime(), inf1000);
		
		
		//period = new AdminPeriod(inf1000Spring09, connection);
		//period2 = new AdminPeriod(inf1000Fall09, connection);
		
		// Create some test users
		homer = new Student(homerId, connection);
		bart = new Student(bartId, connection);
		lisa = new Student(lisaId, connection);
		
		adminNode = new AdminNode(ifiId, connection);
	}
	
	@Test
	public void getPath() throws NoSuchObjectException, NamingException, InvalidNameException {
		NodePath path = adminNode.getPath();
		
		NodePath check = new NodePath(new String[]{"uio", "matnat", "ifi"});
		assertTrue(path.equals(check));
	}
	
	@Test
	public void getNodesWhereIsAdmin() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException, NoSuchUserException {
		
		///List<AdminNode> getNodesWhereIsAdmin()
		
		List<AdminNode> adminNodes = adminNode.getNodesWhereIsAdmin();
		
		assertEquals(0, adminNodes.size());
		
		//long inf1010 = courseNode.create("inf1010", "Programmering intro", ifiId);
		AdminNode nd = adminNode.addSubnode("ND", "");
		nd.addNodeAdmin(homerId);		
		
		// Course should not be returned by getNodesWhereIsAdmin
		AdminCourse inf1010 = adminNode.addCourse("inf1010", "Programmering intro");
		inf1010.addCourseAdmin(homerId);
		
		List<AdminNode> adminCourses = adminNode.getNodesWhereIsAdmin();
		assertEquals(1, adminCourses.size());
	}
	
	@Test
	public void addSubnode() throws UnauthorizedException, NoSuchObjectException, NamingException, PathExistsException, InvalidNameException {
		
		assertEquals(0, adminNode.getSubnodes().size());
		
		AdminNode ifi2 = adminNode.addSubnode("ifi2", "Ifi 2");
		assertEquals(1, adminNode.getSubnodes().size());
		assertEquals(ifi2.nodeId, adminNode.getSubnodes().get(0).nodeId);
	}
	
	@Test
	public void removeSubnodes() throws UnauthorizedException, NoSuchObjectException, NamingException, PathExistsException, InvalidNameException {
		
		AdminNode ifi2 = adminNode.addSubnode("ifi2", "Ifi 2");
		AdminNode ifi3 = adminNode.addSubnode("ifi3", "Ifi 3");
		
		adminNode.removeSubnode(ifi2);
		assertEquals(1, adminNode.getSubnodes().size());
		
		adminNode.removeSubnode(ifi3);
		assertEquals(0, adminNode.getSubnodes().size());
	}
	
	@Test
	public void getSubnodes() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		AdminNode ifi2 = adminNode.addSubnode("ifi2", "Ifi 2");
		AdminNode ifi3 = adminNode.addSubnode("ifi3", "Ifi 3");
		
		List<AdminNode> subnodes = adminNode.getSubnodes();
		
		for (AdminNode node : subnodes) {
			assertTrue(node.nodeId == ifi2.nodeId || node.nodeId == ifi3.nodeId);
		}
	}
	
	@Test
	public void addCourse() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
				
		AdminCourse newCourse = adminNode.addCourse("inf2100", "Proglab");

		List<AdminCourse> courses = adminNode.getCourses();
		assertEquals(newCourse.courseId, courses.get(0).courseId);
		
		AdminCourse newCourse2 = adminNode.addCourse("inf1060", "C");
		courses = adminNode.getCourses();
		assertEquals(2, courses.size());
	}
	
	@Test
	public void removeCourse() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		
		AdminCourse newCourse = adminNode.addCourse("inf2100", "Proglab");
		AdminCourse newCourse2 = adminNode.addCourse("inf1060", "C");
		
		adminNode.removeCourse(newCourse);
		assertEquals(1, adminNode.getCourses().size());
		
		adminNode.removeCourse(newCourse2);
		assertEquals(0, adminNode.getCourses().size());
	}
	
	@Test
	public void getCourses() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		AdminCourse newCourse = adminNode.addCourse("inf2100", "Proglab");
		AdminCourse newCourse2 = adminNode.addCourse("inf1060", "C");
		
		List<AdminCourse> courses = adminNode.getCourses();
		
		for (AdminCourse course : courses) {
			assertTrue(course.courseId == newCourse.courseId || course.courseId == newCourse2.courseId);
		}
	}
	
	@Test
	public void setNodeName() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		String newNodeName = "hf";
		adminNode.setNodeName(newNodeName);
		assertEquals(newNodeName, adminNode.getNodeName());
	}
	
	@Test
	public void setNodeDisplayName() throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		String newNodeDisplayName = "hf";
		adminNode.setNodeDisplayName(newNodeDisplayName);
		assertEquals(newNodeDisplayName, adminNode.getNodeDisplayName());
	}	
	
	@Test
	public void addNodeAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, NamingException {
		adminNode.addNodeAdmin(homerId);
		adminNode.addNodeAdmin(lisaId);
		
		List<Long> admins = adminNode.getNodeAdmins();
		assertEquals(2, admins.size());
	}
	
	@Test
	public void removeNodeAdmin() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, NamingException {
		adminNode.addNodeAdmin(homerId);
		adminNode.addNodeAdmin(lisaId);
		
		adminNode.removeNodeAdmin(homerId);	
		assertEquals(1, adminNode.getNodeAdmins().size());
		
		adminNode.removeNodeAdmin(lisaId);
		assertEquals(0, adminNode.getNodeAdmins().size());
	}
	
	@Test
	public void getNodeAdmins() throws NoSuchObjectException, NoSuchUserException, UnauthorizedException, NamingException {
		adminNode.addNodeAdmin(homerId);
		adminNode.addNodeAdmin(lisaId);
		
		List<Long> admins = adminNode.getNodeAdmins();
		assertEquals(2, admins.size());
				
		for (long admin : admins) {
			assertTrue(admin== homerId || admin == lisaId);
		}
	}
}
