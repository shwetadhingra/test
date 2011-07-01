package com.palm.cloud.services.cmdb.resource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import com.palm.cloud.services.cmdb.collection.json.JSONParser;
import com.palm.cloud.services.cmdb.collection.xml.Edge;
import com.palm.cloud.services.cmdb.collection.xml.Vertex;
import com.palm.cloud.services.cmdb.collection.xml.XMLParser;
import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.condition.LogicalCondition;
import com.palm.cloud.services.cmdb.condition.ValueCondition;

public class JsonTests {

	@Test
	public void testConditionJson() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
    	ValueCondition condition1 = new ValueCondition(
    			"country", "equal", "USA");
    	LogicalCondition condition2 = new LogicalCondition("and");
    	ValueCondition condition3 = new ValueCondition(
    			"state", "notEqual", "CA");
    	ValueCondition condition4 = new ValueCondition(
    			"location", "like", "ORD");
    	condition2.setConditions(Arrays.asList(
    			new Condition[]{condition3, condition4}));
    	Condition[] conditions = {condition1, condition2};
    	StringWriter sw = new StringWriter();
    	MappingJsonFactory jsonFactory = new MappingJsonFactory();
    	JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);
    	objectMapper.writeValue(jsonGenerator, conditions);
    	sw.close();
    	String serialized = sw.getBuffer().toString();
    	Assert.assertNotNull(serialized);
    	System.out.println(serialized);
    	Condition[] deserialized = objectMapper
    		.readValue(serialized, Condition[].class);
    	Assert.assertNotNull(deserialized);
   		printConditions(deserialized);
	}

	private static void printConditions(Condition[] deserialized) {
		for (Condition condition : deserialized) {
			if (condition instanceof LogicalCondition) {
				System.out.println(condition.getOper().getOperation());
				if (((LogicalCondition) condition).getConditions() != null) {
					printConditions(((LogicalCondition) condition)
							.getConditions().toArray(new Condition[0]));
				}
			} else if (condition instanceof ValueCondition) {
				System.out.printf("%s %s %s\n", 
						((ValueCondition) condition).getName(), 
						condition.getOper().getOperation(),
						((ValueCondition) condition).getValue());
			}
		}
	}

	@Test
	public void testToObject() {
		String json = readFile("json-test-2.txt");
		Vertex vertex = JSONParser.toObject(Vertex.class, json);
		Assert.assertNotNull(vertex);
		print(vertex);
	}

	@Test
	public void testToJSON() throws Exception {
		String xml = readFile("json-test-1.xml");
		Vertex vertex = XMLParser.unmarshall(Vertex.class, xml);
		Assert.assertNotNull(vertex);
		String json = JSONParser.toJSON(vertex);
		Assert.assertNotNull(json);
		System.out.println(json);
	}

	private void print(Vertex vertex) {
		System.out.println(vertex.getType());
		print(vertex.getConditions());
		if (vertex.getEdge() != null) {
			for (Edge edge : vertex.getEdge()) {
				print(edge);
			}
		}
	}
	
	private void print(List<? extends Condition> filterConditions) {
		if (filterConditions != null) {
			for (Condition condition : filterConditions) {
				if (condition instanceof ValueCondition) {
					ValueCondition vc = (ValueCondition) condition;
					System.out.printf("\t%s %s %s\n", vc.getName(), 
							vc.getOper(), vc.getValue());
				} else if (condition instanceof LogicalCondition) {
					System.out.printf("\t%s\n", condition.getOper());
					print(((LogicalCondition) condition).getConditions());
				}
			}
		}
	}
	
	private void print(Edge edge) {
		System.out.printf("\t%s %s\n", edge.getType(), edge.getDirection());
		if (edge.getVertex() != null) {
			print(edge.getVertex());
		}
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
