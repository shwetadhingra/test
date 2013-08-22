var rootUrl = "../../rs/";
var typeArray;
var relationArray;
var unionArray;
var objectArray;
var attributeArray;
var SPACE = /^[ ]*$/;
var ALPHA = /^[A-Za-z]*$/;
var ALPHA_SYM = /^[A-Za-z-_]*$/;
var ALPHANUM_SYM = /^[A-Za-z0-9-_.:]*$/;

$(document).ready(function() {
	$("#tabs").tabs();

	$(".selector").tabs();

	metaFunction(true);

	$("#typeInput").keyup(function() {
		if(!(ALPHA.test($(this).val()))){
			$("#typeInput_span").html("[A-Z] [a-z] only");
		}else {
			$("#typeInput_span").contents().remove();
		}
	});

	$("#typeInput").blur(function() {
		$("#typeInput_span").contents().remove();
	});

	$("#addMore").click(function() {
		addRow("#typeAttrTable");
	});

	$("#typeAttrTable").on("click", "button", function() {
		$(this).closest("tr").remove();
	});

	$("#typeAttrTable").on("keyup", "input:text", function() {
		if(!(ALPHA_SYM.test($(this).val()))) {
			$(this).closest("tr").find(".attr")
			.html("[A-Z] [a-z] '-' '_'only");
		} else {
			$(this).closest("tr").find(".attr").contents().remove();
		}
	});


	$("#addType").click(function() {
		if(validateMetaTypeInput() && validateMetaAttrInputs()) {
			addType();
		} else {
			$("#addtp").html("<b>Check #Name Format #Attribute Format</b>");
		}
	});

	$("#reset").click(function() {
		resetMetaType();
	});



	$("input:text").focus(function() {
		$("#addtp").contents().remove();
		$("#addrp").contents().remove();
		$("#addop").contents().remove();
		$("#relp").contents().remove();
	});

	$("select").change(function() {
		$("#relp").contents().remove();
		$("#updeltp").contents().remove();
		$("#updelop").contents().remove();
		$("#updeldrp").contents().remove();
	});

	$("#addRType").click(function() {
		if($("#fromSelect").val() != 'Select Type' && 
				$("#toSelect").val() != 'Select Type' && 
				$("relationshipSelect").val() != 'Select Type') {
			addRelationshipType();
		} else {
			$("#addrp").html("<b>Select Type and Relationship</b>");
		}
	});

	$("#refreshRType").click(function() {
		metaFunction(true);
	});


///////////////////////////////////////////////////////////////////////////////

	$("#updateTypeSelect").change(function() {
		var type = $("#updateTypeSelect").val();
		emptyTable("updateTypeTable", 2);
		attributeArray.clear();
		if(type != 'Select Type') {
			getAttributes(type, "updateTypeTable");
		}
	});

	$("#updateTypeTable").on("click", "input:button", function() {

		if($(this).attr("id") != 'updateAddAttrButton') {
			var attr = $(this).closest("tr").find("input").val();
			if(attr != '') {
				deleteAttr(attr, this);
			} else {
				$(this).closest("tr").remove();
			}
		} else {
			$("#updeltp").contents().remove();
			addRow("#updateTypeTable");
		}
	});

	$("#updateTypeTable").on("click", "button", function() {
		$(this).closest("tr").remove();
	});

	$("#updateTypeTable").on("keyup", "input:text", function() {
		if(!(ALPHA_SYM.test($(this).val()))) {
			$(this).closest("tr").find(".attr").html("Only [A-Z] [a-z] '-' '_'");
		} else {
			$(this).closest("tr").find(".attr").contents().remove();
			$("#updeltp").contents().remove();
		}
	});


	$("#updateType").click(function() {
		if($("#updateTypeSelect").val() != 'Select Type'
			&& $("#updateTypeTable tr").length > 3 && validateUpdateMetaAttr()) {
			updateType();
		} else {
			$("#updeltp").html("<b>#Select Type #Check Attribute Format</b>");
		}
	});


	$("#deleteType").click(function() {
		if($("#updateTypeSelect").val() != 'Select Type') {
			deleteType();
		} else {
			$("#updeltp").html("<b>Select Type to Delete</b>");
		}
	});

	$("#updateRefresh").click(function() {
		metaFunction(true);
	});

///////////////////////////////////////////////////////////////////////////////

	$("#deleteRelation").click(function() {
		var relationship = $("#updateRelationSelect").val();
		if(relationship != 'Select Type Relationship') {
			deletetypeRelationship(relationship);
		} else {
			$("#updelrp").html("<b>Select a Relationship to Delete</b>");
		}
	});

	$("#updateRelationRefresh").click(function() {
		metaFunction(true);
	});

///////////////////////////////////////////////////////////////////////////////

	$("#objectInput").focus(function() {
		$("#objectInput").val('');
		$("#objectInput").css("color", "#000000");
	});


	$("#objectInput").blur(function() {
		if($("#objectInput").val() == "") {
			$("#objectInput").val("Search Type");
			$("#objectInput").css("color", "#999999");
		}
	});

	$("#objectSelect").change(function() {
		getAttributesPrep();
	});

	

	$("#name").keyup(function() {
		if(!(ALPHANUM_SYM.test($(this).val()))) {
			$("#object_span").html("Only [A-Z] [a-z] [0-9] '-' '_' '.' ':'");
		} else {
			$("#object_span").contents().remove();
		}
	});

	$("#addObject").click(function() {
		var errorMessage = displayAddError();
		if(errorMessage == 'Enter') {
			addObject();
		}
	});

	$("#resetObject").click(function() {
		dataFunction(true);
	});

///////////////////////////////////////////////////////////////////////////////

	$("#updateObjectSelect").change(function() {
		var type = $("#updateObjectSelect").val();
		$("#updateObjectMetaLabel").val('');
		$("#updateObjectInput").val('');
		emptyTable("updateObjectTable", 10);
		objectArray = new Array();
		if(type != 'Select Type') {
			$("#updateObjectInput").autocomplete({
				source: function(request, response) {
					jQuery.ajax({
						type: 'GET',
						url: rootUrl + 'data/objects/type/' + $("#updateObjectSelect").val() + '\/' + request.term + "/?maxResults=10",
						dataType: 'json',
						success: function(json) {
							response(jQuery.map(json, function(item) {
								return {
									label: item.name,
									value: item.name
								};
							}));
						},
						error: function(a, b, c) {
							debugger;
						}
					});
				},
				select: function(event, ui) {
					if (ui.item) {
						$(this).val(ui.item.value);
					}
					$("#fetchObject").trigger("click");
				},
				minLength: 1
			});
		}
	});
	
	$("#fetchObject").click(function() {
		$("#updelop").contents().remove();
		var object = $("#updateObjectInput").val();
		if(object != '') {
			emptyTable("updateObjectTable", 10);
			fetchObject(object);
		}else {
			$("#updelop").html("<b>Enter Object Name</b>");
		}
	});

	$("#updateObject").click(function() {
		$("#updelop").contents().remove();
		if($("#updateObjectSelect").val() != 'Select Type'
			&& $("#updateObjectInput").val() != '') {
			updateObject();
		} else {
			$("#updelop").html("<b>Select Type and Enter Object Name</b>");
		}

	});

	$("#deleteObject").click(function() {
		$("#updelop").contents().remove();
		if($("#updateObjectSelect").val() != 'Select Type'
			&& $("#updateObjectInput").val() != '') {
			deleteObject();
		} else {
			$("#updelop").html("<b>Select Type and Enter Object Name</b>");
		}
	});

	$("#refreshUpdateObject").click(function() {
		dataFunction(true);
	});

///////////////////////////////////////////////////////////////////////////////

	$("#fromRSelect").change(function() {
		$("#relateOFromInput").val('');
		objectArray = new Array();
		if($("#fromRSelect").val() != 'Select Type') {
			$("#relateOFromInput").autocomplete({
				source: function(request, response) {
					jQuery.ajax({
						type: 'GET',
						url: rootUrl + 'data/objects/type/' + $("#fromRSelect").val() + '\/' + request.term + "/?maxResults=10",
						dataType: 'json',
						success: function(json) {
							response(jQuery.map(json, function(item) {
								return {
									label: item.name,
									value: item.name
								};
							}));
						},
						error: function(a, b, c) {
							debugger;
						}
					});
				},
				select: function(event, ui) {
					if (ui.item) {
						$(this).val(ui.item.value);
					}
				},
				minLength: 1
			});
		}
	});

	$("#toRSelect").change(function() {
		$("#relateOToInput").val('');
		objectArray = new Array();
		if($("#toRSelect").val() != 'Select Type') {
			$("#relateOToInput").autocomplete({
				source: function(request, response) {
					jQuery.ajax({
						type: 'GET',
						url: rootUrl + 'data/objects/type/' + $("#toRSelect").val() + '\/' + request.term + "/?maxResults=10",
						dataType: 'json',
						success: function(json) {
							response(jQuery.map(json, function(item) {
								return {
									label: item.name,
									value: item.name
								};
							}));
						},
						error: function(a, b, c) {
							debugger;
						}
					});
				},
				select: function(event, ui) {
					if (ui.item) {
						$(this).val(ui.item.value);
					}
				},
				minLength: 1
			});
		}
	});

	$("#relationShipRSelect").change(function() {
		$("#relateStatus").val('live');
		var object = $("#relationShipRSelect").val();
		emptyTable("relateObjectTable", 8);
		if(object != 'Select Type') {
			getAttributes(object, "relateObjectTable");
		}
	});

	$("#relate").click(function() {
		var errorMessage = displayRelateErrors();
		if(errorMessage == 'Enter') {
			var tableName = "relateObjectTable";
			var from = $("#relateOFromInput").val();
			var to = $("#relateOToInput").val();
			var relation = $("#relationShipRSelect").val();
			var status = $("#relateStatus").val();
			var tag = "#relp";
			addRelation(tableName, from, to, relation, status, tag, 'POST');
		}
	});

	$("#resetRO").click(function() {
		resetAddRelationships();
	});

///////////////////////////////////////////////////////////////////////////////

	$("#updateRelationFromSelect").change(function() {
		$("#updateRelationFromInput").val('');
		emptyTable("updateRelateObjectTable", 8);
		objectArray = new Array();
		if($("#updateRelationFromSelect").val() != 'Select Type') {
			$("#updateRelationFromInput").autocomplete({
				source: function(request, response) {
					jQuery.ajax({
						type: 'GET',
						url: rootUrl + 'data/objects/type/' + $("#updateRelationFromSelect").val() + '\/' + request.term + "/?maxResults=10",
						dataType: 'json',
						success: function(json) {
							response(jQuery.map(json, function(item) {
								return {
									label: item.name,
									value: item.name
								};
							}));
						},
						error: function(a, b, c) {
							debugger;
						}
					});
				},
				select: function(event, ui) {
					if (ui.item) {
						$(this).val(ui.item.value);
					}
				},
				minLength: 1
			});
		}
	});

	$("#updateRelationToSelect").change(function() {
		$("#updateRelationToInput").val('');
		emptyTable("updateRelateObjectTable", 8);
		objectArray = new Array();
		if($("#updateRelationToSelect").val() != 'Select Type') {
			$("#updateRelationToInput").autocomplete({
				source: function(request, response) {
					jQuery.ajax({
						type: 'GET',
						url: rootUrl + 'data/objects/type/' + $("#updateRelationToSelect").val() + '\/' + request.term + "/?maxResults=10",
						dataType: 'json',
						success: function(json) {
							response(jQuery.map(json, function(item) {
								return {
									label: item.name,
									value: item.name
								};
							}));
						},
						error: function(a, b, c) {
							debugger;
						}
					});
				},
				select: function(event, ui) {
					if (ui.item) {
						$(this).val(ui.item.value);
					}
				},
				minLength: 1
			});
		}
	});

	$("#updateRelationRelationshipSelect").change(function() {
		emptyTable("updateRelateObjectTable", 8);
		$("#updateRelationStatus").val('');
		$("#updateRelationFetch").focus();

	});

	$("#updateRelationFetch").click(function() {
		var fromSelect = $("#updateRelationFromSelect").val();
		var fromInput = $("#updateRelationFromInput").val();
		var toSelect = $("#updateRelationToSelect").val();
		var toInput = $("#updateRelationToInput").val();
		var relSelect = $("#updateRelationRelationshipSelect").val();
		$("#updeldrp").contents().remove();
		if(fromSelect != 'Select Type' && fromInput != '' &&
				toSelect != 'Select Type' && toInput != '' &&
				relSelect != 'Select Type') {
			emptyTable("updateRelateObjectTable", 8);
			fetchRelation(fromInput, relSelect, toInput);
		} else {
			$("#updeldrp").html("<b>Enter From, To Objects and Relationship</b>");
		}
	});

	$("#updateRelationUpdate").click(function() {
		var fromSelect = $("#updateRelationFromSelect").val();
		var fromInput = $("#updateRelationFromInput").val();
		var toSelect = $("#updateRelationToSelect").val();
		var toInput = $("#updateRelationToInput").val();
		var relSelect = $("#updateRelationRelationshipSelect").val();

		if(fromSelect != 'Select Type' && fromInput != '' &&
				toSelect != 'Select Type' && toInput != '' &&
				relSelect != 'Select Type') {
			var tableName = "updateRelateObjectTable";
			var from = fromInput;
			var to = toInput;
			var relation = relSelect;
			var status = $("#updateRelationStatus").val();
			var tag = "#updeldrp";
			addRelation(tableName, from, to, relation, status, tag, 'PUT');
		} else {
			$("#updeldrp").html("<b>Enter From, To Objects and Relationship</b>");
		}
	}); 


	$("#updateRelationDelete").click(function() {
		var fromSelect = $("#updateRelationFromSelect").val();
		var fromInput = $("#updateRelationFromInput").val();
		var toSelect = $("#updateRelationToSelect").val();
		var toInput = $("#updateRelationToInput").val();
		var relSelect = $("#updateRelationRelationshipSelect").val();

		if(fromSelect != 'Select Type' && fromInput != '' &&
				toSelect != 'Select Type' && toInput != '' &&
				relSelect != 'Select Type') {
			deleteRelation(fromInput,relSelect, toInput);
		}
	});

	$("#updateRelationReset").click(function() {
		resetUpdateDataRelationships();
	});

});


