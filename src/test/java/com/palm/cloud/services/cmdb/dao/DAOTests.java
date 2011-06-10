package com.palm.cloud.services.cmdb.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;
import com.palm.cloud.services.cmdb.entity.MetaClassDO;
import com.palm.cloud.services.cmdb.entity.MetaClassRelationshipDO;
import com.palm.cloud.services.cmdb.entity.MetaStatusDO;
import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO;
import com.palm.cloud.services.cmdb.entity.ObjectDO;
import com.palm.cloud.services.cmdb.entity.RelationshipAttributeDO;
import com.palm.cloud.services.cmdb.entity.RelationshipDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/test/resources/applicationContext-test.xml"
})
@Transactional
public class DAOTests {

	@Autowired
	IMetaClassDAO metaClassDAO;
	
	@Autowired
	IMetaAttributeDAO metaAttributeDAO;
	
	@Autowired
	IMetaClassRelationshipDAO metaClassRelationshipDAO;
	
	@Autowired
	IMetaStatusDAO metaStatusDAO;
	
	@Autowired
	IObjectDAO objectDAO;
	
	@Autowired
	IObjectAttributeDAO objectAttributeDAO;
	
	@Autowired
	IRelationshipDAO relationshipDAO;
	
	@Autowired
	IRelationshipAttributeDAO relationshipAttributeDAO;
	
	@Test
	public void testMetaAttributeDAO() throws Exception {
		MetaAttributeDO entity = new MetaAttributeDO();
		entity.setName("label");
		entity = this.metaAttributeDAO.create(entity);
		print(entity);
		MetaAttributeDO selected = this.metaAttributeDAO
			.findById(entity.getId());
		print(selected);
		Assert.assertEquals(entity.getId(), selected.getId());
		selected.setName("label-new");
		MetaAttributeDO updated = this.metaAttributeDAO
			.update(selected);
		print(updated);
		Assert.assertEquals(updated.getName(), this.metaAttributeDAO
				.findById(entity.getId()).getName());
		selected = metaAttributeDAO.findByName(updated.getName());
		Assert.assertNotNull(selected);
		print(selected);
		this.metaAttributeDAO.delete(selected);
		Assert.assertNull(this.metaAttributeDAO.findById(entity.getId()));
	}

	@Test
	public void testMetaClassDAO() throws Exception {
		MetaAttributeDO attribute = new MetaAttributeDO();
		attribute.setName("label");
		attribute = metaAttributeDAO.create(attribute);
		print(attribute);
		
		MetaClassDO entity = new MetaClassDO();
		entity.setName("function");
		entity.setParent(null);;
		entity.setRelationship(false);
		entity.addAttribute(attribute);
		entity = this.metaClassDAO.create(entity);
		print(entity);
		MetaClassDO selected = this.metaClassDAO.findById(entity.getId());
		print(selected);
		Assert.assertEquals(entity.getId(), selected.getId());
		selected.setName("server");
		MetaClassDO updated = this.metaClassDAO.update(selected);
		print(updated);
		Assert.assertEquals(updated.getName(), this.metaClassDAO
				.findById(entity.getId()).getName());
		selected = metaClassDAO.findByName(updated.getName());
		Assert.assertNotNull(selected);
		print(selected);
		selected = metaClassDAO.findByNameAndType(updated.getName(), 
				updated.isRelationship());
		Assert.assertNotNull(selected);
		print(selected);
		this.metaClassDAO.delete(selected);
		Assert.assertNull(this.metaClassDAO.findById(entity.getId()));
		metaAttributeDAO.delete(attribute);
	}

