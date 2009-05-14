package org.devilry.core.session.dao;

import javax.ejb.Remote;

@Remote
public interface CourseNodeRemote extends NodeRemote {
	public void setCourseCode(String courseCode);
	public String getCourseCode();
}

