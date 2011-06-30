package com.palm.cloud.services.cmdb.collection;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.collection.xml.Edge;
import com.palm.cloud.services.cmdb.collection.xml.Vertex;
import com.palm.cloud.services.cmdb.collection.xml.XMLParser;
import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.service.ICMDBDataService;

@Transactional
@WebService
public class CollectionServiceImpl implements ICollectionService {
	
	protected static Logger log = Logger.getLogger(CollectionServiceImpl.class);
	
	private enum Direction {FORWARD, REVERSE}
	
	@Autowired
	private ICMDBDataService cmdbDataService;

	private static final String DEFINITION_ATTRIBUTE = "definition";
	
	public List<Node> getCollection(String collectionName, int offset,
			int maxResults) {
		
		List<Node> nodes = null;
		Vertex vertex = this.getDefinition(collectionName);
		nodes = buildNodes(vertex, offset, maxResults);
		return nodes;
	}

	private Vertex getDefinition(String collectionName) {
		Vertex vertex = null;
		try {
			CIAttribute attribute = cmdbDataService.getAttribute(collectionName, 
					DEFINITION_ATTRIBUTE);
			String definitionXML = attribute.getValue();
			vertex = XMLParser.unmarshall(Vertex.class, definitionXML);
		} catch (JAXBException e) {
			log.error("Error parsing collection definition xml", e);
		}
		return vertex;
	}
	
	private List<Node> buildNodes(Vertex vertex, int offset, int maxResults) {
		List<Node> nodes = new ArrayList<Node>();
		if (vertex != null) {
			List<CIObject> roots = null;
			if (vertex.getFilterConditions() != null) {
				roots = cmdbDataService.getObjectsByConditions(vertex.getType(), 
						offset, maxResults, 
						vertex.getFilterConditions().toArray(new Condition[0]));
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
		boolean isForward = Direction.FORWARD.name().equalsIgnoreCase(
				edge.getDirection());
		List<CIObject> objects = null;
		if (isForward) {
			objects = cmdbDataService.getToObjectsByType(
					node.getObject().getName(), edge.getType(), 
					edge.getVertex().getType());
		} else {
			objects = cmdbDataService.getFromObjectsByType(
					node.getObject().getName(), edge.getType(), 
					edge.getVertex().getType());
		}
		if (objects != null) {
			for (CIObject object : objects) {
				Link link = new Link();
				link.setNode(buildNode(edge.getVertex(), object));
				node.addLink(link);
			}
		}
	}
	
	public Node getCollectionByRoot(String collectionName, String root) {
		Node node = null;
		Vertex vertex = this.getDefinition(collectionName);
		CIObject rootObject = cmdbDataService.getObject(root);
		node = buildNode(vertex, rootObject);
		return node;
	}

}
