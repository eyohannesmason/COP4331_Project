import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

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


}
