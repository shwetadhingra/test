package com.palm.cloud.services.cmdb.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.meta.MetaClassRelationship;
import com.palm.cloud.services.cmdb.meta.MetaStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/test/resources/applicationContext-test.xml"
})
@Transactional
public class MetaServiceTests {

	@Autowired
	private ICMDBMetaService cmdbMetaService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testMetaStatus() {
		String status1 = "default";
		String status2 = "system";
		cmdbMetaService.addStatus(status1);
		MetaStatus selected1 = cmdbMetaService.getStatus(status1);
		Assert.assertNotNull(selected1);
		cmdbMetaService.addStatus(status2);
		MetaStatus selected2 = cmdbMetaService.getStatus(status2);
		Assert.assertNotNull(selected2);
		List<MetaStatus> statuses = cmdbMetaService.getAllStatuses();
		Assert.assertNotNull(statuses);
		cmdbMetaService.deleteStatus(status1);
		thrown.expect(EmptyResultDataAccessException.class);
		cmdbMetaService.getStatus(status1);
	}

	@Test
	public void testMetaAttribute() {
		String attribute = "label";
		cmdbMetaService.addAttribute(attribute);
		MetaAttribute selected = cmdbMetaService.getAttribute(attribute);
		Assert.assertNotNull(selected);
		cmdbMetaService.deleteAttribute(attribute);
		thrown.expect(EmptyResultDataAccessException.class);
		cmdbMetaService.getAttribute(attribute);
	}
	
	@Test
	public void testMetaClass() {
		MetaClass parent = new MetaClass();
		parent.setName("node");
		parent.setRelationshipType(false);
		parent.addAttribute(new MetaAttribute("label"));
		parent.addAttribute(new MetaAttribute("version"));
		cmdbMetaService.addType(parent);
		MetaClass selected = cmdbMetaService.getType(parent.getName());
		Assert.assertNotNull(selected);
		print(selected);
		MetaClass child = new MetaClass();
		child.setName("node::server");
		child.setRelationshipType(false);
		child.setParent(parent);
		child.addAttribute(new MetaAttribute("status"));
		cmdbMetaService.addType(child);
		selected = cmdbMetaService.getType(child.getName());
		Assert.assertNotNull(selected);
		print(selected);
		selected.setParent(null);
		selected.setRelationshipType(true);
		selected.setAttributes(null);
		cmdbMetaService.updateType(selected);
		MetaClass updated = cmdbMetaService.getType(selected.getName());
		print(updated);
		cmdbMetaService.deleteType("node::server");
		cmdbMetaService.deleteType("node");
	}
	
	@Test
	public void testMetaClassRelationship() {
		String fromType = "node";
		String relationType = "PartOf";
		String toType = "function";
		MetaClass fromClass = new MetaClass();
		fromClass.setName(fromType);
		cmdbMetaService.addType(fromClass);
		MetaClass toClass = new MetaClass();
		toClass.setName(toType);
		cmdbMetaService.addType(toClass);
		MetaClass relationClass = new MetaClass();
		relationClass.setRelationshipType(true);
		relationClass.setName(relationType);
		cmdbMetaService.addType(relationClass);
		cmdbMetaService.addTypeRelationship(fromType, relationType, toType);
		List<MetaClassRelationship> relationships = cmdbMetaService
			.getTypeRelationshipsByFromType(fromType);
		Assert.assertNotNull(relationships);
		for (MetaClassRelationship r : relationships) {
			print(r);
		}
		relationships = cmdbMetaService.getTypeRelationshipsByRelationType(
				relationType);
		Assert.assertNotNull(relationships);
		for (MetaClassRelationship r : relationships) {
			print(r);
		}
		relationships = cmdbMetaService.getTypeRelationshipsByToType(toType);
		Assert.assertNotNull(relationships);
		for (MetaClassRelationship r : relationships) {
			print(r);
		}
		MetaClassRelationship relationship = cmdbMetaService
				.getTypeRelationship(fromType, relationType, toType);
		Assert.assertNotNull(relationship);
		cmdbMetaService.deleteTypeRelationship(fromType, relationType, toType);
		cmdbMetaService.deleteType(fromType);
		cmdbMetaService.deleteType(toType);
		cmdbMetaService.deleteType(relationType);
	}
	
	@Test
	@Rollback(false)
	public void testNoRollback() {
		MetaClass parent = new MetaClass();
		parent.setName("node");
		parent.setRelationshipType(false);
		parent.addAttribute(new MetaAttribute("label"));
		cmdbMetaService.addType(parent);
		cmdbMetaService.deleteType(parent.getName());
		cmdbMetaService.deleteAttribute("label");
	}
	private void print(MetaClassRelationship relationship) {
		System.out.printf("%s %s %s\n", relationship.getFromType().getName(), 
				relationship.getRelationshipType().getName(), 
				relationship.getToType().getName());
	}
	
	private void print(MetaClass type) {
		System.out.printf("%s %b\n", type.getName(), type.isRelationshipType());
		if (type.getAttributes() != null) {
			for (MetaAttribute attribute : type.getAttributes()) {
				System.out.println("\t" + attribute.getName());
			}
		}
		if (type.getParent() != null) {
			print(type.getParent());
		}
	}

}
