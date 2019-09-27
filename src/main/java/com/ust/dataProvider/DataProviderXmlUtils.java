package com.ust.dataProvider;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DataProviderXmlUtils {

	/**
	 * @author Rakesh M
	 */

	/**
	 * Logic for XML reading serializing and de-serializing using Jackson API
	 * 
	 * @param fileName
	 * @param parentElement
	 * @param clazz
	 * @return
	 */

	public static Object[][] getTestData(String fileName, String parentElement, Class clazz) {
		try {
			List<Object> list = getData(fileName, parentElement, clazz);
			Object[][] dataObject = new Object[list.size()][1];
			for (int i = 0; i < list.size(); i++) {
				dataObject[i][0] = list.get(i);
			}
			return dataObject;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Get test data from XML
	 * 
	 * @param pathToXMLData
	 *            - path to xml data
	 * @param parentElement
	 *            - parent tag
	 * @param type
	 *            - type of class
	 * @param <T>
	 *            - generic type
	 * @return - return a class
	 */
	public static <T> List<T> getXMLDTO(File pathToXMLData, String parentElement, Class<T> type) {
		List<T> list = new ArrayList<>();
		try {
			DocumentBuilder dbBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dbBuilder.parse(pathToXMLData);
			doc.getDocumentElement().normalize();

			// read in contents of element that you have found using xpath into
			// node list
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate(parentElement, doc.getDocumentElement(), XPathConstants.NODESET);

			// Jackson read xml/class
			ObjectMapper xmlMapper = new XmlMapper();
			CollectionType ct = xmlMapper.getTypeFactory().constructCollectionType(List.class, type);

			// convert elements into list of objects - reads in the first
			// element from the xml found w/ xpath
			list = xmlMapper.readValue(convertNodeToString(nodes.item(0)), ct);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not get test data accurately!");
		}
		return list;
	}

	/**
	 * Converts a node to an xml string
	 * 
	 * @param node
	 *            - xml node
	 * @return - return node as string
	 * @throws TransformerException
	 */
	private static String convertNodeToString(Node node) throws TransformerException {
		StringWriter stringWriter = new StringWriter();
		Transformer serializer = TransformerFactory.newInstance().newTransformer();
		serializer.transform(new DOMSource(node), new StreamResult(stringWriter));
		return stringWriter.toString();
	}

	protected static <T> List<T> getData(String xmlFileName, String parentElement, Class<T> type) {

		File xmlFile = new File(getTestDataDirectory(), xmlFileName);
		List<T> list = DataProviderXmlUtils.getXMLDTO(xmlFile, parentElement, type);
		return list;
	}

	// TODO put the string for pathname into a Resource file
	public static String getTestDataDirectory() {
		File testData = new File("src/test/resources/TestData/");
		return testData.getAbsolutePath();
	}

	public static String getCoreResourcesDirectory() {
		File resourcesDirectory = new File("src/main/resources");
		return resourcesDirectory.getAbsolutePath();
	}

}
