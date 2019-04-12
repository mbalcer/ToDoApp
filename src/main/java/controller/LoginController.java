package controller;

import dao.UserDAO;
import entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import utility.InfoDialog;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private ResourceBundle properties;


    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void initialize() {
        userDAO = new UserDAO();
        properties = ResourceBundle.getBundle("bundles.messages");
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
                loadUserView(userFromDatabase.get());
            } else  {
                InfoDialog.showAlert(properties.getString("login.title.error"), properties.getString("login.error.password"));
            }
        } else {
            InfoDialog.showAlert(properties.getString("login.title.error"), properties.getString("login.error.nouser"));
        }
    }

    public void loadUserView(User user) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/userView.fxml"));
        loader.setResources(properties);
        Parent parent = null;
        try {
             parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserViewController userController = loader.getController();
        userController.setAppController(appController);
        userController.setLoginController(this);
        userController.setUser(user);
        userController.loadListTask(false, ".*");
        appController.setMainBorderPane(parent);
    }

    @FXML
    public void signUp() {
        if (tf_r_login.getText().isEmpty()) {
            InfoDialog.showAlert(properties.getString("register.title.error"), properties.getString("register.error.nologin"));
        } else if (tf_r_email.getText().isEmpty())
            InfoDialog.showAlert(properties.getString("register.title.error"), properties.getString("register.error.noemail"));
        else if (pf_r_password.getText().length() < 6)
            InfoDialog.showAlert(properties.getString("register.title.error"), properties.getString("register.error.shortpassword"));
        else if (!(pf_r_password.getText().equals(pf_r_repeatPassword.getText())))
            InfoDialog.showAlert(properties.getString("register.title.error"), properties.getString("register.error.passwordnotsame"));
        else {
            User newUser = new User(tf_r_login.getText(), pf_r_password.getText(), tf_r_email.getText());
            userDAO.add(newUser);
            InfoDialog.showAlert(properties.getString("register.title.success"), properties.getString("register.success.info"));
        }
    }

}
