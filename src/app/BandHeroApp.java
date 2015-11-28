package app;

import controllers.IController;
import controllers.ProfileController;
import controllers.RegistrationController;
import controllers.SignInController;
import models.User;
import sun.java2d.cmm.Profile;
import views.ProfileView;
import views.RegistrationView;
import views.SignInView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        loadSignInView();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void loadProfileView(User user) {
        controller = new ProfileController(user, new ProfileView());
        mainFrame.setContentPane(((ProfileController) controller).getView());
        mainFrame.revalidate();
    }

    public void loadRegistrationView() {
        controller = RegistrationController.getInstance();
        ((RegistrationController) controller).setView(new RegistrationView());
        ((RegistrationController) controller).addActionListeners();
        mainFrame.setContentPane(((RegistrationController) controller).getView());
        mainFrame.revalidate();
    }

    public void loadSignInView() {
        //Create SignInView and add it to the app Frame
        controller = SignInController.getInstance();
        ((SignInController) controller).setView(new SignInView());
        ((SignInController) controller).addActionListeners();
        mainFrame.setContentPane(((SignInController) controller).getView());
        mainFrame.revalidate();
    }

    public IController getController() { return controller; }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public static BandHeroApp getInstance() {
        return instance;
    }

    private IController controller;
    private JFrame mainFrame;
    private static BandHeroApp instance = new BandHeroApp();
}
