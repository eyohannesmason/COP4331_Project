package database;

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
        return getItems("musician");
    }

    public Element getMusician(String musicianID) throws Exception {
        return getDocument(XML_PATH).getElementById(musicianID);
    }

    public void addMusician(Element userElement) throws Exception {
        String name = userElement.getElementsByTagName("name").item(0).getTextContent(),
                 id = userElement.getAttribute("id");
        LinkedHashMap<String, String> children = new LinkedHashMap<String, String>();
        children.put("name", name);
        children.put("instruments", "");
        addElementToRoot("musician", id, children);
    }

    public void addInstruments(String id, String primaryInstrument) throws Exception {

    }

    public void addInstruments(String id, String primaryInstrument, String[] secondaryInstruments) {

    }

}
