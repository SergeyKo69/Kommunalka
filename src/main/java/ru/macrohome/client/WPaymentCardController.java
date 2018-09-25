package ru.macrohome.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.macrohome.common.WHTable;

import java.text.DecimalFormat;

public class WPaymentCardController {
    public DecimalFormat df = new DecimalFormat("#.00");
    private int id_file;
    private int id_payment;

    @FXML
    public Label lblDate;
    @FXML
    public Label lblTotal;
    @FXML
    public Label lblVal;
    @FXML
    public Label lblPrice;
    @FXML
    public Label lblTotalVal;
    @FXML
    public Button bOpenImg;
    @FXML
    public Button bChooseImg;
    @FXML
    public Label lblStatusImage;
    @FXML
    public Button bClose;
    @FXML
    public Label lblM3;

    @FXML
    public void initialize(){

    }

    public void initForm(WHTable rowTable){
        id_file = rowTable.getWtDataId();
        if (id_file > 0){
            lblStatusImage.setText("File upload");
            lblStatusImage.setStyle("-fx-text-fill: green");
        }
        id_payment = rowTable.getWtId();
        lblDate.setText(rowTable.getWtDate());
        lblTotal.setText(rowTable.getWtTotal());
        lblVal.setText(rowTable.getWtValue());
        lblM3.setText(rowTable.getWtM3());
        lblPrice.setText(rowTable.getWtPrice());
        lblTotal.setText(rowTable.getWtTotal());
    }

    public void clickOpenImg(ActionEvent event) {
    }

    public void clickChooseImg(ActionEvent event) {
    }

    public void clickClose(ActionEvent event) {
        close();
    }

    public void close(){
        Stage s = (Stage)bClose.getScene().getWindow();
        s.close();
    }
}
