package controllers;

import app.BandHeroApp;
import database.BandDB;
import database.MusicianDB;
import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utils.BCrypt;
import views.SignInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInController { // todo possibly change name, not really a controller yet

    /*public static void main(String[] args) throws  Exception {
        SignInController sic = new SignInController();
        sic.addUser("Ronald McDonald", "password", "musician");
        sic.addUser("The White Stripes", "megwhite1", "band");
    }*/

    //Made it a Singleton since I can't think of a reason to ever have
    //have more than one SignInController.
    private SignInController() {
        userDB = UserDB.getUserDB();
        musicianDB = MusicianDB.getMusicianDB();
        bandDB = BandDB.getBandDB();
    }

    public static SignInController getInstance() {
        return instance;
    }

    public void addUser(String name, String password, String type) throws Exception {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Element newUser = userDB.addUser(name, hashedPassword, type);
        if (type.equals("musician")) {
            musicianDB.addMusician(newUser);
        } else if (type.equals("band")) {
            bandDB.addBand(newUser);
        } else {
            throw new IllegalArgumentException("user type invalid");
        }
    }

    private Element getUser(String name) throws Exception {
        NodeList users = userDB.getUsers();
        Element user;
        String currentName;
        for (int i=0; i<users.getLength(); i++) {
            user = (Element) users.item(i);
            currentName = user.getElementsByTagName("name").item(0).getTextContent();
            if (currentName.equals(name)) {
                return user;
            }
        }
        return null;
    }

    public boolean logIn(String name, String password) throws Exception {
        Element user = getUser(name);
        if (user != null && checkPassword(name, password)) {
            userDB.setUserLoggedIn(user.getAttribute("id"));
            return true;
        }
        return false;
    }

    public void createSignInActionListener(SignInView origview) {
        final SignInView view = origview;
        System.out.println(view);
        if(view == null) {
            System.out.println("Could not get Sign In View!!!");
        }
        else {
            //Add action listener to SignIn Button.
            view.addSignInActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Email : " + view.getEmail() + "\nPassword : " + view.getPassword());
                    if(view.getEmail().isEmpty()) {
                        JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Email field can't be empty!");
                    }
                    else if(view.getPassword().isEmpty()) {
                        JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Password field can't be empty!");
                    }
                    else {
                        try {
                            if (logIn(view.getEmail(), view.getPassword())) {
                                JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Login Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Login Failed! Try again, or click Register to create a new account.", "Sign In Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), ex.getMessage(), "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            });
        }
    }

    public boolean checkPassword(String name, String passAttempt) throws Exception {
        Element user = getUser(name);
        if (user != null) {
            String hash = user.getElementsByTagName("password").item(0).getTextContent();
            return BCrypt.checkpw(passAttempt, hash);
        }
        return false;
    }

    private static SignInController instance = new SignInController();
    private static UserDB userDB;
    private static MusicianDB musicianDB;
    private static BandDB bandDB;
}
