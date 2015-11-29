package views;

import javax.swing.*;
import java.awt.*;

public class NavMenu extends BaseView {

    public NavMenu() {
        super(new GridBagLayout());
        createComponents();
        this.setMaximumSize(getPreferredSize());
    }

    public void createComponents() {
        //Create Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        //Add Home Button
        homeButton = new JButton("Home");
        c.insets = new Insets(2,2,2,2);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(homeButton, c);

        //Add Edit Profile Button
        editProfileButton = new JButton("Edit Profile");
        c.gridx = 0;
        c.gridy = 1;
        this.add(editProfileButton, c);

        //Add Messages Button
        messagesButton = new JButton("Messages");
        c.gridx = 0;
        c.gridy = 2;
        this.add(messagesButton, c);

        //Add Empty Space to push buttons to top
        c.gridy = 3;
        c.weighty = 1;
        this.add(Box.createHorizontalGlue(), c);
    }

    @Override
    public Dimension getPreferredSize() {
        int w = 0;
        int h = 0;
        for(int i = 0; i < this.getComponents().length; i++) {
            if(w < this.getComponent(i).getPreferredSize().getWidth()) {
                w = (int) this.getComponent(i).getPreferredSize().getWidth();
            }
            h += this.getComponent(i).getPreferredSize().getHeight();
        }
        return new Dimension(w, h);
    }

    JButton homeButton,
            editProfileButton,
            messagesButton;
}