function metaFunction(bool) {
	typeArray = new Array();
	relationArray = new Array();
	unionArray = new Array();
	getAllTypes(bool);
}

function validateMetaTypeInput() {
	var goOnFlag = true;
	if(SPACE.test($("#typeInput").val()) || !(ALPHA.test($("#typeInput").val()))) {
		goOnFlag = false;
	}
	return goOnFlag;
}

function validateMetaAttrInputs() {
	var goOnFlag = true;
	$("#typeAttrTable tr input:text").each(function() {
		if(!(ALPHA_SYM.test($(this).val()))) {
			goOnFlag = false;
		}
	});
	return goOnFlag;
}

function validateUpdateMetaAttr() {
	var goOnFlag = true;
	$("#updateTypeTable tr input:text").each(function() {
		if(!(ALPHA_SYM.test($(this).val()))) {
			goOnFlag = false;
		}
	});
	return goOnFlag;
}

function resetMetaType() {
	$("#addtp").contents().remove();
	$("#typeInput").val('');
	$("#firstAttr").val('');
	emptyTable("typeAttrTable", 1);
	$("#addtp").contents().remove();
	$(".attr").contents().remove();
}

function resetMetaTypeRelationship() {
	$("#addrp").contents().remove();
	$("#fromSelect").empty();
	$("#toSelect").empty();
	$("#relationshipSelect").empty();
	$("#fromSelect").append('<option>Select Type</option>');
	$("#toSelect").append('<option>Select Type</option>');
	$("#relationshipSelect").append('<option>Select Type</option>');
	populateSelect("fromSelect", typeArray);
	populateSelect("toSelect", typeArray);
	populateSelect("relationshipSelect", relationArray);
	$("#addrp").contents().remove();
	$(".attr").contents().remove();
}

