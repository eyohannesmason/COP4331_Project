package models;

import app.BandHeroApp;
import database.Database;
import database.MusicianDB;
import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class User {

    public User() {
        //Get the default user image
        loadImage("src/images/defaultUserImg.png");
    }
    public User(Element userElement) {
        //Load user image
        loadImage(userElement.getElementsByTagName("profileImage").item(0).getTextContent());
        email = userElement.getElementsByTagName("email").item(0).getTextContent();
        userType = userElement.getElementsByTagName("type").item(0).getTextContent();
        id = userElement.getAttribute("id");
        secondaryInstruments = new ArrayList<>();
        try {
            Node instr = MusicianDB.getMusicianDB().getMusician(id).getElementsByTagName("instruments").item(0);
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

    /**
     * Get the User profile Image.
     * @return An ImageIcon containing the rendered image.
     */
    public ImageIcon getProfileImage() {
        return new ImageIcon(userProfileImage);
    }

    /**
     * Read the userProfileImage from a file at the given path.
     * @param path The path to the image.
     */
    private void loadImage(String path) {
        try {
            userProfileImage = ImageIO.read(new File(path));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getEmail() {
        return email;
    }
    public String getUserType() {
        return userType;
    }
    public String getId() {
        return id;
    }
    public ArrayList<String> getSecondaryInstruments() {
        return secondaryInstruments;
    }
    public String getPrimaryInstrument() {
        return primaryInstrument;
    }

    private String primaryInstrument;
    private ArrayList<String> secondaryInstruments;
    private String id;
    private String userType;
    private String email;
    private BufferedImage userProfileImage;
}
