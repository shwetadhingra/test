package com.palm.cloud.services.cmdb.resource;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

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

}
