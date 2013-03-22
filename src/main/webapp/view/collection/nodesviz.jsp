<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.IOException" %>    
<%@ page import="java.util.AbstractMap"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.List" %>    
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.palm.cloud.services.cmdb.collection.Node"%>
<%@ page import="com.palm.cloud.services.cmdb.collection.Link"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Collection Viz</title>
</head>
<body>
<script type="text/vnd.graphviz" id="CollectionViz">
<%!
private void print(Node node, JspWriter out, String objectURL) 
		throws IOException {
	
	String objectName = node.getObject().getName();
	out.println("\"" + objectName + "\""
			+ "[label = \""
			+ objectName
			+ "\",href=\""
			+ objectURL + objectName + "/"
			+ "\"];");
	if (node.getLinks() != null) {
		for (Link link : node.getLinks()) {
			Node toNode = link.getNode();
			String toObjectName = toNode.getObject().getName();
			print(toNode, out, objectURL);
			if (link.isForward()) {
				out.println("\"" + objectName + "\"" 
						+ " -> "
						+ "\"" + toObjectName + "\""
						+ "[label = \""
						+ link.getType()
						+ "\"];");
			} else {
				out.println("\"" + toObjectName + "\"" 
						+ " -> "
						+ "\"" + objectName + "\""
						+ "[label = \""
						+ link.getType()
						+ "\"];");
				
			}
		}
	}
	
}

private Set<String> getVertices(Node node, Set<String> vertices) {
	if (vertices == null) {
		vertices = new HashSet<String>();
	}
	vertices.add(node.getObject().getName());
	if (node.getLinks() != null) {
		for (Link link : node.getLinks()) {
			vertices = getVertices(link.getNode(), vertices);
		}
	}
	return vertices;
}

private Set<Map.Entry<String, Map.Entry<String, String>>> getEdges(Node node, 
		Set<Map.Entry<String, Map.Entry<String, String>>> edges) {
	
	if (edges == null) {
		edges = new HashSet<Map.Entry<String, Map.Entry<String, String>>>();
	}
	String objectName = node.getObject().getName();
	if (node.getLinks() != null) {
		for (Link link : node.getLinks()) {
			Node toNode = link.getNode();
			String toObjectName = toNode.getObject().getName();
			edges = getEdges(toNode, edges);
			if (link.isForward()) {
				edges.add(new AbstractMap.SimpleImmutableEntry<String, 
						Map.Entry<String, String>>(objectName, 
								new AbstractMap
									.SimpleImmutableEntry<String, String>(
											link.getType(), toObjectName)));
			} else {
				edges.add(new AbstractMap.SimpleImmutableEntry<String, 
						Map.Entry<String, String>>(toObjectName, 
								new AbstractMap
									.SimpleImmutableEntry<String, String>(
											link.getType(), objectName)));
			}
		}
	}
	return edges;
}

private void printVertices(Set<String> vertices, JspWriter out, 
		String objectURL) throws IOException {
	
	for (String vertex : vertices) {
		out.println("\"" + vertex + "\""
				+ "[label = \""
				+ vertex
				+ "\",href=\""
				+ objectURL + vertex + "/"
				+ "\"];");
	}
}

private void printEdges(Set<Map.Entry<String, Map.Entry<String, String>>> edges, 
		JspWriter out) throws IOException {
	
	for (Map.Entry<String, Map.Entry<String, String>> edge : edges) {
		out.println("\"" + edge.getKey() + "\"" 
				+ " -> "
				+ "\"" + edge.getValue().getValue() + "\""
				+ "[label = \""
				+ edge.getValue().getKey()
				+ "\"];");
	}
}
%>
<%
out.println("digraph CollectionViz {");
out.println();
String contextPath = request.getContextPath();
String objectURL = contextPath + "/rs/data/object/";
List<Node> nodes = (List<Node>) pageContext.findAttribute("it");
if (nodes != null) {
	Set<String> vertices = new HashSet<String>();
	Set<Map.Entry<String, Map.Entry<String, String>>> edges 
		= new HashSet<Map.Entry<String, Map.Entry<String, String>>>();
	for (Node node : nodes) {
		vertices = getVertices(node, vertices);
		edges = getEdges(node, edges);
		//print(node, out, objectURL);
	}
	printVertices(vertices, out, objectURL);
	out.println();
	printEdges(edges, out);
}
out.println();
out.println("}");
%>

</script>

<script src="<%=contextPath%>/resources/js/viz.js"></script>

<script src="<%=contextPath%>/resources/js/renderviz.js"></script>

<script>
document.body.innerHTML += renderViz("CollectionViz", "svg");
</script>

</body>
</html>