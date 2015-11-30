package views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class SignInView extends BaseView {

    public SignInView() {
        super(new GridBagLayout());
        createComponents();
    }

    /**
     * Add an ActionListener to the SignIn Button.
     * @param l The ActionListener to add.
     */
    public void addSignInActionListener(ActionListener l) {
        signInButton.addActionListener(l);
    }

    /**
     * Add an ActionListener to the Register Button.
     * @param l The ActionListener to add.
     */
    public void addRegisterActionListener(ActionListener l) {
        registerButton.addActionListener(l);
    }

    /**
     * Add a KeyListener to the Password Field.
     * @param l The KeyListener to add.
     */
    public void addPasswordFieldKeyListener(KeyListener l) {
        passwordTextField.addKeyListener(l);
    }

    /**
     * Get the user entered Email.
     * @return Email entered by user.
     */
    public String getEmail() {
        return emailTextField.getText().trim();
    }

    /**
     * Get the user entered Password.
     * @return Password entered by user.
     */
    public String getPassword() {
        return passwordTextField.getText().trim();
    }

    protected void createComponents() {
        //Create Constraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        //Create Sign In panel
        signInPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(signInPanel, BoxLayout.PAGE_AXIS);
        signInPanel.setLayout(boxLayout);
        //Create Container
        container = new JPanel(new BorderLayout());
        //Create Buttons Container
        buttons = new JPanel(new FlowLayout());

        //Create Form Components
        signInLabel = new JLabel("Sign In");
        signInLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        emailLabel = new JLabel("Email Address");
        emailTextField = new JTextField();
        passwordLabel = new JLabel("Password");
        passwordTextField = new JPasswordField();
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

        c.gridx = 0;
        c.gridy = 0;
        this.add(container, c);
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
