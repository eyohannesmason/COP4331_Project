package views;

import app.BandHeroApp;
import database.MusicianDB;
import models.User;
import org.w3c.dom.Node;

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

        JLabel title = new JLabel("My Instruments: ");
        title.setFont(new Font(getFont().getName(), getFont().getStyle(), 30));
        c.gridx = 0;
        c.gridy = 0;
        this.add(title, c);

        addInstruments();
    }

    private void addInstruments() {
        User user = BandHeroApp.getInstance().getCurrentUser();
        if(user.getUserType().equals("musician")) {
            try {
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
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    GridBagConstraints c;
}
