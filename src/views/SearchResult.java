package views;

import app.BandHeroApp;
import database.BandDB;
import models.Band;
import models.Musician;
import models.User;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchResult extends BaseView {
    public SearchResult(User user) {
        super(new GridBagLayout());
        this.user = user;
        createComponents();
    }

    @Override
    public void createComponents() {
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        this.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new MetalBorders.Flush3DBorder()));
        c.gridx = 0;
        c.gridy = 0;

        JLabel email = new JLabel(user.getEmail());
        email.setFont(new Font(getFont().getName(), getFont().getStyle(), 20));
        this.add(email, c);

        if(BandHeroApp.getInstance().getCurrentUser().getUserType().equals("band") && user.getUserType().equals("musician")) {
            addMember = new JButton("Add Member");
            c.gridx = 1;
            this.add(addMember, c);

            addMember.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        BandDB.getBandDB().addBandMember(BandHeroApp.getInstance().getCurrentUser().getId(), user.getId());
                    }
                    catch (Exception x) {
                        System.out.println(x.getMessage());
                    }
                }
            });
        }
        else {
            c.gridx = 1;
            this.add(Box.createVerticalStrut(20), c);
        }

    }

    JButton addMember;
    User user;
    GridBagConstraints c;

}
