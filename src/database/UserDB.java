package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class UserDB extends Database {

    public UserDB(String xmlPath) {
        XML_PATH = xmlPath;
    }

    public NodeList getUsers() throws Exception {
        Document document = getDocument("src/database/users.xml");
        return document.getDocumentElement().getElementsByTagName("user");
    }

    public  void addUser(String name, String password, String type) throws Exception {
        // todo should generate blank musician/band and profile XML
        Document document = getDocument(XML_PATH);
        Element newUser = document.createElement("user");
        newUser.setAttribute("id", generateNewID());

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
        saveDocument(document);
    }

    public void setUserLoggedIn(String id) throws Exception {
        changeAttribute(id, "logged-in", "true");
    }

    public void setUserLoggedOut(String id) throws Exception {
        changeAttribute(id, "logged-in", "false");
    }
}
