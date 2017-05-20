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

@Service("feeService")
@Transactional
public class FeeServiceImpl implements FeeService {

	@Autowired
	private FeeDao dao;

	@Autowired
	private ResultDao resultDao;

	@Autowired
	private ProjectDao projectDao;

	public Fee findByPK(int pk) {
		return dao.findByPK(pk);
	}

	public Fee findByFeeID(int feeId) {
		return dao.findByFeeID(feeId);
	}

	public Fee findByFeeNameAndProjectId(String feeName, int id) {
		return dao.findByFeeNameAndProjectId(feeName, id);
	}
	
	public List<Fee> findFeeByProjectId(int id){
		return dao.findFeeByProjectId(id);
	}

	@Transactional
	public void saveFees(Fee fee) {
		dao.save(fee);
		Project project = projectDao.findByPK(fee.getProject().getId());
		Result re_entity = resultDao.findResultByProjectId(fee.getProject().getId());
		List<Fee> fees = findFeeByProjectId(fee.getProject().getId());
		Double tongChiPhi = 0D;
		for (Fee f : fees) {
			tongChiPhi += f.getValue() * f.getRate();
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

	@Transactional
	public void updateFees(Fee fee) {
		Fee entity = dao.findByPK(fee.getFeeId());
		if (entity != null) {
			entity.setFeeId(fee.getFeeId());
			entity.setName(fee.getName());
			entity.setValue(fee.getValue());
			entity.setCurrency(fee.getCurrency());
			entity.setRate(fee.getRate());
		}
		
		Project project = projectDao.findByPK(fee.getProject().getId());
		Result re_entity = resultDao.findResultByProjectId(fee.getProject().getId());
		List<Fee> fees = findFeeByProjectId(fee.getProject().getId());
		Double tongChiPhi = 0D;
		for (Fee f : fees) {
			tongChiPhi += f.getValue() * f.getRate();
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

	public void deleteByFeeID(int feeId) {
		dao.deleteByFeeID(feeId);
	}

	public List<Fee> findAllFees() {
		return dao.findAllFees();
	}

	public boolean isFeeUnique(String feeName, int id) {
		Fee fee = findByFeeNameAndProjectId(feeName, id);
		return (fee == null);
	}

}
