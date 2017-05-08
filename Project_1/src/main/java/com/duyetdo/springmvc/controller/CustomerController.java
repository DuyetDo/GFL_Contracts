package com.duyetdo.springmvc.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.duyetdo.springmvc.model.Customer;
import com.duyetdo.springmvc.service.CustomerService;

@Controller
@RequestMapping("/")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	MessageSource messageSource;
	
	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * This method will list all existing projects.
	 */
	@RequestMapping(value = { "/customersList" }, method = RequestMethod.GET)
	public String listCustomers(ModelMap model) {
		List<Customer> customers = customerService.findAllCustomers();
		model.addAttribute("customers", customers);
		model.addAttribute("loggedinuser", getPrincipal());
		return "customersList";
	}

	/**
	 * This method will provide the medium to add a new customer.
	 */
	@RequestMapping(value = { "/newcustomer" }, method = RequestMethod.GET)
	public String newCustomer(ModelMap model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		model.addAttribute("editCustomer", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addCustomer";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the customer input
	 */
	@RequestMapping(value = { "/newcustomer" }, method = RequestMethod.POST)
	public String saveCustomer(@Valid Customer customer, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "addCustomer";
		}

		if (!customerService.isCustomerIDUnique(customer.getCustomerId())) {
			FieldError customerIdError = new FieldError("customer", "customerId", messageSource
					.getMessage("non.unique.customerId", new String[] { customer.getCustomerId() }, Locale.getDefault()));
			result.addError(customerIdError);
			return "addCustomer";
		} else if (!customerService.isCustomerNameUnique(customer.getName())) {
			FieldError customerNameError = new FieldError("customer", "name", messageSource.getMessage("non.unique.customerName",
					new String[] { customer.getName() }, Locale.getDefault()));
			result.addError(customerNameError);
			return "addCustomer";

		}

		customerService.saveCustomers(customer);
		
		model.addAttribute("cus", customer);
		model.addAttribute("customerSuccess", "Khách hàng " + customer.getName() + " " + " đã được thêm!");
		model.addAttribute("loggedinuser", getPrincipal());
		return "customerSuccess";
	}
	
	/**
	 * This method will provide the medium to update an existing customer.
	 */
	@RequestMapping(value = { "/edit-customer-{customerId}" }, method = RequestMethod.GET)
	public String editCustomer(@PathVariable String customerId, ModelMap model) {
		Customer customer = customerService.findByCustomerID(customerId);
		model.addAttribute("customer", customer);
		model.addAttribute("editCustomer", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addCustomer";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the customer input
	 */
	@RequestMapping(value = { "/edit-customer-{customerId}" }, method = RequestMethod.POST)
	public String updateCustomer(@Valid Customer customer, BindingResult result, ModelMap model, @PathVariable String customerId) {

		if (result.hasErrors()) {
			return "addCustomer";
		}

		customerService.updateCustomers(customer);

		model.addAttribute("cus", customer);
		model.addAttribute("customerSuccess",
				"Khách hàng " + customer.getName() + " " + " đã được sửa thông tin!");
		model.addAttribute("loggedinuser", getPrincipal());
		return "customerSuccess";
	}

	/**
	 * This method will delete an project by it's customerID value.
	 */
	@RequestMapping(value = { "/delete-customer-{customerId}" }, method = RequestMethod.GET)
	public String deleteCustomer(@PathVariable String customerId) {
		customerService.deleteByCustomerID(customerId);
		return "redirect:/customersList";
	}

}
