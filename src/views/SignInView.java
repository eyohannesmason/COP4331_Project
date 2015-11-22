package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by costin on 11/19/2015.
 */
public class SignInView extends JPanel {

    public SignInView() {
        //TODO : remove border
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

    public void addSignInActionListener(ActionListener l) {
        signInButton.addActionListener(l);
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
        signInPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(signInPanel, BoxLayout.PAGE_AXIS);
        signInPanel.setLayout(boxLayout);
        //Create Container
        container = new JPanel(new BorderLayout(0, 10));
        //container.setMaximumSize(new Dimension(300, 400));
        //Create Buttons Container
        buttons = new JPanel(new FlowLayout());

        //Create Form Components
        signInLabel = new JLabel("Sign In");
        signInLabel.setMaximumSize(new Dimension(300, 50));
        signInLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        emailLabel = new JLabel("Email Address");
        emailLabel.setMaximumSize(new Dimension(300, 25));
        emailTextField = new JTextField();
        emailTextField.setMaximumSize(new Dimension(300, 25));
        passwordLabel = new JLabel("Password");
        passwordLabel.setMaximumSize(new Dimension(300, 25));
        passwordTextField = new JPasswordField();
        passwordTextField.setMaximumSize(new Dimension(300, 25));
        signInButton = new JButton("Sign In");
        registerButton = new JButton("Register");

        //Add components to Containers
        container.add(signInLabel, BorderLayout.PAGE_START);
        signInPanel.add(emailLabel);
        signInPanel.add(emailTextField);
        signInPanel.add(passwordLabel);
        signInPanel.add(passwordTextField);
        container.add(signInPanel, BorderLayout.CENTER);
        buttons.add(signInButton);
        buttons.add(registerButton);
        container.add(buttons, BorderLayout.PAGE_END);
        //container.setPreferredSize(new Dimension(200, (int) container.getPreferredSize().getHeight()));
        container.setMinimumSize(container.getPreferredSize());
        this.add(container);
    }
    private JPanel buttons;
    private JPanel container;
    private JPanel signInPanel;
    private JButton signInButton;
    private JButton registerButton;
    private JLabel signInLabel;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordTextField;
}
