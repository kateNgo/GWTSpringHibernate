package com.ppben.gwtspring.server.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ppben.gwtspring.server.dao.CustomerDAO;
import com.ppben.gwtspring.shared.dto.CustomerDTO;
import com.ppben.gwtspring.shared.services.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDAO customerDAO;

	@PostConstruct
	public void init() throws Exception {
	}
	
	@PreDestroy
	public void destroy() {
	}

	public CustomerDTO findCustomer(long customerId) {
		
		return customerDAO.findById(customerId);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public CustomerDTO saveCustomer(String firstName, String lastName,
			String address, String emailAddress, String phone) throws Exception {
			
		CustomerDTO customerDTO = new CustomerDTO(firstName, lastName, address, emailAddress, phone);
		return customerDAO.persist(customerDTO);
		//return customerDTO;
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public CustomerDTO updateCustomer(long customerId, String firstName, String lastName,
			String address, String emailAddress, String phone) throws Exception {
		
		CustomerDTO customerDTO = customerDAO.findById(customerId);
		
		if(customerDTO != null) {
			customerDTO.setFirstName(firstName);
			customerDTO.setLastName(lastName);
			customerDTO.setAddress(address);
			customerDTO.setEmailAddress(emailAddress);
			customerDTO.setPhone(phone);
		}
		return customerDTO;

	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteCustomer(long customerId) throws Exception {
		
		CustomerDTO customerDTO = customerDAO.findById(customerId);
		
		if(customerDTO != null)
			customerDAO.remove(customerDTO);

	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public CustomerDTO saveOrUpdateCustomer(long customerId, String firstName, String lastName,
			String address, String emailAddress, String phone) throws Exception {

		CustomerDTO customerDTO = new CustomerDTO(customerId,firstName, lastName, address, emailAddress, phone) ;
		
		return customerDAO.merge(customerDTO);
		
	}

	@Override
	public List<CustomerDTO> findAll() {
		
		return customerDAO.findAll();
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteCustomers(List<Long> ids) throws Exception {
		if (ids != null){
			for(int i=0;i<ids.size();i++){
				deleteCustomer(ids.get(i));
				
			}
		}
		
	}

}
