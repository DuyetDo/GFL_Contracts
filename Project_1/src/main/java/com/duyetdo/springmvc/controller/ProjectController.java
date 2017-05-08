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
import org.springframework.web.servlet.ModelAndView;

import com.duyetdo.springmvc.model.Customer;
import com.duyetdo.springmvc.model.Fee;
import com.duyetdo.springmvc.model.Project;
import com.duyetdo.springmvc.model.Result;
import com.duyetdo.springmvc.service.CustomerService;
import com.duyetdo.springmvc.service.FeeService;
import com.duyetdo.springmvc.service.ProjectService;
import com.duyetdo.springmvc.service.ResultService;

@Controller
@RequestMapping("/")
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ResultService resultService;
	
	@Autowired
	FeeService feeService;

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
	@RequestMapping(value = { "/projectsList" }, method = RequestMethod.GET)
	public String listProjects(ModelMap model) {
		List<Project> projects = projectService.findAllProjects();
		model.addAttribute("projects", projects);
		model.addAttribute("loggedinuser", getPrincipal());
		return "projectsList";
	}
	
	/**
	 * This method will show detail a project.
	 */
	@RequestMapping(value = { "/detail-project-{projectId}" }, method = RequestMethod.GET)
	public String detailProject(@PathVariable String projectId, ModelMap model) {
		Project project = projectService.findByProjectID(projectId);
		Result result = resultService.findResultByProjectId(projectId);
		List<Fee> fees = feeService.findFeeByProjectId(projectId);
		
		model.addAttribute("detail", project.getName());
		model.addAttribute("project", project);
		model.addAttribute("fees", fees);
		model.addAttribute("result", result);
		model.addAttribute("loggedinuser", getPrincipal());
		return "detailProject";
	}

	/**
	 * This method will provide the medium to add a new project.
	 */
	@RequestMapping(value = { "/newproject" }, method = RequestMethod.GET)
	public String newProject(ModelMap model) {
		Project project = new Project();
		model.addAttribute("project", project);
		model.addAttribute("editProject", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addProject";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	
	@RequestMapping(value = { "/newproject" }, method = RequestMethod.POST)
	public ModelAndView saveProject(@Valid Project project, BindingResult result, ModelMap model) {
		
		ModelAndView modelAndView1 = new ModelAndView("addProject");
		ModelAndView modelAndView2 = new ModelAndView("success");
		
		if (result.hasErrors()) {
			return modelAndView1;
		}

		if (!projectService.isProjectIDUnique(project.getProjectId())) {
			FieldError projectIdError = new FieldError("project", "projectId", messageSource
					.getMessage("non.unique.projectId", new String[] { project.getProjectId() }, Locale.getDefault()));
			result.addError(projectIdError);
			return modelAndView1;
		} else if (!projectService.isProjectNameUnique(project.getName())) {
			FieldError projectNameError = new FieldError("project", "name", messageSource.getMessage("non.unique.projectName",
					new String[] { project.getName() }, Locale.getDefault()));
			result.addError(projectNameError);
			return modelAndView1;

		}

		projectService.saveProjects(project);
		
		Result re = resultService.findResultByProjectId(project.getProjectId());
		List<Fee> fees = feeService.findFeeByProjectId(project.getProjectId());

		model.addAttribute("fees", fees);
		model.addAttribute("re", re);
		model.addAttribute("pj", project);
		model.addAttribute("projectSuccess",project.getName() + " " + " được thêm thành công! ");
		model.addAttribute("loggedinuser", getPrincipal());
		return modelAndView2;
	}
	
	/**
	 * This method will provide the medium to update an existing project.
	 */
	@RequestMapping(value = { "/edit-project-{projectId}" }, method = RequestMethod.GET)
	public String editProject(@PathVariable String projectId, ModelMap model) {
		Project project = projectService.findByProjectID(projectId);
		model.addAttribute("project", project);
		model.addAttribute("editProject", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "addProject";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating project in database. It also validates the project input
	 */
	@RequestMapping(value = { "/edit-project-{projectId}" }, method = RequestMethod.POST)
	public ModelAndView updateProject(@Valid Project project, BindingResult result, ModelMap model, @PathVariable String projectId) {

		ModelAndView modelAndView1 = new ModelAndView("addProject");
		ModelAndView modelAndView2 = new ModelAndView("success");
		
		if (result.hasErrors()) {
			return modelAndView1;
		}

		projectService.updateProjects(project);
		
		Result re = resultService.findResultByProjectId(project.getProjectId());
		
		List<Fee> fees = feeService.findFeeByProjectId(project.getProjectId());
		
		model.addAttribute("fees", fees);
		model.addAttribute("re", re);
		model.addAttribute("pj", project);
		model.addAttribute("projectSuccess",
				project.getName() + " " + " đã được cập nhật.");
		model.addAttribute("loggedinuser", getPrincipal());
		return modelAndView2;
	}

	/**
	 * This method will delete an project by it's projectID value.
	 */
	@RequestMapping(value = { "/delete-project-{projectId}" }, method = RequestMethod.GET)
	public String deleteProject(@PathVariable String projectId) {
		projectService.deleteByProjectID(projectId);
		return "redirect:/projectsList";
	}
	
	/**
	 * This method will provide Customers list to select
	 */
	@ModelAttribute("select_customers")
	public List<Customer> initializeCustomers() {
		return customerService.findAllCustomers();
	}

}
