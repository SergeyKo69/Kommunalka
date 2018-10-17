package ru.macrohome.client;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private ObservableList<HistoryTable> tableSettings = FXCollections.observableArrayList();

    @FXML
    public Button bDel;
    @FXML
    public TableView tTableHistory;
    @FXML
    public TableColumn<HistoryTable, Integer> id;
    @FXML
    public TableColumn<HistoryTable, String> date;
    @FXML
    public TableColumn<HistoryTable, String> value;
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
        id.setCellValueFactory(tableSettings->new SimpleObjectProperty<>(tableSettings.getValue().getId()));
        date.setCellValueFactory(tableSettings->new SimpleStringProperty(tableSettings.getValue().getDate()));
        value.setCellValueFactory(tableSettings->new SimpleStringProperty(tableSettings.getValue().getVal1()));
        id.setStyle("-fx-alignment: CENTER;");
        date.setStyle("-fx-alignment: CENTER;");
        value.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    public void clickDel(ActionEvent event) {
        HistoryTable historyRow = (HistoryTable) tTableHistory.getSelectionModel().getSelectedItem();
        if (historyRow != null) {
             History.delete(historyRow);
            initHistory();
        }
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
