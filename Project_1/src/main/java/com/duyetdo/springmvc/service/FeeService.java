package com.duyetdo.springmvc.service;

import java.util.List;

import com.duyetdo.springmvc.model.Fee;

public interface FeeService {
	
	Fee findByPK(int pk);

	Fee findByFeeID(int feeId);

	Fee findByFeeNameAndProjectId(String feeName, int id);
	
	void saveFees(Fee fee);
	
	void updateFees(Fee fee);

	void deleteByFeeID(int feeId);
	
	List<Fee> findFeeByProjectId(int id);

	List<Fee> findAllFees();
	
	boolean isFeeUnique(String feeName, int id);
}
