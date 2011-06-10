package com.palm.cloud.services.cmdb.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntityTests {

	private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void setUp() throws Exception {
		try {
			emf = Persistence.createEntityManagerFactory(
					"com.palm.cloud.services.cmdb.test");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		emf.close();
	}

	@Test
	public void testMetaAttribute() throws Exception {
		int id = 0;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			MetaAttributeDO insert = new MetaAttributeDO();
			insert.setName("label-old");
			em.persist(insert);
			em.flush();
			id = insert.getId();

			MetaAttributeDO select = em.find(MetaAttributeDO.class, id);
			System.out.printf("%d %s %s %s\n", select.getId(), select.getName(), 
					select.getCreated(), select.getUpdated());

			select.setName("label-new");
			em.merge(select);

			MetaAttributeDO update = em.find(MetaAttributeDO.class, id);
			System.out.printf("%d %s %s %s\n", update.getId(), update.getName(), 
					update.getCreated(), update.getUpdated());

			MetaAttributeDO delete = em.find(MetaAttributeDO.class, id);
			System.out.printf("%d %s %s %s\n", update.getId(), update.getName(), 
					update.getCreated(), update.getUpdated());
			em.remove(delete);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}
	
	@Test
	public void testMetaClass() throws Exception {
		int id = 0;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			MetaClassDO insert = new MetaClassDO();
			insert.setName("function");
			insert.setParent(null);
			insert.setRelationship(false);
			em.persist(insert);
			em.flush();
			id = insert.getId();
			
			MetaClassDO select = em.find(MetaClassDO.class, id);
			System.out.printf("%d %d %b %s %s %s\n", select.getId(), 
					select.getParent(), select.isRelationship(), 
					select.getName(), select.getCreated(), select.getUpdated());

			select.setName("server");
			select.setRelationship(true);
			em.merge(select);

			MetaClassDO update = em.find(MetaClassDO.class, id);
			System.out.printf("%d %d %b %s %s %s\n", update.getId(), 
					update.getParent(), update.isRelationship(), 
					update.getName(), update.getCreated(), update.getUpdated());

			MetaClassDO delete = em.find(MetaClassDO.class, id);
			System.out.printf("%d %d %b %s %s %s\n", delete.getId(), 
					delete.getParent(), delete.isRelationship(), 
					delete.getName(), delete.getCreated(), delete.getUpdated());
			em.remove(delete);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Test
	public void testMetaStatus() throws Exception {
		int id = 0;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			MetaStatusDO insert = new MetaStatusDO();
			insert.setName("live");
			em.persist(insert);
			em.flush();
			id = insert.getId();

			MetaStatusDO select = em.find(MetaStatusDO.class, id);
			System.out.printf("%d %s %s %s\n", select.getId(), select.getName(), 
					select.getCreated(), select.getUpdated());

			select.setName("archive");
			em.merge(select);

			MetaStatusDO update = em.find(MetaStatusDO.class, id);
			System.out.printf("%d %s %s %s\n", update.getId(), update.getName(), 
					update.getCreated(), update.getUpdated());

			MetaStatusDO delete = em.find(MetaStatusDO.class, id);
			System.out.printf("%d %s %s %s\n", update.getId(), update.getName(), 
					update.getCreated(), update.getUpdated());
			em.remove(delete);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}
	
	@Test
	public void testObject() throws Exception {
		int statusId = 0;
		int classId = 0;
		int objectId = 0;
		int attributeMasterId1 = 0;
		int attributeMasterId2 = 0;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			MetaStatusDO status = new MetaStatusDO();
			status.setName("default");
			em.persist(status);
			em.flush();
			statusId = status.getId();
			
			MetaAttributeDO attributeMaster1 = new MetaAttributeDO();
			attributeMaster1.setName("label");
			em.persist(attributeMaster1);
			em.flush();
			attributeMasterId1 = attributeMaster1.getId();
			
			MetaAttributeDO attributeMaster2 = new MetaAttributeDO();
			attributeMaster2.setName("version");
			em.persist(attributeMaster2);
			em.flush();
			attributeMasterId2 = attributeMaster2.getId();
			
			MetaClassDO insert = new MetaClassDO();
			insert.setName("function");
			insert.setParent(null);
			insert.setRelationship(false);
			insert.addAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId1));
			insert.addAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId2));
			em.persist(insert);
			em.flush();
			classId = insert.getId();
			
			ObjectAttributeDO oa1 = new ObjectAttributeDO();
			oa1.setAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId1));
			oa1.setValue("label-value");
			ObjectAttributeDO oa2 = new ObjectAttributeDO();
			oa2.setAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId2));
			oa2.setValue("version-value");
			ObjectDO object = new ObjectDO();
			object.setName("10.1.1.11");
			object.setNamespace("DEFAULT");
			object.setStatus(em.find(MetaStatusDO.class, statusId));
			object.addAttribute(oa1);
			object.addAttribute(oa2);
			MetaClassDO klass = em.find(MetaClassDO.class, classId);
			object.setKlass(klass);
			em.persist(object);
			em.flush();
			objectId = object.getId();

			ObjectDO delete = em.find(ObjectDO.class, objectId);
			System.out.printf("%d %s %s %s %s %s %s\n", delete.getId(), 
					delete.getName(), delete.getKlass().getName(), 
					delete.getStatus().getName(),
					delete.getNamespace(), delete.getCreated(), 
					delete.getUpdated());
			for (ObjectAttributeDO attribute : delete.getAttributes()) {
				System.out.printf("%d %s %s %s %s %s\n", attribute.getId(), 
						attribute.getObject().getName(), 
						attribute.getAttribute().getName(), 
						attribute.getValue(), attribute.getCreated(), 
						attribute.getUpdated());
			}
			em.remove(delete);

			em.remove(em.find(MetaClassDO.class, classId));
			em.remove(em.find(MetaAttributeDO.class, attributeMasterId1));
			em.remove(em.find(MetaAttributeDO.class, attributeMasterId2));
			em.remove(em.find(MetaStatusDO.class, statusId));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}
	
	@Test
	public void testObjectAttribute() throws Exception {
		int statusId = 0;
		int classId = 0;
		int objectId = 0;
		int attributeMasterId = 0;
		int attributeId = 0;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			MetaStatusDO status = new MetaStatusDO();
			status.setName("default");
			em.persist(status);
			em.flush();
			statusId = status.getId();

			MetaAttributeDO attributeMaster = new MetaAttributeDO();
			attributeMaster.setName("label");
			em.persist(attributeMaster);
			em.flush();
			attributeMasterId = attributeMaster.getId();
			
			MetaClassDO insert = new MetaClassDO();
			insert.setName("function");
			insert.setParent(null);
			insert.addAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId));
			insert.setRelationship(false);
			em.persist(insert);
			em.flush();
			classId = insert.getId();
			
			ObjectDO object = new ObjectDO();
			object.setName("10.1.1.11");
			object.setNamespace("DEFAULT");
			MetaClassDO klass = em.find(MetaClassDO.class, classId);
			object.setKlass(klass);
			object.setStatus(em.find(MetaStatusDO.class, statusId));
			em.persist(object);
			em.flush();
			objectId = object.getId();

			ObjectAttributeDO attribute = new ObjectAttributeDO();
			attribute.setObject(em.find(ObjectDO.class, objectId));
			attribute.setAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId));
			attribute.setValue("label-value");
			em.persist(attribute);
			em.flush();
			attributeId = attribute.getId();
			
			ObjectAttributeDO select = em.find(ObjectAttributeDO.class, 
					attributeId);
			System.out.printf("%d %s %s %s %s %s\n", select.getId(), 
					select.getObject().getName(), 
					select.getAttribute().getName(), 
					select.getValue(), select.getCreated(), 
					select.getUpdated());

			em.remove(em.find(ObjectAttributeDO.class, attributeId));
			em.remove(em.find(ObjectDO.class, objectId));
			em.remove(em.find(MetaClassDO.class, classId));
			em.remove(em.find(MetaAttributeDO.class, attributeMasterId));
			em.remove(em.find(MetaStatusDO.class, statusId));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
			
		}
	}

	@Test
	public void testRelationship() throws Exception {
		int statusId = 0;
		int classId1 = 0;
		int classId2 = 0;
		int relationshipClassId = 0;
		int objectId1 = 0;
		int objectId2 = 0;
		int attributeMasterId1 = 0;
		int attributeMasterId2 = 0;
		int relationshipId = 0;
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			MetaStatusDO status = new MetaStatusDO();
			status.setName("default");
			em.persist(status);
			em.flush();
			statusId = status.getId();

			MetaAttributeDO attributeMaster1 = new MetaAttributeDO();
			attributeMaster1.setName("label");
			em.persist(attributeMaster1);
			em.flush();
			attributeMasterId1 = attributeMaster1.getId();
			
			MetaAttributeDO attributeMaster2 = new MetaAttributeDO();
			attributeMaster2.setName("version");
			em.persist(attributeMaster2);
			em.flush();
			attributeMasterId2 = attributeMaster2.getId();
			
			MetaClassDO class1 = new MetaClassDO();
			class1.setName("server");
			class1.setParent(null);
			class1.setRelationship(false);
			class1.addAttribute(attributeMaster1);
			class1.addAttribute(attributeMaster2);
			em.persist(class1);
			em.flush();
			classId1 = class1.getId();
			
			MetaClassDO class2 = new MetaClassDO();
			class2.setName("function");
			class2.setParent(null);
			class2.setRelationship(false);
			class2.addAttribute(attributeMaster1);
			class2.addAttribute(attributeMaster2);
			em.persist(class2);
			em.flush();
			classId2 = class2.getId();
			
			MetaClassDO relationshipClass = new MetaClassDO();
			relationshipClass.setName("PartOf");
			relationshipClass.setParent(null);
			relationshipClass.setRelationship(true);
			relationshipClass.addAttribute(attributeMaster1);
			relationshipClass.addAttribute(attributeMaster2);
			em.persist(relationshipClass);
			em.flush();
			relationshipClassId = relationshipClass.getId();
			
			ObjectAttributeDO oa1 = new ObjectAttributeDO();
			oa1.setAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId1));
			oa1.setValue("label-value");
			ObjectAttributeDO oa2 = new ObjectAttributeDO();
			oa2.setAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId2));
			oa2.setValue("version-value");
			
			ObjectDO object1 = new ObjectDO();
			object1.setName("10.1.1.11");
			object1.setNamespace("DEFAULT");
			object1.setStatus(em.find(MetaStatusDO.class, statusId));
			object1.addAttribute(oa1);
			object1.addAttribute(oa2);
			MetaClassDO klass1 = em.find(MetaClassDO.class, classId1);
			object1.setKlass(klass1);
			em.persist(object1);
			ObjectDO object2 = new ObjectDO();
			object2.setName("pws");
			object2.setNamespace("DEFAULT");
			object2.setStatus(em.find(MetaStatusDO.class, statusId));
			object2.addAttribute(oa1);
			object2.addAttribute(oa2);
			MetaClassDO klass2 = em.find(MetaClassDO.class, classId2);
			object2.setKlass(klass2);
			em.persist(object2);
			em.flush();
			objectId1 = object1.getId();
			objectId2 = object2.getId();

			RelationshipAttributeDO ra1 = new RelationshipAttributeDO();
			ra1.setAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId1));
			ra1.setValue("label-value");
			RelationshipAttributeDO ra2 = new RelationshipAttributeDO();
			ra2.setAttribute(em.find(MetaAttributeDO.class, 
					attributeMasterId2));
			ra2.setValue("version-value");
			RelationshipDO relationship = new RelationshipDO();
			relationship.setNamespace("DEFAULT");
			relationship.setFromObject(em.find(ObjectDO.class, objectId1));
			relationship.setToObject(em.find(ObjectDO.class, objectId2));
			relationship.setKlass(em.find(MetaClassDO.class, 
					relationshipClassId));
			relationship.setStatus(em.find(MetaStatusDO.class, statusId));
			relationship.setName(relationship.getFromObject().getName() + "->" 
					+ relationship.getKlass().getName() + "->" 
					+ relationship.getToObject().getName());
			relationship.addAttribute(ra1);
			relationship.addAttribute(ra2);
			em.persist(relationship);
			em.flush();
			relationshipId = relationship.getId();

			RelationshipDO delete = em.find(RelationshipDO.class, 
					relationshipId);
			System.out.printf("%d %s %s %s %s %s %s %s %s\n", delete.getId(), 
					delete.getName(), delete.getNamespace(), 
					delete.getKlass().getName(), delete.getStatus().getName(),
					delete.getFromObject().getName(), 
					delete.getToObject().getName(), delete.getCreated(), 
					delete.getUpdated());
			for (RelationshipAttributeDO attribute : delete.getAttributes()) {
				System.out.printf("%d %s %s %s %s %s\n", attribute.getId(), 
						attribute.getRelationship().getName(), 
						attribute.getAttribute().getName(), 
						attribute.getValue(), attribute.getCreated(), 
						attribute.getUpdated());
			}
			em.remove(delete);

			em.remove(em.find(ObjectDO.class, objectId1));
			em.remove(em.find(ObjectDO.class, objectId2));
			em.remove(em.find(MetaClassDO.class, classId1));
			em.remove(em.find(MetaClassDO.class, classId2));
			em.remove(em.find(MetaClassDO.class, relationshipClassId));
			em.remove(em.find(MetaAttributeDO.class, attributeMasterId1));
			em.remove(em.find(MetaAttributeDO.class, attributeMasterId2));
			em.remove(em.find(MetaStatusDO.class, statusId));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}
	
}