function resetUpdateMetaType(bool) {
	emptyTable("updateTypeTable", 2);
	$("#updateTypeSelect").empty();
	$("#updateTypeSelect").append("<option>Select Type</option>");
	populateSelect("updateTypeSelect", unionArray);
	if(bool) {
		$("#updeltp").contents().remove();
	}
	attributeArray = new buckets.Dictionary();
}

function resetUpdateMetaRelationship() {
	$("#updateRelationSelect").empty();
	$("#updateRelationSelect").append("<option>Select Type Relationship</option>");
	getRelationshipTypes();
	$("#updelrp").contents().remove();
}



function getRelationshipTypes() {
	jQuery.ajax({
		type: 'GET',
		url: rootUrl + 'meta/relationships',
		dataType: 'json',
		success: function(json) {
			populateRelationships(json);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText +'\nresponse text: ' + XMLHttpRequest.responseText);
		}
	});
}

function populateRelationships(json) {
	jQuery.each(json, function(index, data) {
		var selectBox = document.getElementById("updateRelationSelect");
		var option = document.createElement("option");
		option.text = data.fromType.name + "-" + data.relationshipType.name + "-" +
		data.toType.name;
		option.value = data.fromType.name + "-" + data.relationshipType.name + "-" +
		data.toType.name;
		selectBox.options.add(option);
	});
}

