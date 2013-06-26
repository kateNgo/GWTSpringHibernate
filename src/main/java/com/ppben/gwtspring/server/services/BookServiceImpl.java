package com.ppben.gwtspring.server.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ppben.gwtspring.server.dao.BookDAO;
import com.ppben.gwtspring.shared.dto.BookDTO;
import com.ppben.gwtspring.shared.services.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDAO bookDAO;

	@PostConstruct
	public void init() throws Exception {
	}
	
	@PreDestroy
	public void destroy() {
	}

	public BookDTO findBook(long bookId) {
		
		return bookDAO.findById(bookId);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveBook(String title, String description, int publishYear, String pulisher) throws Exception {
			
		BookDTO bookDTO = new BookDTO(title, description, publishYear, pulisher) ;//bookDAO.findById(bookId);
		bookDAO.persist(bookDTO);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateBook(long bookId, String title, String description, int publishYear, String pulisher) throws Exception {
		
		BookDTO bookDTO = bookDAO.findById(bookId);
		
		if(bookDTO != null) {
			bookDTO.setTitle(title);
			bookDTO.setDescription(description);
			bookDTO.setPublishYear(publishYear);
			bookDTO.setPulisher(pulisher);
		}

	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteBook(long bookId) throws Exception {
		
		BookDTO bookDTO = bookDAO.findById(bookId);
		
		if(bookDTO != null)
			bookDAO.remove(bookDTO);

	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveOrUpdateBook(long bookId, String title, String description, int publishYear, String pulisher) throws Exception {

		BookDTO bookDTO = new BookDTO(bookId, title, description);
		bookDTO.setPublishYear(publishYear);
		bookDTO.setPulisher(pulisher);
		bookDAO.merge(bookDTO);
		
	}

	
	@Override
	public List<BookDTO> searchBook(BookDTO bookDTO) throws Exception {
		return bookDAO.find(bookDTO);
		
	}

	public List<BookDTO> searchTerm(String term) throws Exception{
		return bookDAO.searchTerm(term);
	}
	public List<BookDTO> findAll() throws Exception{
		return bookDAO.findAll();
	}
	

}
