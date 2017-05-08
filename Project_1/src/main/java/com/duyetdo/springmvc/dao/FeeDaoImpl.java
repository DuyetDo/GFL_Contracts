package com.duyetdo.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.duyetdo.springmvc.model.Fee;

@Repository("feeDao")
public class FeeDaoImpl extends AbstractDao<Integer, Fee> implements FeeDao {

	static final Logger logger = LoggerFactory.getLogger(FeeDaoImpl.class);

	public Fee findByPK(int pk) {
		Fee fee = getByKey(pk);
		return fee;
	}

	public Fee findByFeeID(int feeId) {
		logger.info("FeeId : {}", feeId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("feeId", feeId));
		Fee fee = (Fee) crit.uniqueResult();
		return fee;
	}

	public Fee findByFeeNameAndProjectId(String feeName, String projectId) {
		logger.info("FeeId : {}", feeName);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("name", feeName));
		crit.add(Restrictions.eq("project.projectId", projectId));
		Fee fee = (Fee) crit.uniqueResult();
		return fee;
	}
	
	@SuppressWarnings("unchecked")
	public List<Fee> findFeeByProjectId(String projectId){
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("project.projectId", projectId));
		List<Fee> fees = (List<Fee>) crit.list();
		return fees;
	}

	public void save(Fee fee) {
		persist(fee);
	}

	public void deleteByFeeID(int feeId) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("feeId", feeId));
		Fee fee = (Fee) crit.uniqueResult();
		if (null != fee) {
			delete(fee);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Fee> findAllFees() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		List<Fee> fees = (List<Fee>) criteria.list();
		return fees;
	}

}
