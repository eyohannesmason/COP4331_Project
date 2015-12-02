package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Database {
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    protected String XML_PATH;

    public Database() {
        XML_PATH = "src/database/users.xml";
    }

    public Database(String xmlPath) {
        XML_PATH = xmlPath;
    }

    /**
     * Helper function to return an empty XML element
     * @param tagName name of the desired element (can be anything)
     * @return an empty Element
     * @throws Exception on XML IO errors
     */
    public Element getBlankElement(String tagName) throws Exception {
        return getDocument(XML_PATH).createElement(tagName);
    }

    /**
     * Retrieves an element with a certain ID attribute.
     * @param id ID of the desired Element
     * @return the desired Element
     * @throws Exception on XML IO errors
     */
    protected Element getElementById(String id) throws Exception {
        Document document = getDocument(XML_PATH);
        XPath xpath =  XPathFactory.newInstance().newXPath();
        String queryString = "//*[@id='"+id+"']";
        Node node = (Node) xpath.evaluate(queryString, document, XPathConstants.NODE);
        return (Element) node;
    }

    /**
     * Retrieves an element with a certain ID attribute from a particular XML document.
     * @param document The XML document to be parsed
     * @param id ID of the desired Element
     * @return the desired Element
     * @throws Exception on XML IO errors
     */
    protected Element getElementById(Document document, String id) throws Exception {
        XPath xpath =  XPathFactory.newInstance().newXPath();
        String queryString = "//*[@id='"+id+"']";
        Node node = (Node) xpath.evaluate(queryString, document, XPathConstants.NODE);
        return (Element) node;
    }

    /**
     * Retrieves an element with a particular email address.
     * @param email email address to search for
     * @return an Element whose email address matches the search parameter
     * @throws Exception
     */
    protected Element getElementByEmail(String email) throws Exception {
        Document document = getDocument(XML_PATH);
        Node root = document.getElementsByTagName("*").item(0);
        NodeList nodes = ((Element)root).getElementsByTagName("*");

        Node      currentNode = null,
             currentEmailNode = null;

        String  currentEmail = null;
        Element element = null;
        for(int i=0; i<nodes.getLength(); i++) {
            currentNode = nodes.item(i);
            currentEmailNode = ((Element)currentNode).getElementsByTagName("email").item(0);
            if (currentEmailNode != null) {
                currentEmail = currentEmailNode.getTextContent();
                if (currentEmail.equals(email)) {
                    element = (Element) currentNode;
                    break;
                }
            }
        }
        return element;
    }

    /**
     * Given a user's email, get their user ID
     * @param email the user's email address
     * @return the user's ID
     * @throws Exception
     */
    protected String getIdByEmail(String email) throws Exception {
        Element element = getElementByEmail(email);
        return element.getAttribute("id");
    }

    /**
     * Creates an instance of the XML document
     * @param filePath the location of the XML document
     * @return a Document object representing an XML document
     * @throws Exception
     */
    protected Document getDocument(String filePath) throws Exception {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));
        document.getDocumentElement().normalize();
        return document;
    }

    /**
     * Saves changes to an XML document
     * @param document the document to save
     * @throws Exception
     */
    protected void saveDocument(Document document) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(XML_PATH));
        transformer.transform(source, result);
    }

    /**
     * Gets all children of the root node
     * @return NodeList containing all items in an XML document
     * @throws Exception
     */
    protected NodeList getItems() throws Exception {
        Document document = getDocument(XML_PATH);
        Node root = document.getElementsByTagName("*").item(0);
        return ((Element) root).getElementsByTagName("*");

    }

    /**
     * Creates a new unique ID
     * @return String of 8 random letters and numbers
     */
    protected String generateNewID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Change the value of an attribute of an XML element
     * @param elmID ID of the element
     * @param attributeName name of the attribute
     * @param newValue new value of the attribute
     * @throws Exception
     */
    protected void changeAttribute(String elmID, String attributeName, String newValue) throws Exception {
        Document document = getDocument(XML_PATH);
        document.getElementById(elmID).setAttribute(attributeName, newValue);
        saveDocument(document);
    }

    /**
     * Adds a new XML element to the root element
     * @param tagName name of the new XML element
     * @param childContents tag names and text contents of the new XML elements child elements
     * @return the new XML element
     * @throws Exception
     */
    protected Element addElementToRoot(String tagName, HashMap<String, String> childContents) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newElm = document.createElement(tagName);
        for(HashMap.Entry<String, String> entry: childContents.entrySet()) {
            Element childElm = document.createElement(entry.getKey());
            childElm.setTextContent(entry.getValue());
            newElm.appendChild(childElm);
        }
        newElm.setAttribute("id", generateNewID());
        newElm.setAttribute("logged-in", "false");
        document.getDocumentElement().appendChild(newElm);
        saveDocument(document);
        return newElm;
    }

    /**
     * Same as the other version, but gives the new XML element a specific ID instead of generating a new one
     * @param tagName name of the new XML element
     * @param id ID of the new XML element
     * @param childContents tag names and text contents of the new XML element's child elements
     * @return the new XML element
     * @throws Exception
     */
    protected Element addElementToRoot(String tagName, String id, HashMap<String, String> childContents) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newElm = document.createElement(tagName);
        for(HashMap.Entry<String, String> entry: childContents.entrySet()) {
            Element childElm = document.createElement(entry.getKey());
            childElm.setTextContent(entry.getValue());
            newElm.appendChild(childElm);
        }
        newElm.setAttribute("id", id);
        document.getDocumentElement().appendChild(newElm);
        saveDocument(document);
        return newElm;
    }

    /**
     * Adds a new empty element to the root element
     * @param tagName name of the new XML element
     * @param id ID of the new XML element
     * @return the new XML element
     * @throws Exception
     */
    protected Element addElementToRoot(String tagName, String id) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newElm = document.createElement(tagName);
        newElm.setAttribute("id", id);
        document.getDocumentElement().appendChild(newElm);
        saveDocument(document);
        return newElm;
    }

    /**
     * Performs an XPath search on the document
     * @param xpathString XPath pattern used for the search
     * @return NodeList where each Node matches the pattern
     * @throws Exception
     */
    protected NodeList getNodeListByXPath(String xpathString) throws Exception {
        Document document = getDocument(XML_PATH);
        XPath xpath =  XPathFactory.newInstance().newXPath();
        try {
            return (NodeList) xpath.evaluate(xpathString, document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Returns an XML element that matches an XPath pattern
     * @param xpathString XPath pattern used for the search
     * @return the matching XML element
     * @throws Exception
     */
    protected Node getNodeByXPath(String xpathString) throws Exception {
        Document document = getDocument(XML_PATH);
        XPath xpath =  XPathFactory.newInstance().newXPath();
        try {
            return (Node) xpath.evaluate(xpathString, document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
