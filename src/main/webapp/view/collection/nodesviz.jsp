<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.IOException" %>    
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Collection Viz</title>
</head>
<body>
<script type="text/vnd.graphviz" id="CollectionViz">
<%!
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
Map<String, Object> model = (Map<String, Object>) pageContext
	.findAttribute("it");
if (model != null) {
	Set<String> vertices = (Set<String>) model.get("vertices");
	if (vertices != null) {
		printVertices(vertices, out, objectURL);
	}
	out.println();
	Set<Map.Entry<String, Map.Entry<String, String>>> edges 
	= (Set<Map.Entry<String, Map.Entry<String, String>>>) model
		.get("edges");
	if (edges != null) {
		printEdges(edges, out);
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