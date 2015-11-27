package controllers;

import database.BandDB;
import database.MusicianDB;
import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class AuthenticationController implements IController {
     protected AuthenticationController() {
         userDB = UserDB.getUserDB();
         musicianDB = MusicianDB.getMusicianDB();
         bandDB = BandDB.getBandDB();
     }

    protected Element getUser(String name) throws Exception {
        NodeList users = userDB.getUsers();
        Element user;
        String currentName;
        for (int i=0; i<users.getLength(); i++) {
            user = (Element) users.item(i);
            currentName = user.getElementsByTagName("name").item(0).getTextContent();
            if (currentName.equals(name)) {
                return user;
            }
        }
        return null;
    }

    protected static UserDB userDB;
    protected static MusicianDB musicianDB;
    protected static BandDB bandDB;
}
