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

    /**
     * Retrieves all musicians.
     * @return NodeList where each Node is a musician
     * @throws Exception on XML IO errors
     */
    public NodeList getMusicians() throws Exception {
        return getItems();
    }

    /**
     * Retrieves a specific musician
     * @param musicianID userID of the musician
     * @return an Element representing a musician
     * @throws Exception on XML IO errors
     */
    public Element getMusician(String musicianID) throws Exception {
        return getElementById(musicianID);
    }

    /**
     * Retrieves a specific musician using the email tied to it's account
     * @param email the email address of the musician
     * @return an Element representing a musician
     * @throws Exception on XML IO errors
     */
    public Element getMusicianByEmail(String email) throws Exception {
        return getElementByEmail(email);
    }

    /**
     * Adds a musician to the database
     * @param userElement Element created on sign up, represents a generic user (either musician or band)
     * @throws Exception on XML IO errors
     */
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

    /**
     * Adds a primary instrument to a musician
     * @param id userID of the musician
     * @param primaryInstrument the musician's new primary instrument
     * @throws Exception on XML IO errors
     */
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

    /**
     * Adds a new primary instrument and new secondary instruments to a musician
     * @param id userID of the musician
     * @param primaryInstrument name of the primary instrument
     * @param secondaryInstruments list of names of secondary instruments
     * @throws Exception on XML IO errors
     */
    public void addInstruments(String id, String primaryInstrument, String[] secondaryInstruments) throws Exception {
        addInstruments(id, primaryInstrument);
        addInstruments(id, secondaryInstruments);
    }

    /**
     * Adds secondary instruments to a musician
     * @param id userID of the musician
     * @param secondaryInstruments list of names of secondary instruments
     * @throws Exception on XML IO errors
     */
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

    /**
     * Removes the primary instrument from a musician
     * @param id userID of the musician
     * @param primaryInstrument
     * @throws Exception on XML IO errors
     */
    public void removeInstruments(String id, String primaryInstrument) throws Exception {
        String[] instruments = {primaryInstrument};
        removeAnyInstruments(id, "instruments", instruments);
    }

    /**
     * Removes secondary instruments from a musician
     * @param id userID of the musician
     * @param secondaryInstruments list of secondary instruments
     * @throws Exception on XML IO errors
     */
    public void removeInstruments(String id, String[] secondaryInstruments) throws Exception {
        removeAnyInstruments(id, "instruments", secondaryInstruments);
    }

    /**
     * Adds an instrument to the list of instruments a musician is willing to play in a jam session
     * @param id userID of the musician
     * @param instrument name of the instrument
     * @throws Exception
     */
    public void addGivenInstrument(String id, String instrument) throws Exception {
        String[] instruments = {instrument};
        addGivenInstruments(id, instruments);
    }

    /**
     * Adds an instrument to the list of instruments a musician is looking for a partner to play in a jam session.
     * @param id userID of the musician
     * @param instrument name of the instrument
     * @throws Exception
     */
    public void addNeededInstrument(String id, String instrument) throws Exception {
        String[] instruments = {instrument};
        addNeededInstruments(id, instruments);
    }

    /**
     * Adds instruments to the list of instruments a musician is looking for a partner to play in a jam session.
     * @param id userID of the musician
     * @param instruments list of names of instruments
     * @throws Exception
     */
    public void addNeededInstruments(String id, String[] instruments) throws Exception {
        addNeededOrGiven(id, "need", instruments);
    }

    /**
     * Adds instruments to the list of instruments a musician is willing to play in a jam session
     * @param id userID of the musician
     * @param instruments list of names of instruments
     * @throws Exception
     */
    public void addGivenInstruments(String id, String[] instruments) throws Exception {
        addNeededOrGiven(id, "give", instruments);
    }

    /**
     * Removes instruments from the list of instruments a musician is looking for a partner to play in a jam session.
     * @param id userID of the musician
     * @param instruments list of names of instruments
     * @throws Exception
     */
    public void removeNeededInstruments(String id, String[] instruments) throws Exception {
        removeAnyInstruments(id, "need", instruments);
    }

    /**
     * Removes instruments from the list of instruments a musician is willing to play in a jam session
     * @param id userID of the musician
     * @param instruments list of names of instruments
     * @throws Exception
     */
    public void removeGivenInstruments(String id, String[] instruments) throws Exception {
        removeAnyInstruments(id, "give", instruments);
    }

    /**
     * Retrieves all musicians who play a specific instrument
     * @param instrument the instrument played by the musicians
     * @return NodeList where each Node is a musician who plays the instrument
     * @throws Exception
     */
    public NodeList getMusiciansByInstrument(String instrument) throws Exception {
        String xpathString = "//instruments/*[contains(.,'"+instrument+"')]/../..";
        return getNodeListByXPath(xpathString);
    }

    /**
     * Retrieves all musicians who are willing to play a certain instrument in a jam session
     * @param givenInstrument the instrument played by the musicians
     * @return NodeList where each Node is a musician willing to play the instrument
     * @throws Exception
     */
    public NodeList getMusiciansByGiven(String givenInstrument) throws Exception {
        String xpathString = "//give/instrument[contains(.,'"+givenInstrument+"')]/../..";
        return getNodeListByXPath(xpathString);
    }

    /**
     * Retrieves all musicians who are looking for other people to play a specific instrument
     * @param neededInstrument the instrument the musicians are looking for
     * @return NodeList where each Node is a musician looking for someone to play the instrument
     * @throws Exception
     */
    public NodeList getMusiciansByNeeded(String neededInstrument) throws Exception {
        String xpathString = "//need/instrument[contains(.,'"+neededInstrument+"')]/../..";
        return getNodeListByXPath(xpathString);
    }

    /**
     * Helper function to add an instrument to a musician's list of needed or given instruments
     * @param id userID of the musician
     * @param needOrGive either "need" or "give"
     * @param instruments list of instruments needed or given by the musician
     * @throws Exception
     */
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

    /**
     * Helper function to remove instruments from a musicians profile
     * @param id userID of the musician
     * @param parentName parent name of the instrument element. Either "instruments", "need", or "give"
     * @param instruments list of instruments to remove from the musician
     * @throws Exception
     */
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
