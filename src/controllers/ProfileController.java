package controllers;

import models.User;
import views.ProfileView;

public class ProfileController implements IController{

    public ProfileController(User user, ProfileView view) {
        this.user = user;
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
