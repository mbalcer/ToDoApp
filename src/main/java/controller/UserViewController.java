package controller;

import dao.TaskDAO;
import entity.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserViewController {

    @FXML
    private TextField tf_search;

    @FXML
    private VBox vbox_listTask;

    @FXML
    MenuItem switchingDisplayOfTasks;

    private AppController appController;
    private LoginController loginController;
    private TaskDAO taskDAO;
    private User user;
    private boolean showIsCompleted;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void showAddTaskView() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/addTaskView.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddTaskController addTaskController = fxmlLoader.getController();
        addTaskController.setLoginController(loginController);
        addTaskController.setUser(user);
        appController.setMainBorderPane(parent);
    }

    @FXML
    void searchTask() {
        String valueEntered = tf_search.getText();
        String regex = ".*"+valueEntered+".*";
        loadListTask(showIsCompleted, regex);

    }

    @FXML
    void showCompletedTasks() {
        if (showIsCompleted) {
            loadListTask(false, ".*");
            switchingDisplayOfTasks.setText("Completed tasks");
        }
        else {
            loadListTask(true, ".*");
            switchingDisplayOfTasks.setText("Tasks to do");
        }
    }

    @FXML
    void signOut() {
        appController.loadLoginScreen();
    }

    public void initialize() {
        taskDAO = new TaskDAO();
        vbox_listTask.setSpacing(10);
    }

    private GridPane createGridPane(Long taskId, String topic, String dateTask, boolean isAfter, String color, boolean isSelected) {
        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(53.0);
        gridPane.setPrefHeight(53.0);
        gridPane.setPrefWidth(400.0);

        gridPane.getStyleClass().add("gridpane-item");
        gridPane.getStylesheets().add("../css/designUserView.css");

        ColumnConstraints columnConstraints = new ColumnConstraints(10.0, 51.0, 195.2);
        columnConstraints.setHgrow(Priority.SOMETIMES);
        gridPane.getColumnConstraints().add(columnConstraints);

        ColumnConstraints columnConstraints2 = new ColumnConstraints(10.0, 352.0,348.6);
        columnConstraints.setHgrow(Priority.SOMETIMES);
        gridPane.getColumnConstraints().add(columnConstraints2);

        RowConstraints rowConstraints = new RowConstraints(10.0, 30.0, 30.0);
        rowConstraints.setVgrow(Priority.SOMETIMES);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane.getRowConstraints().add(rowConstraints);

        CheckBox checkbox = new CheckBox();
        checkbox.setMnemonicParsing(false);
        checkbox.setPrefHeight(14.0);
        checkbox.setPrefWidth(10.0);
        checkbox.getStyleClass().add("checkbox-item");
        checkbox.setSelected(isSelected);
        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    taskDAO.setIsCompleted(taskId, true);
                    loadListTask(false, ".*");
                } else  {
                    taskDAO.setIsCompleted(taskId, false);
                    loadListTask(true, ".*");
                }
            }
        });

        gridPane.setHalignment(checkbox, HPos.CENTER);
        gridPane.setValignment(checkbox, VPos.CENTER);
        gridPane.getChildren().add(checkbox);

        Label labelTopic = new Label(topic);
        labelTopic.getStyleClass().add("topic-item");

        Label labelDate = new Label(dateTask);
        labelDate.getStyleClass().add("date-item");
        if (isAfter==true)
            labelDate.getStyleClass().add("date-item-red");

        gridPane.addColumn(1, labelTopic, labelDate);
        Border border = new Border(new BorderStroke(
                Paint.valueOf(color),
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(1))
        );
        gridPane.setBorder(border);

        return gridPane;
    }

    public void loadListTask(boolean isCompleted, String regex) {
        showIsCompleted = isCompleted;
        vbox_listTask.getChildren().clear();
        taskDAO.readAll(user.getId(), isCompleted)
                .stream()
                .filter(task -> task.getName().toLowerCase().matches(regex))
                .forEach(task -> {
                    boolean isAfter = false;
                    Date today = new Date();
                    if (today.compareTo(task.getDate())>0)
                        isAfter = true;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    String dateTask = dateFormat.format(task.getDate());
                    GridPane gridPane = createGridPane(task.getId(), task.getName(), dateTask, isAfter, task.getColor(), isCompleted);
                    vbox_listTask.getChildren().add(gridPane);
                });
    }



}
