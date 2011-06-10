package com.palm.cloud.services.cmdb.collection.xml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

public class XMLParserTests {

	@Test
	public void testVertexUnmarshall() throws JAXBException {
		String xml = readFile("Test-Collection-1.xml");
		System.out.println(xml);
		Vertex vertex = XMLParser
			.unmarshall(Vertex.class, xml);
		Assert.assertNotNull(vertex);
		print(vertex);
	}
	
	@Test
	public void testVertexMarshall() throws JAXBException {
		String xml = readFile("Test-Collection-2.xml");
		Vertex vertex = XMLParser
			.unmarshall(Vertex.class, xml);
		Assert.assertNotNull(vertex);
		String marshalled = XMLParser.marshall(vertex);
		Assert.assertNotNull(marshalled);
		System.out.println(marshalled);
	}
	
	private void print(Vertex vertex) {
		System.out.println(vertex.getType());
		if (vertex.getEdge() != null) {
			for (Edge edge : vertex.getEdge()) {
				print(edge);
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
