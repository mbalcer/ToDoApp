package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppController {
    @FXML
    BorderPane mainBorderPane;

    private void setMainBorderPane(String path) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainBorderPane.setCenter(parent);
    }

    @FXML
    private void initialize() {
        setMainBorderPane("/view/loginView.fxml");
    }
}
