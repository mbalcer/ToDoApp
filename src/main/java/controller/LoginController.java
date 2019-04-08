package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    @FXML
    void showPassword() {
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
    void signIn() {

    }

    @FXML
    void signUp() {

    }

}
