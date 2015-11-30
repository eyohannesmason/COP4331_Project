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

    public static BandDB getBandDB() {return database;}
    public NodeList getBands() throws Exception {
        return getItems();
    }

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

    public void addBandMember(String bandID, String memberID) throws Exception {
        Document document = getDocument(XML_PATH);
        Element newMember = document.createElement("member");
        newMember.setAttribute("id", memberID);

        Element musician = MusicianDB.getMusicianDB().getMusician(memberID);
        Element newMemberEmailElement = (Element) musician.getElementsByTagName("email").item(0);
        newMember.appendChild(newMemberEmailElement);

        Element band = getElementById(document, bandID);
        Element membersElement = (Element) band.getElementsByTagName("members").item(0);
        membersElement.appendChild(newMember);

        saveDocument(document);
    }

    public void addBandMember(String bandID, String memberID, String instrument) throws Exception {
        String[] instruments = {instrument};
        addBandMember(bandID, memberID, instruments);
    }

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

    public void removeBandMember(String bandID, String memberID) throws Exception {
        Document document = getDocument(XML_PATH);
        Element band = getElementById(document, bandID);
        Element membersElement = (Element) band.getElementsByTagName("members").item(0);
        Element member = getBandMemberById(document, bandID, memberID);
        membersElement.removeChild(member);
        saveDocument(document);
    }

    private Element getBandMemberById(Document document, String bandID, String memberID) throws Exception {
        XPath xpath =  XPathFactory.newInstance().newXPath();
        String queryString = "//*[@id='"+bandID+"']/members/member[@id='"+memberID+"']";
        Node node = (Node) xpath.evaluate(queryString, document, XPathConstants.NODE);
        return (Element) node;
    }

    public void addNeededInstrument(String bandID, String instrument) throws Exception {
        String[] instruments = {instrument};
        addNeededInstruments(bandID, instruments);
    }

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

    public void removeNeedeInstrument(String bandID, String instrument) throws Exception {
        String[] instruments = {instrument};
        removeNeededInstruments(bandID, instruments);
    }

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

    private static BandDB database = new BandDB();
    private BandDB() {XML_PATH = "src/database/bands.xml";}
}
