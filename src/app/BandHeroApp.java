package app;

import controllers.*;
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
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        loadSignInView();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void loadProfileView(User user) {
        controller = new ProfileController(user);
        ((ProfileController) getController()).setView(new ProfileView());
        ((ProfileController) getController()).getNavMenuController().setView(((ProfileController) controller).getView().getNavMenu());
        SearchController.getInstance().addActionListeners();
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

    public void setUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public IController getController() { return controller; }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public static BandHeroApp getInstance() {
        return instance;
    }

    private User currentUser;
    private IController controller;
    private JFrame mainFrame;
    private static BandHeroApp instance = new BandHeroApp();
}
