package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

    public Element getUser(String email) throws Exception {
        NodeList users = getUsers();
        Element user;
        String currentEmail;
        for (int i=0; i<users.getLength(); i++) {
            if (users.item(i).getNodeType() == Node.ELEMENT_NODE) {
                try {
                    user = (Element) users.item(i);
                    NodeList emailList = user.getElementsByTagName("email");
                    Node currentNode = emailList.item(0);
                    if (currentNode != null) {
                        currentEmail = currentNode.getTextContent();
                        if (currentEmail.equals(email)) {
                            return user;
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return null;
    }

    public Element addUser(String email, String password, String type) throws Exception {
        LinkedHashMap<String, String> children = new LinkedHashMap<String, String>();
        children.put("email", email);
        children.put("password", password);
        children.put("type", type);
        children.put("profileImage", "src/images/defaultUserImg.png");
        return addElementToRoot("user", children);
    }

    public String getUserType(String userID) throws Exception {
        Document document = getDocument(XML_PATH);
        Element user = getElementById(document, userID);
        Element type = (Element) user.getElementsByTagName("type").item(0);
        return type.getTextContent();
    }

    public void setUserLoggedIn(String id) throws Exception {
        changeAttribute(id, "logged-in", "true");
    }

    public void setUserLoggedOut(String id) throws Exception {
        changeAttribute(id, "logged-in", "false");
    }
}
