package com.duyetdo.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duyetdo.springmvc.dao.FeeDao;
import com.duyetdo.springmvc.dao.ProjectDao;
import com.duyetdo.springmvc.dao.ResultDao;
import com.duyetdo.springmvc.model.Fee;
import com.duyetdo.springmvc.model.Project;
import com.duyetdo.springmvc.model.Result;

@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ResultDao resultDao;
	
	@Autowired
	private FeeDao feeDao;
	
	public Project findByProjectPK(String pk){
		return projectDao.findByPK(pk);
	}

	public Project findByProjectID(String projectId) {
		Project project = projectDao.findByProjectID(projectId);
		return project;
	}

	public Project findByProjectName(String projectName) {
		Project project = projectDao.findByProjectName(projectName);
		return project;
	}

	@Transactional
	public void saveProjects(Project project) {
		projectDao.save(project);
		List<Fee> fees = feeDao.findFeeByProjectId(project.getProjectId());
		Double tongChiPhi = 0D;
		for (Fee fee : fees) {
			tongChiPhi += fee.getValue() * fee.getRate();
		}
		Result result= new Result();
		try {
			Double laiGop = (project.getValue() - project.getGiaVon()) * project.getRate();
			Double tiLe_laiGop = laiGop / (project.getValue() * project.getRate());
			Double loiNhuanRong = laiGop - tongChiPhi;
			Double tl_LNR_GT = loiNhuanRong / (project.getValue() * project.getRate());
			Double tl_LNR_GV = loiNhuanRong / (project.getGiaVon() * project.getRate());

			result.setProject(project);
			result.setLaiGop(laiGop);
			result.setLnRong(loiNhuanRong);
			result.setTlLaiGop(tiLe_laiGop);
			result.setLnrTrenValue(tl_LNR_GT);
			result.setLnrTrenGv(tl_LNR_GV);
		} catch (Exception e) {
			System.out.println(e);
		}

		resultDao.save(result);
	}
	

	@Transactional
	public void updateProjects(Project project) {
		Project entity = projectDao.findByPK(project.getProjectId());
		Result re_entity = resultDao.findResultByProjectId(project.getProjectId());
		if (entity != null) {
			entity.setProjectId(project.getProjectId());
			entity.setName(project.getName());
			entity.setValue(project.getValue());
			entity.setGiaVon(project.getGiaVon());
			entity.setCurrency(project.getCurrency());
			entity.setRate(project.getRate());
			entity.setDate(project.getDate());
			entity.setCustomer(project.getCustomer());
		}
		
		List<Fee> fees = feeDao.findFeeByProjectId(project.getProjectId());
		Double tongChiPhi = 0D;
		for (Fee fee : fees) {
			tongChiPhi += fee.getValue() * fee.getRate();
		}
		try {
			Double laiGop = (project.getValue() - project.getGiaVon()) * project.getRate();
			Double tiLe_laiGop = laiGop / (project.getValue() * project.getRate());
			Double loiNhuanRong = laiGop - tongChiPhi;
			Double tl_LNR_GT = loiNhuanRong / (project.getValue() * project.getRate());
			Double tl_LNR_GV = loiNhuanRong / (project.getGiaVon() * project.getRate());

			if (re_entity != null) {
				re_entity.setProject(project);
				re_entity.setLaiGop(laiGop);
				re_entity.setLnRong(loiNhuanRong);
				re_entity.setTlLaiGop(tiLe_laiGop);
				re_entity.setLnrTrenValue(tl_LNR_GT);
				re_entity.setLnrTrenGv(tl_LNR_GV);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void deleteByProjectID(String projectId) {
		projectDao.deleteByProjectID(projectId);
	}

	public List<Project> findAllProjects() {
		return projectDao.findAllProjects();
	}
	

	public boolean isProjectIDUnique(String projectId) {
		Project project = findByProjectID(projectId);
		return (project == null);
	}

	public boolean isProjectNameUnique(String projectName) {
		Project project = findByProjectName(projectName);
		return (project == null);
	}

}
