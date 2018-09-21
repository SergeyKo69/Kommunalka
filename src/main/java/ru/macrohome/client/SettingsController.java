package ru.macrohome.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.macrohome.common.*;
import ru.macrohome.entity.SettingsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public class SettingsController {

    private SettingsEntity temp_eSettings;
    private SettingsEntity temp_wSettings;
    private SettingsEntity temp_aSettings;

    @FXML
    public DatePicker eDate;
    @FXML
    public TextField eDay;
    @FXML
    public TextField eNight;
    @FXML
    public DatePicker wDate;
    @FXML
    public TextField wValue;
    @FXML
    public Button bSave;
    @FXML
    public Button bClose;
    @FXML
    public TextField txtAccount;
    @FXML
    public Button bWHistory;
    @FXML
    public Button bAHistory;

    @FXML
    private void initialize(){
        initValueForm();
        getSettings();
    }

    public void initValueForm(){
        eDate.setConverter(DateUtils.getStringConverter());
        eDate.setPromptText("dd.MM.yyyy");
        wDate.setConverter(DateUtils.getStringConverter());
        wDate.setPromptText("dd.MM.yyyy");
    }

    private void getESettings(Date date){
        //Electricity.
        Answer answ = DataBaseUtility.getSettingsFirstValueByDateView(date,1);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error get electricity settings",answ.description + "\n You need reopen application");
        }else{
            List<Entities> list = answ.list;
            if (list.size() == 0){
                eDate.setValue(LocalDate.now());
            }else{
                SettingsEntity settings = (SettingsEntity) list.get(0);
                temp_eSettings = settings;
                eDate.setValue(settings.getDate().toLocalDate());
                eDay.setText(settings.getVal1());
                eNight.setText(settings.getVal2());
            }
        }
    }

    private void getWSettings(Date date){
        Answer answ = DataBaseUtility.getSettingsFirstValueByDateView(date,2);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error get water settings",answ.description + "\n You need reopen application");
        }else{
            List<Entities> list = answ.list;
            if (list.size() == 0){
                wDate.setValue(LocalDate.now());
            }else{
                SettingsEntity settings = (SettingsEntity) list.get(0);
                temp_wSettings = settings;
                wDate.setValue(settings.getDate().toLocalDate());
                wValue.setText(settings.getVal1());
            }
        }
    }

    private void getASettings(Date date){
        Answer answ = DataBaseUtility.getSettingsFirstValueByDateView(date,3);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error get personal account settings",answ.description + "\n You need reopen application");
        }else{
            List<Entities> list = answ.list;
            if (list.size() > 0){
                SettingsEntity settings = (SettingsEntity) list.get(0);
                temp_aSettings = settings;
                txtAccount.setText(settings.getVal1());
            }
        }
    }

    private void getSettings(){
        Date date = Date.valueOf(LocalDate.now());
        //Electricity.
        getESettings(date);
        //Water.
        getWSettings(date);
        //Personal account.
        getASettings(date);
    }

    public void clickEDate(ActionEvent actionEvent) {
        eDay.setText("0");
        eNight.setText("0");
        Date date = Date.valueOf(eDate.getValue());
        if (date != null){
            getESettings(date);
        }
    }

    public void clickWDate(ActionEvent actionEvent) {
        wValue.setText("0");
        Date date = Date.valueOf(wDate.getValue());
        if (date != null){
            getWSettings(date);
        }
    }

    public void clickBSave(ActionEvent actionEvent) {
        //Electricity.
        int i = 0;
        SettingsEntity[] settings = new SettingsEntity[3];
        if (temp_eSettings == null || (!eDay.getText().equals(temp_eSettings.getVal1()) ||
                !eNight.getText().equals(temp_eSettings.getVal2()))) {
            settings[i] = new SettingsEntity();
            if (temp_eSettings != null && eDate.getValue().equals(temp_eSettings.getDate().toLocalDate())) {
                settings[i].setId(temp_eSettings.getId()); // update value.
            }
            settings[i].setDate(Date.valueOf(eDate.getValue()));
            settings[i].setVal1(eDay.getText());
            settings[i].setVal2(eNight.getText());
            settings[i].setViewId(1);
            i++;
        }
        //Water.
        if (temp_wSettings == null || (temp_wSettings != null && !wValue.getText().equals(temp_wSettings.getVal1()))) {
            settings[i] = new SettingsEntity();
            if (temp_wSettings != null && wDate.getValue().equals(temp_wSettings.getDate().toLocalDate())) {
                settings[i].setId(temp_wSettings.getId()); // update.
            }
            settings[i].setDate(Date.valueOf(wDate.getValue()));
            settings[i].setVal1(wValue.getText());
            settings[i].setViewId(2);
            i++;
        }
        //Personal account.
        if (temp_aSettings == null || (temp_aSettings != null &&
                !txtAccount.getText().equals(temp_aSettings.getVal1()))) {
            settings[i] = new SettingsEntity();
            if (temp_aSettings != null) {
                settings[i].setId(temp_aSettings.getId());
            }
            settings[i].setDate(Date.valueOf(LocalDate.now()));
            settings[i].setVal1(txtAccount.getText());
            settings[i].setViewId(3);
        }
        if (settings[0] != null) {
            Answer answ = DataBaseUtility.saveEntity(settings);
            if (answ.answ == Answers.ERROR) {
                InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error save settings",
                        "Error save settings\n" + answ.description);
            }
        }
        close();
    }

    public void clickBClose(ActionEvent actionEvent) {
        close();
    }

    public void close(){
        Stage s = (Stage) bClose.getScene().getWindow();
        s.close();
    }

    public void eDKeyTyped(KeyEvent keyEvent) {
        onlyNumbers(eDay);
    }

    public void eNKeyTyped(KeyEvent keyEvent) {
         onlyNumbers(eNight);
    }

    public void wKeyTyped(KeyEvent keyEvent) {
        onlyNumbers(wValue);
    }

    public void onlyNumbers(TextField field){
        field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    field.setText(oldValue);
                }
            }
        });
    }

    public void clickOpenHistory(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/historySElectricity_form.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error open form",e.getMessage());
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void clickOpenWaterHistory(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/historySWater_form.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error open form",e.getMessage());
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void clickOpenAccHistory(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/historyAccount_form.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error open form",e.getMessage());
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
