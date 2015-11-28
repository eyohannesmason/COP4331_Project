package controllers;

import app.BandHeroApp;
import org.w3c.dom.Element;
import utils.BCrypt;
import views.RegistrationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationController extends AuthenticationController {

    private RegistrationController() {
        super();
    }

    public static RegistrationController getInstance() { return instance; }

    public void addUser(String email, String password, String type) throws Exception {
        // todo throw exception if user already exists
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Element newUser = userDB.addUser(email, hashedPassword, type);
        if (type.equals("musician")) {
            musicianDB.addMusician(newUser);
        } else if (type.equals("band")) {
            bandDB.addBand(newUser);
        } else {
            throw new IllegalArgumentException("user type invalid");
        }
    }

    public void setView(RegistrationView view) {
        this.view = view;
    }

    public RegistrationView getView() {
        return view;
    }

    public void addActionListeners() {
        getView().addRegisterActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String  email = getView().getEmail().trim(),
                        password = getView().getPassword().trim(),
                        repeat = getView().getRepeatPassword().trim();
                if (email.isEmpty() || password.isEmpty() || repeat.isEmpty()) {
                    getView().showPopUpAlert("All fields are required.");
                }
                else if (!password.equals(repeat)) {
                    getView().showPopUpAlert("Passwords don't match.");
                } else {

                    String type = (getView().isMusician()) ? "musician" : "band";
                    try {
                        RegistrationController.getInstance().addUser(email, password, type);
                        getView().showPopUpAlert("New user added.");
                        BandHeroApp.getInstance().loadSignInView();
                    } catch (Exception ex) {
                        // todo should be 'user already exists' error
                        getView().showPopUpAlert("Error adding new user.");
                    }
                }
            }
        });

        //Add back listener
        getView().addBackActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BandHeroApp.getInstance().loadSignInView();
            }
        });
    }

    private RegistrationView view;
    private static RegistrationController instance = new RegistrationController();
}
