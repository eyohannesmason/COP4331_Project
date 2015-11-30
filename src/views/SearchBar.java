package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchBar extends BaseView {

    public SearchBar() {
        super(new GridBagLayout());
        createComponents();
    }

    /**
     * Add an ActionListener to the Search Text Field.
     * @param l The ActionListener to add.
     */
    public void addSearchFieldActionListener(ActionListener l) {
        searchTextField.addActionListener(l);
    }

    /**
     * Add an ActionListener to the Search Button.
     * @param l The ActionListener to add.
     */
    public void addSearchButtonActionListener(ActionListener l) {
        searchButton.addActionListener(l);
    }

    @Override
    public void createComponents() {
        //Create constraints
        GridBagConstraints c = new GridBagConstraints();

        //Add Invisible Spacing.
        c.weightx = 0.25;
        c.gridx = 0;
        c.gridy = 0;
        this.add(Box.createHorizontalGlue(), c);

        //Initialize Search Field
        searchTextField = new JTextField();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 20, 0, 5);
        c.weightx = 0.50;
        c.gridx = 1;
        c.gridy = 0;
        this.add(searchTextField, c);

        //Initialize Search Button
        searchButton = new JButton("Search");
        searchButton.setIcon(new ImageIcon("src/images/searchIcon.png"));
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        this.add(searchButton, c);

        //Add Invisible Spacing
        c.weightx = 0.25;
        c.gridx = 3;
        c.gridy = 0;
        this.add(Box.createHorizontalGlue(), c);

    }

    @Override
    public Dimension getPreferredSize() {
        int w = 0;
        int h = 0;
        for(int i = 0; i < this.getComponents().length; i++) {
            w += (int) this.getComponent(i).getPreferredSize().getWidth();
            if(h < this.getComponent(i).getPreferredSize().getWidth()) {
                h = (int) this.getComponent(i).getPreferredSize().getHeight();
            }
        }
        return new Dimension(w, h);
    }

    JTextField searchTextField;
    JButton searchButton;
}
