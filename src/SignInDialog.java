import service.DbService;

import java.sql.SQLException;

public class SignInDialog extends SignUpDialog {
    private final Display d;

    public SignInDialog(String name, Display d, DbService service) {
        super(name, d, service);
        this.d = d;
    }

    @Override
    public void okPressed() {
        if (nameField.getText().isEmpty()){
            ErrorMessage em = new ErrorMessage("", "User Field must not be empty", d, this);
            return;
        }
        if (passwordField.getText().isEmpty()){
            ErrorMessage em = new ErrorMessage("","Password Field must not be empty", d, this);
            return;
        }
        try {
            if (!getService().userExists(nameField.getText(), new String(passwordField.getPassword()))){
                ErrorMessage em = new ErrorMessage("","User not found", d, this);
                return;
            }
            ErrorMessage em = new ErrorMessage("","User " + nameField.getText() + " successfully signed in", d, this);
        } catch (SQLException ex) {
            ErrorMessage em = new ErrorMessage("","Database error " + ex.getMessage(), d, this);
            //throw new RuntimeException(ex);
        }
        d.buttonPressed = false;
        d.setUser(nameField.getText());
        this.dispose();
    }
}
