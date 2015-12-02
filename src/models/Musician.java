package models;

import database.MusicianDB;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class Musician extends User {
    public Musician() {
        super();
    }
    public Musician(Element userElement) {
        super(userElement);
        secondaryInstruments = new ArrayList<>();
        try {
            Node instr = MusicianDB.getMusicianDB().getMusician(getId()).getElementsByTagName("instruments").item(0);
            for (int i = 0; i < instr.getChildNodes().getLength(); i++) {
                if(!instr.getChildNodes().item(i).getTextContent().isEmpty()) {
                    if(instr.getChildNodes().item(i).getNodeName().trim().equals("primary")) {
                        primaryInstrument = instr.getChildNodes().item(i).getTextContent().trim();
                    }
                    else {
                        secondaryInstruments.add(instr.getChildNodes().item(i).getTextContent().trim());
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getSecondaryInstruments() {
        return secondaryInstruments;
    }
    public String getPrimaryInstrument() {
        return primaryInstrument;
    }

    private String primaryInstrument;
    private ArrayList<String> secondaryInstruments;
}
