package com.duyetdo.springmvc.service;

import java.util.List;

import com.duyetdo.springmvc.model.Project;

public interface ProjectService {
	
	Project findByProjectPK(String pk);

	Project findByProjectID(String projectId);
	
	Project findByProjectName(String projectName);

	void saveProjects(Project project);
	
	void updateProjects(Project project);

	void deleteByProjectID(String projectId);

	List<Project> findAllProjects();

	boolean isProjectIDUnique(String projectId);
	
	boolean isProjectNameUnique(String projectName);

}
