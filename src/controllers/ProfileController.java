package controllers;

import models.User;
import views.ProfileView;

public class ProfileController implements IController{

    public ProfileController(User user) {
        this.user = user;
    }

    /**
     * Set the ProfileView of the controller.
     * @param view ProfileView to be used.
     */
    public void setView(ProfileView view) {
        this.view = view;
    }

    /**
     * Get current user.
     * @return The current User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Get current profile view.
     * @return Profile View.
     */
    public ProfileView getView() {
        return view;
    }


    private User user;
    private ProfileView view;
}
