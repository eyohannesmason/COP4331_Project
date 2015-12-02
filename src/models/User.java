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

    private String id;
    private String userType;
    private String email;
    private BufferedImage userProfileImage;
}
