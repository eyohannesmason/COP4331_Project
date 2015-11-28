package views;

import app.BandHeroApp;
import controllers.ProfileController;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends BaseView {

    public ProfileView() {
        super(new BorderLayout());
    }

    public void createComponents() {
        //Add User Image
        userImage = ((ProfileController) BandHeroApp.getInstance().getController()).getUser().getProfileImage();
        userImage.setPreferredSize(new Dimension(150, 150));

        //Add Components to Container
        this.add(userImage, BorderLayout.PAGE_START);


    }

    JLabel userImage;
}
