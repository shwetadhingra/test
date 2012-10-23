package com.palm.cloud.services.cmdb.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.domain.CIRelationshipAttribute;
import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.meta.MetaClass;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/test/resources/applicationContext-test.xml"
})
@Transactional
public class DataServiceTests {

	@Autowired
	private ICMDBDataService cmdbDataService;

	@Autowired
	private ICMDBMetaService cmdbMetaService;
	
	private String status1 = "live";
	
	private String status2 = "archive";
	
	private String attribute1 = "label";
	
	private String attribute2 = "version";
	
	private String attribute3 = "region";
	
	private String fromType = "server";
	
	private String toType = "function";
	
	private String relationType1 = "PartOf";
	
	private String relationType2 = "MemberOf";
	
	@Before
	public void setup() {
		cmdbMetaService.addStatus(status1);
		cmdbMetaService.addStatus(status2);

		cmdbMetaService.addAttribute(attribute1);
		cmdbMetaService.addAttribute(attribute2);
		cmdbMetaService.addAttribute(attribute3);

		MetaClass fromKlass = new MetaClass();
		fromKlass.setName(fromType);
		fromKlass.addAttribute(new MetaAttribute(attribute1));
		fromKlass.addAttribute(new MetaAttribute(attribute3));
		cmdbMetaService.addType(fromKlass);
		
		MetaClass relationKlass1 = new MetaClass();
		relationKlass1.setName(relationType1);
		relationKlass1.setRelationshipType(true);
		relationKlass1.addAttribute(new MetaAttribute(attribute1));
		relationKlass1.addAttribute(new MetaAttribute(attribute2));
		cmdbMetaService.addType(relationKlass1);
		
		MetaClass relationKlass2 = new MetaClass();
		relationKlass2.setName(relationType2);
		relationKlass2.setRelationshipType(true);
		cmdbMetaService.addType(relationKlass2);
		
		MetaClass toKlass = new MetaClass();
		toKlass.setName(toType);
		toKlass.addAttribute(new MetaAttribute(attribute1));
		toKlass.addAttribute(new MetaAttribute(attribute2));
		cmdbMetaService.addType(toKlass);
		
		cmdbMetaService.addTypeRelationship(fromType, relationType1, toType);
		cmdbMetaService.addTypeRelationship(fromType, relationType2, toType);
	}
	
	@After
	public void tearDown() {
		cmdbMetaService.deleteTypeRelationship(fromType, relationType1, toType);
		cmdbMetaService.deleteTypeRelationship(fromType, relationType2, toType);
		cmdbMetaService.deleteType(fromType);
		cmdbMetaService.deleteType(relationType1);
		cmdbMetaService.deleteType(relationType2);
		cmdbMetaService.deleteType(toType);
		cmdbMetaService.deleteAttribute(attribute1);
		cmdbMetaService.deleteAttribute(attribute2);
		cmdbMetaService.deleteAttribute(attribute3);
		cmdbMetaService.deleteStatus(status1);
		cmdbMetaService.deleteStatus(status2);
	}
	
