package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class AppController {
    @FXML
    BorderPane mainBorderPane;

    public void setMainBorderPane(Parent parent) {
        mainBorderPane.setCenter(parent);
    }

    @FXML
    private void initialize() {
        loadLoginScreen();
    }

    public void loadLoginScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/loginView.fxml"));
        ResourceBundle properties = ResourceBundle.getBundle("bundles.messages");
        loader.setResources(properties);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController loginController = loader.getController();
        loginController.setAppController(this);
        setMainBorderPane(parent);
    }
}
