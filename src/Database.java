import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Random;
import java.util.UUID;

public class Database {
    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    private static Document getDocument(String filePath) throws Exception {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));
        document.getDocumentElement().normalize();
        return document;
    }

    public static NodeList getUsers() throws Exception {
        Document document = getDocument("src/users.xml");
        return document.getElementsByTagName("user");
    }

    private static String generateNewUserID() {
        Random random = new Random();
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public static void addUser(String name, String password, String type) throws Exception {
        Document document = getDocument("src/users.xml");
        Element newUser = document.createElement("user");
        newUser.setAttribute("id", generateNewUserID());

        Element userName = document.createElement("name"),
                userPass = document.createElement("password"),
                userType = document.createElement("type");

        userName.setTextContent(name);
        userPass.setTextContent(password);
        userType.setTextContent(type);
        newUser.appendChild(userName);
        newUser.appendChild(userPass);
        newUser.appendChild(userType);
        document.getDocumentElement().appendChild(newUser);

        // save to file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(document);
        StreamResult console = new StreamResult(new File("src/users.xml"));
        transformer.transform(source, console);
    }


}
