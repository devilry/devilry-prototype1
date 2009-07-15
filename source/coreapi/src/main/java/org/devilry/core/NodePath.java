package org.devilry.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class NodePath implements Serializable, Comparable<NodePath>,
		Iterable<String> {

	private ArrayList<String> nodePath = new ArrayList<String>();

	public static final String defaultSeparator = ".";

	public NodePath() {

	}

	public NodePath(NodePath nodePathToCopy) {

		for (int i = 0; i < nodePathToCopy.size(); i++)
			nodePath.add(nodePathToCopy.get(i));
	}

	public NodePath(String path, String splitter) throws InvalidNameException {
		this(path.split(splitter));
	}

	public NodePath(String[] nodes) throws InvalidNameException {
		for (int i = 0; i < nodes.length; i++) {
			
			if (nodes[i].trim().equals(""))
				throw new InvalidNameException("Invalid nodepath contains empty node!");
			
			nodePath.add(nodes[i].toLowerCase());
		}
	}

	public String get(int index) {
		return nodePath.get(index);
	}

	public void addToEnd(String node) {
		nodePath.add(node);
	}

	public void addToStart(String node) {
		nodePath.add(0, node);
	}

	public String[] toArray() {
		return nodePath.toArray(new String[nodePath.size()]);
	}

	public int size() {
		return nodePath.size();
	}

	public String removeFirstPathComponent() {
		return nodePath.remove(0);
	}

	public String removeLastPathComponent() {
		return nodePath.remove(nodePath.size() - 1);
	}

	public String toString() {

		StringBuffer buf = new StringBuffer();

		for (String n : nodePath)
			buf.append(buf.length() == 0 ? n : defaultSeparator + n);

		return buf.toString();
	}

	public int compareTo(NodePath anotherNodePath) {
		String anotherPathString = anotherNodePath.toString();
		String nodePathString = toString();
		return nodePathString.compareTo(anotherPathString);
	}

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
