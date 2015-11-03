package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class UserDB extends Database {

    public UserDB(String xmlPath) {
        XML_PATH = xmlPath;
    }

    public NodeList getUsers() throws Exception {
        Document document = getDocument("src/database/users.xml");
        return document.getDocumentElement().getElementsByTagName("user");
    }

    public String addUser(String name, String password, String type) throws Exception {
        LinkedHashMap<String, String> children = new LinkedHashMap<String, String>();
        children.put("name", name);
        children.put("password", password);
        children.put("type", type);
        Element newUser = addElementToRoot("user", children);
        return newUser.getAttribute("id");
    }

    public void setUserLoggedIn(String id) throws Exception {
        changeAttribute(id, "logged-in", "true");
    }

    public void setUserLoggedOut(String id) throws Exception {
        changeAttribute(id, "logged-in", "false");
    }
}