function getAllTypes(bool) {
	jQuery.ajax({
		type: "GET",
		url: rootUrl + "meta/types",
		dataType: "json",
		success: function(json) {
			jQuery.each(json, function(index, data) {
				if(!(data.relationshipType)) {
					typeArray.push(data.name);
					unionArray.push(data.name);
				}else {
					relationArray.push(data.name);
					unionArray.push(data.name);
				}
			});
			resetMetaType();
			resetMetaTypeRelationship();
			resetUpdateMetaType(bool);
			resetUpdateMetaRelationship();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('status:' + XMLHttpRequest.status + ', status text: ' 
					+ XMLHttpRequest.statusText +'\nresponse text: ' 
					+ XMLHttpRequest.responseText);
		}
	});
}

function getAllTypesForData(reset) {
	jQuery.ajax({
		type: "GET",
		url: rootUrl + "meta/types",
		dataType: "json",
		success: function(json) {
			jQuery.each(json, function(index, data) {
				if(!(data.relationshipType)) {
					typeArray.push(data.name);
					unionArray.push(data.name);
				}else {
					relationArray.push(data.name);
					unionArray.push(data.name);
				}
			});
			resetAddData();
			resetUpdateData(reset);
			resetAddRelationships();
			resetUpdateDataRelationships();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('status:' + XMLHttpRequest.status + ', status text: ' 
					+ XMLHttpRequest.statusText +'\nresponse text: ' 
					+ XMLHttpRequest.responseText);
		}
	});
}

