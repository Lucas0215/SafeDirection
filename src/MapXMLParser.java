import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MapXMLParser {
	
	public NodeList parseVertices() {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
		documentBuilder = dfactory.newDocumentBuilder();
		Document document = documentBuilder.parse("xml/map_data.xml");
		Element root = document.getDocumentElement();
		XPathFactory xfactory = XPathFactory.newInstance();
		XPath xpath = xfactory.newXPath();
		
		NodeList vertices = (NodeList) xpath.compile("//map/nodeSet/node").evaluate(document, XPathConstants.NODESET);
		
		return vertices;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public NodeList parseEdges() {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
		documentBuilder = dfactory.newDocumentBuilder();
		Document document = documentBuilder.parse("xml/map_data.xml");
		Element root = document.getDocumentElement();
		XPathFactory xfactory = XPathFactory.newInstance();
		XPath xpath = xfactory.newXPath();
		
		NodeList edges = (NodeList) xpath.compile("//map/edgeSet/edge").evaluate(document, XPathConstants.NODESET);
		
		return edges;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
