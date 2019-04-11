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
import javafx.scene.paint.Color;
import utility.InfoDialog;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    private LoginController loginController;
    private TaskDAO taskDAO;
    private User user;
    private String infoError;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initialize() {
        taskDAO = new TaskDAO();
    }

    private String toRGBCode( Color color )
    {
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
    }

    private boolean checkData() {
        infoError = null;
        if (nameTask.getText().isEmpty())
            infoError = "Nazwa zadania nie może być pusta";
        else if (dateTask.getValue()==null)
            infoError = "Określ datę zadania";
        else if (timeTask.getValue()==null)
            infoError = "Określ czas zadania";
        else
            return true;

        return false;
    }

    @FXML
    void addTask() {
        if (!checkData())
            InfoDialog.showAlert("Błąd", infoError);
        else {
            LocalDateTime localDateTime = LocalDateTime.of(dateTask.getValue(), timeTask.getValue());
            Instant instant = localDateTime.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
            Date date = Date.from(instant);
            Color color = colorTask.getValue();
            Task task = new Task(user.getId(), nameTask.getText(), date, descriptionTask.getText(), toRGBCode(color), false);
            taskDAO.add(task);
            clearAllField();
        }
    }


    @FXML
    void backToListTask() {
        loginController.loadUserView(user);
    }

}
