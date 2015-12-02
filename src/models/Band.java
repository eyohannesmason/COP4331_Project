package models;


import app.BandHeroApp;
import database.BandDB;
import database.MusicianDB;
import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class Band extends User {
    public Band() {
        super();
        members = new ArrayList<>();
    }

    public Band(Element userElement) {
        super(userElement);
        members = new ArrayList<>();
        try {
            Node m = BandDB.getBandDB().getBand(getId()).getElementsByTagName("members").item(0);
            for(int i = 0; i < m.getChildNodes().getLength(); i++) {
                if(m.getChildNodes().item(i).getNodeName().equals("member") ) {
                    Element tempMemb = (Element) m.getChildNodes().item(i);
                    if(!tempMemb.getAttribute("id").isEmpty()) {
                        for(int x = 0; x < tempMemb.getChildNodes().getLength(); x++) {
                            if(tempMemb.getChildNodes().item(x).getNodeName().equals("email")) {
                                getMembers().add(new Musician(UserDB.getUserDB().getUser(tempMemb.getChildNodes().item(x).getTextContent())));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Musician> getMembers() {
        return members;
    }

    private ArrayList<Musician> members;
}
