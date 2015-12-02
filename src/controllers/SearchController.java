package controllers;

import database.BandDB;
import database.MusicianDB;
import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashSet;
import java.util.Set;

public class SearchController implements IController {

    public NodeList searchPlayers(String instrumentString) throws Exception {
        String[] instruments = instrumentString.split(" ");
        Element players = userDB.getBlankElement("players");
        NodeList currentPlayers;
        Node currentPlayer;
        
        Set<Node> uniquePlayers = new HashSet<Node>();
        for(String instrument: instruments) {
            currentPlayers = musicianDB.getMusiciansByInstrument(instrument);
            for (int i=0; i<currentPlayers.getLength(); i++) {
                currentPlayer = currentPlayers.item(i);
                if (currentPlayer != null) {
                    uniquePlayers.add(currentPlayer);
                }
            }
        }
        for(Node player: uniquePlayers) {
            players.appendChild((Element) player);
        }
        return players.getElementsByTagName("*");
    }


    public NodeList searchGiven(String instrumentString) throws Exception {
        String[] instruments = instrumentString.split(" ");
        Element tmp = userDB.getBlankElement("givenHolder");
        NodeList currentMusicians;
        Node currentMusician;

        Set<Node> uniqueMusicians = new HashSet<>();
        for (String instrument: instruments) {
            currentMusicians = musicianDB.getMusiciansByGiven(instrument);
            for (int i=0; i<currentMusicians.getLength(); i++) {
                currentMusician = currentMusicians.item(i);
                if (currentMusician != null) {
                    uniqueMusicians.add(currentMusician);
                }
            }
        }
        for(Node musician: uniqueMusicians) {
            tmp.appendChild((Element) musician);
        }
        return tmp.getElementsByTagName("*");
    }

    public NodeList searchNeeded(String instrumentString) throws Exception {
        int i;
        String[] instruments = instrumentString.split(" ");
        Element tmp = userDB.getBlankElement("neededHolder");
        NodeList currentMusicians, currentBands;
        Node currentMusician, currentBand;
        Set<Node> uniqueMusicians = new HashSet<>(),
                      uniqueBands = new HashSet<>();
        for (String instrument: instruments) {
            currentMusicians = musicianDB.getMusiciansByNeeded(instrument);
            currentBands = bandDB.getBandsByNeeded(instrument);
            for(i=0; i<currentMusicians.getLength(); i++) {
                currentMusician = currentMusicians.item(i);
                if (currentMusician != null) {
                    uniqueMusicians.add(currentMusician);
                }
            }
            for(i=0; i<currentBands.getLength(); i++) {
                currentBand = currentBands.item(i);
                if (currentBand != null) {
                    uniqueBands.add(currentBand);
                }
            }
        }
        for (Node musician: uniqueMusicians) {
            tmp.appendChild((Element) musician);
        }
        for (Node band: uniqueBands) {
            tmp.appendChild((Element) band);
        }
        return tmp.getElementsByTagName("*");
    }

    public static SearchController getInstance() {return controller;}
    private SearchController() {
        userDB = UserDB.getUserDB();
        musicianDB = MusicianDB.getMusicianDB();
        bandDB = BandDB.getBandDB();
    }
    private static SearchController controller = new SearchController();
    private static UserDB userDB;
    private static MusicianDB musicianDB;
    private static BandDB bandDB;
}
