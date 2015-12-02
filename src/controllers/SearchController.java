package controllers;

import app.BandHeroApp;
import com.sun.webkit.dom.NodeListImpl;
import database.BandDB;
import database.MusicianDB;
import database.UserDB;
import models.User;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import views.SearchBar;
import views.SearchResults;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchController implements IController {

    public ArrayList<User> searchPlayers(String instrumentString) throws Exception {
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
