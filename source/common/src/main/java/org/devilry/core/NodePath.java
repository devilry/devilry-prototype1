package org.devilry.core;

import java.io.Serializable;
import java.util.ArrayList;

public class NodePath implements Serializable {

	ArrayList<String> nodePath = new ArrayList<String>();
	
	public NodePath() {
		
	}
	
	public NodePath(String path, String splitter) {
		this(path.split(splitter));
	}
	
	public NodePath(String [] nodes) {
		for (int i = 0; i < nodes.length; i++) {
			nodePath.add(nodes[i]);
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
	
	public String [] toArray() {
		return nodePath.toArray(new String[nodePath.size()]);
	}
	
	public int size() {
		return nodePath.size();
	}
	
	public void removeFirst() {
		nodePath.remove(0);
	}
	
}
