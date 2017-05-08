package com.duyetdo.springmvc.dao;

import java.util.List;

import com.duyetdo.springmvc.model.Project;

public interface ProjectDao {
	
	Project findByPK(String pk);
	
	Project findByProjectID(String projectId);
	
	Project findByProjectName(String projectName);
	
	void save(Project project);
		
	void deleteByProjectID(String projectId);
	
	List<Project> findAllProjects();

}
