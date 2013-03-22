<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.IOException" %>    
<%@ page import="java.util.List" %>    
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
%>
<%
out.println("digraph CollectionViz {");
out.println();
String contextPath = request.getContextPath();
String objectURL = contextPath + "/rs/data/object/";
List<Node> nodes = (List<Node>) pageContext.findAttribute("it");
if (nodes != null) {
	for (Node node : nodes) {
		print(node, out, objectURL);
	}
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