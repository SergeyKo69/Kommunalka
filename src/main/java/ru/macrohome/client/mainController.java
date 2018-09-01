package ru.macrohome.client;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class mainController {

    @FXML
    public Label ePriceDay;
    @FXML
    public DatePicker eDate;
    @FXML
    public Label ePriceNight;
    @FXML
    public Label eDayPrevValue;
    @FXML
    public Label eNightPrevValue;
    @FXML
    public TextField txtEDayCurValue;
    @FXML
    public Label eDayCurValueKwt;
    @FXML
    public Label eDayCostValue;
    @FXML
    public TextField txtENightCurValue;
    @FXML
    public Label eNightCurValueKwt;
    @FXML
    public Label eNightCostValue;
    @FXML
    public Button bEPayment;
    @FXML
    public Button bEFile;
    @FXML
    public Button bERemovePayment;
    @FXML
    public TableView tableEPayments;
    @FXML
    public DatePicker wDate;
    @FXML
    public Label wPrice;
    @FXML
    public Label wPrevVal;
    @FXML
    public TextField wCurValue;

    public void clickClose(ActionEvent actionEvent) {
    }

    public void clickSettings(ActionEvent actionEvent) {
    }

    public void clickAbout(ActionEvent actionEvent) {
    }

    public void clickEDate(ActionEvent actionEvent) {
    }

    public void eDayKeyTyped(KeyEvent keyEvent) {
    }

    public void eNightKeyTyped(KeyEvent keyEvent) {
    }

    public void clickEPayment(ActionEvent actionEvent) {
    }

    public void clickEFile(ActionEvent actionEvent) {
    }

    public void clickEHistory(MouseEvent mouseEvent) {
    }

    public void clickWDate(Event event) {
    }

    public void wValueKeyTyped(KeyEvent keyEvent) {
    }
}
