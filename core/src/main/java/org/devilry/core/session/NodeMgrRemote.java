package org.devilry.core.session;

import org.devilry.core.entity.Node;
import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface NodeMgrRemote {
    public Node addNode(String name);
    public Node addNode(String name, Node parent);
	public Node addCourseNode(String code, String name);
	public Node addCourseNode(String code, String name, Node parent);
	public Node update(Node node);
	public Node findNode(String name);
	public Node findNode(String name, String parent);
	public Node findByPath(String path);
	public void setRoot(String path);
	public Node getRoot();
}