	@Test
	public void testCIObject() {
		CIObject object = new CIObject("10.1.1.11", null, fromType, status1);
		object.addAttribute(new CIAttribute(attribute1, "myhost.palm.com"));
		object.addAttribute(new CIAttribute(attribute2, "1.0"));
		cmdbDataService.addObject(object);
		CIObject selected = cmdbDataService.getObject(object.getName());
		Assert.assertNotNull(selected);
		print(selected);
		selected = new CIObject("10.1.1.11", null, fromType, status2);
		cmdbDataService.updateObject(selected);
		CIObject updated = cmdbDataService.getObject(selected.getName());
		Assert.assertNotNull(updated);
		print(updated);
		selected = new CIObject("10.1.1.11", null, fromType, status2);
		selected.addAttribute(new CIAttribute(attribute1, "ourhost.palm.com"));
		cmdbDataService.updateObject(selected);
		updated = cmdbDataService.getObject(selected.getName());
		Assert.assertNotNull(updated);
		print(updated);
		selected = new CIObject("10.1.1.11", null, fromType, status2);
		selected.addAttribute(new CIAttribute(attribute1, "newhost.palm.com"));
		selected.addAttribute(new CIAttribute(attribute3, "SJC"));
		selected.addAttribute(new CIAttribute("junk", "junk"));
		cmdbDataService.updateObject(selected);
		updated = cmdbDataService.getObject(selected.getName());
		Assert.assertNotNull(updated);
		print(updated);
		selected = new CIObject("10.1.1.11", null, fromType, status2);
		selected.addAttribute(new CIAttribute(attribute1, "newhost.palm.com"));
		cmdbDataService.updateObject(selected);
		updated = cmdbDataService.getObject(selected.getName());
		Assert.assertNotNull(updated);
		print(updated);
		List<CIObject> objects = cmdbDataService.getObjects(fromType, 0, 10);
		Assert.assertNotNull(objects);
		for (CIObject domain : objects) {
			print(domain);
		}
		cmdbDataService.deleteObject(object.getName());
		updated.setNamespace("system");
		cmdbDataService.addObject(updated);
		selected = cmdbDataService.getObjectNS(updated.getName(), 
				updated.getNamespace());
		Assert.assertNotNull(selected);
		print(selected);
		objects = cmdbDataService.getObjectsNS(fromType, 
				selected.getNamespace(), 0, 10);
		Assert.assertNotNull(objects);
		for (CIObject domain : objects) {
			print(domain);
		}
		cmdbDataService.deleteObjectNS(selected.getName(), selected
				.getNamespace());
	}
	
	@Test
	public void testCIAttribute() {
		CIObject object = new CIObject("10.1.1.11", null, fromType, status1);
		cmdbDataService.addObject(object);
		CIObject selected = cmdbDataService.getObject(object.getName());
		print(selected);
		cmdbDataService.addAttribute(selected.getName(), attribute1, 
				"myhost.palm.com");
		selected = cmdbDataService.getObject(object.getName());
		print(selected);
		cmdbDataService.addAttributeNS(object.getName(), object.getNamespace(), 
				attribute2, "1.0");
		cmdbDataService.addAttributeNS(object.getName(), object.getNamespace(), 
				attribute3, "SJC");
		selected = cmdbDataService.getObject(object.getName());
		print(selected);
		List<CIAttribute> attributes = cmdbDataService.getAttributes(
				object.getName());
		Assert.assertNotNull(attributes);
		for (CIAttribute attribute : attributes) {
			print(attribute);
		}
		cmdbDataService.updateAttribute(object.getName(), attribute3, "SMF");
		selected = cmdbDataService.getObject(object.getName());
		print(selected);
		attributes = cmdbDataService.getAttributesNS(object.getName(), 
				object.getNamespace());
		Assert.assertNotNull(attributes);
		for (CIAttribute attribute : attributes) {
			print(attribute);
		}
		print(cmdbDataService.getAttribute(object.getName(), attribute3));
		cmdbDataService.updateAttributeNS(object.getName(), 
				object.getNamespace(), attribute3, "LAX");
		selected = cmdbDataService.getObject(object.getName());
		print(selected);
		print(cmdbDataService.getAttributeNS(object.getName(), 
				object.getNamespace(), attribute3));
		cmdbDataService.deleteAttribute(object.getName(), attribute1);
		selected = cmdbDataService.getObject(object.getName());
		print(selected);
		cmdbDataService.deleteAttributeNS(object.getName(), 
				object.getNamespace(), attribute3);
		selected = cmdbDataService.getObject(object.getName());
		print(selected);
		cmdbDataService.deleteObject(object.getName());
	}
	
