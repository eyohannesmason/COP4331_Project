package views;

import app.BandHeroApp;
import models.Band;
import models.Musician;

import javax.swing.*;
import java.awt.*;

public class InstrumentsView extends BaseView{
    public InstrumentsView() {
        super(new GridBagLayout());
        createComponents();
    }

    @Override
    public void createComponents() {
        c = new GridBagConstraints();

        if(BandHeroApp.getInstance().getCurrentUser().getUserType().equals("musician")) {
            addMusicianInstruments();
        } else {
            addBandMemberInstruments();
        }
    }

    private void addMusicianInstruments() {
        Musician user = (Musician) BandHeroApp.getInstance().getCurrentUser();
        try {
            JLabel title = new JLabel("My Instruments: ");
            title.setFont(new Font(getFont().getName(), getFont().getStyle(), 30));
            c.gridx = 0;
            c.gridy = 0;
            this.add(title, c);

            JLabel primary = new JLabel("Primary Instrument: ");
            Font subtitle = new Font(getFont().getName(), getFont().getStyle(), 20);
            primary.setFont(subtitle);
            c.gridy++;
            this.add(primary, c);

            c.gridy++;
            this.add(new JLabel(user.getPrimaryInstrument()), c);

            c.gridy++;
            JLabel secondary = new JLabel("Secondary Instruments: ");
            secondary.setFont(subtitle);
            this.add(secondary, c);
            for(int i = 0; i < user.getSecondaryInstruments().size(); i++) {
                c.gridy ++;
                this.add(new JLabel(user.getSecondaryInstruments().get(i)), c);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addBandMemberInstruments() {
        Band band = (Band) BandHeroApp.getInstance().getCurrentUser();

        JLabel title = new JLabel("Band Member Instruments: ");
        title.setFont(new Font(getFont().getName(), getFont().getStyle(), 30));
        c.gridx = 1;
        c.gridy = 0;
        this.add(title, c);

        c.gridx = 0;
        for(int i = 0; i < band.getMembers().size(); i++) {
            c.gridy = i / 3;
            if(i % 3 == 0) {
                c.gridx = 0;
            } else if (i % 3 == 1) {
                c.gridx = 1;
            } else {
                c.gridx = 2;
            }

            c.gridy++;
            JLabel member = new JLabel(band.getMembers().get(i).getEmail());
            member.setFont(new Font(getFont().getName(), getFont().getStyle(), 20));
            this.add(member, c);

            JLabel primary = new JLabel("Primary Instrument: ");
            Font subtitle = new Font(getFont().getName(), getFont().getStyle(), 15);
            primary.setFont(subtitle);
            c.gridy++;
            this.add(primary, c);

            c.gridy++;
            this.add(new JLabel(band.getMembers().get(i).getPrimaryInstrument()), c);

            c.gridy++;
            JLabel secondary = new JLabel("Secondary Instruments: ");
            secondary.setFont(subtitle);
            this.add(secondary, c);
            for(int y = 0; y < band.getMembers().get(i).getSecondaryInstruments().size(); y++) {
                c.gridy ++;
                this.add(new JLabel(band.getMembers().get(i).getSecondaryInstruments().get(y)), c);
            }
        }

    }

    GridBagConstraints c;
}
