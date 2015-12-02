package controllers;

import app.BandHeroApp;
import database.BandDB;
import database.MusicianDB;
import database.UserDB;
import models.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import views.SearchResults;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SearchController implements IController {


    public ArrayList<User> searchPlayers(String instrumentString) throws Exception {
        String[] instruments = instrumentString.split(" ");
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

        ArrayList<User> users = new ArrayList<>();
        for(Node player: uniquePlayers) {
            System.out.println(((Element) player).getAttribute("id"));
            for(int i = 0; i < player.getChildNodes().getLength(); i++) {
                if(player.getChildNodes().item(i).getNodeName().equals("email")) {
                    users.add(new User(userDB.getUser(player.getChildNodes().item(i).getTextContent())));
                    break;
                }
            }
        }
        return users;
    }


    public NodeList searchGiven(String instrumentString) throws Exception {
        String[] instruments = instrumentString.split(" ");
        Element tmp = null;
        Document document = null;
        HashMap<Element, Document> holder = musicianDB.getBlankElementAndDocument("givenHolder");

        for(HashMap.Entry<Element, Document> entry: holder.entrySet()) {
            tmp = entry.getKey();
            document = entry.getValue();
        }
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
        if (tmp != null && document != null) {
            for(Node musician: uniqueMusicians) {
                tmp.appendChild(document.importNode((Element) musician, true));
            }
            return tmp.getElementsByTagName("*");
        }
        return null;
    }

    public NodeList searchNeeded(String instrumentString) throws Exception {
        int i;
        String[] instruments = instrumentString.split(" ");
        Element tmp = null;
        Element bTmp = null, mTmp = null;
        Document mDoc = null, bDoc = null;

        HashMap<Element, Document> mHolder = musicianDB.getBlankElementAndDocument("tmp");
        HashMap<Element, Document> bHolder = bandDB.getBlankElementAndDocument("tmp");

        for(HashMap.Entry<Element, Document> entry: mHolder.entrySet()) {
            mTmp = entry.getKey();
            mDoc = entry.getValue();
        }

        for(HashMap.Entry<Element, Document> entry: bHolder.entrySet()) {
            bTmp = entry.getKey();
            bDoc = entry.getValue();
        }

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
        if (mTmp != null && bTmp != null) {
            for (Node musician: uniqueMusicians) {
                mTmp.appendChild(mDoc.importNode((Element) musician, true));
            }
            for (Node band: uniqueBands) {
                mTmp.appendChild(mDoc.importNode((Element) band, true));
            }
            return mTmp.getElementsByTagName("*");
        }
        return null;
    }

    public static SearchController getInstance() {return controller;}
    private SearchController() {
        userDB = UserDB.getUserDB();
        musicianDB = MusicianDB.getMusicianDB();
        bandDB = BandDB.getBandDB();
    }

    public void addActionListeners() {
        ((ProfileController) BandHeroApp.getInstance().getController()).getView().getSearchBar().addSearchButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ((ProfileController) BandHeroApp.getInstance().getController()).getView().getDynamicContentPanel().setContent(new SearchResults(SearchController.getInstance().searchPlayers(((ProfileController) BandHeroApp.getInstance().getController()).getView().getSearchBar().getSearchText())));
                }
                catch (Exception ex) {
                    System.out.println("Search Exception.");
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    private static SearchController controller = new SearchController();
    private static UserDB userDB;
    private static MusicianDB musicianDB;
    private static BandDB bandDB;
}
