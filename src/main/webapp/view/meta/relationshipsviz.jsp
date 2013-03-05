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
if (types != null) {
	for (MetaClass type : types) {
		if (!type.isRelationshipType()) {
			out.println(type.getName()
					+ "[label = \""
					+ type.getName()
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
				+ "\"];");
	}
}
out.println();
out.println("}");
%>

</script>

<script src="../../../resources/js/viz.js"></script>

<script>

function inspect(s) {
  return "<pre>" + s.replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\"/g, "&quot;") + "</pre>"
}

function src(id) {
  return document.getElementById(id).innerHTML;
}

function render(id, format) {
  var result;
  try {
    result = Viz(src(id), format);
    if (format === "svg")
      return result;
    else
      return inspect(result);
  } catch(e) {
    return inspect(e.toString());
  }
}

document.body.innerHTML += render("MetaTypeRelationships", "svg");

</script>
</body>
</html>