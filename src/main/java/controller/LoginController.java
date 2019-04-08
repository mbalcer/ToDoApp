package controller;

import dao.UserDAO;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Optional;

public class LoginController {

    @FXML
    private TextField tf_l_login;

    @FXML
    private PasswordField pf_l_password;

    @FXML
    private Button btn_sign_in;

    @FXML
    private CheckBox cb_show;

    @FXML
    private TextField tf_r_login;

    @FXML
    private TextField tf_r_email;

    @FXML
    private PasswordField pf_r_password;

    @FXML
    private PasswordField pf_r_repeatPassword;

    @FXML
    private Button btn_sign_up;

    private String password;
    private UserDAO userDAO;

    @FXML
    private void initialize() {
        userDAO = new UserDAO();
    }

    @FXML
    public void showPassword() {
        if (cb_show.isSelected()) {
            password = pf_l_password.getText();
            pf_l_password.clear();
            pf_l_password.setPromptText(password);
        } else {
            pf_l_password.setText(password);
            pf_l_password.setPromptText("");
        }
    }

    @FXML
    public void signIn() {
        Optional<User> userFromDatabase = userDAO.read(tf_l_login.getText());
        if (userFromDatabase.isPresent()) {
            if (userFromDatabase.get().getPassword().equals(pf_l_password.getText())) {
                System.out.println("Zalogowany");
            } else  {
                System.out.println("Błędne hasło");
            }
        } else {
            System.out.println("Nie ma takiego użytkownika");
        }
    }

    @FXML
    public void signUp() {

    }

}
