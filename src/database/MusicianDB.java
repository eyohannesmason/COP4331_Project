package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.LinkedHashMap;

public class MusicianDB extends Database {
    public MusicianDB(String filePath) {
        XML_PATH = filePath;
    }
    public NodeList getMusicians() throws Exception {
        return getItems("musician");
    }

    public void addMusician(Element userElement) throws Exception {
        String name = userElement.getElementsByTagName("name").item(0).getTextContent(),
                 id = userElement.getAttribute("id");
        Element newMusician = addElementToRoot(name, id);
    }

    public void addInstruments(String id, String primaryInstrument) throws Exception {

    }

    public void addInstruments(String id, String primaryInstrument, String[] secondaryInstruments) {

    }

}
