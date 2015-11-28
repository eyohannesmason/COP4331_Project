package models;

import app.BandHeroApp;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class User {

    public User() {
        //Get the default user image
        loadImage("src/images/defaultUserImg.png");
    }
    public User(Element userElement) {
        //Load user image
        loadImage(userElement.getElementsByTagName("profileImage").item(0).getTextContent());
    }

    /**
     * Get the User profile Image.
     * @return A JLabel containing the rendered image.
     */
    public JLabel getProfileImage() {
        return new JLabel(new ImageIcon(userProfileImage));
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

    private BufferedImage userProfileImage;
}
