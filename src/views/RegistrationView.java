package views;

import app.BandHeroApp;
import controllers.SignInController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationView extends JPanel {

    public RegistrationView() {
        this.setLayout(new FlowLayout());
        createComponents();
        this.setPreferredSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        int w = 0;
        int h = 0;
        for(int i = 0; i < this.getComponents().length; i++) {
            w += this.getComponent(i).getPreferredSize().getWidth();
            h += this.getComponent(i).getPreferredSize().getHeight();
        }
        return new Dimension(w, h);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void addRegisterActionListener(ActionListener l) {
        registerButton.addActionListener(l);
    }

    public String getEmail() {
        return emailTextField.getText().trim();
    }

    public String getPassword() {
        return passwordTextField.getText().trim();
    }

    private void createComponents() {
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
        buttons.add(registerButton);
        container.add(buttons, BorderLayout.PAGE_END);
        container.setMinimumSize(container.getPreferredSize());
        this.add(container);
        addActionListeners();
    }

    private void addActionListeners() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String     email = emailTextField.getText(),
                        password = passwordTextField.getText().trim(),
                          repeat = repeatField.getText().trim();
                if (email.isEmpty() || password.isEmpty() || repeat.isEmpty()) {
                    showPopUpAlert("All fields are required.");
                }
                else if (!password.equals(repeat)) {
                    showPopUpAlert("Passwords don't match.");
                } else {

                    String type = (musicianButton.isSelected()) ? "musician" : "band";
                    try {
                        SignInController.getInstance().addUser(email, password, type);
                        showPopUpAlert("New user added.");
                        // todo trigger view change
                    } catch (Exception ex) {
                        // todo should be 'user already exists' error
                        showPopUpAlert("Error adding new user.");
                    }
                }
            }
        });
    }

    private void showPopUpAlert(String message) {
        JOptionPane.showMessageDialog(BandHeroApp.getInstance().getMainFrame(), message);
    }

    private JPanel buttons, container, registerPanel;
    private JButton registerButton;
    private JLabel registerLabel, emailLabel, passwordLabel, repeatLabel;
    private JTextField emailTextField;
    private JPasswordField passwordTextField, repeatField;
    private JRadioButton musicianButton, bandButton;
}
