package com.duyetdo.springmvc.service;

import java.util.List;

import com.duyetdo.springmvc.model.Customer;

public interface CustomerService {

	Customer findByCustomerPK(String pk);

	Customer findByCustomerID(String customerId);

	Customer findByCustomerName(String customerName);

	void saveCustomers(Customer customer);

	void updateCustomers(Customer customer);

	void deleteByCustomerID(String customerId);

	List<Customer> findAllCustomers();

	boolean isCustomerIDUnique(String customerId);

	boolean isCustomerNameUnique(String customerName);
}
