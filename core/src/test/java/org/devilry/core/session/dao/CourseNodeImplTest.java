package org.devilry.core.session.dao;

import javax.naming.*;
import javax.persistence.*;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.devilry.core.session.*;

public class CourseNodeImplTest {
	CourseNodeRemote node;
	TreeManagerRemote tm;
	InitialContext ctx;

	@Before
	public void setUp() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		p.put("openejb.deploymentId.format",
				"{ejbName}{interfaceType.annotationName}");
		p.put("openejb.jndiname.format",
				"{ejbName}{interfaceType.annotationName}");
		ctx = new InitialContext(p);
		
		tm = (TreeManagerRemote) ctx.lookup("TreeManagerImplRemote");
		node = (CourseNodeRemote) ctx.lookup("CourseNodeImplRemote");

		tm.addNode("uio", "Universitetet i Oslo");
		tm.addNode("matnat", "Det matematisk-naturvitenskapelige fakultet",
				tm.getNodeIdFromPath("uio"));
		tm.addNode("ifi", "Institutt for informatikk", 
				tm.getNodeIdFromPath("uio.matnat"));
		tm.addCourseNode("inf1000", "INF1000", "Grunnkurs i objektorientert programmering",
				tm.getNodeIdFromPath("uio.matnat.ifi"));
	}

	@After
	public void tearDown() {
		tm.delNode(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000"));
		tm.delNode(tm.getNodeIdFromPath("uio.matnat.ifi"));
		tm.delNode(tm.getNodeIdFromPath("uio.matnat"));
		tm.delNode(tm.getNodeIdFromPath("uio"));
	}

	@Test
	public void setCourseCode() {
//		node.init(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000"));
//		node.setCourseCode("INF1010");
//		assertEquals("INF1010", node.getCourseCode());
//		node.setCourseCode("INF1000");
	}

	@Test
	public void getCourseCode() {
/*		node.init(tm.getNodeIdFromPath("uio.matnat.ifi.inf1000"));
		assertEquals("INF1000", node.getCourseCode()); */
	}
}

