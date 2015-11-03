package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Database {
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    protected String XML_PATH;

    public Database() {
        XML_PATH = "src/database/users.xml"; //
    }

    public Database(String xmlPath) {
        XML_PATH = xmlPath;
    }

    protected Document getDocument(String filePath) throws Exception {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));
        document.getDocumentElement().normalize();
        return document;
    }

    protected void saveDocument(Document document) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(XML_PATH));
        transformer.transform(source, result);
    }

    protected String generateNewID() {
        /*I figure we could make DB items' share IDs,
          e.g. the Profile with ID=123 corresponds to
          the user with ID=123.
          */
        return UUID.randomUUID().toString().substring(0, 8);
    }

    protected void changeAttribute(String elmID, String attributeName, String newValue) throws Exception {
        Document document = getDocument(XML_PATH);
        document.getElementById(elmID).setAttribute(attributeName, newValue);
        saveDocument(document);
    }

    protected Element addElementToRoot(String tagName, HashMap<String, String> childContents) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newElm = document.createElement(tagName);
        for(HashMap.Entry<String, String> entry: childContents.entrySet()) {
            Element childElm = document.createElement(entry.getKey());
            childElm.setTextContent(entry.getValue());
            newElm.appendChild(childElm);
        }
        newElm.setIdAttribute(generateNewID(), false);
        document.getDocumentElement().appendChild(newElm);
        saveDocument(document);
        return newElm;
    }

    protected Element addElementToRoot(String tagName, String id, HashMap<String, String> childContents) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newElm = document.createElement(tagName);
        for(HashMap.Entry<String, String> entry: childContents.entrySet()) {
            Element childElm = document.createElement(entry.getKey());
            childElm.setTextContent(entry.getValue());
            newElm.appendChild(childElm);
        }
        newElm.setIdAttribute(id, true);
        document.getDocumentElement().appendChild(newElm);
        saveDocument(document);
        return newElm;
    }
}
