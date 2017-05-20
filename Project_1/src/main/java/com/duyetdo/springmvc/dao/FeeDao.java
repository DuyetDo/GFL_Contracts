package com.duyetdo.springmvc.dao;

import java.util.List;

import com.duyetdo.springmvc.model.Fee;

public interface FeeDao {
	
	Fee findByPK(int pk);

	Fee findByFeeID(int feeId);

	Fee findByFeeNameAndProjectId(String feeName, int id);
	
	List<Fee> findFeeByProjectId(int id);

	void save(Fee fee);

	void deleteByFeeID(int feeId);

	List<Fee> findAllFees();
}
