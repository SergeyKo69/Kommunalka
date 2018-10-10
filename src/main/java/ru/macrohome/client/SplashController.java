package ru.macrohome.client;

import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashController {
    @FXML
    public ProgressBar pbStatus;
    @FXML
    public Label lblStatus;

    public ProgressBar getPbStatus() {
        return pbStatus;
    }

    public void setPbStatus(ProgressBar pbStatus) {
        this.pbStatus = pbStatus;
    }

    public Label getLblStatus() {
        return lblStatus;
    }

    public void setLblStatus(Label lblStatus) {
        this.lblStatus = lblStatus;
    }
}
