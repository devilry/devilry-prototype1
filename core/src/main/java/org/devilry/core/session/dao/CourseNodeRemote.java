package org.devilry.core.session.dao;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CourseNodeRemote extends NodeRemote {
	public List<Long> getAllCourseIds();
}
