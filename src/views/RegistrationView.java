package views;

import app.BandHeroApp;
import controllers.RegistrationController;
import controllers.SignInController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationView extends BaseView {
    public RegistrationView() {
       super(new GridBagLayout());
        createComponents();
    }

    public void addRegisterActionListener(ActionListener l) {
        registerButton.addActionListener(l);
    }

    public void addBackActionListener(ActionListener l) {
        backButton.addActionListener(l);
    }

    public String getEmail() {
        return emailTextField.getText().trim();
    }

    public String getPassword() {
        return passwordTextField.getText().trim();
    }

    public String getRepeatPassword() { return repeatField.getText(); }

    public boolean isMusician() {
        return musicianButton.isSelected() ? true : false;
    }

    protected void createComponents() {
        //Create Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        //Create Sign In panel
        registerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(registerPanel, BoxLayout.PAGE_AXIS);
        registerPanel.setLayout(boxLayout);
        //Create Container
        container = new JPanel(new BorderLayout(0, 10));
        //container.setMaximumSize(new Dimension(300, 400));
        //Create Buttons Container
        buttons = new JPanel(new FlowLayout());

        //Create Form Components
        registerLabel = new JLabel("Create New Account");
        registerLabel.setMaximumSize(new Dimension(300, 50));
        registerLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        emailLabel = new JLabel("Email Address");
        emailLabel.setMaximumSize(new Dimension(300, 25));
        emailTextField = new JTextField();
        emailTextField.setMaximumSize(new Dimension(300, 25));
        passwordLabel = new JLabel("Password");
        passwordLabel.setMaximumSize(new Dimension(300, 25));
        repeatLabel = new JLabel("Repeat Password");
        repeatLabel.setMaximumSize(new Dimension(300, 25));
        passwordTextField = new JPasswordField();
        passwordTextField.setMaximumSize(new Dimension(300, 25));
        repeatField = new JPasswordField();
        repeatField.setMaximumSize(new Dimension(300, 25));
        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        musicianButton = new JRadioButton("musician", true);
        bandButton = new JRadioButton("band");
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(musicianButton);
        radioGroup.add(bandButton);

        //Add components to Containers
        container.add(registerLabel, BorderLayout.PAGE_START);
        registerPanel.add(emailLabel);
        registerPanel.add(emailTextField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordTextField);
        registerPanel.add(repeatLabel);
        registerPanel.add(repeatField);
        registerPanel.add(musicianButton);
        registerPanel.add(bandButton);

        container.add(registerPanel, BorderLayout.CENTER);
        buttons.add(backButton);
        buttons.add(registerButton);
        container.add(buttons, BorderLayout.PAGE_END);
        container.setMinimumSize(container.getPreferredSize());

        c.gridx = 0;
        c.gridy = 0;
        this.add(container);
    }

    public void showPopUpAlert(String message) {
        JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), message);
    }

    private JPanel buttons, container, registerPanel;
    private JButton registerButton, backButton;
    private JLabel registerLabel, emailLabel, passwordLabel, repeatLabel;
    private JTextField emailTextField;
    private JPasswordField passwordTextField, repeatField;
    private JRadioButton musicianButton, bandButton;
}
