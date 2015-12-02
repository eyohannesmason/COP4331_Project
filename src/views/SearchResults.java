package views;

import database.UserDB;
import models.User;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchResults extends BaseView {
    public SearchResults(ArrayList<User> results) {
        super(new GridBagLayout());
        this.results = results;
        createComponents();
    }

    @Override
    public void createComponents() {
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;

        JLabel title = new JLabel("Search Results : ");
        title.setFont(new Font(getFont().getName(), getFont().getStyle(), 30));
        this.add(title, c);

        /*for(int i = 0; i < results.getLength(); i++) {
            if(results.item(i).getNodeName().equals("band") || results.item(i).getNodeName().equals("musician")) {
                c.gridy++;
                for(int y = 0; y < results.item(i).getChildNodes().getLength(); y++) {
                    if(results.item(i).getChildNodes().item(y).getNodeName().equals("email")) {
                        try {
                            this.add(new SearchResult(new User(UserDB.getUserDB().getUser(results.item(i).getChildNodes().item(y).getTextContent().trim()))));
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }*/

        for(int i = 0; i < results.size(); i++) {
            c.gridy++;
            try {
                this.add(new SearchResult(results.get(i)), c);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    ArrayList<User> results;
    GridBagConstraints c;
}
