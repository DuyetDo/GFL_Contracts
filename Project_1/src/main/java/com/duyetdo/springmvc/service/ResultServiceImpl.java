package com.duyetdo.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duyetdo.springmvc.dao.ProjectDao;
import com.duyetdo.springmvc.dao.FeeDao;
import com.duyetdo.springmvc.dao.ResultDao;
import com.duyetdo.springmvc.model.Fee;
import com.duyetdo.springmvc.model.Project;
import com.duyetdo.springmvc.model.Result;

@Service("resultService")
@Transactional
public class ResultServiceImpl implements ResultService {

	@Autowired
	private ResultDao dao;

	@Autowired
	private FeeDao feeDao;

	@Autowired
	private ProjectDao projectDao;

	public Result findByPK(int pk) {
		return dao.findByPK(pk);
	}

	public Result findByResultID(int resultId) {
		return dao.findByResultID(resultId);
	}

	public Result findResultByProjectId(String projectId) {
		return dao.findResultByProjectId(projectId);
	}

	public void saveResult(Result result) {
		List<Fee> fees = feeDao.findFeeByProjectId(result.getProject().getProjectId());
		Project project = projectDao.findByProjectID(result.getProject().getProjectId());
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

			result.setProject(project);
			result.setLaiGop(laiGop);
			result.setLnRong(loiNhuanRong);
			result.setTlLaiGop(tiLe_laiGop);
			result.setLnrTrenValue(tl_LNR_GT);
			result.setLnrTrenGv(tl_LNR_GV);
		} catch (Exception e) {
			System.out.println(e);
		}

		dao.save(result);
	}

	public void deleteByResultID(int resultId) {
		dao.deleteByResultID(resultId);
	}

	public List<Result> findAllResults() {
		return dao.findAllResults();
	}

	public boolean isResultUnique(String projectId) {
		Result result = findResultByProjectId(projectId);
		return (result == null);
	}

}
