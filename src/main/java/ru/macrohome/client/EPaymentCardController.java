package ru.macrohome.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.macrohome.common.Answer;
import ru.macrohome.common.Answers;
import ru.macrohome.common.EHTable;
import ru.macrohome.common.InterfaceBoxes;
import ru.macrohome.entity.DatasEntity;
import ru.macrohome.entity.PaymentsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Optional;

public class EPaymentCardController {
    public DecimalFormat df = new DecimalFormat("#.00");
    private EHTable rowTable;

    @FXML
    public Label lblDate;
    @FXML
    public Label lblTotal;
    @FXML
    public Label lblDayVal;
    @FXML
    public Label lblDayKwt;
    @FXML
    public Label lblDayPrice;
    @FXML
    public Label lblDayTotal;
    @FXML
    public Label lblNightVal;
    @FXML
    public Label lblNightKwt;
    @FXML
    public Label lblNightPrice;
    @FXML
    public Label lblNightTotal;
    @FXML
    public Button bOpenImg;
    @FXML
    public Button bChooseImg;
    @FXML
    public Label lblStatusImage;
    @FXML
    public Button bClose;

    @FXML
    public void initialize(){

    }

    public void initForm(EHTable rowTable){
        this.rowTable = rowTable;
        lblDate.setText(rowTable.getDate());
        if (rowTable.getDataId() > 0){
            lblStatusImage.setText("File upload");
            lblStatusImage.setStyle("-fx-text-fill: green");
        }
        lblTotal.setText(rowTable.geteHTotal());
        lblDayVal.setText(rowTable.geteHDay());
        lblDayKwt.setText(rowTable.geteHKwtDay());
        lblDayPrice.setText(rowTable.geteHPDay());
        lblDayTotal.setText(df.format(Double.parseDouble(rowTable.geteHKwtDay())*
                Double.parseDouble(rowTable.geteHPDay())));
        lblNightVal.setText(rowTable.geteHNight());
        lblNightKwt.setText(rowTable.geteHKwtNight());
        lblNightPrice.setText(rowTable.geteHPNight());
        lblNightTotal.setText(df.format(Double.parseDouble(rowTable.geteHKwtNight())*
                Double.parseDouble(rowTable.geteHPNight())));
    }

    public void clickOpenImg(ActionEvent event) {
    }

    public void clickChooseImg(ActionEvent event) {
        if (rowTable.getDataId() > 0){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("File is found");
            alert.setHeaderText("File is found");
            alert.setContentText("Replace file ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DatasEntity entity = new DatasEntity();
                entity.setId(rowTable.getDataId());
                entity.setName("");
                Answer answ = DataBaseUtility.delete(entity);
                if (answ.answ == Answers.ERROR){
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error delete filw",answ.description);
                    return;
                }

            }
        }
    }

    public void clickClose(ActionEvent event) {
        close();
    }

    public void close(){
        Stage s = (Stage)bClose.getScene().getWindow();
        s.close();
    }
}
