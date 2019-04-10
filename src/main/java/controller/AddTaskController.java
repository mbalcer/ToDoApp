package controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import dao.TaskDAO;
import entity.Task;
import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

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
    private TaskDAO taskDAO;
    private User user;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initialize() {
        taskDAO = new TaskDAO();
    }

    @FXML
    void addTask() {
        Instant instant = Instant.from(dateTask.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Task task = new Task(user.getId(), nameTask.getText(), date, descriptionTask.getText(), colorTask.getValue().toString(), false);
        taskDAO.add(task);
    }

    @FXML
    void backToListTask() {
        loginController.loadUserView(user);
    }

}
