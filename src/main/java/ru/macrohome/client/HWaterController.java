package ru.macrohome.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.macrohome.common.*;
import ru.macrohome.entity.SettingsEntity;

import java.sql.Date;

public class HWaterController {
    private ObservableList<SettingsEntity> tableSettings = FXCollections.observableArrayList();

    @FXML
    public Button bDel;
    @FXML
    public TableView tTableHistory;
    @FXML
    public TableColumn<SettingsEntity, Integer> id;
    @FXML
    public TableColumn<SettingsEntity, Date> date;
    @FXML
    public TableColumn<SettingsEntity, String> value;
    @FXML
    public Button bClose;

    @FXML
    private void initialize(){
        initTable();
        initHistory();
    }

    private void initHistory() {
        if (!History.initHistory(tableSettings,tTableHistory,"2")){
            close();
        }
    }

    private void initTable() {
        id.setCellValueFactory(new PropertyValueFactory<SettingsEntity, Integer>("id"));
        date.setCellValueFactory(new PropertyValueFactory<SettingsEntity, Date>("date"));
        value.setCellValueFactory(new PropertyValueFactory<SettingsEntity, String>("val1"));
    }

    public void clickDel(ActionEvent event) {
        SettingsEntity settingRow = (SettingsEntity) tTableHistory.getSelectionModel().getSelectedItem();
        History.delete(settingRow);
        initHistory();
    }

    public void clickTab(MouseEvent mouseEvent) {
    }

    public void clickClose(ActionEvent event) {
        close();
    }

    private void close(){
        Stage s = (Stage) bClose.getScene().getWindow();
        s.close();
    }
}
