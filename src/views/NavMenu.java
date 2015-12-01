package views;

import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class NavMenu extends BaseView {

    public NavMenu() {
        super(new GridBagLayout());
        createComponents();
        this.setMaximumSize(getPreferredSize());
    }

    /**
     * Add an ActionListener to the Home Button
     * @param l The ActionListener to add.
     */
    public void addViewProfileButtonActionListener(ActionListener l) {
        viewProfileButton.addActionListener(l);
    }

    /**
     * Add an ActionListener to the EditProfile Button
     * @param l The ActionListener to add.
     */
    public void addEditProfileButtonActionListener(ActionListener l) {
        editProfileButton.addActionListener(l);
    }

    /**
     * Add an ActionListener to the Messages Button
     * @param l The ActionListener to add.
     */
    public void addMessagesButtonActionListener(ActionListener l) {
        messagesButton.addActionListener(l);
    }

    /**
     * Add an ActionListener to the SignOut Button
     * @param l The ActionListener to add.
     */
    public void addSignOutButtonActionListener(ActionListener l) {
        signOutButton.addActionListener(l);
    }

    public void createComponents() {
        //Create GridBag Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        //Add Buttons Container to NavMenu
        c.gridx = 0;
        c.gridy = 0;
        this.add(createButtons(), c);

        //Add Empty Space to push buttons to top
        c.gridx = 0;
        c.gridy = 1;
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

    private JPanel createButtons() {
        //Create Panel
        JPanel buttonsContainer = new JPanel(new GridBagLayout());
        buttonsContainer.setBorder(new MetalBorders.Flush3DBorder());

        //Create Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        //Add Home Button
        viewProfileButton = new JButton("View Profile");
        viewProfileButton.setIcon(new ImageIcon("src/images/userIcon.png"));
        c.insets = new Insets(2,2,2,2);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        buttonsContainer.add(viewProfileButton, c);

        //Add Edit Profile Button
        editProfileButton = new JButton("Edit Profile");
        editProfileButton.setIcon(new ImageIcon("src/images/editIcon.png"));
        c.gridx = 0;
        c.gridy = 1;
        buttonsContainer.add(editProfileButton, c);

        //Add Messages Button
        messagesButton = new JButton("Messages");
        messagesButton.setIcon(new ImageIcon("src/images/messagesIcon.png"));
        c.gridx = 0;
        c.gridy = 2;
        buttonsContainer.add(messagesButton, c);

        //Add SignOut Button
        signOutButton = new JButton("Sign Out");
        signOutButton.setIcon(new ImageIcon("src/images/signOffIcon.png"));
        c.gridx = 0;
        c.gridy = 3;
        buttonsContainer.add(signOutButton, c);

        return buttonsContainer;
    }


    JButton viewProfileButton,
            editProfileButton,
            messagesButton,
            signOutButton;
}
