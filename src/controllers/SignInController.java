package controllers;

import app.BandHeroApp;
import models.User;
import org.w3c.dom.Element;
import utils.BCrypt;
import views.SignInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInController extends AuthenticationController {

    //Made it a Singleton since I can't think of a reason to ever have
    //have more than one SignInController.
    private SignInController() {
        super();
    }


    public static SignInController getInstance() { return instance; }

    public boolean logIn(String email, String password)  {
        Element     user = null;
        boolean loggedIn = false;
        try {
            user = getUser(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        if (user != null) {
            try {
                loggedIn = checkPassword(email, password);
                if (loggedIn) {userDB.setUserLoggedIn(user.getAttribute("id"));}
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return loggedIn;
    }

    public void addActionListeners() {
        //Add action listener to SignIn Button.
        getView().addSignInActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(view.getEmail().isEmpty()) {
                        JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Email field can't be empty!");
                    }
                    else if(view.getPassword().isEmpty()) {
                        JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Password field can't be empty!");
                    }
                    else {
                        try {
                            String email = view.getEmail(), password = view.getPassword();
                            if (logIn(email, password)) {
                                BandHeroApp.getInstance().loadProfileView(new User(getUser(view.getEmail())));
                                //JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Login Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), "Login Failed! Try again, or click Register to create a new account.", "Sign In Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            System.out.println(ex.getMessage());
                        }
                    }

                }
            });
        getView().addRegisterActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BandHeroApp.getInstance().loadRegistrationView();
            }
        });
    }

    public boolean checkPassword(String email, String passAttempt) throws Exception {
        Element user = getUser(email);
        if (user != null) {
            String hash = user.getElementsByTagName("password").item(0).getTextContent();
            return BCrypt.checkpw(passAttempt, hash);
        }
        return false;
    }

    public void setView(SignInView view) {
        this.view = view;
    }

    public SignInView getView() {
        return view;
    }

    private SignInView view;
    private static SignInController instance = new SignInController();
}
