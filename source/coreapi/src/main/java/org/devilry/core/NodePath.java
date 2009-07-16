package org.devilry.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The NodePath class is used to represent a path to a node in the devilry 
 * system. A path consists of one or more path elements which together forms a 
 * full unique path to a node in the system.
 * An example nodepath is "uio.matnat.ifi.inf1000.oblig1"
 * 
 * A nodepath can only consist of characters from a-z and numbers 0-9. 
 * Upper case characters are automatically converted to lowercase.
 */
public class NodePath implements Serializable, Comparable<NodePath>,
		Iterable<String> {

	private ArrayList<String> nodePath = new ArrayList<String>();

	public static final String defaultSeparator = ".";

	public NodePath() {

	}

	/**
	 * Takes an existing NodePath as argument, and copies the node path 
	 * elements into the new NodePath.
	 * @param nodePathToCopy  the NodePath to copy.
	 */
	public NodePath(NodePath nodePathToCopy) {

		for (int i = 0; i < nodePathToCopy.size(); i++)
			nodePath.add(nodePathToCopy.get(i));
	}

	/**
	 * Takes in a path as a string and a splitter (separator) to used to split
	 * the path into path components. The default separator is a dot (.).
	 * @param path      - the path consisting of path elements separated by dot.
	 * @param splitter  - the splitter as a regular expression used to separate 
	 * 					  the path elements 
	 * @throws InvalidNameException
	 */
	public NodePath(String path, String splitter) throws InvalidNameException {
		this(path.split(splitter));
	}

	/**
	 * Takes a String array of path elements and forms a NodePath.
	 * @param nodes  - a String array containing the path elements.
	 * @throws InvalidNameException
	 */
	public NodePath(String[] nodes) throws InvalidNameException {
		for (int i = 0; i < nodes.length; i++) {
			
			String node = nodes[i].trim();
			
			if (node.equals(""))
				throw new InvalidNameException("Invalid nodepath contains empty node!");
			
			nodePath.add(node.toLowerCase());
		}
	}

	/**
	 * Returns the path element at index index
	 * @param index - the index of the path element to return.
	 * @return		- the path element
	 */
	public String get(int index) {
		return nodePath.get(index);
	}

	/**
	 * Adds the argument node as element to the end of the current path.
	 * @param node - path element to add
	 * @return	   - the NodePath
	 * @throws InvalidNameException
	 */
	public NodePath addToEnd(String node) throws InvalidNameException {
		
		node = node.trim();
		
		if (node.equals(""))
			throw new InvalidNameException("Invalid nodepath contains empty node!");
		
		nodePath.add(node.toLowerCase());
		return this;
	}

	/**
	 * Adds the argument node as element to the beginning of the current path.
	 * @param node - path element to add
	 * @return	   - the NodePath
	 * @throws InvalidNameException
	 */
	public NodePath addToStart(String node) throws InvalidNameException {
		
		node = node.trim();
		
		if (node.equals(""))
			throw new InvalidNameException("Invalid nodepath contains empty node!");
		
		nodePath.add(0, node.toLowerCase());
		return this;
	}

	/**
	 * Returns a String array containing the nodepath elements. 
	 * @return
	 */
	public String[] toArray() {
		return nodePath.toArray(new String[nodePath.size()]);
	}

	/**
	 * Returns the number of path elements in the nodepath.
	 * @return the size.
	 */
	public int size() {
		return nodePath.size();
	}

	/**
	 * Remove the first element of the nodepath.
	 * @return - the removed element.
	 */
	public String removeFirstPathElement() {
		return nodePath.remove(0);
	}

	/**
	 * Remove the last element of the nodepath.
	 * @return - the removed element.
	 */
	public String removeLastPathElement() {
		return nodePath.remove(nodePath.size() - 1);
	}

	/**
	 * Contstructs a string from the nodepath.
	 * Each element is concatenated separated by the default separator (.).
	 */
	public String toString() {

		StringBuffer buf = new StringBuffer();

		for (String n : nodePath)
			buf.append(buf.length() == 0 ? n : defaultSeparator + n);

		return buf.toString();
	}

	/**
	 * Compares the nodepath with the argument nodepath using string compare.
	 * @return       0 if equal, negative value of input is less, positive if greater.
	 */
	public int compareTo(NodePath anotherNodePath) {
		String anotherPathString = anotherNodePath.toString();
		String nodePathString = toString();
		return nodePathString.compareTo(anotherPathString);
	}

	/**
	 * Compares the nodepath with the argument anotherPath by checking if they
	 * contain the same elements.
	 * @param anotherPath     the NodePath to compare with.
	 * @return                true of the contain the same elements.
	 */
	public boolean equals(NodePath anotherPath) {
		return compareTo(anotherPath) == 0;
	}

	public boolean equals(Object anotherPath) {

		if (anotherPath instanceof NodePath) {
			return equals((NodePath) anotherPath);
		}
		return false;
	}

	public Iterator<String> iterator() {
		return nodePath.iterator();
	}
}
