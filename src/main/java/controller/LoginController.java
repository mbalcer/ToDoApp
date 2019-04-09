package controller;

import dao.UserDAO;
import entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import utility.InfoDialog;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
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
    private AppController appController;


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

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
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/userView.fxml"));
                Parent parent = null;
                try {
                     parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UserViewController userController = loader.getController();
                userController.setAppController(appController);
                appController.setMainBorderPane(parent);
            } else  {
                InfoDialog.showAlert("Niepoprawne dane", "Błędne hasło");
            }
        } else {
            InfoDialog.showAlert("Niepoprawne dane", "Nie ma takiego użytkownika");
        }
    }

    @FXML
    public void signUp() {
        if (tf_r_login.getText().isEmpty()) {
            InfoDialog.showAlert("Popraw dane", "Login nie może być pusty");
        } else if (tf_r_email.getText().isEmpty())
            InfoDialog.showAlert("Popraw dane", "Email nie może być pusty");
        else if (pf_r_password.getText().length() < 6)
            InfoDialog.showAlert("Popraw dane", "Hasło powinno mieć więcej niż 6 znaków");
        else if (!(pf_r_password.getText().equals(pf_r_repeatPassword.getText())))
            InfoDialog.showAlert("Popraw dane", "Hasła nie są takie same");
        else {
            User newUser = new User(tf_r_login.getText(), pf_r_password.getText(), tf_r_email.getText());
            userDAO.add(newUser);
            InfoDialog.showAlert("Sukces", "Dodano nowego użytkownika");
        }
    }

}