	@Test
	public void testMetaRelationshipDAO() throws Exception {
		MetaAttributeDO attribute = new MetaAttributeDO();
		attribute.setName("label");
		attribute = metaAttributeDAO.create(attribute);
		print(attribute);
		
		MetaClassDO fromClass = new MetaClassDO();
		fromClass.setName("server");
		fromClass.setParent(null);;
		fromClass.setRelationship(false);
		fromClass.addAttribute(attribute);
		fromClass = metaClassDAO.create(fromClass);
		
		MetaClassDO toClass = new MetaClassDO();
		toClass.setName("function");
		toClass.setParent(null);;
		toClass.setRelationship(false);
		toClass.addAttribute(attribute);
		toClass = metaClassDAO.create(toClass);

		MetaClassDO relationshipClass = new MetaClassDO();
		relationshipClass.setName("PartOf");
		relationshipClass.setParent(null);;
		relationshipClass.setRelationship(false);
		relationshipClass.addAttribute(attribute);
		relationshipClass = metaClassDAO.create(relationshipClass);
		
		MetaClassRelationshipDO classRelationship 
			= new MetaClassRelationshipDO();
		classRelationship.setFromKlass(fromClass);
		classRelationship.setRelationshipKlass(relationshipClass);
		classRelationship.setToKlass(toClass);
		classRelationship = metaClassRelationshipDAO.create(
				classRelationship);
		print(classRelationship);
		MetaClassRelationshipDO selected = metaClassRelationshipDAO
			.findById(classRelationship.getId());
		print(selected);
		Assert.assertEquals(classRelationship.getId(), selected.getId());
		selected = metaClassRelationshipDAO.findByTypes(fromClass.getName(), 
				relationshipClass.getName(), toClass.getName());
		Assert.assertNotNull(selected);
		print(selected);
		List<MetaClassRelationshipDO> relationships = metaClassRelationshipDAO
			.findByFromType(fromClass.getName());
		Assert.assertNotNull(relationships);
		for (MetaClassRelationshipDO relationship : relationships) {
			print(relationship);
		}
		relationships = metaClassRelationshipDAO
			.findByToType(toClass.getName());
		Assert.assertNotNull(relationships);
		for (MetaClassRelationshipDO relationship : relationships) {
			print(relationship);
		}
		relationships = metaClassRelationshipDAO
			.findByRelationType(relationshipClass.getName());
		Assert.assertNotNull(relationships);
		for (MetaClassRelationshipDO relationship : relationships) {
			print(relationship);
		}
		metaClassRelationshipDAO.delete(classRelationship);
		Assert.assertNull(metaClassDAO.findById(classRelationship.getId()));
		metaClassDAO.delete(fromClass);
		metaClassDAO.delete(toClass);
		metaClassDAO.delete(relationshipClass);
		metaAttributeDAO.delete(attribute);
	}

	@Test
	public void testMetaStatusDAO() throws Exception {
		MetaStatusDO entity = new MetaStatusDO();
		entity.setName("default");
		entity = metaStatusDAO.create(entity);
		print(entity);
		MetaStatusDO selected = metaStatusDAO.findById(entity.getId());
		print(selected);
		Assert.assertEquals(entity.getId(), selected.getId());
		selected.setName("system");
		MetaStatusDO updated = metaStatusDAO.update(selected);
		print(updated);
		Assert.assertEquals(updated.getName(), metaStatusDAO
				.findById(entity.getId()).getName());
		selected = metaStatusDAO.findByName(updated.getName());
		Assert.assertNotNull(selected);
		print(selected);
		List<MetaStatusDO> statuses = metaStatusDAO.findAll();
		Assert.assertNotNull(statuses);
		for (MetaStatusDO status : statuses) {
			print(status);
		}
		metaStatusDAO.delete(selected);
		Assert.assertNull(metaStatusDAO.findById(entity.getId()));
	}

	@Test
	public void testObjectAttributeDAO() throws Exception {
		MetaClassDO klass = new MetaClassDO();
		klass.setName("function");
		klass.setParent(null);;
		klass.setRelationship(false);
		klass = metaClassDAO.create(klass);

		MetaStatusDO status = new MetaStatusDO();
		status.setName("default");
		status = metaStatusDAO.create(status);
		
		ObjectDO object = new ObjectDO();
		object.setName("10.1.1.11");
		object.setNamespace("DEFAULT");
		object.setStatus(status);
		object.setKlass(klass);
		object = objectDAO.create(object);
		
		MetaAttributeDO attributeMaster = new MetaAttributeDO();
		attributeMaster.setName("label");
		attributeMaster = metaAttributeDAO.create(attributeMaster);

		ObjectAttributeDO attribute = new ObjectAttributeDO();
		attribute.setAttribute(attributeMaster);
		attribute.setValue("label-value");
		attribute.setObject(object);
		attribute = objectAttributeDAO.create(attribute);

		ObjectAttributeDO selected = objectAttributeDAO.findById(attribute
				.getId());
		print(selected);
		Assert.assertEquals(attribute.getId(), selected.getId());
		
		selected.setValue("label-new-value");
		ObjectAttributeDO updated = objectAttributeDAO.update(selected);
		print(updated);
		Assert.assertEquals(selected.getValue(), updated.getValue());
		
		selected = objectAttributeDAO.findByName(object.getName(), attribute
				.getAttribute().getName());
		print(selected);
		Assert.assertEquals(attribute.getId(), selected.getId());

		List<ObjectAttributeDO> attributes = objectAttributeDAO
			.findAllByObjectName(object.getName());
		Assert.assertNotNull(attributes);
		for (ObjectAttributeDO oa : attributes) {
			print(oa);
		}
		
		objectAttributeDAO.delete(selected.getId());
		Assert.assertNull(objectAttributeDAO.findById(attribute.getId()));

		objectDAO.delete(object);
		metaAttributeDAO.delete(attributeMaster);
		metaClassDAO.delete(klass);
		metaStatusDAO.delete(status);
	}

