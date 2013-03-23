<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="com.palm.cloud.services.cmdb.domain.CIRelationship"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Relationships</title>
</head>
<body>
<script type="text/vnd.graphviz" id="Relationships">

<%
String contextPath = request.getContextPath();
String objectURL = contextPath + "/rs/data/object/";
out.println("digraph Relationships {");
out.println();
List<CIRelationship> model = (List<CIRelationship>) pageContext.findAttribute("it");
if (model != null) {
	Set<String> objects = new HashSet<String>();
	for (CIRelationship relation : model) {
		objects.add(relation.getFromObject());
		objects.add(relation.getToObject());
	}
	for (String object : objects) {
		out.println("\"" + object + "\""
				+ "[label = \""
				+ object
				+ "\",href=\""
				+ objectURL + object + "/"
				+ "\"];");
	}
	for (CIRelationship relation : model) {
		out.println("\"" + relation.getFromObject() + "\""
				+ " -> "
				+ "\"" + relation.getToObject() + "\""
				+ "[label = \""
				+ relation.getType()
				+ "\"];");
	}
}
out.println();
out.println("}");
%>

</script>

<script src="<%=contextPath%>/resources/js/viz.js"></script>

<script src="<%=contextPath%>/resources/js/renderviz.js"></script>

<script>
document.body.innerHTML += renderViz("Relationships", "svg");
</script>

</body>
</html>