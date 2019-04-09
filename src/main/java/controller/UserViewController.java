package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UserViewController {

    @FXML
    private TextField tf_search;

    @FXML
    private VBox vbox_listTask;

    private AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    void addNewTask() {

    }

    @FXML
    void searchTask() {

    }

    @FXML
    void signOut() {
        appController.loadLoginScreen();
    }

}