	@Test
	public void testObjectDAO() throws Exception {
		MetaClassDO klass = new MetaClassDO();
		klass.setName("function");
		klass.setParent(null);;
		klass.setRelationship(false);
		klass = metaClassDAO.create(klass);
		
		MetaStatusDO status = new MetaStatusDO();
		status.setName("default");
		status = metaStatusDAO.create(status);
		
		MetaAttributeDO attributeMaster1 = new MetaAttributeDO();
		attributeMaster1.setName("label");
		attributeMaster1 = metaAttributeDAO.create(attributeMaster1);
		
		MetaAttributeDO attributeMaster2 = new MetaAttributeDO();
		attributeMaster2.setName("version");
		attributeMaster2 = metaAttributeDAO.create(attributeMaster2);
		
		ObjectAttributeDO oa1 = new ObjectAttributeDO();
		oa1.setAttribute(attributeMaster1);
		oa1.setValue("label-value");
		ObjectAttributeDO oa2 = new ObjectAttributeDO();
		oa2.setAttribute(attributeMaster2);
		oa2.setValue("version-value");
		
		ObjectDO object = new ObjectDO();
		object.setName("10.1.1.11");
		object.setNamespace("DEFAULT");
		object.setStatus(status);
		object.addAttribute(oa1);
		object.addAttribute(oa2);
		object.setKlass(klass);
		object = objectDAO.create(object);

		ObjectDO selected = objectDAO.findById(object.getId());
		print(selected);
		Assert.assertEquals(object.getId(), selected.getId());
		
		selected = objectDAO.findByNameAndNamespace("10.1.1.11", "DEFAULT");
		print(selected);
		Assert.assertEquals(object.getId(), selected.getId());

		selected = objectDAO.findByName("10.1.1.11");
		print(selected);
		Assert.assertEquals(object.getId(), selected.getId());

		List<ObjectDO> objects = objectDAO.findAllByClass("function", 0, 10);
		for (ObjectDO o : objects) {
			print(o);
		}
		
		objectDAO.delete(object);
		Assert.assertNull(objectDAO.findById(object.getId()));
		
		metaClassDAO.delete(klass);
		metaAttributeDAO.delete(attributeMaster1);
		metaAttributeDAO.delete(attributeMaster2);
		metaStatusDAO.delete(status);
	}
	
