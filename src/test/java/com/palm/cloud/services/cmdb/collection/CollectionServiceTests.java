package com.palm.cloud.services.cmdb.collection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.palm.cloud.services.cmdb.service.ICMDBDataService;
import com.palm.cloud.services.cmdb.service.ICMDBMetaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/test/resources/applicationContext-test.xml"
})
@Transactional
public class CollectionServiceTests {

	@Autowired
	private ICMDBDataService cmdbDataService;

	@Autowired
	private ICMDBMetaService cmdbMetaService;
	
	@Autowired
	private ICollectionService collectionService;
	
	private String status1 = "live";
	
	private String attribute1 = "label";
	
	private String attribute2 = "version";
	
	private String attribute3 = "region";
	
	private String attribute4 = "definition";
	
	private String fromType = "Server";
	
	private String toType = "Function";
	
	private String relationType1 = "PartOf";
	
	private String relationType2 = "MemberOf";
	
	private String collectionType = "Collection";
	
	private String collection1 = "template-1";
	
	private String collection2 = "template-2";
	
	private String collection3 = "template-3";
	
	@Before
	public void setup() {
		cmdbMetaService.addStatus(status1);

		cmdbMetaService.addAttribute(attribute1);
		cmdbMetaService.addAttribute(attribute2);
		cmdbMetaService.addAttribute(attribute3);
		cmdbMetaService.addAttribute(attribute4);

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
		
		MetaClass collectionClass = new MetaClass();
		collectionClass.setName(collectionType);
		collectionClass.addAttribute(new MetaAttribute(attribute4));
		cmdbMetaService.addType(collectionClass);
		
		cmdbMetaService.addTypeRelationship(fromType, relationType1, toType);
		cmdbMetaService.addTypeRelationship(fromType, relationType2, toType);

		CIObject object1 = new CIObject("10.1.1.11", null, fromType, status1);
		object1.addAttribute(new CIAttribute(attribute1, "host-1.palm.com"));
		object1.addAttribute(new CIAttribute(attribute3, "SJC"));

		CIObject object2 = new CIObject("10.1.1.12", null, fromType, status1);
		object2.addAttribute(new CIAttribute(attribute1, "host-2.palm.com"));
		object2.addAttribute(new CIAttribute(attribute3, "SMF"));

		CIObject object3 = new CIObject("10.1.1.13", null, fromType, status1);
		object3.addAttribute(new CIAttribute(attribute1, "host-3.palm.com"));
		object3.addAttribute(new CIAttribute(attribute3, "SJC"));

		CIObject object4 = new CIObject("10.1.1.14", null, fromType, status1);
		object4.addAttribute(new CIAttribute(attribute1, "host-4.palm.com"));
		object4.addAttribute(new CIAttribute(attribute3, "SMF"));

		CIObject object5 = new CIObject("pws", null, toType, status1);
		object5.addAttribute(new CIAttribute(attribute1, "pws"));
		object5.addAttribute(new CIAttribute(attribute2, "1.0"));

		CIObject object6 = new CIObject("acs", null, toType, status1);
		object6.addAttribute(new CIAttribute(attribute1, "acs"));
		object6.addAttribute(new CIAttribute(attribute2, "2.0"));

		cmdbDataService.addObject(object1);
		cmdbDataService.addObject(object2);
		cmdbDataService.addObject(object3);
		cmdbDataService.addObject(object4);
		cmdbDataService.addObject(object5);
		cmdbDataService.addObject(object6);
		
		CIRelationship relation1 = new CIRelationship(null, 
				object1.getName(), object5.getName(), relationType1, status1);
		relation1.addAttribute(new CIRelationshipAttribute(attribute1, 
				"relation-1"));

		CIRelationship relation2 = new CIRelationship(null, 
				object2.getName(), object5.getName(), relationType2, status1);

		CIRelationship relation3 = new CIRelationship(null, 
				object3.getName(), object6.getName(), relationType1, status1);
		relation3.addAttribute(new CIRelationshipAttribute(attribute1, 
				"relation-3"));

		CIRelationship relation4 = new CIRelationship(null, 
				object4.getName(), object6.getName(), relationType2, status1);
		
		cmdbDataService.addRelation(relation1);
		cmdbDataService.addRelation(relation2);
		cmdbDataService.addRelation(relation3);
		cmdbDataService.addRelation(relation4);
		
		String definition = readFile(collection1 + ".xml");
		CIObject collectionObject1 = new CIObject(collection1, null, 
				collectionType, status1);
		collectionObject1.addAttribute(new CIAttribute(attribute4, definition));
		cmdbDataService.addObject(collectionObject1);

		definition = readFile(collection2 + ".xml");
		CIObject collectionObject2 = new CIObject(collection2, null, 
				collectionType, status1);
		collectionObject2.addAttribute(new CIAttribute(attribute4, definition));
		cmdbDataService.addObject(collectionObject2);

		definition = readFile(collection3 + ".xml");
		CIObject collectionObject3 = new CIObject(collection3, null, 
				collectionType, status1);
		collectionObject3.addAttribute(new CIAttribute(attribute4, definition));
		cmdbDataService.addObject(collectionObject3);
	}
	
