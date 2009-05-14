package org.devilry.core.entity;

import javax.persistence.*;
import org.devilry.core.entity.*;

@Entity
@DiscriminatorValue("CN")
public class CourseNode extends Node {
	private String courseCode;

	public CourseNode() {

	}

	public String getCourseCode() {
		return this.courseCode;
	}

	public void setCourseCode(String code) {
		this.courseCode = code;
	}
}

