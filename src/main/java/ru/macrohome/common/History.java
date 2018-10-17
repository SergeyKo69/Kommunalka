package ru.macrohome.common;


import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.macrohome.client.PDFViewController;
import ru.macrohome.entity.DatasEntity;
import ru.macrohome.entity.PaymentsEntity;
import ru.macrohome.entity.SettingsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Optional;

public class History {
    public static DecimalFormat df = FormUtility.getDecimalFormat();
    public static boolean initHistory(ObservableList<HistoryTable> tableSettings, TableView tTableHistory, String viewId){
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
            HistoryTable table = new HistoryTable();
            table.setId(settings.getId());
            table.setDate(DateUtils.getDateInFormated(settings.getDate()));
            table.setVal1(settings.getVal1());
            table.setVal2(settings.getVal2());
            tableSettings.add(table);
        }
        tTableHistory.setItems(tableSettings);
        return true;
    }

    public static void delete(HistoryTable historyRow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete history");
        alert.setHeaderText("Delete history");
        alert.setContentText("History will be delete! Continue ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Date date = DateUtils.getDateOfString(historyRow.getDate());
            if (date != null) {
                SettingsEntity entity = new SettingsEntity();
                entity.setId(historyRow.getId());
                entity.setDate(date);
                entity.setVal1(historyRow.getVal1());
                entity.setVal2(historyRow.getVal2());
                Answer answ = DataBaseUtility.delete(entity);
                if (answ.answ == Answers.ERROR) {
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error database",
                            "Error database \n" + answ.description);
                }
            }
        }
    }

    public static Answer initEPaymentHistory(ObservableList<EHTable> tableHistory, TableView tTablePayment){
        System.out.println("2");
        tTablePayment.getItems().clear();
        System.out.println("3");
        Condition[] condition = new Condition[1];
        System.out.println("4");
        condition[0] = new Condition("viewId", "1");
        System.out.println("5");
        Answer answ = DataBaseUtility.getList(Tables.PAYMENTS,condition);
        System.out.println("6");
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error database",
                    "Error database \n" + answ.description);
            return answ;
        }
        System.out.println("7");
        EHTable rowTable;
        System.out.println("8");
        for (int i = 0; i < answ.list.size(); i++) {
            PaymentsEntity settings = (PaymentsEntity) answ.list.get(i);
            rowTable = new EHTable();
            rowTable.setId(settings.getId());
            rowTable.setDataId(settings.getDataId());
            if (settings.getDataId() > 0) {
                System.out.println("start set image");
                rowTable.setImage();
                System.out.println("end set image");
            }
            rowTable.setDate(DateUtils.getDateInFormated(settings.getDate()));
            rowTable.seteHDay(settings.getrVal1());
            rowTable.seteHNight(settings.getrVal2());
            rowTable.seteHPDay(settings.getpVal1());
            rowTable.seteHPNight(settings.getpVal2());
            rowTable.seteHKwtDay(settings.getVal1());
            rowTable.seteHKwtNight(settings.getVal2());
            rowTable.seteHTotal(df.format((Double.parseDouble(rowTable.geteHKwtDay())*
                    Double.parseDouble(rowTable.geteHPDay())) + (Double.parseDouble(rowTable.geteHKwtNight())*
                    Double.parseDouble(rowTable.geteHPNight()))));
            tableHistory.add(rowTable);
        }
        tTablePayment.setItems(tableHistory);
        return new Answer(Answers.OK);
    }

    public static Answer initWPaymentHistory(ObservableList<WHTable> tableHistory, TableView tTablePayment){
        tTablePayment.getItems().clear();
        Condition[] condition = new Condition[1];
        condition[0] = new Condition("viewId", "2");
        Answer answ = DataBaseUtility.getList(Tables.PAYMENTS,condition);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error database",
                    "Error database \n" + answ.description);
            return answ;
        }
        WHTable rowTable;
        for (int i = 0; i < answ.list.size(); i++) {
            PaymentsEntity settings = (PaymentsEntity) answ.list.get(i);
            rowTable = new WHTable();
            rowTable.setWtId(settings.getId());
            rowTable.setWtDataId(settings.getDataId());
            if (settings.getDataId() > 0) {
                rowTable.setImage();
            }
            rowTable.setWtDate(DateUtils.getDateInFormated(settings.getDate()));
            rowTable.setWtValue(settings.getrVal1());
            rowTable.setWtPrice(settings.getpVal1());
            rowTable.setWtM3(settings.getVal1());
            rowTable.setWtTotal(df.format((Double.parseDouble(settings.getVal1())*
                    Double.parseDouble(settings.getpVal1()))));
            tableHistory.add(rowTable);
        }
        tTablePayment.setItems(tableHistory);
        return new Answer(Answers.OK);
    }

    public static void openPDF(int data_id){
        Condition[] condition = new Condition[1];
        condition[0] = new Condition("id", String.valueOf(data_id));
        Answer answ = DataBaseUtility.getList(Tables.DATAS, condition);
        if (answ.answ == Answers.ERROR) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error database",
                    "Error database \n" + answ.description);
            return;
        }else{
            if (answ.list.size() > 0){
                DatasEntity entity = (DatasEntity) answ.list.get(0);
                FXMLLoader fxmlLoader = new FXMLLoader(History.class.getResource("/pdfviewer_form.fxml"));
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error open form",e.getMessage());
                }
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                PDFViewController controller = fxmlLoader.getController();
                controller.openByteArray(entity.getData());
                stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        controller.beforeClosing();
                    }
                });
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }
        }
    }
}
