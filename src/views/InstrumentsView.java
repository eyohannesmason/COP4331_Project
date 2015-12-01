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
                Node instruments = MusicianDB.getMusicianDB().getMusician(user.getId()).getElementsByTagName("instruments").item(0);
                JLabel primary = new JLabel("Primary Instrument: ");
                Font subtitle = new Font(getFont().getName(), getFont().getStyle(), 20);
                primary.setFont(subtitle);
                c.gridy++;
                this.add(primary, c);

                c.gridy++;
                int start = 0;
                for(int i = 0; i < instruments.getChildNodes().getLength(); i++) {
                    start++;
                    if(!instruments.getChildNodes().item(i).getTextContent().isEmpty()) {
                        this.add(new JLabel(instruments.getChildNodes().item(1).getTextContent().trim()), c);
                        break;
                    }
                }
                c.gridy++;
                JLabel secondary = new JLabel("Secondary Instruments: ");
                secondary.setFont(subtitle);
                this.add(secondary, c);
                for(int i = start; i < instruments.getChildNodes().getLength(); i++) {
                    c.gridy ++;
                    if(!instruments.getChildNodes().item(i).getTextContent().isEmpty()) {
                        this.add(new JLabel(instruments.getChildNodes().item(i).getTextContent().trim()), c);
                    }
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    GridBagConstraints c;
}