function emptyTable(tableName, fromRow) {
	var table = document.getElementById(tableName);
	for(var v=table.rows.length-1; v>=fromRow; v--) {
		table.deleteRow(v);
	}
}

function populateSelect(selectName, array) {
	jQuery.each(array, function(index, type) {
		var selectBox = document.getElementById(selectName);
		var option = document.createElement("option");
		option.text = type;
		option.value = type;
		selectBox.options.add(option);
	});
}

function addRow(tableId) {
	var row = "<tr><td></td><td><input type=\"text\" /></td>" +
	"<td><button>X</button></td><td><span class=\"attr\"></span></td></tr>";
	$(tableId).append(row);
	$(tableId+' tr:last input:text').focus();
	return;
}

function addType() {
	attributes = [];
	var attrTypeRequest;
	$("#typeAttrTable tr input:text").each(function() {
		if(!(SPACE.test($(this).val()))) {
			attributes.push({name : $(this).val()});
		}
	});
	if($("#relationSelect").val() == 'True') {
		attrTypeRequest = {
				name : 	$("#typeInput").val(),
				attributes : attributes,
				relationshipType : "true"
		};
	} else {
		attrTypeRequest = {
				name : 	$("#typeInput").val(),
				attributes : attributes
		};
	}
	jQuery.ajax({
		type: 'POST',
		contentType : 'application/json',
		url : rootUrl + 'meta/types',
		dataType : 'json',
		data : JSON.stringify(attrTypeRequest),
		success : $("#addtp").html("<b>Meta Type Added</b>"),
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#addtp").html("<b>"+XMLHttpRequest.statusText+". Please try again</b>");
		}
	});
}

function addRelationshipType() {
	jQuery.ajax({
		type: 'POST',
		url: rootUrl +'meta/relationships/'+ $("#fromSelect").val() + '\/' + $("#relationshipSelect").val() +
		'\/' + $("#toSelect").val(),
		dataType : 'json',
		success : function() {
			$("#addrp").html("<b>Type Relationship Added</b>");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#addrp").html("<b>"+XMLHttpRequest.statusText+". Please try again</b>");
		}

	});
}

function getAttributes(object, tableName) {
	var attrArray = new Array();
	jQuery.ajax({
		type: "GET",
		url: rootUrl + "meta/types/" + object,
		dataType: "json",
		success: function(json) {
			if(json.attributes != null) {
				jQuery.each(json.attributes, function(index, data) {
					attrArray.push(data.name);
				});
			}
			if(tableName == 'updateTypeTable') {
				updateFillAttribute(tableName, attrArray);
				$("#updateTypeRelationInput").val(json.relationshipType);
			} else {
				fillAttribute(tableName, attrArray);
			}

		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('status:' + XMLHttpRequest.status + ', status text: ' 
					+ XMLHttpRequest.statusText +'\nresponse text: ' 
					+ XMLHttpRequest.responseText);
		}
	});
}

function updateFillAttribute(tableName, attrArray) {
	var table = document.getElementById(tableName);
	var lastRow = table.rows.length;
	var row = table.insertRow(lastRow);
	var cell0 = row.insertCell(0);
	cell0.innerHTML = "Attributes";
	cell0.className = "sideBar";
	var cell1 = row.insertCell(1);
	var button = document.createElement('input');
	button.type = 'button';
	button.value = 'Add More';
	button.id = "updateAddAttrButton";
	cell1.appendChild(button);
	jQuery.each(attrArray, function(index, attr) {
		var table = document.getElementById(tableName);
		var lastRow = table.rows.length;
		var row = table.insertRow(lastRow);
		var cell0 = row.insertCell(0);
		var cell1 = row.insertCell(1);
		var input = document.createElement('input');
		input.type = 'text';
		input.id = 'attrvalue'+index;
		input.value = attr;
		cell1.appendChild(input);
		var cell2 = row.insertCell(2);
		var button = document.createElement('input');
		button.type = 'button';
		button.value = 'X';
		cell2.appendChild(button);
		attributeArray.set(attr,attr);
		var cell3 = row.insertCell(3);
		var span = document.createElement('span');
		span.className = 'attr';
		cell3.appendChild(span);
	});
}

function deleteAttr(attr, row) {
	jQuery.ajax({
		type: 'DELETE',
		url : rootUrl + 'meta/types/'+$("#updateTypeSelect").val() +'/attributes/' + attr,
		dataType: 'json',
		success: function() {
			$("#updeltp").html("<b>Attribute Deleted</b>");
			$(row).closest("tr").remove();
			attributeArray.remove(attr);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText +'\nresponse text: ' + XMLHttpRequest.responseText);
		}	
	});
}

