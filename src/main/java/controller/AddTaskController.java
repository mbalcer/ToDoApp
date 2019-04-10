package controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddTaskController {

    @FXML
    private TextField nameTask;

    @FXML
    private JFXDatePicker dateTask;

    @FXML
    private JFXTimePicker timeTask;

    @FXML
    private TextArea descriptionTask;

    @FXML
    private JFXColorPicker colorTask;

    private LoginController loginController;
    private User user;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void addTask() {

    }

    @FXML
    void backToListTask() {
        loginController.loadUserView(user);
    }

}