	@Test
	public void testCIRelationAndAttribute() {
		CIObject fromObject1 = new CIObject("10.1.1.11", null, fromType, 
				status1);
		cmdbDataService.addObject(fromObject1);
		CIObject fromObject2 = new CIObject("10.1.1.12", "system", fromType, 
				status1);
		cmdbDataService.addObject(fromObject2);
		CIObject toObject1 = new CIObject("pws", null, toType, status1);
		cmdbDataService.addObject(toObject1);
		CIObject toObject2 = new CIObject("pws", "system", toType, status1);
		cmdbDataService.addObject(toObject2);
		CIRelationship relation1 = new CIRelationship(null, 
				fromObject1.getName(), toObject1.getName(), relationType1, 
				status1);
		relation1.addAttribute(new CIRelationshipAttribute(attribute1, 
				"relation-1"));
		cmdbDataService.addRelation(relation1);
		CIRelationship selected1 = cmdbDataService.getRelation(
				getDerivedName(relation1));
		Assert.assertNotNull(selected1);
		print(selected1);
		CIRelationship relation2 = new CIRelationship("system", 
				fromObject2.getName(), toObject2.getName(), relationType1, 
				status1);
		cmdbDataService.addRelation(relation2);
		CIRelationship selected2 = cmdbDataService.getRelationNS(
				getDerivedName(relation2), relation2.getNamespace());
		Assert.assertNotNull(selected2);
		print(selected2);
		List<CIRelationship> relationships = cmdbDataService.getFromRelations(
				fromObject1.getName(), selected1.getType());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getFromRelationsNS(
				fromObject2.getName(), selected2.getNamespace(), 
				selected2.getType());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getToRelations(toObject1.getName(), 
				selected1.getType());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getToRelationsNS(toObject2.getName(), 
				selected2.getNamespace(), selected2.getType());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getAllFromRelations(fromObject1
				.getName());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getAllFromRelationsNS(fromObject2
				.getName(), fromObject2.getNamespace());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getAllToRelations(toObject1
				.getName());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getAllToRelationsNS(toObject2
				.getName(), toObject2.getNamespace());
		Assert.assertNotNull(relationships);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		cmdbDataService.addRelationAttribute(getDerivedName(relation1), 
				attribute2, "1.0");
		cmdbDataService.addRelationAttribute(getDerivedName(relation1), 
				"junk", "junk");
		selected1 = cmdbDataService.getRelation(getDerivedName(relation1));
		Assert.assertNotNull(selected1);
		print(selected1);
		cmdbDataService.addRelationAttributeNS(getDerivedName(relation2), 
				relation2.getNamespace(), attribute1, "relation-2");
		cmdbDataService.addRelationAttributeNS(getDerivedName(relation2), 
				relation2.getNamespace(), attribute2, "1.0");
		cmdbDataService.addRelationAttributeNS(getDerivedName(relation2), 
				relation2.getNamespace(), "junk", "junk");
		selected2 = cmdbDataService.getRelationNS(getDerivedName(relation2), 
				relation2.getNamespace());
		Assert.assertNotNull(selected2);
		print(selected2);
		cmdbDataService.updateRelationAttribute(getDerivedName(relation1), 
				attribute2, "2.0");
		CIRelationshipAttribute relAttribute = cmdbDataService
			.getRelationAttribute(getDerivedName(relation1), attribute2);
		Assert.assertEquals("2.0", relAttribute.getValue());
		print(relAttribute);
		selected1 = cmdbDataService.getRelation(getDerivedName(relation1));
		Assert.assertNotNull(selected1);
		print(selected1);
		cmdbDataService.updateRelationAttributeNS(getDerivedName(relation2), 
				relation2.getNamespace(), attribute2, "3.0");
		relAttribute = cmdbDataService.getRelationAttributeNS(getDerivedName(
				relation2), relation2.getNamespace(), attribute2);
		Assert.assertEquals("3.0", relAttribute.getValue());
		print(relAttribute);
		selected2 = cmdbDataService.getRelationNS(getDerivedName(relation2), 
				relation2.getNamespace());
		Assert.assertNotNull(selected2);
		print(selected2);
		cmdbDataService.deleteRelationAttribute(getDerivedName(relation1), 
				attribute2);
		cmdbDataService.deleteRelationAttributeNS(getDerivedName(relation2), 
				relation2.getNamespace(), attribute2);
		List<CIRelationshipAttribute> relAttributes = cmdbDataService
			.getRelationAttributes(getDerivedName(relation1));
		Assert.assertNotNull(relAttributes);
		for (CIRelationshipAttribute ra : relAttributes) {
			print(ra);
		}
		relAttributes = cmdbDataService.getRelationAttributesNS(
				getDerivedName(relation2), relation2.getNamespace());
		Assert.assertNotNull(relAttributes);
		for (CIRelationshipAttribute ra : relAttributes) {
			print(ra);
		}
		selected1 = cmdbDataService.getRelation(getDerivedName(relation1));
		Assert.assertNotNull(selected1);
		print(selected1);
		selected2 = cmdbDataService.getRelationNS(getDerivedName(relation2), 
				relation2.getNamespace());
		Assert.assertNotNull(selected2);
		print(selected2);
		relationships = cmdbDataService.getRelations(relationType1, 0, 10);
		Assert.assertTrue(relationships.size() > 0);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		relationships = cmdbDataService.getRelationsNS(relationType1, 
				relation2.getNamespace(), 0, 10);
		Assert.assertTrue(relationships.size() > 0);
		for (CIRelationship relation : relationships) {
			print(relation);
		}
		List<CIObject> objects = cmdbDataService.getFromObjectsByType(
				toObject1.getName(), relationType1, fromObject1.getType());
		Assert.assertTrue(objects.size() > 0);
		for (CIObject o : objects) {
			print(o);
		}
		objects = cmdbDataService.getFromObjectsByTypeNS(
				toObject2.getName(), toObject2.getNamespace(), 
				relationType1, fromObject2.getType());
		Assert.assertTrue(objects.size() > 0);
		for (CIObject o : objects) {
			print(o);
		}
		objects = cmdbDataService.getToObjectsByType(
				fromObject1.getName(), relationType1, toObject1.getType());
		Assert.assertTrue(objects.size() > 0);
		for (CIObject o : objects) {
			print(o);
		}
		objects = cmdbDataService.getToObjectsByTypeNS(
				fromObject2.getName(), fromObject2.getNamespace(), 
				relationType1, toObject2.getType());
		Assert.assertTrue(objects.size() > 0);
		for (CIObject o : objects) {
			print(o);
		}
		cmdbDataService.deleteRelation(getDerivedName(relation1));
		cmdbDataService.deleteRelationNS(getDerivedName(relation2), 
				relation2.getNamespace());
		cmdbDataService.deleteObject(fromObject1.getName());
		cmdbDataService.deleteObjectNS(fromObject2.getName(), 
				fromObject2.getNamespace());
		cmdbDataService.deleteObject(toObject1.getName());
		cmdbDataService.deleteObjectNS(toObject2.getName(), 
				toObject2.getNamespace());
	}
	
