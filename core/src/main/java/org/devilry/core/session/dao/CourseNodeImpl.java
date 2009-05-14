package org.devilry.core.session.dao;

import javax.ejb.Stateful;
import javax.persistence.*;

import org.devilry.core.entity.*;

@Stateful
public class CourseNodeImpl extends NodeImpl implements CourseNodeRemote {
	public void setCourseCode(String courseCode) {
		((CourseNode) node).setCourseCode(courseCode);
		em.merge(node);
	}

	public String getCourseCode() {
		return ((CourseNode) node).getCourseCode();
	}
}

