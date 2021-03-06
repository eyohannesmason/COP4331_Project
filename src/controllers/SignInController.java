package controllers;

import app.BandHeroApp;
import models.Band;
import models.Musician;
import models.User;
import org.w3c.dom.Element;
import utils.BCrypt;
import views.SignInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
                    signIn(view);
                }
            });
        //Add ActionListener to the Register Button
        getView().addRegisterActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BandHeroApp.getInstance().loadRegistrationView();
            }
        });

        //Add ActionListener to the Password Field.
        getView().addPasswordFieldKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { return; }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    signIn(view);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { return; }
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

    private void signIn(SignInView view) {
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
                    User user = new User(getUser(view.getEmail()));
                    if(user.getUserType().equals("musician")) {
                        user = new Musician(getUser(view.getEmail()));
                    } else {
                        user = new Band(getUser(view.getEmail()));
                    }
                    BandHeroApp.getInstance().setUser(user);
                    BandHeroApp.getInstance().loadProfileView(user);
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

    public SignInView getView() {
        return view;
    }

    private SignInView view;
    private static SignInController instance = new SignInController();
}
