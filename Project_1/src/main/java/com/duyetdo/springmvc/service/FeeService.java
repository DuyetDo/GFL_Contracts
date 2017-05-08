package com.duyetdo.springmvc.service;

import java.util.List;

import com.duyetdo.springmvc.model.Fee;

public interface FeeService {
	
	Fee findByPK(int pk);

	Fee findByFeeID(int feeId);

	Fee findByFeeNameAndProjectId(String feeName, String projectId);
	
	void saveFees(Fee fee);
	
	void updateFees(Fee fee);

	void deleteByFeeID(int feeId);
	
	List<Fee> findFeeByProjectId(String projectId);

	List<Fee> findAllFees();
	
	boolean isFeeUnique(String feeName, String projectId);
}
