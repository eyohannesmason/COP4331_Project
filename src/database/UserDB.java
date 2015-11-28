package database;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.LinkedHashMap;


public class UserDB extends Database {

    private UserDB() {
        XML_PATH = "src/database/users.xml";
    }
    private static UserDB database = new UserDB();
    public static UserDB getUserDB() {return database;}

    public NodeList getUsers() throws Exception {
        return getItems();
    }

    public Element addUser(String name, String password, String type) throws Exception {
        LinkedHashMap<String, String> children = new LinkedHashMap<String, String>();
        children.put("name", name);
        children.put("password", password);
        children.put("type", type);
        return addElementToRoot("user", children);
    }

    public void setUserLoggedIn(String id) throws Exception {
        changeAttribute(id, "logged-in", "true");
    }

    public void setUserLoggedOut(String id) throws Exception {
        changeAttribute(id, "logged-in", "false");
    }
}