	@After
	public void tearDown() {
		cmdbDataService.deleteObject("10.1.1.11");
		cmdbDataService.deleteObject("10.1.1.12");
		cmdbDataService.deleteObject("10.1.1.13");
		cmdbDataService.deleteObject("10.1.1.14");
		cmdbDataService.deleteObject("pws");
		cmdbDataService.deleteObject("acs");
		cmdbDataService.deleteObject(collection1);
		cmdbDataService.deleteObject(collection2);
		cmdbDataService.deleteObject(collection3);
		
		cmdbMetaService.deleteTypeRelationship(fromType, relationType1, toType);
		cmdbMetaService.deleteTypeRelationship(fromType, relationType2, toType);
		cmdbMetaService.deleteType(fromType);
		cmdbMetaService.deleteType(relationType1);
		cmdbMetaService.deleteType(relationType2);
		cmdbMetaService.deleteType(toType);
		cmdbMetaService.deleteType(collectionType);
		cmdbMetaService.deleteAttribute(attribute1);
		cmdbMetaService.deleteAttribute(attribute2);
		cmdbMetaService.deleteAttribute(attribute3);
		cmdbMetaService.deleteAttribute(attribute4);
		cmdbMetaService.deleteStatus(status1);
	}
	
	@Test
	public void testGetCollectionByRoot1() {
		Node node = collectionService.getCollectionByRoot(collection1, 
				"10.1.1.11");
		Assert.assertNotNull(node);
		print(node);
		System.out.println("----------------------------------");
	}
	
	@Test
	public void testGetCollection1() {
		List<Node> nodes = collectionService.getCollection(collection1, 0, 10);
		Assert.assertTrue(nodes.size() > 0);
		for (Node node : nodes) {
			print(node);
			System.out.println("----------------------------------");
		}
	}
	
	@Test
	public void testGetCollectionByRoot2() {
		Node node = collectionService.getCollectionByRoot(collection2, 
				"pws");
		Assert.assertNotNull(node);
		print(node);
		System.out.println("----------------------------------");
	}
	
	@Test
	public void testGetCollection2() {
		List<Node> nodes = collectionService.getCollection(collection2, 0, 10);
		Assert.assertTrue(nodes.size() > 0);
		for (Node node : nodes) {
			print(node);
			System.out.println("----------------------------------");
		}
	}
	
	@Test
	public void testGetCollection3() {
		List<Node> nodes = collectionService.getCollection(collection3, 0, 10);
		Assert.assertTrue(nodes.size() > 0);
		for (Node node : nodes) {
			print(node);
			System.out.println("----------------------------------");
		}
	}
	
	private void print(Node node) {
		print(node.getObject());
		if (node.getLinks() != null) {
			for (Link link : node.getLinks()) {
				print(link.getNode());
			}
		}
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
	
	private String readFile(String fileName) {
		String result = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.getClass().getResourceAsStream(fileName)));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			result = builder.toString();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
