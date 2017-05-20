package com.duyetdo.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.duyetdo.springmvc.model.Project;

@Repository("projectDao")
public class ProjectDaoImpl extends AbstractDao<Integer, Project> implements ProjectDao {
	static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);

	public Project findByPK(int pk) {
		Project project = getByKey(pk);
		if (project != null) {
			Hibernate.initialize(project.getFees());
			Hibernate.initialize(project.getResults());
		}
		return project;
	}

	public Project findByProjectID(String projectId) {
		logger.info("ProjectID : {}", projectId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("projectId", projectId));
		Project project = (Project) crit.uniqueResult();
		if (project != null) {
			Hibernate.initialize(project.getFees());
			Hibernate.initialize(project.getResults());
		}
		return project;
	}

	public Project findByProjectName(String projectName) {
		logger.info("ProjectName : {}", projectName);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("name", projectName));
		Project project = (Project) crit.uniqueResult();
		if (project != null) {
			Hibernate.initialize(project.getFees());
			Hibernate.initialize(project.getResults());
		}
		return project;
	}

	public void save(Project project) {
		persist(project);
	}

	public void deleteByProjectID(String projectId) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("projectId", projectId));
		Project project = (Project) crit.uniqueResult();
		if (null != project) {
			delete(project);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Project> findAllProjects() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
																		// duplicates.
		List<Project> projects = (List<Project>) criteria.list();
		return projects;
	}

}