	@Test
	public void testRelationshipDAO() throws Exception {
		MetaStatusDO status = new MetaStatusDO();
		status.setName("default");
		status = metaStatusDAO.create(status);
		
		MetaClassDO class1 = new MetaClassDO();
		class1.setName("server");
		class1.setParent(null);;
		class1.setRelationship(false);
		class1 = metaClassDAO.create(class1);
		
		MetaClassDO class2 = new MetaClassDO();
		class2.setName("function");
		class2.setParent(null);;
		class2.setRelationship(false);
		class2 = metaClassDAO.create(class2);
		
		MetaClassDO relationshipClass = new MetaClassDO();
		relationshipClass.setName("PartOf");
		relationshipClass.setParent(null);;
		relationshipClass.setRelationship(true);
		relationshipClass = metaClassDAO.create(relationshipClass);
		
		MetaAttributeDO attributeMaster1 = new MetaAttributeDO();
		attributeMaster1.setName("label");
		attributeMaster1 = metaAttributeDAO.create(attributeMaster1);
		
		MetaAttributeDO attributeMaster2 = new MetaAttributeDO();
		attributeMaster2.setName("version");
		attributeMaster2 = metaAttributeDAO.create(attributeMaster2);
		
		ObjectAttributeDO oa1 = new ObjectAttributeDO();
		oa1.setAttribute(attributeMaster1);
		oa1.setValue("label-value");
		ObjectAttributeDO oa2 = new ObjectAttributeDO();
		oa2.setAttribute(attributeMaster2);
		oa2.setValue("version-value");
		
		ObjectDO object1 = new ObjectDO();
		object1.setName("10.1.1.11");
		object1.setNamespace("DEFAULT");
		object1.setStatus(status);
		object1.addAttribute(oa1);
		object1.addAttribute(oa2);
		object1.setKlass(class1);
		object1 = objectDAO.create(object1);
		
		ObjectAttributeDO oa3 = new ObjectAttributeDO();
		oa3.setAttribute(attributeMaster1);
		oa3.setValue("label-value");
		ObjectAttributeDO oa4 = new ObjectAttributeDO();
		oa4.setAttribute(attributeMaster2);
		oa4.setValue("version-value");
		
		ObjectDO object2 = new ObjectDO();
		object2.setName("pws");
		object2.setNamespace("DEFAULT");
		object2.setStatus(metaStatusDAO.findById(status.getId()));
		object2.addAttribute(oa3);
		object2.addAttribute(oa4);
		object2.setKlass(metaClassDAO.findById(class2.getId()));
		object2 = objectDAO.create(object2);

		ObjectDO object3 = new ObjectDO();
		object3.setName("10.1.1.12");
		object3.setNamespace("DEFAULT");
		object3.setStatus(status);
		object3.addAttribute(oa1);
		object3.addAttribute(oa2);
		object3.setKlass(class1);
		object3 = objectDAO.create(object3);

		RelationshipAttributeDO ra1 = new RelationshipAttributeDO();
		ra1.setAttribute(attributeMaster1);
		ra1.setValue("label-value");
		RelationshipAttributeDO ra2 = new RelationshipAttributeDO();
		ra2.setAttribute(attributeMaster2);
		ra2.setValue("version-value");
		
		RelationshipDO relationship = new RelationshipDO();
		relationship.setNamespace("DEFAULT");
		relationship.setFromObject(object1);
		relationship.setToObject(object2);
		relationship.setKlass(relationshipClass);
		relationship.setStatus(status);
		relationship.setName(relationship.getFromObject().getName() + "->" 
				+ relationship.getKlass().getName() + "->" 
				+ relationship.getToObject().getName());
		relationship.addAttribute(ra1);
		relationship.addAttribute(ra2);
		relationship = relationshipDAO.create(relationship);
		
		RelationshipDO selected = relationshipDAO
			.findById(relationship.getId());
		print(selected);
		Assert.assertEquals(relationship.getId(), selected.getId());
		
		selected = relationshipDAO.findByNameAndNamespace(
				relationship.getName(), "DEFAULT");
		print(selected);
		Assert.assertEquals(relationship.getId(), selected.getId());
	
		selected = relationshipDAO.findByName(relationship.getName());
		print(selected);
		Assert.assertEquals(relationship.getId(), selected.getId());

		RelationshipAttributeDO attribute = relationshipAttributeDAO
			.findByName(selected.getName(), ra1.getAttribute().getName());
		Assert.assertNotNull(attribute);
		print(attribute);
		
		RelationshipAttributeDO ra3 = new RelationshipAttributeDO();
		ra3.setAttribute(attributeMaster1);
		ra3.setValue("label-value");
		RelationshipAttributeDO ra4 = new RelationshipAttributeDO();
		ra4.setAttribute(attributeMaster2);
		ra4.setValue("version-value");
		
		RelationshipDO relationship2 = new RelationshipDO();
		relationship2.setNamespace("DEFAULT");
		relationship2.setFromObject(object3);
		relationship2.setToObject(object2);
		relationship2.setKlass(relationshipClass);
		relationship2.setStatus(status);
		relationship2.setName(relationship2.getFromObject().getName() + "->" 
				+ relationship2.getKlass().getName() + "->" 
				+ relationship2.getToObject().getName());
		relationship2.addAttribute(ra3);
		relationship2.addAttribute(ra4);
		relationship2 = relationshipDAO.create(relationship2);
		
		List<RelationshipDO> relationships = relationshipDAO
			.findByFromObjectName(relationship.getFromObject().getName());
		Assert.assertNotNull(relationships);
		for (RelationshipDO r : relationships) {
			print(r);
		}
	
		relationships = relationshipDAO.findByToObjectName(relationship
				.getToObject().getName());
		Assert.assertNotNull(relationships);
		for (RelationshipDO r : relationships) {
			print(r);
		}

		relationships = relationshipDAO.findByFromObjectNameAndClass(
				relationship.getFromObject().getName(), 
				relationship.getKlass().getName());
		Assert.assertNotNull(relationships);
		for (RelationshipDO r : relationships) {
			print(r);
		}

		relationships = relationshipDAO.findByToObjectNameAndClass(
				relationship.getToObject().getName(), 
				relationship.getKlass().getName());
		Assert.assertNotNull(relationships);
		for (RelationshipDO r : relationships) {
			print(r);
		}

		relationships = relationshipDAO.findAllByClassAndNamespace(
				relationshipClass.getName(), "DEFAULT", 0, 10);
		Assert.assertTrue(relationships.size() > 0);
		for (RelationshipDO r : relationships) {
			print(r);
		}

		relationships = relationshipDAO.findAllByClass(
				relationshipClass.getName(), 0, 10);
		Assert.assertTrue(relationships.size() > 0);
		for (RelationshipDO r : relationships) {
			print(r);
		}

		List<RelationshipAttributeDO> attributes = relationshipAttributeDAO
			.findAllByRelationName(relationship2.getName());
		Assert.assertNotNull(attributes);
		for (RelationshipAttributeDO ra : attributes) {
			print(ra);
		}
		
		List<ObjectDO> objects = objectDAO.findFromObjectsByClass(
				relationship.getToObject().getName(), 
				relationship.getKlass().getName(), 
				relationship.getFromObject().getKlass().getName());
		Assert.assertTrue(objects.size() > 0);
		for (ObjectDO o : objects) {
			print(o);
		}
		
		objects = objectDAO.findToObjectsByClass(
				relationship.getFromObject().getName(), 
				relationship.getKlass().getName(), 
				relationship.getToObject().getKlass().getName());
		Assert.assertTrue(objects.size() > 0);
		for (ObjectDO o : objects) {
			print(o);
		}
		
		relationshipDAO.delete(relationship);
		Assert.assertNull(relationshipDAO.findById(relationship.getId()));
		
		relationshipDAO.delete(relationship2);
		Assert.assertNull(relationshipDAO.findById(relationship2.getId()));

		objectDAO.delete(object1);
		objectDAO.delete(object2);
		objectDAO.delete(object3);
		metaClassDAO.delete(class1);
		metaClassDAO.delete(class2);
		metaClassDAO.delete(relationshipClass);
		metaAttributeDAO.delete(attributeMaster1);
		metaAttributeDAO.delete(attributeMaster2);
		metaStatusDAO.delete(status);
	}

