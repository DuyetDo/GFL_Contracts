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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.duyetdo.springmvc.model.Fee;
import com.duyetdo.springmvc.model.Project;
import com.duyetdo.springmvc.model.Result;
import com.duyetdo.springmvc.service.FeeService;
import com.duyetdo.springmvc.service.ProjectService;
import com.duyetdo.springmvc.service.ResultService;

@Controller
@RequestMapping("/")
public class FeeController {
	
	@Autowired
	FeeService feeService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	ResultService resultService;
	
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
	 * This method will list all existing project fees.
	 */
	@RequestMapping(value = { "/feesList" }, method = RequestMethod.GET)
	public String listFees(ModelMap model) {
		List<Fee> fees = feeService.findAllFees();
		model.addAttribute("fees", fees);
		model.addAttribute("loggedinuser", getPrincipal());
		return "feesList";
	}
	
	/**
	 * This method will provide the medium to add a new project.
	 */
	@RequestMapping(value = { "/newfee" }, method = RequestMethod.GET)
	public String newFee(ModelMap model) {
		Fee fee = new Fee();
		model.addAttribute("fee", fee);
		model.addAttribute("editFee", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addFee";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * saving fee in database. It also validates the fee input
	 */
	@RequestMapping(value = { "/newfee" }, method = RequestMethod.POST)
	public String saveFee(@Valid Fee fee, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "addFee";
		}

	    if (!feeService.isFeeUnique(fee.getName(), fee.getProject().getProjectId())) {
			FieldError feeNameError = new FieldError("fee", "name", messageSource.getMessage("non.unique.feeName",
					new String[] { fee.getName() }, Locale.getDefault()));
			result.addError(feeNameError);
			return "addFee";

		}

		feeService.saveFees(fee);
		
		Result re = resultService.findResultByProjectId(fee.getProject().getProjectId());
		Project project = projectService.findByProjectID(fee.getProject().getProjectId());
		List<Fee> fees = feeService.findFeeByProjectId(fee.getProject().getProjectId());

		model.addAttribute("fees", fees);
		model.addAttribute("re", re);
		model.addAttribute("pj", project);
		model.addAttribute("feesListLink", "/feesList");
		model.addAttribute("feeSuccess", fee.getName() + " " + " được thêm thành công!");
		model.addAttribute("loggedinuser", getPrincipal());
		return "success";
	}
	
	/**
	 * This method will provide the medium to update an existing project.
	 */
	@RequestMapping(value = { "/edit-fee-{feeId}" }, method = RequestMethod.GET)
	public String editProject(@PathVariable int feeId, ModelMap model) {
		Fee fee = feeService.findByFeeID(feeId);
		model.addAttribute("fee", fee);
		model.addAttribute("editFee", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addFee";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating fee in database. It also validates the fee input
	 */
	@RequestMapping(value = { "/edit-fee-{feeId}" }, method = RequestMethod.POST)
	public String updateProject(@Valid Fee fee, BindingResult result, ModelMap model, @PathVariable int feeId) {

		if (result.hasErrors()) {
			return "addFee";
		}

		feeService.updateFees(fee);
		
		Result re = resultService.findResultByProjectId(fee.getProject().getProjectId());
		Project project = projectService.findByProjectID(fee.getProject().getProjectId());
		List<Fee> fees = feeService.findFeeByProjectId(fee.getProject().getProjectId());

		model.addAttribute("fees", fees);
		model.addAttribute("re", re);
		model.addAttribute("pj ", project);
		model.addAttribute("feesListLink", "/feesList");
		model.addAttribute("feeSuccess",
				fee.getName() + " " + " đã được thay đổi.");
		model.addAttribute("loggedinuser", getPrincipal());
		return "success";
	}

	/**
	 * This method will delete an fee by it's feeID value.
	 */
	@RequestMapping(value = { "/delete-fee-{feeId}" }, method = RequestMethod.GET)
	public String deleteProject(@PathVariable int feeId) {
		feeService.deleteByFeeID(feeId);
		return "redirect:/feesList";
	}
	
	/**
	 * This method will provide Projects list to select
	 */
	@ModelAttribute("select_projects")
	public List<Project> initializeProjects() {
		return projectService.findAllProjects();
	}

}