function updateType() {
	var newAttrArray = new buckets.Set();
	$("#updateTypeTable tr input:text").each(function() {
		var newAttr = $(this).val();
		if(!(SPACE.test(newAttr)) && attributeArray.get(newAttr) == undefined) {
			newAttrArray.add(newAttr);
		}
	});

	if(newAttrArray.size() > 0) {
		jQuery.each(newAttrArray.toArray(), function(index, data) {
			jQuery.ajax({
				type: 'POST',
				contentType: 'application/json',
				url : rootUrl+'meta/types/'+$("#updateTypeSelect").val()+
				'/attributes/'+data,
				success: function() {
					newAttrArray.remove(data);
					if(newAttrArray.size() == 0) {
						var type = $("#updateTypeSelect").val();
						emptyTable("updateTypeTable", 2);
						attributeArray.clear();
						if(type != 'Select Type') {
							getAttributes(type, "updateTypeTable");
						}
						$("#updeltp").html("<b>Meta Type Updated</b>");
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					$("#updeltp").html("<b>"+XMLHttpRequest.statusText+"</b>");
				}

			});
		});
	}

}


function deleteType() {
	jQuery.ajax({
		type : 'DELETE',
		url : rootUrl + "meta/types/" + $("#updateTypeSelect").val(),
		success : function() {
			metaFunction(false);
			$("#updeltp").html("<b>Meta Type Deleted</b>");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#updeltp").html("<b>"+XMLHttpRequest.statusText+"</b>");
		}
	});
}

function deletetypeRelationship(relationship) {
	var from = relationship.split("-")[0];
	var relation = relationship.split("-")[1];
	var to = relationship.split("-")[2];
	jQuery.ajax({
		type: 'DELETE',
		url: rootUrl + 'meta/relationships/' + from + '\/' + relation + '\/' + to,
		contentType : 'application/json',
		success: function() {
			$("#updelrp").html("<b>Relationship Deleted</b>");
		},error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#updelrp").html("<b>"+XMLHttpRequest.statusText+"</b>");
		}
	});
}

function dataFunction(reset) {
	typeArray = new Array();
	relationArray = new Array();
	unionArray = new Array();
	getAllTypesForData(reset);
}



function resetAddData() {
	$("#objectInput").val('Search Type');
	$("#objectSelect").empty();
	$("#objectSelect").append('<option>Select Type</option>');
	populateSelect("objectSelect", typeArray);
	autoComplete(typeArray, "#objectInput");
	$("#addop").contents().remove();

	$("#name").val('');
	$("#namespace").val('DEFAULT');
	$("#status").val('live');

	emptyTable("objectTable", 8);

}

function resetUpdateData(reset) {
	$("#updateObjectMetaLabel").val('');
	$("#updateNamespace").val('DEFAULT');

	$("#updateObjectSelect").empty();
	$("#updateObjectSelect").append("<option>Select Type</option>");
	populateSelect("updateObjectSelect", typeArray);

	$("#updateObjectInput").val('');

	if(reset) {
		$("#updelop").contents().remove();
	}

	emptyTable("updateObjectTable", 10);
}


function resetAddRelationships() {
	$("#fromRSelect").empty();
	$("#fromRSelect").append('<option>Select Type</option>');
	$("#relateOFromInput").val('');

	$("#toRSelect").empty();
	$("#toRSelect").append('<option>Select Type</option>');
	$("#relateOToInput").val('');

	$("#relationShipRSelect").empty();
	$("#relationShipRSelect").append('<option>Select Type</option>');


	$("#relateStatus").val('live');

	populateSelect("fromRSelect", typeArray);
	populateSelect("toRSelect", typeArray);
	populateSelect("relationShipRSelect", relationArray);

	emptyTable("relateObjectTable", 8);
	$("#relp").contents().remove();
}

function resetUpdateDataRelationships() {
	$("#updateRelationFromSelect").empty();
	$("#updateRelationFromSelect").append('<option>Select Type</option>');
	$("#updateRelationFromInput").val('');

	$("#updateRelationToSelect").empty();
	$("#updateRelationToSelect").append('<option>Select Type</option>');
	$("#updateRelationToInput").val('');

	$("#updateRelationRelationshipSelect").empty();
	$("#updateRelationRelationshipSelect").append('<option>Select Type</option>');

	populateSelect("updateRelationFromSelect", typeArray);
	populateSelect("updateRelationToSelect", typeArray);
	populateSelect("updateRelationRelationshipSelect", relationArray);

	emptyTable("updateRelateObjectTable", 8);
	$("#updeldrp").contents().remove();

}



function autoComplete(source, destination) {
	$(destination).autocomplete({
		source: source,
		select: function(event, ui) {
			if (ui.item) {
				$(this).val(ui.item.value);
				$("#objectSelect").val(ui.item.value);
			}
			$("#objectSelect").trigger("change");
		},
		minLength: 1
	});
}

