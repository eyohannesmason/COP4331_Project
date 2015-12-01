package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.LinkedHashMap;
import java.util.LinkedList;

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
        Document document = getDocument(XML_PATH);
        return getElementById(document, musicianID);
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
            if (primary==null) {
                primary = document.createElement("primary");
                instruments.appendChild(primary);
            }
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

    public void removeInstruments(String id, String primaryInstrument) throws Exception {
        String[] instruments = {primaryInstrument};
        removeAnyInstruments(id, "instruments", instruments);
    }

    public void removeInstruments(String id, String[] secondaryInstruments) throws Exception {
        removeAnyInstruments(id, "instruments", secondaryInstruments);
    }

    public void addGivenInstrument(String id, String instrument) throws Exception {
        String[] instruments = {instrument};
        addGivenInstruments(id, instruments);
    }

    public void addNeededInstrument(String id, String instrument) throws Exception {
        String[] instruments = {instrument};
        addNeededInstruments(id, instruments);
    }

    public void addNeededInstruments(String id, String[] instruments) throws Exception {
        addNeededOrGiven(id, "need", instruments);
    }

    public void addGivenInstruments(String id, String[] instruments) throws Exception {
        addNeededOrGiven(id, "give", instruments);
    }

    public void removeNeededInstruments(String id, String[] instruments) throws Exception {
        removeAnyInstruments(id, "need", instruments);
    }

    public void removeGivenInstruments(String id, String[] instruments) throws Exception {
        removeAnyInstruments(id, "give", instruments);
    }

    public NodeList getMusiciansByInstrument(String instrument) throws Exception {
        String xpathString = "//instruments/*[contains(.,'"+instrument+"')]/../..";
        return getNodeListByXPath(xpathString);
    }

    public NodeList getMusiciansByGiven(String givenInstrument) throws Exception {
        String xpathString = "//give/instrument[contains(.,'"+givenInstrument+"')]/../..";
        return getNodeListByXPath(xpathString);
    }

    public NodeList getMusiciansByNeeded(String neededInstrument) throws Exception {
        String xpathString = "//need/instrument[contains(.,'"+neededInstrument+"')]/../..";
        return getNodeListByXPath(xpathString);
    }

    private void addNeededOrGiven(String id, String needOrGive, String[] instruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element element = getElementById(document, id);
        Element neededOrGiven = (Element) element.getElementsByTagName(needOrGive).item(0);
        Element newInstrument;
        for(String instrument: instruments) {
            newInstrument = document.createElement("instrument");
            newInstrument.setTextContent(instrument);
            neededOrGiven.appendChild(newInstrument);
        }
        saveDocument(document);
    }

    private void removeAnyInstruments(String id, String parentName, final String[] instruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element musician = getElementById(document, id);
        Element neededOrGiven = (Element) musician.getElementsByTagName(parentName).item(0);
        NodeList instrumentElements = neededOrGiven.getElementsByTagName("*");
        LinkedList<Element> toRemove = new LinkedList<Element>();
        Node currentInstrument;
        String currentTextContent;
        for(int i=0; i<instrumentElements.getLength(); i++) {
            currentInstrument = instrumentElements.item(i);
            currentTextContent = currentInstrument.getTextContent();
            for(String instrument: instruments) {
                if (currentTextContent.equals(instrument)) {
                    toRemove.add((Element) currentInstrument);
                }
            }
        }
        for(Element instrument: toRemove) {
            neededOrGiven.removeChild(instrument);
        }
        saveDocument(document);
    }
}
