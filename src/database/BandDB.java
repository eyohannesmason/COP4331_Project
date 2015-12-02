package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class BandDB extends Database {

    /**
     * Returns the single instance of the band database
     * @return the BandDB object
     */
    public static BandDB getBandDB() {return database;}

    /**
     * Gets all the bands
     * @return a NodeList, where each Node represents a band
     * @throws Exception on XML I/O errors
     */
    public NodeList getBands() throws Exception {
        return getItems();
    }

    /**
     * Retrieves a specific band.
     * @param bandID userID of the desired band.
     * @return an Element object representing a band.
     * @throws Exception on XML IO errors
     */

    public Element getBand(String bandID) throws Exception {
        return getElementById(bandID);
    }
    
    public Element getBandByEmail(String email) throws Exception {
        return getElementByEmail(email);
    }

    /**
     * Retrieves bands who are in need of a specific instrument.
     * @param instrument Name of the needed instrument.
     * @return a NodeList where each Node represents a band.
     * @throws Exception on XML IO errors
     */
    public NodeList getBandsByNeeded(String instrument) throws Exception {
        String xpathString = "//need/instrument[contains(.,'"+instrument+"')]/../..";
        return getNodeListByXPath(xpathString);
    }

    /**
     * Adds a new band to the database
     * @param userElement Element created on sign up, represents a generic user (either musician or band)
     * @throws Exception on XML IO errors
     */
    public void addBand(Element userElement) throws Exception {
        String email = userElement.getElementsByTagName("email").item(0).getTextContent(),
                id = userElement.getAttribute("id");
        LinkedHashMap<String, String> children = new LinkedHashMap<String, String>();
        children.put("email", email);
        children.put("profileImage", "src/images/defaultUserImg.png");
        children.put("members", "");
        children.put("need", "");
        addElementToRoot("band", id, children);
    }

    /**
     * Add a new member to a band
     * @param bandID userID of the band
     * @param memberID userID of the new member (an existing musician)
     * @throws Exception on XML IO errors
     */
    public void addBandMember(String bandID, String memberID) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newMember = document.createElement("member");
        newMember.setAttribute("id", memberID);

        Element musician = MusicianDB.getMusicianDB().getMusician(memberID);
        Element emailElement = document.createElement("email");
        emailElement.setTextContent(musician.getElementsByTagName("email").item(0).getTextContent());
        newMember.appendChild(emailElement);

        Element members = (Element) getElementById(document, bandID).getElementsByTagName("members").item(0);
        members.appendChild(newMember);
        saveDocument(document);
    }

    /**
     * Adds a new member to a band, specifying which instrument the new member plays
     * @param bandID userID of the band
     * @param memberID userID of the new member
     * @param instrument instrument the new member plays
     * @throws Exception on XML IO errors
     */
    public void addBandMember(String bandID, String memberID, String instrument) throws Exception {
        String[] instruments = {instrument};
        addBandMember(bandID, memberID, instruments);
    }

    /**
     * Adds a new member to a band, specifying which instruments the new member plays
     * @param bandID userID of the band
     * @param memberID userID of the new member
     * @param instruments instruments the new member plays
     * @throws Exception on XML IO errors
     */
    public void addBandMember(String bandID, String memberID, String[] instruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newMember = document.createElement("member");
        newMember.setAttribute("id", memberID);

        Element musician = MusicianDB.getMusicianDB().getMusician(memberID);
        Element musicianEmailElement = (Element) musician.getElementsByTagName("email").item(0);

        Element memberEmailElement = document.createElement("email");
        memberEmailElement.setTextContent(musicianEmailElement.getTextContent());
        newMember.appendChild(memberEmailElement);
        Element newInstrument;
        for(String instrument: instruments) {
            newInstrument = document.createElement("instrument");
            newInstrument.setTextContent(instrument);
            newMember.appendChild(newInstrument);
        }

        Element band = getElementById(document, bandID);
        Element membersElement = (Element) band.getElementsByTagName("members").item(0);
        membersElement.appendChild(newMember);

        saveDocument(document);
    }

    /**
     * Removes a member from a band
     * @param bandID userID of the band
     * @param memberID userID of the member to be removed
     * @throws Exception on XML IO errors
     */
    public void removeBandMember(String bandID, String memberID) throws Exception {
        Document document = getDocument(XML_PATH);
        Element band = getElementById(document, bandID);
        Element membersElement = (Element) band.getElementsByTagName("members").item(0);
        Element member = getBandMemberById(document, bandID, memberID);
        membersElement.removeChild(member);
        saveDocument(document);
    }

    /**
     * Adds an instrument to the band's list of needed instruments
     * @param bandID userID of the band
     * @param instrument instrument the band needs
     * @throws Exception on XML IO errors
     */
    public void addNeededInstrument(String bandID, String instrument) throws Exception {
        String[] instruments = {instrument};
        addNeededInstruments(bandID, instruments);
    }

    /**
     * Adds multiple instruments to a band's list of needed instruments
     * @param bandID userID of the band
     * @param instruments list of instruments the band needs
     * @throws Exception on XML IO errors
     */
    public void addNeededInstruments(String bandID, String[] instruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element element = getElementById(document, bandID);
        Element neededOrGiven = (Element) element.getElementsByTagName("need").item(0);
        Element newInstrument;
        for(String instrument: instruments) {
            newInstrument = document.createElement("instrument");
            newInstrument.setTextContent(instrument);
            neededOrGiven.appendChild(newInstrument);
        }
        saveDocument(document);
    }

    /**
     * Removes a single instrument from a band's list of needed instruments.
     * @param bandID userID of the band
     * @param instrument name of the instrument to be removed
     * @throws Exception on XML IO errors
     */
    public void removeNeededInstrument(String bandID, String instrument) throws Exception {
        String[] instruments = {instrument};
        removeNeededInstruments(bandID, instruments);
    }

    /**
     * Remove multiple instruments from a band's list of needed instruments.
     * @param bandID userID of the band
     * @param instruments list of instruments to be removed
     * @throws Exception on XML IO errors
     */
    public void removeNeededInstruments(String bandID, String[] instruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element band = getElementById(document, bandID);
        Element needed = (Element) band.getElementsByTagName("need").item(0);
        NodeList instrumentElements = needed.getElementsByTagName("*");
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
            needed.removeChild(instrument);
        }
        saveDocument(document);
    }

    /**
     * Specifies which instrument a band member plays
     * @param bandID userID of the band
     * @param memberID userID of the member
     * @param instrument instrument the member plays
     * @throws Exception on XML IO errors
     */
    public void addMemberInstrument(String bandID, String memberID, String instrument) throws Exception {
        String[] instruments = {instrument};
        addMemberInstruments(bandID, memberID, instruments);
    }

    /**
     * Specifies which instruments a band member plays
     * @param bandID userID of the band
     * @param memberID userID of the member
     * @param instruments list of instruments the member plays
     * @throws Exception on XML IO errors
     */
    public void addMemberInstruments(String bandID, String memberID, String[] instruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element member = getBandMemberById(document, bandID, memberID);
        Element newInstrument;
        for(String instrument: instruments) {
            newInstrument = document.createElement("instrument");
            newInstrument.setTextContent(instrument);
            member.appendChild(newInstrument);
        }
        saveDocument(document);
    }

    /**
     * Removes an instrument from the list of instruments a band member plays
     * @param bandID userID of the band
     * @param memberID userID of the band member
     * @param instrument instrument the band member no longer plays
     * @throws Exception on XML IO errors
     */
    public void removeMemberInstrument(String bandID, String memberID, String instrument) throws Exception {
        String[] instruments = {instrument};
        removeMemberInstruments(bandID, memberID, instruments);
    }

    /**
     * Removes multiple instruments from the list of instruments a band member plays
     * @param bandID userID of the band
     * @param memberID userID of the band member
     * @param instruments list of instruments the band member no longer plays
     * @throws Exception on XML IO errors
     */
    public void removeMemberInstruments(String bandID, String memberID, String[] instruments) throws Exception {
        Document document = getDocument(XML_PATH);
        Element member = getBandMemberById(document, bandID, memberID);
        NodeList instrumentElements = member.getElementsByTagName("instrument");
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
            member.removeChild(instrument);
        }
        saveDocument(document);
    }

    /**
     * Retrieves a band member
     * @param document XML document containing band data
     * @param bandID userID of the band
     * @param memberID userID of the band member
     * @return an Element representing a band member
     * @throws Exception on XML IO errors
     */
    private Element getBandMemberById(Document document, String bandID, String memberID) throws Exception {
        XPath xpath =  XPathFactory.newInstance().newXPath();
        String queryString = "//*[@id='"+bandID+"']/members/member[@id='"+memberID+"']";
        Node node = (Node) xpath.evaluate(queryString, document, XPathConstants.NODE);
        return (Element) node;
    }

    private static BandDB database = new BandDB();
    private BandDB() {XML_PATH = "src/database/bands.xml";}
}
