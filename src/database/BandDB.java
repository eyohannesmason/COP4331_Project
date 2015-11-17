package database;

import org.w3c.dom.NodeList;

public class BandDB extends Database {

    public static BandDB getBandDB() {return database;}
    public NodeList getBands() throws Exception {
        return getItems("band");
    }
    
    private static BandDB database = new BandDB();
    private BandDB() {XML_PATH = "src/database/bands.xml";}
}
