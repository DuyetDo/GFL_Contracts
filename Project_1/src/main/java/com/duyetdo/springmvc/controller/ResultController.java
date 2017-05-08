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
import com.duyetdo.springmvc.model.Project;
import com.duyetdo.springmvc.model.Result;
import com.duyetdo.springmvc.service.ProjectService;
import com.duyetdo.springmvc.service.ResultService;

@Controller
@RequestMapping("/")
public class ResultController {
	
	@Autowired
	ResultService resultService;
	
	@Autowired
	ProjectService projectService;
	
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
	 * This method will list all existing project results.
	 */
	@RequestMapping(value = { "/resultsList" }, method = RequestMethod.GET)
	public String listFees(ModelMap model) {
		List<Result> results = resultService.findAllResults();
		model.addAttribute("results", results);
		model.addAttribute("loggedinuser", getPrincipal());
		return "resultsList";
	}
	
	/**
	 * This method will provide the medium to calculate project result.
	 */
	@RequestMapping(value = { "/calculate" }, method = RequestMethod.GET)
	public String Result(ModelMap model) {
		Result result = new Result();
		model.addAttribute("result", result);
		model.addAttribute("loggedinuser", getPrincipal());
		return "result";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * saving result in database. It also validates the result input
	 */
	@RequestMapping(value = { "/calculate" }, method = RequestMethod.POST)
	public String saveResult(@Valid Result re, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "result";
		}

	    if (!resultService.isResultUnique(re.getProject().getProjectId())) {
			FieldError resultError = new FieldError("result", "project.projectId", messageSource.getMessage("non.unique.result.project.projectId",
					new String[] { re.getProject().getProjectId() }, Locale.getDefault()));
			result.addError(resultError);
			model.addAttribute("calfail", "Danh sách kết quả.");
			return "result";

		}

	    resultService.saveResult(re);

	    model.addAttribute("resultsListLink", "/resultsList");
		model.addAttribute("resultSuccess", "Dự án " + re.getProject().getProjectId() + " " + " được đánh giá thành công !");
		model.addAttribute("loggedinuser", getPrincipal());
		return "success";
	}
	
	/**
	 * This method will delete an fee by it's resultId value.
	 */
	@RequestMapping(value = { "/delete-result-{resultId}" }, method = RequestMethod.GET)
	public String deleteResult(@PathVariable int resultId) {
		resultService.deleteByResultID(resultId);
		return "redirect:/resultsList";
	}
	
	/**
	 * This method will provide Projects list to select
	 */
	@ModelAttribute("project_select")
	public List<Project> initializeProjects() {
		return projectService.findAllProjects();
	}

}