	private void print(MetaClassDO klass) {
		System.out.printf("%d %d %b %s %s %s\n", klass.getId(), 
				klass.getParent(), klass.isRelationship(), 
				klass.getName(), klass.getCreated(), 
				klass.getUpdated());
		for (MetaAttributeDO attribute : klass.getAttributes()) {
			print(attribute);
		}
	}

	private void print(MetaClassRelationshipDO classRelationship) {
		System.out.printf("%d %s %s %s %s %s\n", classRelationship.getId(),
				classRelationship.getFromKlass().getName(),
				classRelationship.getRelationshipKlass().getName(),
				classRelationship.getToKlass().getName(),
				classRelationship.getCreated(),
				classRelationship.getUpdated());
	}

	private void print(MetaAttributeDO attribute) {
		System.out.printf("%d %s %s %s\n", attribute.getId(), 
				attribute.getName(), attribute.getCreated(), 
				attribute.getUpdated());
	}
	
	private void print(MetaStatusDO status) {
		System.out.printf("%d %s %s %s\n", status.getId(), 
				status.getName(), status.getCreated(), 
				status.getUpdated());
	}
	
	private void print(ObjectAttributeDO attribute) {
		System.out.printf("%d %s %s %s %s %s\n", attribute.getId(), 
				attribute.getObject().getName(), 
				attribute.getAttribute().getName(), 
				attribute.getValue(), attribute.getCreated(), 
				attribute.getUpdated());
	}
	
	private void print(ObjectDO object) {
		System.out.printf("%d %s %s %s %s %s %s\n", object.getId(), 
				object.getName(), object.getKlass().getName(), 
				object.getStatus().getName(),
				object.getNamespace(), object.getCreated(), 
				object.getUpdated());
		for (ObjectAttributeDO attribute : object.getAttributes()) {
			print(attribute);
		}
		
	}
	
	private void print(RelationshipDO relationship) {
		System.out.printf("%d %s %s %s %s %s %s %s\n", relationship.getId(), 
				relationship.getName(), relationship.getNamespace(), 
				relationship.getKlass().getName(), 
				relationship.getStatus().getName(),
				relationship.getFromObject().getName(), 
				relationship.getToObject().getName(), 
				relationship.getCreated(), relationship.getUpdated());
		for (RelationshipAttributeDO attribute : relationship
				.getAttributes()) {
			
			print(attribute);
		}
		
	}

	private void print(RelationshipAttributeDO attribute) {
		System.out.printf("%d %s %s %s %s %s\n", attribute.getId(), 
				attribute.getRelationship().getName(), 
				attribute.getAttribute().getName(), 
				attribute.getValue(), attribute.getCreated(), 
				attribute.getUpdated());
		
	}

}
