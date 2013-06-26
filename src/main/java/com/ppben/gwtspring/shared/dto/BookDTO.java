package com.ppben.gwtspring.shared.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name="BOOK")
public class BookDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6216840590795558438L;
	
	@Id
	@Column(name="book_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bookId;
	
	@Column(name="title", nullable=false, length=50)
	private String title;
	
	@Column (name="publishYear")
	private int publishYear;
	
	@Column(name="pulisher" , length=50)
	private String pulisher;
	
	@Column(name="description", nullable=true, length=200)
	private String description;

	public BookDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookDTO(long bookId, String title, String description) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.description = description;
	}
	public BookDTO(String title, String description, int publishYear, String pulisher) {
		super();
		this.title = title;
		this.description = description;
		this.publishYear = publishYear;
		this.pulisher = pulisher;
	}
	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public String getPulisher() {
		return pulisher;
	}

	public void setPulisher(String pulisher) {
		this.pulisher = pulisher;
	}
	
	
	
}
