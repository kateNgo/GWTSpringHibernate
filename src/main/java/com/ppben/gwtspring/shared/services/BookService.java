package com.ppben.gwtspring.shared.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ppben.gwtspring.shared.dto.BookDTO;

@RemoteServiceRelativePath("springGwtServices/bookService")
public interface BookService extends RemoteService{
	
	public BookDTO findBook(long bookId);
	public void saveBook(String title, String description, int publishYear, String pulisher) throws Exception;
	public void updateBook(long bookId, String title,  String description, int publishYear, String pulisher) throws Exception;
	public void saveOrUpdateBook(long bookId, String title,  String description, int publishYear, String pulisher) throws Exception;
	public void deleteBook(long bookId) throws Exception;
	public List<BookDTO> searchBook(BookDTO bookDTO) throws Exception;
	public List<BookDTO> searchTerm(String term) throws Exception;
	public List<BookDTO> findAll() throws Exception;
	
	
}
