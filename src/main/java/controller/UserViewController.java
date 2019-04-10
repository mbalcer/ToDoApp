package controller;

import dao.TaskDAO;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Date;


public class UserViewController {

    @FXML
    private TextField tf_search;

    @FXML
    private VBox vbox_listTask;

    private AppController appController;
    private LoginController loginController;
    private TaskDAO taskDAO;
    private User user;

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

    }

    @FXML
    void signOut() {
        appController.loadLoginScreen();
    }

    public void initialize() {
        taskDAO = new TaskDAO();
        vbox_listTask.setSpacing(10);
    }

    private GridPane createGridPane(String topic, Date dateTask) {
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

        gridPane.setHalignment(checkbox, HPos.CENTER);
        gridPane.setValignment(checkbox, VPos.CENTER);
        gridPane.getChildren().add(checkbox);

        Label labelTopic = new Label(topic);
        labelTopic.getStyleClass().add("topic-item");

        Label labelDate = new Label(dateTask.toString());
        labelDate.getStyleClass().add("date-item");

        gridPane.addColumn(1, labelTopic, labelDate);

        return gridPane;
    }

    public void loadListTask() {
        taskDAO.readAll(user.getId())
                .stream()
                .forEach(task -> {
                    GridPane gridPane = createGridPane(task.getName(), task.getDate());
                    vbox_listTask.getChildren().add(gridPane);
                });
    }



}