	private String getDerivedName(CIRelationship relation) {
		StringBuilder derivedName = new StringBuilder();
		derivedName.append(relation.getFromObject())
			.append("~")
			.append(relation.getType())
			.append("~")
			.append(relation.getToObject());
		return derivedName.toString();
	}
	
	private void print(CIObject object) {
		System.out.printf("%s %s %s %s\n", object.getName(), 
				object.getNamespace(), object.getStatus(), object.getType());
		if (object.getAttributes() != null) {
			for (CIAttribute attribute : object.getAttributes()) {
				print(attribute);
			}
		}
	}
	
	private void print(CIAttribute attribute) {
		System.out.printf("\t%s:%s\n", attribute.getName(), 
				attribute.getValue());
	}
	
	private void print(CIRelationship relationship) {
		System.out.printf("%s %s %s %s %s %s\n", relationship.getName(), 
				relationship.getNamespace(), relationship.getStatus(), 
				relationship.getType(), relationship.getFromObject(), 
				relationship.getToObject());
		if (relationship.getAttributes() != null) {
			for (CIRelationshipAttribute attribute : relationship
					.getAttributes()) {
				
				print(attribute);
			}
		}
	}

	private void print(CIRelationshipAttribute attribute) {
		System.out.printf("\t%s:%s\n", attribute.getName(), 
				attribute.getValue());
	}

}
