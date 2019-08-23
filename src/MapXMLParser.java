import java.io.IOException;

import javax.swing.JOptionPane;
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
		Document document = documentBuilder.parse(this.getClass().getResourceAsStream("map_data.xml"));
		Element root = document.getDocumentElement();
		XPathFactory xfactory = XPathFactory.newInstance();
		XPath xpath = xfactory.newXPath();
		
		NodeList vertices = (NodeList) xpath.compile("//map/nodeSet/node").evaluate(document, XPathConstants.NODESET);
		
		return vertices;
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (SAXException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (XPathExpressionException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		}
		return null;
	}
	
	public NodeList parseEdges() {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
		documentBuilder = dfactory.newDocumentBuilder();
		Document document = documentBuilder.parse(this.getClass().getResourceAsStream("map_data.xml"));
		Element root = document.getDocumentElement();
		XPathFactory xfactory = XPathFactory.newInstance();
		XPath xpath = xfactory.newXPath();
		
		NodeList edges = (NodeList) xpath.compile("//map/edgeSet/edge").evaluate(document, XPathConstants.NODESET);
		
		return edges;
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (SAXException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		} catch (XPathExpressionException e) {
			JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
		}
		return null;
	}
	
}
