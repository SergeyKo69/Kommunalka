package ru.macrohome.common;


import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import ru.macrohome.entity.SettingsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.util.Optional;

public class History {
    public static boolean initHistory(ObservableList<SettingsEntity> tableSettings, TableView tTableHistory, String viewId){
        tTableHistory.getItems().clear();
        Condition[] condition = new Condition[1];
        condition[0] = new Condition("viewId",viewId);
        Answer answ = DataBaseUtility.getList(Tables.SETTINGS,condition);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error database",
                    "Error database \n" + answ.description);
            return false;
        }
        for (int i = 0; i < answ.list.size(); i++) {
            SettingsEntity settings = (SettingsEntity)answ.list.get(i);
            tableSettings.add(settings);
        }
        tTableHistory.setItems(tableSettings);
        return true;
    }

    public static void delete(SettingsEntity settingRow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete history");
        alert.setHeaderText("Delete history");
        alert.setContentText("History will be delete! Continue ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Answer answ = DataBaseUtility.delete(settingRow);
            if (answ.answ == Answers.ERROR){
                InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error database",
                        "Error database \n" + answ.description);
            }
        }
    }
}
