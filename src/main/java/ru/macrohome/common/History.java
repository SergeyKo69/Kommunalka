package ru.macrohome.common;


import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import ru.macrohome.entity.PaymentsEntity;
import ru.macrohome.entity.SettingsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.util.Optional;

public class History {
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

    public static void initEPaymentHistory(ObservableList<EHTable> tableHistory, TableView tTablePayment){
        tTablePayment.getItems().clear();
        Condition[] condition = new Condition[1];
        condition[0] = new Condition("viewId", "1");
        Answer answ = DataBaseUtility.getList(Tables.PAYMENTS,condition);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error database",
                    "Error database \n" + answ.description);
        }
        EHTable rowTable;
        for (int i = 0; i < answ.list.size(); i++) {
            PaymentsEntity settings = (PaymentsEntity) answ.list.get(i);
            rowTable = new EHTable();
            rowTable.setId(settings.getId());
            rowTable.setDate(DateUtils.getDateInFormated(settings.getDate()));
            rowTable.seteHDay(settings.getrVal1());
            rowTable.seteHNight(settings.getrVal2());
            rowTable.seteHPDay(settings.getpVal1());
            rowTable.seteHPNight(settings.getpVal2());
            rowTable.seteHKwtDay(settings.getVal1());
            rowTable.seteHKwtNight(settings.getVal2());
            rowTable.seteHTotal(String.valueOf((Double.parseDouble(rowTable.geteHKwtDay())*
                    Double.parseDouble(rowTable.geteHPDay())) + (Double.parseDouble(rowTable.geteHKwtNight())*
                    Double.parseDouble(rowTable.geteHPNight()))));
            tableHistory.add(rowTable);
        }
        tTablePayment.setItems(tableHistory);
    }

    public static void initWPaymentHistory(ObservableList<WHTable> tableHistory, TableView tTablePayment){
        tTablePayment.getItems().clear();
        Condition[] condition = new Condition[1];
        condition[0] = new Condition("viewId", "2");
        Answer answ = DataBaseUtility.getList(Tables.PAYMENTS,condition);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error database",
                    "Error database \n" + answ.description);
        }
        WHTable rowTable;
        for (int i = 0; i < answ.list.size(); i++) {
            PaymentsEntity settings = (PaymentsEntity) answ.list.get(i);
            rowTable = new WHTable();
            rowTable.setWtId(settings.getId());
            rowTable.setWtDate(DateUtils.getDateInFormated(settings.getDate()));
            rowTable.setWtValue(settings.getrVal1());
            rowTable.setWtPrice(settings.getpVal1());
            rowTable.setWtM3(settings.getVal1());
            rowTable.setWtTotal(String.valueOf(Double.parseDouble(settings.getVal1())*
                    Double.parseDouble(settings.getpVal1())));
            tableHistory.add(rowTable);
        }
        tTablePayment.setItems(tableHistory);
    }
}
