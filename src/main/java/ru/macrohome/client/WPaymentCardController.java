package ru.macrohome.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.macrohome.common.*;
import ru.macrohome.entity.DatasEntity;
import ru.macrohome.entity.PaymentsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Optional;

public class WPaymentCardController {
    public DecimalFormat df = new DecimalFormat("#.00");
    private WHTable rowTable;
    private final String DESCRIPTION_FILE = "PDF files (*.pdf)";
    private final String EXTENSIONS_FILE = "*.pdf";
    private boolean modify = false;
    private MainController mainForm;

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

    public void initForm(MainController mainForm, WHTable rowTable){
        this.mainForm = mainForm;
        this.rowTable = rowTable;
        if (rowTable.getWtDataId() > 0){
            lblStatusImage.setText("File upload");
            lblStatusImage.setStyle("-fx-text-fill: green");
        }
        lblDate.setText(rowTable.getWtDate());
        lblTotal.setText(rowTable.getWtTotal());
        lblVal.setText(rowTable.getWtValue());
        lblM3.setText(rowTable.getWtM3());
        lblPrice.setText(rowTable.getWtPrice());
        lblTotal.setText(rowTable.getWtTotal());
    }

    public void clickOpenImg(ActionEvent event) {
        History.openPDF(rowTable.getWtDataId());
    }

    public void clickChooseImg(ActionEvent event) {
        Answer answ = null;
        if (rowTable.getWtDataId() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("File is found");
            alert.setHeaderText("File is found");
            alert.setContentText("Replace file ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DatasEntity entity = new DatasEntity();
                entity.setId(rowTable.getWtDataId());
                entity.setName("");
                answ = DataBaseUtility.delete(entity);
                if (answ.answ == Answers.ERROR) {
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error delete file", answ.description);
                    return;
                }
            } else {
                return;
            }
        }
        File file = InterfaceBoxes.chooseFile((Stage) bChooseImg.getScene().getWindow(),
                DESCRIPTION_FILE, EXTENSIONS_FILE);
        if (file != null) {
            int id_data = DataBaseUtility.saveFile(file);
            if (id_data > 0) {
                Condition[] condition = new Condition[2];
                condition[0] = new Condition("viewId", "2");
                condition[1] = new Condition("id", String.valueOf(rowTable.getWtId()));
                answ = DataBaseUtility.getList(Tables.PAYMENTS, condition);
                if (answ.answ == Answers.ERROR) {
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error database",
                            "Error database \n" + answ.description);
                    return;
                }
                if (answ.list.size() > 0) {
                    PaymentsEntity[] entityesPayment = new PaymentsEntity[1];
                    entityesPayment[0] = (PaymentsEntity) answ.list.get(0);
                    entityesPayment[0].setDataId(id_data);
                    answ = DataBaseUtility.saveEntity(entityesPayment);
                    if (answ.answ == Answers.ERROR) {
                        InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error database",
                                "Error database \n" + answ.description);
                    } else {
                        lblStatusImage.setText("File upload");
                        lblStatusImage.setStyle("-fx-text-fill: green");
                        modify = true;
                    }
                } else {
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error database",
                            "Error database. List history not found");
                }

            }
        }
    }

    public void clickClose(ActionEvent event) {
        close();
    }

    public void close(){
        if (modify){
            mainForm.initWPaymentHistory();
        }
        Stage s = (Stage)bClose.getScene().getWindow();
        s.close();
    }
}
