package org.devilry.core;

import javax.naming.*;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public abstract class NodePathTest {
	
	
	@Before
	public void setUp() throws NamingException, PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException {
		
		
	}
	
	
	@Test
	public void splitConstructorTest() throws InvalidNameException {
		
		NodePath nPath;
		
		nPath = new NodePath("uio", ".");
		assertEquals(nPath.size(), 1);
		assertEquals(nPath.toString(), "uio");
				
		nPath = new NodePath("uio.matnat.ifi.inf1000", ".");
		assertEquals(nPath.size(), 4);
		assertEquals(nPath.toString(), "uio.matnat.ifi.inf1000");
	}
	
	@Test
	public void arrayConstructorTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		
		nPath = new NodePath("uio", ".");
		assertEquals(nPath.size(), 1);
		assertEquals(nPath.toString(), "uio");
		
		nPath = new NodePath(path);
		assertEquals(nPath.size(), 4);
		assertEquals(nPath.toString(), "uio.matnat.ifi.inf1000");
	}
	
	@Test
	public void getTest() throws InvalidNameException {
		
		NodePath nPath;
		nPath = new NodePath("uio.matnat.ifi.inf1000", ".");
		
		assertEquals(nPath.get(0), "uio");
		assertEquals(nPath.get(1), "matnat");
		assertEquals(nPath.get(2), "ifi");
		assertEquals(nPath.get(3), "inf1000");
		
	}
	
	@Test
	public void addToStartTest() throws InvalidNameException {
		
		NodePath nPath;
		nPath = new NodePath();
		assertEquals(nPath.size(), 0);
		
		nPath.addToStart("uio");
		assertEquals(nPath.toString(), "uio");
		
		nPath.addToStart("matnat");
		assertEquals(nPath.toString(), "uio.matnat");
	}
	
	@Test
	public void addToEndTest() throws InvalidNameException {
		
		NodePath nPath;
		nPath = new NodePath();
		assertEquals(nPath.size(), 0);
		
		nPath.addToEnd("uio");
		assertEquals(nPath.toString(), "uio");
		
		nPath.addToEnd("matnat");
		assertEquals(nPath.toString(), "uio.matnat");
	}
	
	@Test
	public void removeFirstPathComponentTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		nPath.removeFirstPathComponent();
		assertEquals(nPath.size(), 3);
		assertEquals(nPath.get(0), "matnat");
	}
	
	@Test
	public void removeLastPathComponentTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		nPath.removeFirstPathComponent();
		assertEquals(nPath.size(), 3);
		assertEquals(nPath.get(0), "uio");
		assertEquals(nPath.get(2), "ifi");
	}
	
	
	@Test
	public void toArrayTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		String [] toArray = nPath.toArray();
		assertEquals(toArray.length, path.length);
		assertEquals(toArray[0], path[0]);
		assertEquals(toArray[1], path[1]);
		assertEquals(toArray[2], path[2]);
		assertEquals(toArray[3], path[3]);
	}
	
	@Test
	public void toStringTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		assertEquals(nPath.toString(), "uio.matnat.ifi.inf1000");
	}
	
	@Test
	public void compareToTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		NodePath nPath2 = new NodePath(path);
		
		assertEquals(nPath.compareTo(nPath2), 0);
		
		nPath2 = new NodePath(new String[] {"uio"});
		assertEquals(nPath.compareTo(nPath2), -1);
		
		nPath2 = new NodePath(new String[] {"uio", "matnat", "ifi", "inf1000", "Assignemnt1"});
		assertEquals(nPath.compareTo(nPath2), 1);
	}
	
	@Test
	public void equalsTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		NodePath nPath2 = new NodePath(path);
		
		assertTrue(nPath.equals(nPath2));
		
	}
	
	@Test
	public void sizeTest() throws InvalidNameException {
		
		NodePath nPath = new NodePath();
		assertEquals(nPath.size(), 0);
		
		nPath.addToEnd("matnat");
		assertEquals(nPath.size(), 1);
		
		nPath.addToStart("uio");
		assertEquals(nPath.size(), 2);
	
		nPath = new NodePath("uio.matnat.ifi.inf1010", ".");
		assertEquals(nPath.size(), 4);
	}
	
}
