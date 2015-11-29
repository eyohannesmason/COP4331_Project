package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.LinkedHashMap;

public class MusicianDB extends Database {
    private MusicianDB() {
        XML_PATH = "src/database/musicians.xml";
    }
    private static MusicianDB database = new MusicianDB();
    public static MusicianDB getMusicianDB() {return database;}

    public NodeList getMusicians() throws Exception {
        return getItems();
    }

    public Element getMusician(String musicianID) throws Exception {
        return getDocument(XML_PATH).getElementById(musicianID);
    }

    public void addMusician(Element userElement) throws Exception {
        String email = userElement.getElementsByTagName("email").item(0).getTextContent(),
                 id = userElement.getAttribute("id");
        LinkedHashMap<String, String> children = new LinkedHashMap<String, String>();
        children.put("email", email);
        children.put("profileImage", "src/images/defaultUserImg.png");
        children.put("instruments", "");
        children.put("need", "");
        children.put("give", "");
        addElementToRoot("musician", id, children);
    }

    public void addInstruments(String id, String primaryInstrument) throws Exception {
        Document document = getDocument(XML_PATH);
        Element element = getElementById(document, id);
        Element instruments = (Element) element.getElementsByTagName("instruments").item(0);
        Element primary;
        if (instruments.hasChildNodes()) {
            primary = (Element) instruments.getElementsByTagName("primary").item(0);
            primary.setTextContent(primaryInstrument);
        } else {
            primary = document.createElement("primary");
            primary.setTextContent(primaryInstrument);
            instruments.appendChild(primary);
        }
        saveDocument(document);
    }

    public void addInstruments(String id, String primaryInstrument, String[] secondaryInstruments) throws Exception {
        addInstruments(id, primaryInstrument);
        addInstruments(id, secondaryInstruments);
    }

    public void addInstruments(String id, String[] secondaryInstruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element element = getElementById(document, id);
        Element instruments = (Element) element.getElementsByTagName("instruments").item(0);
        Element secondary;
        for(String instrument: secondaryInstruments) {
            secondary = document.createElement("secondary");
            secondary.setTextContent(instrument);
            instruments.appendChild(secondary);
        }
        saveDocument(document);
    }

    public static void main(String[] args) throws Exception {
        MusicianDB db = MusicianDB.getMusicianDB();
        String[] secondary = {"drums", "xylophone"};
        db.addInstruments("1", "banjo", secondary);
    }

}
