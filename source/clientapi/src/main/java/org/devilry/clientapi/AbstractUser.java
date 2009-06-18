package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.UserCommon;


public abstract class AbstractUser<E extends AbstractPeriod> {

	DevilryConnection connection;
	
	UserCommon user;
	long userId;
	
	PeriodNodeCommon periodNode;
	
	AbstractUser(long userId, DevilryConnection connection) {
		this.userId = userId;
		this.connection = connection;
	}
	
	private UserCommon getUserBean() throws NamingException {
		return user == null ? user = connection.getUser() : user;
	}
	
	protected PeriodNodeCommon getPeriodNodeBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
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
