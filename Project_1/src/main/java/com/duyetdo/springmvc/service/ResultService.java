package com.duyetdo.springmvc.service;

import java.util.List;

import com.duyetdo.springmvc.model.Result;

public interface ResultService {

	Result findByPK(int pk);

	Result findByResultID(int resultId);

	Result findResultByProjectId(int projectId);
	
	void saveResult(Result result);

	void deleteByResultID(int resultId);

	List<Result> findAllResults();
	
	boolean isResultUnique(int projectId);
}
