package controllers;

import database.BandDB;
import database.MusicianDB;
import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AuthenticationController implements IController {
     protected AuthenticationController() {
         userDB = UserDB.getUserDB();
         musicianDB = MusicianDB.getMusicianDB();
         bandDB = BandDB.getBandDB();
     }

    protected Element getUser(String email) throws Exception {
        NodeList users = userDB.getUsers();
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

    protected static UserDB userDB;
    protected static MusicianDB musicianDB;
    protected static BandDB bandDB;
}
