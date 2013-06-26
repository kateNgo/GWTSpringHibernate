package com.ppben.gwtspring.shared.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import com.ppben.gwtspring.shared.dto.CustomerDTO;

@RemoteServiceRelativePath("springGwtServices/customerService")
public interface CustomerService extends RemoteService {
	
	public List<CustomerDTO> findAll();
	public CustomerDTO findCustomer(long customerId);
	public CustomerDTO saveCustomer(String firstName, String lastName,
			String address, String emailAddress, String phone) throws Exception;
	public CustomerDTO updateCustomer(long customerId, String firstName, String lastName,
			String address, String emailAddress, String phone) throws Exception;
	public CustomerDTO saveOrUpdateCustomer(long customerId, String firstName, String lastName,
			String address, String emailAddress, String phone) throws Exception;
	public void deleteCustomer(long customerId) throws Exception;
	public void deleteCustomers(List<Long> ids) throws Exception;
	
	
}
