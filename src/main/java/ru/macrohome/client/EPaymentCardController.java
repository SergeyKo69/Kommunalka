package ru.macrohome.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.macrohome.common.*;
import ru.macrohome.entity.DatasEntity;
import ru.macrohome.entity.PaymentsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Optional;

public class EPaymentCardController {
    public DecimalFormat df = new DecimalFormat("#.00");
    private EHTable rowTable;
    private final String DESCRIPTION_FILE = "PDF files (*.pdf)";
    private final String EXTENSIONS_FILE = "*.pdf";
    private boolean modify = false;
    private MainController mainForm;

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

    public void initForm(MainController mainForm, EHTable rowTable){
        this.mainForm = mainForm;
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
        lblNightTotal.setText(df.format(Double.parseDouble(rowTable.geteHKwtNight()) *
                Double.parseDouble(rowTable.geteHPNight())));
    }

    public void clickOpenImg(ActionEvent event) {
        History.openPDF(rowTable.getDataId());
//        Condition[] condition = new Condition[1];
//        condition[0] = new Condition("id", String.valueOf(rowTable.getDataId()));
//        Answer answ = DataBaseUtility.getList(Tables.DATAS, condition);
//        if (answ.answ == Answers.ERROR) {
//            InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error database",
//                    "Error database \n" + answ.description);
//            return;
//        }else{
//            if (answ.list.size() > 0){
//                DatasEntity entity = (DatasEntity) answ.list.get(0);
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pdfviewer_form.fxml"));
//                Parent root = null;
//                try {
//                    root = fxmlLoader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                PDFViewController controller = fxmlLoader.getController();
//                controller.openByteArray(entity.getData());
//                stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
//                    @Override
//                    public void handle(WindowEvent event) {
//                        controller.beforeClosing();
//                    }
//                });
//                stage.initModality(Modality.APPLICATION_MODAL);
//                stage.showAndWait();
//            }
//        }
    }

    public void clickChooseImg(ActionEvent event) {
        Answer answ = null;
        if (rowTable.getDataId() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("File is found");
            alert.setHeaderText("File is found");
            alert.setContentText("Replace file ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DatasEntity entity = new DatasEntity();
                entity.setId(rowTable.getDataId());
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
                condition[0] = new Condition("viewId", "1");
                condition[1] = new Condition("id", String.valueOf(rowTable.getId()));
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
            mainForm.initEPaymentHistory();
        }
        Stage s = (Stage)bClose.getScene().getWindow();
        s.close();
    }
}
