package controllers;

import database.BandDB;
import database.MusicianDB;
import database.UserDB;

public class SearchController implements IController {



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
