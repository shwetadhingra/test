<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>    
<%@ page import="java.util.Map" %>    
<%@ page import="com.palm.cloud.services.cmdb.meta.MetaClass" %>
<%@ page import="com.palm.cloud.services.cmdb.meta.MetaClassRelationship" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Meta Type Relationships</title>
</head>
<body>
<script type="text/vnd.graphviz" id="MetaTypeRelationships">

<%
out.println("digraph MetaTypeRelationships {");
out.println();
Map<String, Object> model = (Map<String, Object>) pageContext.findAttribute("it");
List<MetaClass> types = (List<MetaClass>) model.get("types");
String contextPath = request.getContextPath();
String typeURL = contextPath + "/rs/meta/types/";
if (types != null) {
	for (MetaClass type : types) {
		if (!type.isRelationshipType()) {
			out.println(type.getName()
					+ "[label = \""
					+ type.getName()
					+ "\",href=\""
					+ typeURL + type.getName() + "/"
					+ "\"];");
		}
	}
}
out.println();
List<MetaClassRelationship> relationships = (List<MetaClassRelationship>) model
	.get("relationships");
if (relationships != null) {
	for (MetaClassRelationship relationship : relationships) {
		out.println(relationship.getFromType().getName() 
				+ " -> "
				+ relationship.getToType().getName()
				+ "[label = \""
				+ relationship.getRelationshipType().getName()
				+ "\",href=\""
				+ typeURL + relationship.getRelationshipType().getName() + "/"
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
document.body.innerHTML += renderViz("MetaTypeRelationships", "svg");
</script>

</body>
</html>