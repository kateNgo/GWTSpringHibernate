package com.ppben.gwtspring.shared.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name="CUSTOMER")
public class CustomerDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9177543069589488460L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id", nullable = false)
	public long id;
	
	@Column(name="firstName", nullable=false , length=50)
	public String firstName;
	@Column(name="lastName", nullable=false , length=50)
	public String lastName;
	@Column(name="address", nullable=true , length=100)
	public String address;
	@Column(name="emailAddress", nullable=true, length=30)
	public String emailAddress;
	@Column(name="phone", nullable=true, length=12)
	public String phone;
	
	public CustomerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerDTO(String firstName, String lastName,
			String address, String emailAddress, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emailAddress = emailAddress;
		this.phone = phone;
	}
	public CustomerDTO(long id, String firstName, String lastName,
			String address, String emailAddress, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emailAddress = emailAddress;
		this.phone = phone;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
