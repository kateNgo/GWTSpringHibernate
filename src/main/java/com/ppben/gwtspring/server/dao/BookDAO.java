package com.ppben.gwtspring.server.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;
import com.ppben.gwtspring.shared.dto.BookDTO;


@Repository("bookDAO")
public class BookDAO extends JpaDAO<Long, BookDTO>{
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
	@SuppressWarnings("unchecked")
	public List<BookDTO> find(BookDTO entity) {
		Object res = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query q = em.createQuery("SELECT h FROM " +
						entityClass.getName() + " h where h.bookId=1");
				
				return q.getResultList();
			}
			
		});
		
		return (List<BookDTO>) res;
	}
	public List<BookDTO> findAll() {
		Object res = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				
				Query q = em.createQuery("SELECT h FROM " +
						entityClass.getName() + " h");
				
				return q.getResultList();
			}
			
		});
		
		return (List<BookDTO>) res;
	}
	@SuppressWarnings("unchecked")
	public List<BookDTO> searchTerm(final String term){
		Object res = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				
				
				Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h where h.title like '%" + term + "%'");
				//q.setParameter("term", term);
			   return q.getResultList();
			   
			}
			
		});
		
		return (List<BookDTO>) res;
	}

}
