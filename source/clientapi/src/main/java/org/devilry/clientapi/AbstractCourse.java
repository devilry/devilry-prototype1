package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public abstract class AbstractCourse<E extends AbstractPeriod> {

	DevilryConnection connection;
	
	UserCommon user;
	//long courseId;
	
	CourseNodeCommon courseNode;
	PeriodNodeCommon periodNode;
	
	AbstractCourse(DevilryConnection connection) {
		this.connection = connection;
	}
	
	/*
	AbstractCourse(long courseId, DevilryConnection connection) {
		this.courseId = courseId;
		this.connection = connection;
	}
	*/
	
	protected PeriodNodeCommon getPeriodNodeBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
	
	protected CourseNodeCommon getCourseBean() throws NamingException {
		return courseNode == null ? courseNode = connection.getCourseNode() : courseNode;
	}
	
	public List<E> getActivePeriods() throws NamingException, NoSuchObjectException, UnauthorizedException {
		List<E> periods = getPeriods();
		return periods;
	}
	
	
	protected abstract class PeriodIterator implements Iterable<E>, Iterator<E> {

		Iterator<Long> deliveryCandidateIterator;
		
		public PeriodIterator(List<Long> ids) {
			deliveryCandidateIterator = ids.iterator();
		}
		
		public Iterator<E> iterator() {
			return this;
		}
				
		public boolean hasNext() {
			return deliveryCandidateIterator.hasNext();
		}

		public abstract E next();
				
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public abstract Iterator<E> periods() throws NoSuchObjectException, UnauthorizedException, NamingException;
	
	public List<E> getPeriods() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<E> periodList = new LinkedList<E>();
		Iterator<E> iter = periods();
		
		while (iter.hasNext()) {
			periodList.add(iter.next());
		}
		return periodList;
	}


}