function getAttributesPrep() {
	emptyTable("objectTable", 8);
	$("#addop").contents().remove();
	var object = $("#objectSelect").val();
	if(object != "Select Type") {
		$("#objectInput").val(object);
		$("#name").val('');
		$("#namespace").val('DEFAULT');
		$("#status").val('live');
		getAttributes(object, "objectTable");
	} else {
		$("#objectInput").val('Search Type');
	}
}

function fillAttribute(tableName, attrArray) {
	jQuery.each(attrArray, function(index, attr) {
		var table = document.getElementById(tableName);
		var lastRow = table.rows.length;
		var row = table.insertRow(lastRow);
		var cell0 = row.insertCell(0);
		if(index==0) {
			cell0.innerHTML = "Define Attributes";
			cell0.className = "sideBar";
		}
		var cell1 = row.insertCell(1);
		cell1.className = "attribute";
		cell1.innerHTML = attr;
		var cell2 = row.insertCell(2);
		var input = document.createElement('input');
		input.type = 'text';
		input.id = 'attrvalue'+index;
		cell2.appendChild(input);
	});
}

function addObject() {

	attributes = [];
	var table = document.getElementById("objectTable");
	for(var i=8;i<table.rows.length;i++) {
		var name = table.rows[i].cells[1].innerHTML;
		var value = table.rows[i].cells[2].firstChild.value;
		if(value != '') {
			attributes.push({name : name, value: value});
		}
	}
	var addObjectRequest = {
			name: $("#name").val(), namespace: $("#namespace").val(),
			type: $("#objectSelect").val(), status: $("#status").val(),
			attributes: attributes
	};
	jQuery.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootUrl + 'data/objects',
		dataType: 'json',
		data: JSON.stringify(addObjectRequest),
		success: function() {
			$("#addop").html("<b>Object Added</b>");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#addop").html("<b>"+XMLHttpRequest.statusText+"</b>");

		}
	});
}

function displayAddError() {
	var errorMessage = 'Enter';

	if($("#objectSelect").val() == 'Select Type') {
		errorMessage = errorMessage + ' #Type';
	}

	if(SPACE.test($("#name").val())) {
		errorMessage = errorMessage + ' #ObjectName';
	}

	if(!(ALPHANUM_SYM.test($("#name").val()))) {
		errorMessage = errorMessage + ' #ObjectName Format';
	}

	if($("#namespace").val() == '') {
		errorMessage = errorMessage + ' #NameSpace';
	} 

	if($("#status").val() == '') {
		errorMessage = errorMessage + ' #Status';
	}

	if(errorMessage != 'Enter') {
		$("#addop").html("<b>"+errorMessage+"</b>");
	}

	return errorMessage;

}


function fetchObject(object) {
	jQuery.ajax({
		type : 'GET',
		url : rootUrl + 'data/object/' + object,
		dataType : 'json',
		success : function(json) {
			$("#updateObjectMetaLabel").val(json.type);
			$("#updateNamespace").val(json.namespace);
			$("#updateStatus").val(json.status);
			var map = new buckets.Dictionary();
			if(json.attributes != null) {
				jQuery.each(json.attributes, function(index, attr) {
					map.set(attr.name, attr.value);
				});
			}
			jQuery.ajax({
				type: 'GET',
				url: rootUrl + 'meta/types/' + json.type,
				dataType: 'json',
				success : function(typeJson) {
					fillObjectTable(map, typeJson);
				}
			});

		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#updelop").html("<b>"+XMLHttpRequest.statusText+"</b>");
		}
	});
}

function fillObjectTable(map, typeJson) {
	jQuery.each(typeJson.attributes, function(index, attribute) {
		var table = document.getElementById("updateObjectTable");
		var lastRow = table.rows.length;
		var row = table.insertRow(lastRow);
		var cell0 = row.insertCell(0);
		if(index==0) {
			cell0.innerHTML = "Attributes";
			cell0.className = "sideBar";
		}
		var cell1 = row.insertCell(1);
		cell1.className = "attribute";
		cell1.innerHTML = attribute.name;
		var cell2 = row.insertCell(2);
		var input = document.createElement('input');
		input.type = 'text';
		if(map.get(attribute.name) != undefined) {
			input.value = map.get(attribute.name);
		}
		cell2.appendChild(input);
	});
}

function updateObject() {
	attributes = [];
	var table = document.getElementById("updateObjectTable");
	for(var i=10;i<table.rows.length;i++) {
		var name = table.rows[i].cells[1].innerHTML;
		var value = table.rows[i].cells[2].firstChild.value;
		if(value != '') {
			attributes.push({name : name, value: value});
		}
	}
	var updateObjectRequest = {
			name: $("#updateObjectInput").val(), 
			namespace: $("#updateNamespace").val(),
			type: $("#updateObjectMetaLabel").val(), 
			status: $("#updateStatus").val(),
			attributes: attributes
	};
	jQuery.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootUrl + 'data/objects',
		dataType: 'json',
		data: JSON.stringify(updateObjectRequest),
		success: function() {
			$("#updelop").html("<b>Object Updated</b>");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#updelop").html("<b>"+XMLHttpRequest.statusText+"</b>");
		}
	});
}

