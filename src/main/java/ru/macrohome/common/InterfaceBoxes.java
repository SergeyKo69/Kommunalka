package ru.macrohome.common;

import javafx.scene.control.Alert;

public class InterfaceBoxes {

    public static void showMessage(Alert.AlertType type, String title, String msg){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
