package com.duyetdo.springmvc.dao;

import java.util.List;

import com.duyetdo.springmvc.model.Result;

public interface ResultDao {
	
	Result findByPK(int pk);

	Result findByResultID(int resultId);

	Result findResultByProjectId(int id);

	void save(Result result);

	void deleteByResultID(int resultId);

	List<Result> findAllResults();

}
