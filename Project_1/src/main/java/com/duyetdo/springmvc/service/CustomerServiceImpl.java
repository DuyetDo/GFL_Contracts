package com.duyetdo.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duyetdo.springmvc.dao.CustomerDao;
import com.duyetdo.springmvc.model.Customer;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao dao;

	public Customer findByCustomerPK(int pk) {
		return dao.findByPK(pk);
	}

	public Customer findByCustomerID(String customerId) {
		Customer customer = dao.findByCustomerID(customerId);
		return customer;
	}

	public Customer findByCustomerName(String customerName) {
		Customer customer = dao.findByCustomerName(customerName);
		return customer;
	}

	public void saveCustomers(Customer customer) {
		dao.save(customer);
	}

	public void updateCustomers(Customer customer) {
		Customer entity = dao.findByPK(customer.getId());
		if (entity != null) {
			entity.setCustomerId(customer.getCustomerId());
			entity.setName(customer.getName());
			entity.setEmail(customer.getEmail());
			entity.setPhone(customer.getPhone());
			entity.setFax(customer.getFax());
			entity.setAddress(customer.getAddress());
		}

	}

	public void deleteByCustomerID(String customerId) {
		dao.deleteByCustomerID(customerId);
	}

	public List<Customer> findAllCustomers() {
		return dao.findAllCustomers();
	}

	public boolean isCustomerIDUnique(String customerId) {
		Customer customer = dao.findByCustomerID(customerId);
		return (customer == null);
	}

	public boolean isCustomerNameUnique(String customerName) {
		Customer customer = dao.findByCustomerName(customerName);
		return (customer == null);
	}

}
