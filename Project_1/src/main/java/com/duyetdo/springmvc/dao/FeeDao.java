package com.duyetdo.springmvc.dao;

import java.util.List;

import com.duyetdo.springmvc.model.Fee;

public interface FeeDao {
	
	Fee findByPK(int pk);

	Fee findByFeeID(int feeId);

	Fee findByFeeNameAndProjectId(String feeName, String projectId);
	
	List<Fee> findFeeByProjectId(String projectId);

	void save(Fee fee);

	void deleteByFeeID(int feeId);

	List<Fee> findAllFees();
}
