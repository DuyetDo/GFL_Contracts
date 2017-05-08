package com.duyetdo.springmvc.dao;

import java.util.List;

import com.duyetdo.springmvc.model.Customer;

public interface CustomerDao {
	
	Customer findByPK(String pk);
	
	Customer findByCustomerID(String customerId);
	
	Customer findByCustomerName(String customerName);
	
	void save(Customer customer);
	
	void deleteByCustomerID(String customerId);
	
	List<Customer> findAllCustomers();
}
