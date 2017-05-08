package com.duyetdo.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.duyetdo.springmvc.model.Result;

@Repository("resultDao")
public class ResultDaoImpl extends AbstractDao<Integer, Result> implements ResultDao {

	static final Logger logger = LoggerFactory.getLogger(ResultDaoImpl.class);

	public Result findByPK(int pk) {
		Result result = getByKey(pk);
		return result;
	}

	public Result findByResultID(int resultId) {
		logger.info("ResultId : {}", resultId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("resultId", resultId));
		Result result = (Result) crit.uniqueResult();
		return result;
	}

	public Result findResultByProjectId(String projectId) {
		logger.info("ProjectId : {}", projectId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("project.projectId", projectId));
		Result result = (Result) crit.uniqueResult();
		return result;
	}

	public void save(Result result) {
		persist(result);
	}

	public void deleteByResultID(int resultId) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("resultId", resultId));
		Result result = (Result) crit.uniqueResult();
		if (null != result) {
			delete(result);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Result> findAllResults() {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		List<Result> results = (List<Result>) criteria.list();
		return results;
	}

}
