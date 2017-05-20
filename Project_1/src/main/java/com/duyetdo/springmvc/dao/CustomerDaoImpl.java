package com.duyetdo.springmvc.dao;

import java.util.List;

import com.duyetdo.springmvc.model.Customer;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao<Integer, Customer> implements CustomerDao {

	static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);

	public Customer findByPK(int pk) {
		Customer customer = getByKey(pk);
		if (customer != null) {
			Hibernate.initialize(customer.getProjects());
		}
		return customer;
	}

	public Customer findByCustomerID(String customerId) {
		logger.info("CustomerID : {}", customerId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("customerId", customerId));
		Customer customer = (Customer) crit.uniqueResult();
		if (customer != null) {
			Hibernate.initialize(customer.getProjects());
		}
		return customer;
	}

	public Customer findByCustomerName(String customerName) {
		logger.info("CustomerName : {}", customerName);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("name", customerName));
		Customer customer = (Customer) crit.uniqueResult();
		if (customer != null) {
			Hibernate.initialize(customer.getProjects());
		}
		return customer;
	}

	public void save(Customer customer) {
		persist(customer);
	}

	public void deleteByCustomerID(String customerId) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("customerId", customerId));
		Customer customer = (Customer) crit.uniqueResult();
		if (null != customer) {
			delete(customer);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findAllCustomers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		List<Customer> customers = (List<Customer>) criteria.list();
		return customers;
	}

}
