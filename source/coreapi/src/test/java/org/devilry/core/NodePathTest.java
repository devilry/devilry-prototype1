package org.devilry.core;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NodePath;
import org.junit.Test;
import static org.junit.Assert.*;


public class NodePathTest {
	
	
	@Test
	public void splitConstructorTest() throws InvalidNameException {
		
		NodePath nPath;
		
		System.err.println("path:" + "uio".split("\\."));
		
		nPath = new NodePath("uio", "\\.");
		assertEquals(1, nPath.size());
		assertEquals("uio", nPath.toString());
				
		nPath = new NodePath("uio.matnat.ifi.inf1000", "\\.");
		assertEquals(4, nPath.size());
		assertEquals("uio.matnat.ifi.inf1000", nPath.toString());
	}
	
	@Test
	public void arrayConstructorTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		
		nPath = new NodePath("uio", "\\.");
		assertEquals(1, nPath.size());
		assertEquals("uio", nPath.toString());
		
		nPath = new NodePath(path);
		assertEquals(4, nPath.size());
		assertEquals("uio.matnat.ifi.inf1000", nPath.toString());
	}
	
	@Test
	public void getTest() throws InvalidNameException {
		
		NodePath nPath;
		nPath = new NodePath("uio.matnat.ifi.inf1000", "\\.");
		
		assertEquals("uio", nPath.get(0));
		assertEquals("matnat", nPath.get(1));
		assertEquals("ifi", nPath.get(2));
		assertEquals("inf1000", nPath.get(3));
		
	}
	
	@Test
	public void addToStartTest() throws InvalidNameException {
		
		NodePath nPath;
		nPath = new NodePath();
		assertEquals(nPath.size(), 0);
		
		nPath.addToStart("matnat");
		assertEquals("matnat", nPath.toString());
		
		nPath.addToStart("uio");
		assertEquals("uio.matnat", nPath.toString());
	}
	
	@Test
	public void addToEndTest() throws InvalidNameException {
		
		NodePath nPath;
		nPath = new NodePath();
		assertEquals(nPath.size(), 0);
		
		nPath.addToEnd("uio");
		assertEquals("uio", nPath.toString());
		
		nPath.addToEnd("matnat");
		assertEquals("uio.matnat", nPath.toString());
	}
	
	@Test
	public void removeFirstPathComponentTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		nPath.removeFirstPathElement();
		assertEquals(3, nPath.size());
		assertEquals("matnat", nPath.get(0));
	}
	
	@Test
	public void removeLastPathComponentTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		nPath.removeFirstPathElement();
		assertEquals(3, nPath.size());
		assertEquals("matnat", nPath.get(0));
		assertEquals("inf1000", nPath.get(2));
	}
	
	
	@Test
	public void toArrayTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		String [] toArray = nPath.toArray();
		assertEquals(toArray.length, path.length);
		assertEquals(path[0], toArray[0]);
		assertEquals(path[1], toArray[1]);
		assertEquals(path[2], toArray[2]);
		assertEquals(path[3], toArray[3]);
	}
	
	@Test
	public void toStringTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		assertEquals("uio.matnat.ifi.inf1000", nPath.toString());
	}
	
	@Test
	public void compareToTest() throws InvalidNameException {
		
		NodePath nPath;
		String [] path = new String[] {"uio", "matnat", "ifi", "inf1000"};
		nPath = new NodePath(path);
		
		NodePath nPath2 = new NodePath(path);
		
		assertEquals(0, nPath.compareTo(nPath2));
		
		nPath2 = new NodePath(new String[] {"uio"});
		
		assertTrue(nPath.compareTo(nPath2) > 0);
		
		nPath2 = new NodePath(new String[] {"uio", "matnat", "ifi", "inf1000", "Assignemnt1"});
		assertTrue(nPath.compareTo(nPath2) < 0);
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
		assertEquals(1, nPath.size());
		
		nPath.addToStart("uio");
		assertEquals(2, nPath.size());
	
		nPath = new NodePath("uio.matnat.ifi.inf1010", "\\.");
		assertEquals(4, nPath.size());
	}
	
}
