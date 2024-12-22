package ro.itarcea.spaceInvaders.gui;

import ro.itarcea.spaceInvaders.service.DbService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUpDialog extends JDialog implements ActionListener, GameComponent {
    Display d;
    private final DbService service;
    JPanel p;
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");
    JLabel nameLabel = new JLabel("Name:");
    JLabel passwordLabel = new JLabel("Password:");
    JTextField nameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    public SignUpDialog(String name, Display d, DbService service) {
        this.d = d;
        this.service = service;
        this.setTitle(name);

        setDesign(okButton);
        setDesign(cancelButton);
        setDesign(nameLabel);
        setDesign(passwordLabel);
        setDesign(nameField);
        setDesign(passwordField);

        this.setLayout(new GridLayout(3, 2));
        this.add(nameLabel);
        this.add(nameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(okButton);
        this.add(cancelButton);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        this.setSize(500, 300);
        this.setLocation(d.getLocation().x + 300, d.getLocation().y + 300);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
           okPressed();
        }
        if (e.getActionCommand().equals("Cancel")) {
            System.out.println("Cancel");
            d.buttonPressed = false;
            this.dispose();
        }
    }
    public void okPressed() {
        if (nameField.getText().isEmpty()){
            ErrorMessage em = new ErrorMessage("","User Field must not be empty", d, this);
            return;
        }
        if (passwordField.getText().isEmpty()){
            ErrorMessage em = new ErrorMessage("","Password Field must not be empty", d, this);
            return;
        }
        boolean saved = false;
        try {
            service.newUser(nameField.getText(), new String(passwordField.getPassword()));
            saved = true;
            ErrorMessage em = new ErrorMessage("","User " + nameField.getText() + " successfully saved", d, this);

        } catch (SQLException ex) {
            ErrorMessage em = new ErrorMessage("","Username already picked", d, this);
            //throw new RuntimeException(ex);
        }
        if(saved) {
            d.buttonPressed = false;
            d.setUser(nameField.getText());
            this.dispose();
        }
    }
    public DbService getService() {
        return service;
    }
}
