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

    /**
     * Gets all users of the application
     * @return NodeList where each Node is a user
     * @throws Exception
     */
    public NodeList getUsers() throws Exception {
        return getItems();
    }

    /**
     * Gets a user with a specific email address
     * @param email the user's email address
     * @return an Element representing a user
     * @throws Exception
     */
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

    /**
     * Adds a new user to the database
     * @param email the user's email address
     * @param password the user's hashed password
     * @param type the type of user (musician or band)
     * @return the newly added user
     * @throws Exception
     */
    public Element addUser(String email, String password, String type) throws Exception {
        LinkedHashMap<String, String> children = new LinkedHashMap<String, String>();
        children.put("email", email);
        children.put("password", password);
        children.put("type", type);
        children.put("profileImage", "src/images/defaultUserImg.png");
        return addElementToRoot("user", children);
    }

    /**
     * Checks the type of a specific user
     * @param userID the userID of the user
     * @return the user's type (musician or band)
     * @throws Exception
     */
    public String getUserType(String userID) throws Exception {
        Document document = getDocument(XML_PATH);
        Element user = getElementById(document, userID);
        Element type = (Element) user.getElementsByTagName("type").item(0);
        return type.getTextContent();
    }

    /**
     * Marks a user as logged in in the database
     * @param id userID of the logged-in user
     * @throws Exception
     */
    public void setUserLoggedIn(String id) throws Exception {
        changeAttribute(id, "logged-in", "true");
    }

    /**
     * Marks a user as logged out in the database
     * @param id userID of the logged-out user
     * @throws Exception
     */
    public void setUserLoggedOut(String id) throws Exception {
        changeAttribute(id, "logged-in", "false");
    }
}