function deleteObject() {
	jQuery.ajax({
		type: 'DELETE',
		contentType: 'application/json',
		url: rootUrl + 'data/object/' + $("#updateObjectInput").val(),
		success: function() {
			dataFunction(false);
			$("#updelop").html("<b>Object Deleted</b>");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#updelop").html("<b>"+XMLHttpRequest.statusText+"</b>");
		}
	});


}

function addRelation(tableName, from, to, relation, status, tag, method) {
	attributes = [];
	var table = document.getElementById(tableName);
	for(var i=8;i<table.rows.length;i++) {
		var name = table.rows[i].cells[1].innerHTML;
		var value = table.rows[i].cells[2].firstChild.value;
		if(value != '') {
			attributes.push({name : name, value: value});
		}
	}

	var relateObjectRequest = {
			fromObject : from,
			toObject : to,
			type : relation,
			status: status,
			attributes: attributes
	};
	jQuery.ajax({
		type: method,
		contentType: 'application/json',
		url: rootUrl + 'data/relations',
		data: JSON.stringify(relateObjectRequest),
		success: function() {
			$(tag).html("<b>Relationship Added/Updated</b>");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$(tag).html("<b>"+XMLHttpRequest.statusText+"</b>");
		}
	});
}

function displayRelateErrors() {
	var errorMessage = 'Enter';

	if($("#fromRSelect").val() == 'Select Type' ||
			$("#relateOFromInput").val() == '') {
		errorMessage = errorMessage + ' #FromObject';
	}

	if($("#toRSelect").val() == 'Select Type' ||
			$("#relateOToInput").val() == '') {
		errorMessage = errorMessage + ' #ToObject';
	}

	if($("#relationShipRSelect").val() == 'Select Type') {
		errorMessage = errorMessage + ' #Relationship';
	}

	if(errorMessage != 'Enter') {
		$("#relp").html("<b>"+errorMessage+"</b>");
	}

	return errorMessage;
}

function fetchRelation(fromInput, relSelect, toInput) {
	jQuery.ajax({
		type: 'GET',
		dataType: 'json',
		url: rootUrl + 'data/relation/' + fromInput+'~'+relSelect+'~'+toInput,
		success: function(json) {
			$("#updateRelationStatus").val(json.status);
			var map = new buckets.Dictionary();
			if(json.attributes != null) {
				jQuery.each(json.attributes, function(index, attr) {
					map.set(attr.name, attr.value);
				});
			}
			jQuery.ajax({
				type: 'GET',
				dataType: 'json',
				url: rootUrl + 'meta/types/' + json.type,
				success: function(typeJson) {
					fillUpdateRelationTable(map, typeJson);
				}
			});

		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#updeldrp").html("<b>"+XMLHttpRequest.responseText+". Please check the Relationship</b>");
		}
	});
}

function fillUpdateRelationTable(map, typeJson) {
	jQuery.each(typeJson.attributes, function(index, attribute) {
		var table = document.getElementById("updateRelateObjectTable");
		var lastRow = table.rows.length;
		var row = table.insertRow(lastRow);
		var cell0 = row.insertCell(0);
		if(index==0) {
			cell0.innerHTML = "Defined Attributes";
			cell0.className = "sideBar";
		}
		var cell1 = row.insertCell(1);
		cell1.className = "attribute";
		cell1.innerHTML = attribute.name;
		var cell2 = row.insertCell(2);
		var input = document.createElement('input');
		input.type = 'text';
		if(map.get(attribute.name) != undefined) {
			input.value = map.get(attribute.name);
		}
		cell2.appendChild(input);
	});
}



function deleteRelation(fromInput,relSelect, toInput) {
	jQuery.ajax({
		type: 'DELETE',
		url: rootUrl + 'data/relation/' + fromInput+'~'+relSelect+'~'+toInput,
		success: function(json) {
			$("#updeldrp").html("<b>Relationship Deleted</b>");
			$("#updateRelationFromInput").val('');
			$("#updateRelationToInput").val('');

			$("#updateRelationFromSelect").val('Select Type');
			$("#updateRelationToSelect").val('Select Type');

			$("#updateRelationRelationshipSelect").val('Select Type');
			$("#updateRelationStatus").val('');

			emptyTable("updateRelateObjectTable", 8);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert('status:' + XMLHttpRequest.status + ', status text: ' 
					+ XMLHttpRequest.statusText +'\nresponse text: ' 
					+ XMLHttpRequest.responseText);
		}
	});
}

