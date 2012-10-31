package com.palm.cloud.services.cmdb.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.condition.LogicalCondition;
import com.palm.cloud.services.cmdb.condition.ValueCondition;
import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;
import com.palm.cloud.services.cmdb.entity.MetaAttributeDO_;
import com.palm.cloud.services.cmdb.entity.MetaClassDO;
import com.palm.cloud.services.cmdb.entity.MetaClassDO_;
import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO;
import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO_;
import com.palm.cloud.services.cmdb.entity.ObjectDO;
import com.palm.cloud.services.cmdb.entity.ObjectDO_;
import com.palm.cloud.services.cmdb.entity.RelationshipDO;
import com.palm.cloud.services.cmdb.entity.RelationshipDO_;

@Repository
@Transactional
public class ObjectDAOImpl extends GenericDAOImpl<ObjectDO, Integer> 
		implements IObjectDAO {

	protected static Logger log = Logger.getLogger(ObjectDAOImpl.class);
	
	public ObjectDAOImpl() {
		
	}
	
	public ObjectDAOImpl(EntityManager em) {
		super(em);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public ObjectDO findByNameAndNamespace(String name, String namespace) {
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findByNameAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		return (ObjectDO) query.getSingleResult();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public ObjectDO findByName(String name) {
		return this.findByNameAndNamespace(name, DEFAULT_NAMESPACE);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByClassAndNamespace(String className, 
			String namespace, int offset, int maxResults) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findAllByClassAndNamespace");
		query.setParameter("className", className);
		query.setParameter("namespace", namespace);
		query.setFirstResult(offset);
		query.setMaxResults(maxResults);
		return (List<ObjectDO>) query.getResultList();
	}
	
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByClass(String className, int offset, 
			int maxResults) {
		
		return this.findAllByClassAndNamespace(className, DEFAULT_NAMESPACE, 
				offset, maxResults);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByClassAndNamespace(String name,
			String namespace, String relationClass, String className) {

		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findFromObjectsByClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		query.setParameter("className", className);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByClass(String name,
			String relationClass, String className) {

		return this.findFromObjectsByClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass, className);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByClassAndNamespace(String name,
			String namespace, String relationClass, String className) {

		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findToObjectsByClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		query.setParameter("className", className);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByClass(String name,
			String relationClass, String className) {
		
		return this.findToObjectsByClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass, className);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByRelationClassAndNamespace(
			String name, String namespace, String relationClass) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findFromObjectsByRelationshipClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByRelationClass(String name,
			String relationClass) {
		
		return this.findFromObjectsByRelationClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByRelationClassAndNamespace(String name,
			String namespace, String relationClass) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findToObjectsByRelationshipClassAndNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		query.setParameter("relationClass", relationClass);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByRelationClass(String name,
			String relationClass) {
		
		return this.findToObjectsByRelationClassAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByNamespace(String name,
			String namespace) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findFromObjectsByNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjects(String name) {
		return this.findFromObjectsByNamespace(name, DEFAULT_NAMESPACE);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByNamespace(String name, 
			String namespace) {
		
		Query query = this.getEntityManager().createNamedQuery(
				"ObjectDO.findToObjectsByNamespace");
		query.setParameter("name", name);
		query.setParameter("namespace", namespace);
		return (List<ObjectDO>) query.getResultList();
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjects(String name) {
		return this.findToObjectsByNamespace(name, DEFAULT_NAMESPACE);
	}
	
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByConditionsAndNamespace(String className, 
			String namespace, int offset, int maxResults, 
			Condition... conditions) {
		
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ObjectDO> cq = cb.createQuery(ObjectDO.class);
		cq.distinct(true);
		Root<ObjectDO> o = cq.from(ObjectDO.class);
		Join<ObjectDO, MetaClassDO> om = o.join(ObjectDO_.klass);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(o.get(ObjectDO_.namespace), namespace));
		predicates.add(cb.equal(om.get(MetaClassDO_.name), className));
		predicates.addAll(generatePredicates(cb, o, conditions));
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		TypedQuery<ObjectDO> q = this.getEntityManager().createQuery(cq);
		q.setFirstResult(offset);
		q.setMaxResults(maxResults);
		List<ObjectDO> objects = q.getResultList();
		return objects;
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByConditions(String className, int offset, 
			int maxResults, Condition... conditions) {

		return this.findAllByConditionsAndNamespace(className, 
				DEFAULT_NAMESPACE, offset, maxResults, conditions);
	}
	
	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByConditionsAndNamespace(String name,
			String namespace, String relationClass, String className, 
			Condition... conditions) {

		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ObjectDO> cq = cb.createQuery(ObjectDO.class);
		cq.distinct(true);
		Root<RelationshipDO> r = cq.from(RelationshipDO.class);
		cq.select(r.get(RelationshipDO_.fromObject));
		Join<RelationshipDO, MetaClassDO> rm = r.join(RelationshipDO_.klass);
		Join<RelationshipDO, ObjectDO> rto = r.join(RelationshipDO_.toObject);
		Join<RelationshipDO, ObjectDO> rfo = r.join(RelationshipDO_.fromObject);
		Join<ObjectDO, MetaClassDO> rfom = rfo.join(ObjectDO_.klass);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(rto.get(ObjectDO_.name), name));
		predicates.add(cb.equal(rto.get(ObjectDO_.namespace), namespace));
		predicates.add(cb.equal(rm.get(MetaClassDO_.name), relationClass));
		predicates.add(cb.equal(rfom.get(MetaClassDO_.name), className));
		predicates.addAll(generatePredicates(cb, rfo, conditions));
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		TypedQuery<ObjectDO> q = this.getEntityManager().createQuery(cq);
		List<ObjectDO> objects = q.getResultList();
		return objects;
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findFromObjectsByConditions(String name,
			String relationClass, String className, Condition... conditions) {

		return this.findFromObjectsByConditionsAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass, className, conditions);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByConditionsAndNamespace(String name,
			String namespace, String relationClass, String className,
			Condition... conditions) {

		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ObjectDO> cq = cb.createQuery(ObjectDO.class);
		cq.distinct(true);
		Root<RelationshipDO> r = cq.from(RelationshipDO.class);
		cq.select(r.get(RelationshipDO_.toObject));
		Join<RelationshipDO, MetaClassDO> rm = r.join(RelationshipDO_.klass);
		Join<RelationshipDO, ObjectDO> rfo = r.join(RelationshipDO_.fromObject);
		Join<RelationshipDO, ObjectDO> rto = r.join(RelationshipDO_.toObject);
		Join<ObjectDO, MetaClassDO> rtom = rto.join(ObjectDO_.klass);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(rfo.get(ObjectDO_.name), name));
		predicates.add(cb.equal(rfo.get(ObjectDO_.namespace), namespace));
		predicates.add(cb.equal(rm.get(MetaClassDO_.name), relationClass));
		predicates.add(cb.equal(rtom.get(MetaClassDO_.name), className));
		predicates.addAll(generatePredicates(cb, rto, conditions));
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		TypedQuery<ObjectDO> q = this.getEntityManager().createQuery(cq);
		List<ObjectDO> objects = q.getResultList();
		return objects;
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findToObjectsByConditions(String name,
			String relationClass, String className, Condition... conditions) {

		return this.findToObjectsByConditionsAndNamespace(name, 
				DEFAULT_NAMESPACE, relationClass, className, conditions);
	}
	
	private List<Predicate> generatePredicates(CriteriaBuilder cb, 
			From<?, ObjectDO> o, Condition... conditions) {
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (conditions != null) {
			for (Condition condition : conditions) {
				if (condition instanceof ValueCondition 
						&& condition.getOper() != null
						&& condition.getOper().isConditional()) {
					
					predicates.add(generateValueConditionPredicate(
							cb, o, (ValueCondition) condition));
				} else if (condition instanceof LogicalCondition 
						&& condition.getOper() != null
						&& condition.getOper().isLogical()) {
					
					predicates.add(generateLogicalConditionPredicate(
							cb, o, (LogicalCondition) condition));
				}
			}
		}
		return predicates;
	}
	
	private Predicate generateValueConditionPredicate(CriteriaBuilder cb,
			From<?, ObjectDO> o, ValueCondition condition) {
		
		Predicate predicate = null;
		try {
			Join<ObjectDO, ObjectAttributeDO> oa = o
				.join(ObjectDO_.attributes);
			Join<ObjectAttributeDO, MetaAttributeDO> oam = oa.join(
					ObjectAttributeDO_.attribute);
			Predicate namePredicate = cb.equal(oam.get(MetaAttributeDO_.name), 
					((ValueCondition) condition).getName());
			Expression<?> lhs = oa.get(ObjectAttributeDO_.value);
			String rhs = condition.getValue();
			Method method = CriteriaBuilder.class.getMethod(
					condition.getOper().getOperation(), 
					condition.getOper().getParameterTypes());
			Predicate valuePredicate = (Predicate) method.invoke(cb, lhs, rhs);
			predicate = cb.and(namePredicate, valuePredicate);
		} catch (Exception e) {
			log.error("Error generating value condition predicate", e);
		}
		return predicate;
	}

	private Predicate generateLogicalConditionPredicate(CriteriaBuilder cb, 
			From<?, ObjectDO> o, LogicalCondition logical) {
		
		Predicate predicate = null;
		try {
			if (logical.getConditions() != null && logical.getOper() != null) {
				List<Predicate> predicates = generatePredicates(cb, o, 
						logical.getConditions().toArray(new Condition[0]));
				Method method = CriteriaBuilder.class.getMethod(
						logical.getOper().getOperation(), 
						logical.getOper().getParameterTypes());
				predicate = (Predicate) method.invoke(cb, (Object) predicates
						.toArray(new Predicate[0]));
			}
		} catch (Exception e) {
			log.error("Error generating logical condition predicate", e);
		}
		return predicate;
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByNameClassAndNamespace(String nameLike,
			String className, String namespace, int offset, int maxResults) {

		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ObjectDO> cq = cb.createQuery(ObjectDO.class);
		cq.distinct(true);
		Root<ObjectDO> o = cq.from(ObjectDO.class);
		Join<ObjectDO, MetaClassDO> om = o.join(ObjectDO_.klass);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(o.get(ObjectDO_.namespace), namespace));
		predicates.add(cb.equal(om.get(MetaClassDO_.name), className));
		predicates.add(cb.like(o.get(ObjectDO_.name), "%" + nameLike + "%"));
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		TypedQuery<ObjectDO> q = this.getEntityManager().createQuery(cq);
		q.setFirstResult(offset);
		q.setMaxResults(maxResults);
		List<ObjectDO> objects = q.getResultList();
		return objects;
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByNameAndClass(String nameLike,
			String className, int offset, int maxResults) {
		
		return this.findAllByNameClassAndNamespace(nameLike, className, 
				DEFAULT_NAMESPACE, offset, maxResults);
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByNameAndNamespace(String nameLike, 
			String namespace, int offset, int maxResults) {

		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ObjectDO> cq = cb.createQuery(ObjectDO.class);
		cq.distinct(true);
		Root<ObjectDO> o = cq.from(ObjectDO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(o.get(ObjectDO_.namespace), namespace));
		predicates.add(cb.like(o.get(ObjectDO_.name), "%" + nameLike + "%"));
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		TypedQuery<ObjectDO> q = this.getEntityManager().createQuery(cq);
		q.setFirstResult(offset);
		q.setMaxResults(maxResults);
		List<ObjectDO> objects = q.getResultList();
		return objects;
	}

	@Transactional(readOnly = true, 
			noRollbackFor = EmptyResultDataAccessException.class)
	public List<ObjectDO> findAllByName(String nameLike, 
			int offset, int maxResults) {
		
		return this.findAllByNameAndNamespace(
				nameLike, DEFAULT_NAMESPACE, offset, maxResults);
	}

}
