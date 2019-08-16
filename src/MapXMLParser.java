import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
}
