package app;

import database.UserDB;
import views.RegistrationView;
import views.SignInView;
import controllers.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by costin on 11/20/2015.
 */
public class BandHeroApp {

    private BandHeroApp() {
        if(instance != null) return;
    }

    public static void main(String[] args) {
        BandHeroApp.getInstance().createAndShowGUI();
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("Band Hero");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(500, 400));
        loadRegistrationView();

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void loadRegistrationView() {
        RegistrationView registrationView = new RegistrationView();
        mainFrame.add(registrationView);
    }

    private void loadSignInView() {
        //Create SignInView and add it to the app Frame
        SignInView signInView = new SignInView();
        mainFrame.add(signInView);
        //Add Sign In Action Listener from Controller.
        SignInController.getInstance().createSignInActionListener(signInView);
        try { UserDB.getUserDB().addUser("Costin", "12345", "Musician"); } catch (Exception e) {}
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public static BandHeroApp getInstance() {
        return instance;
    }

    private JFrame mainFrame;
    private static BandHeroApp instance = new BandHeroApp();
}
