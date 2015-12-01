package controllers;

import app.BandHeroApp;
import views.NavMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavMenuController implements IController {
    public NavMenuController() {
    }

    /**
     * Get the NavMenu associated with the controller.
     * @return The NavMenu of the controller.
     */
    public NavMenu getView() {
        return view;
    }

    public void setView(NavMenu view) {
        this.view = view;
        addActionListeners();
    }

    private void addActionListeners() {
        addSignOutListener();
    }

    private void addSignOutListener() {
        getView().addSignOutButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BandHeroApp.getInstance().loadSignInView();
            }
        });

        getView().addViewProfileButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BandHeroApp.getInstance().loadProfileView(BandHeroApp.getInstance().getCurrentUser());
            }
        });
    }

    private NavMenu view;
}
