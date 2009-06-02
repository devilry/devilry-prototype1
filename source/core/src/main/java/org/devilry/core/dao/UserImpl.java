package org.devilry.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.devilry.core.daointerfaces.UserLocal;
import org.devilry.core.daointerfaces.UserRemote;
import org.devilry.core.entity.Identity;
import org.devilry.core.entity.User;

@Stateless
public class UserImpl implements UserRemote, UserLocal {
	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	protected User getUser(long userId) {
		return em.find(User.class, userId);
	}

	protected Identity getIdentity(String identity) {
		return em.find(Identity.class, identity);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void addIdentity(long userId, String identity) {
		
		// Do not add if identity exists
		if (identityExists(identity))
			return;
			
		Identity i = new Identity();
		i.setIdentity(identity);
		User u = getUser(userId);
		i.setUser(u);
		em.persist(i);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String email, String phoneNumber) {
		User u = new User();
		u.setName(name);
		u.setEmail(email);
		u.setPhoneNumber(phoneNumber);
		em.persist(u);
		em.flush();
		return u.getId();
	}

	public long findUser(String identity) {
		Identity i = getIdentity(identity);
		User u = i.getUser(); 
		return u.getId();
	}

	public String getEmail(long userId) {
		return getUser(userId).getEmail();
	}

	@SuppressWarnings("unchecked")
	public List<String> getIdentities(long userId) {
		Query q = em.createQuery("SELECT i.identity FROM Identity i WHERE i.user.id = :userId");
		q.setParameter("userId", userId);
		return q.getResultList();
	}

	public String getName(long userId) {
		return getUser(userId).getName();
	}

	public String getPhoneNumber(long userId) {
		return getUser(userId).getPhoneNumber();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getUsers() {
		Query q = em.createQuery("SELECT u.id FROM User u");
		List<Long> r = q.getResultList();
		return r;
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remove(long userId) {
		Query q = em.createQuery("DELETE FROM Identity i WHERE i.user.id = :userId");
		q.setParameter("userId", userId);
		q.executeUpdate();		
		em.remove(getUser(userId));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void removeIdentity(String identity) {
		Identity i = getIdentity(identity);
		em.remove(i);
	}

	public void setEmail(long userId, String email) {
		getUser(userId).setEmail(email);
	}

	public void setName(long userId, String name) {
		getUser(userId).setName(name);
	}

	public void setPhoneNumber(long userId, String phoneNumber) {
		getUser(userId).setPhoneNumber(phoneNumber);
	}

	public boolean emailExists(String email) {
		Query q = em.createQuery("SELECT u.email FROM User u WHERE u.email = :email");
		q.setParameter("email", email);
		List<String> l = q.getResultList();
		return l.size() == 1;
	}

	public boolean identityExists(String identity) {
		Query q = em.createQuery("SELECT i.identity FROM Identity i WHERE i.identity = :identity");
		q.setParameter("identity", identity);
		List<String> l = q.getResultList();
		return l.size() == 1;
	}

	public boolean userExists(long userId) {
		return getUser(userId) != null;
	}
}
