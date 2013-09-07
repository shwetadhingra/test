package com.palm.cloud.services.cmdb.collection;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.aspect.Loggable;
import com.palm.cloud.services.cmdb.collection.xml.Edge;
import com.palm.cloud.services.cmdb.collection.xml.Vertex;
import com.palm.cloud.services.cmdb.collection.xml.XMLParser;
import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.service.ICMDBDataService;

@Transactional
@WebService
public class CollectionServiceImpl implements ICollectionService {
	
	protected static Logger log = Logger.getLogger(CollectionServiceImpl.class);
	
	private enum Direction {FORWARD, REVERSE}
	
	@Autowired
	private ICMDBDataService cmdbDataService;

	private static final String DEFINITION_ATTRIBUTE = "definition";
	
	@Loggable
	public List<Node> getCollection(String collectionName, int offset,
			int maxResults) {
		
		List<Node> nodes = null;
		Vertex vertex = this.getDefinitionByName(collectionName);
		nodes = buildNodes(vertex, offset, maxResults);
		return nodes;
	}

	@Loggable
	public Node getCollectionByRoot(String collectionName, String root) {
		Node node = null;
		Vertex vertex = this.getDefinitionByName(collectionName);
		CIObject rootObject = cmdbDataService.getObject(root);
		node = buildNode(vertex, rootObject);
		return node;
	}

	@Loggable
	public List<Node> getCollectionByXML(String definition, int offset, 
			int maxResults) {
		
		List<Node> nodes = null;
		Vertex vertex = this.getDefinitionByXml(definition);
		nodes = buildNodes(vertex, offset, maxResults);
		return nodes;
	}

	@Loggable
	public List<Node> getCollectionByDefinition(Vertex vertex, int offset,
			int maxResults) {

		return buildNodes(vertex, offset, maxResults);
	}

	private Vertex getDefinitionByXml(String definitionXML) {
		Vertex vertex = null;
		try {
			vertex = XMLParser.unmarshall(Vertex.class, definitionXML);
		} catch (Exception e) {
			log.error("Error parsing collection definition xml", e);
		}
		return vertex;
	}
	
	private Vertex getDefinitionByName(String collectionName) {
		Vertex vertex = null;
		try {
			CIAttribute attribute = cmdbDataService.getAttribute(collectionName, 
					DEFINITION_ATTRIBUTE);
			String definitionXML = attribute.getValue();
			vertex = XMLParser.unmarshall(Vertex.class, definitionXML);
		} catch (Exception e) {
			log.error("Error parsing collection definition xml", e);
		}
		return vertex;
	}
	
	private List<Node> buildNodes(Vertex vertex, int offset, int maxResults) {
		List<Node> nodes = new ArrayList<Node>();
		if (vertex != null) {
			List<CIObject> roots = null;
			if (vertex.getConditions() != null) {
				roots = cmdbDataService.getObjectsByConditions(vertex.getType(), 
						offset, maxResults, 
						vertex.getConditions().toArray(new Condition[0]));
			} else {
				roots = cmdbDataService.getObjects(vertex.getType(), 
						offset, maxResults);
			}
			for (CIObject root : roots) {
				nodes.add(buildNode(vertex, root));
			}
		}
		return nodes;
	}
	
	private Node buildNode(Vertex vertex, CIObject root) {
		Node node = new Node();
		node.setObject(root);
		if (vertex.getEdge() != null) {
			for (Edge edge : vertex.getEdge()) {
				buildLinks(node, edge);
			}
		}
		return node;
	}
	
	private void buildLinks(Node node, Edge edge) {
		if (node != null && edge != null && edge.getVertex() != null) {
			boolean isForward = Direction.FORWARD.name().equalsIgnoreCase(
					edge.getDirection());
			List<CIObject> objects = null;
			if (isForward) {
				if (edge.getVertex().getConditions() != null) {
					objects = cmdbDataService.getToObjectsByConditions(
							node.getObject().getName(), edge.getType(), 
							edge.getVertex().getType(), edge.getVertex()
							.getConditions().toArray(new Condition[0]));
				} else {
					objects = cmdbDataService.getToObjectsByType(
							node.getObject().getName(), edge.getType(), 
							edge.getVertex().getType());
				}
			} else {
				if (edge.getVertex().getConditions() != null) {
					objects = cmdbDataService.getFromObjectsByConditions(
							node.getObject().getName(), edge.getType(), 
							edge.getVertex().getType(), edge.getVertex()
							.getConditions().toArray(new Condition[0]));
				} else {
					objects = cmdbDataService.getFromObjectsByType(
							node.getObject().getName(), edge.getType(), 
							edge.getVertex().getType());
				}
			}
			if (objects != null) {
				for (CIObject object : objects) {
					Link link = new Link();
					link.setType(edge.getType());
					link.setForward(isForward);
					link.setNode(buildNode(edge.getVertex(), object));
					node.addLink(link);
				}
			}
		}
	}

	@Loggable
	public void writeCollection(List<Node> collection) {
		if (collection != null) {
			for (Node node : collection) {
				writeNode(node);
			}
		}
	}
	
	private void writeNode(Node node) {
		if (node != null) {
			CIObject parent = node.getObject();
			cmdbDataService.addOrUpdateObject(parent);
			if (node.getLinks() != null) {
				for (Link link : node.getLinks()) {
					CIObject child = link.getNode().getObject();
					cmdbDataService.addOrUpdateObject(child);
					CIRelationship relationship = new CIRelationship();
					relationship.setType(link.getType());
					if (link.isForward()) {
						relationship.setFromObject(parent.getName());
						relationship.setToObject(child.getName());
					} else {
						relationship.setFromObject(child.getName());
						relationship.setToObject(parent.getName());
					}
					relationship.setNamespace(parent.getNamespace());
					relationship.setStatus(parent.getStatus());
					cmdbDataService.addOrUpdateRelation(relationship);
					writeNode(link.getNode());
				}
			}
		}
	}
	
}
