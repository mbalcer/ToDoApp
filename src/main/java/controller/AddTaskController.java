package controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import dao.TaskDAO;
import entity.Task;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import utility.InfoDialog;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

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

    @FXML
    private Label lbl_titleAdd;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    private LoginController loginController;
    private TaskDAO taskDAO;
    private User user;
    private String infoError;
    private ResourceBundle properties;
    private Long taskId;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initialize() {
        btn_delete.setVisible(false);
        taskDAO = new TaskDAO();
        properties = ResourceBundle.getBundle("bundles.messages");
    }

    private String toRGBCode( Color color ) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    private void clearAllField() {
        nameTask.clear();
        dateTask.setValue(null);
        timeTask.setValue(null);
        descriptionTask.clear();
        colorTask.setValue(Color.valueOf("0x3B8686FF"));
        lbl_titleAdd.setText(properties.getString("view.add.title"));
        btn_add.setText(properties.getString("view.add"));
    }

    private boolean checkData() {
        infoError = null;
        if (nameTask.getText().isEmpty())
            infoError = properties.getString("add.error.noname");
        else if (dateTask.getValue()==null)
            infoError = properties.getString("add.error.nodate");
        else if (timeTask.getValue()==null)
            infoError = properties.getString("add.error.notime");
        else
            return true;

        return false;
    }

    public void setEditTask(Task task) {
        btn_delete.setVisible(true);
        nameTask.setText(task.getName());
        dateTask.setValue(task.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        timeTask.setValue(task.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        descriptionTask.setText(task.getDescription());
        colorTask.setValue(Color.valueOf(task.getColor()));
        lbl_titleAdd.setText(properties.getString("view.add.title.edit"));
        btn_add.setText(properties.getString("view.add.edit"));
        this.taskId = task.getId();
    }

    @FXML
    void addTask() {
        if (!checkData())
            InfoDialog.showAlert(properties.getString("add.error.title"), infoError);
        else {
            LocalDateTime localDateTime = LocalDateTime.of(dateTask.getValue(), timeTask.getValue());
            Instant instant = localDateTime.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
            Date date = Date.from(instant);
            Color color = colorTask.getValue();
            Task task = new Task(user.getId(), nameTask.getText(), date, descriptionTask.getText(), toRGBCode(color), false);
            if (btn_add.getText() == properties.getString("view.add.edit")) {
                taskDAO.update(this.taskId, task);
                InfoDialog.showAlert(properties.getString("add.success.title"), properties.getString("add.success.infoedit"));
            }
            else {
                taskDAO.add(task);
                InfoDialog.showAlert(properties.getString("add.success.title"), properties.getString("add.success.infoadd"));
            }
            clearAllField();
        }
    }


    @FXML
    void backToListTask() {
        loginController.loadUserView(user);
    }

    @FXML
    public void deleteTask() {
        if(InfoDialog.showConfirmationAlert(properties.getString("delete.confirmation.title"), properties.getString("delete.confirmation.content"))) {
            taskDAO.delete(taskId);
            InfoDialog.showAlert(properties.getString("delete.info.title"), properties.getString("delete.info.content"));
            clearAllField();
        }
    }
}
