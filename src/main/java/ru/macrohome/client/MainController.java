package ru.macrohome.client;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.macrohome.common.*;
import ru.macrohome.entity.DatasEntity;
import ru.macrohome.entity.PaymentsEntity;
import ru.macrohome.entity.SettingsEntity;
import ru.macrohome.entity.ViewsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MainController {

    private ObservableList<EHTable> tableEHistory = FXCollections.observableArrayList();
    private ObservableList<WHTable> tableWHistory = FXCollections.observableArrayList();
    public DecimalFormat df = new DecimalFormat("#.00");
    private final String DESCRIPTION_FILE = "PDF files (*.pdf)";
    private final String EXTENSIONS_FILE = "*.pdf";
    private File eFile;
    private File wFile;

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
    public TextField fEDayCurValue;
    @FXML
    public Label eDayCurValueKwt;
    @FXML
    public Label eDayCostValue;
    @FXML
    public TextField fENightCurValue;
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
    public TableView<EHTable> tableEPayments;
    @FXML
    public TableColumn<EHTable, Integer> id;
    @FXML
    public TableColumn<EHTable, ImageView> eHImg;
    @FXML
    public TableColumn<EHTable, String> eHDate;
    @FXML
    public TableColumn<EHTable, String> eHDay;
    @FXML
    public TableColumn<EHTable, String> eHNight;
    @FXML
    public TableColumn<EHTable, String> eHPDay;
    @FXML
    public TableColumn<EHTable, String> eHPNight;
    @FXML
    public TableColumn<EHTable, String> eHKwtDay;
    @FXML
    public TableColumn<EHTable, String> eHKwtNight;
    @FXML
    public TableColumn<EHTable, String> eHTotal;
    @FXML
    public DatePicker wDate;
    @FXML
    public Label wPrice;
    @FXML
    public Label wPrevVal;
    @FXML
    public TextField wCurValue;
    @FXML
    public Label lblAccount;
    @FXML
    public MenuItem bClose;
    @FXML
    public Menu bFile;
    @FXML
    public Label eTotalPayment;
    @FXML
    public Label wCurValueM3;
    @FXML
    public Label wCurCost;
    @FXML
    public Button bWRemovePayment;
    @FXML
    public TableView<WHTable> tableWPayment;
    @FXML
    public TableColumn<WHTable, Integer> wtId;
    @FXML
    public TableColumn<WHTable, ImageView> wtImg;
    @FXML
    public TableColumn<WHTable, String> wtDate;
    @FXML
    public TableColumn<WHTable, String> wValue;
    @FXML
    public TableColumn<WHTable, String> wtPrice;
    @FXML
    public TableColumn<WHTable, String> wtM3;
    @FXML
    public TableColumn<WHTable, String> wtTotal;
    @FXML
    public Label wTotal;
    @FXML
    public Label lblEFileName;
    @FXML
    public Label lblWFileName;
    @FXML
    public Button bWFile;

    @FXML
    public void initialize(){
        initFirstValues();
        initTables();
        initDatas();
        initLateEPaymentValues();
        initLateWPaymentValues();
    }

    public void clickClose(ActionEvent actionEvent) {
        close();
    }

    private void initTables(){
        initView();
        initETable();
        initWTable();
        initEPaymentHistory();
        initWPaymentHistory();
    }

    public void initEPaymentHistory(){
        History.initEPaymentHistory(tableEHistory, tableEPayments);
    }

    public void initWPaymentHistory(){
        History.initWPaymentHistory(tableWHistory, tableWPayment);
    }

    public void initLateEPaymentValues() {
        eDayPrevValue.setText("0");
        eNightPrevValue.setText("0");
        Answer answ = DataBaseUtility.getPaymentFirstValueByDateView(Date.valueOf(eDate.getValue()), 1);
        if (answ.answ == Answers.ERROR) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error initialization", answ.description + "\n Application will be close");
            close();
        }else {
            if (answ.list.size() > 0) {
                PaymentsEntity payment = (PaymentsEntity) answ.list.get(0);
                eDayPrevValue.setText(payment.getrVal1());
                eNightPrevValue.setText(payment.getrVal2());
            }
        }
    }

    public void initLateWPaymentValues() {
        wPrevVal.setText("0");
        Answer answ = DataBaseUtility.getPaymentFirstValueByDateView(Date.valueOf(eDate.getValue()), 2);
        if (answ.answ == Answers.ERROR) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error initialization", answ.description + "\n Application will be close");
            close();
        }else{
            if (answ.list.size() > 0){
                PaymentsEntity payment = (PaymentsEntity)answ.list.get(0);
                wPrevVal.setText(payment.getrVal1());
            }
        }
    }

    private void initETable() {
        id.setCellValueFactory(new PropertyValueFactory<EHTable, Integer>("id"));
        eHDate.setCellValueFactory(tableEHistory->new SimpleStringProperty(tableEHistory.getValue().getDate()));
        eHImg.setCellValueFactory(tableEHistory->new SimpleObjectProperty<>(tableEHistory.getValue().getImageView()));
        eHDay.setCellValueFactory(tableEHistory->new SimpleStringProperty(tableEHistory.getValue().geteHDay()));
        eHNight.setCellValueFactory(tableEHistory->new SimpleStringProperty(tableEHistory.getValue().geteHNight()));
        eHPDay.setCellValueFactory(tableEHistory->new SimpleStringProperty(tableEHistory.getValue().geteHPDay()));
        eHPNight.setCellValueFactory(tableEHistory->new SimpleStringProperty(tableEHistory.getValue().geteHPNight()));
        eHKwtDay.setCellValueFactory(tableEHistory->new SimpleStringProperty(tableEHistory.getValue().geteHKwtDay()));
        eHKwtNight.setCellValueFactory(tableEHistory->new SimpleStringProperty(tableEHistory.getValue().geteHKwtNight()));
        eHTotal.setCellValueFactory(tableEHistory-> new SimpleStringProperty(tableEHistory.getValue().geteHTotal()));
        id.setStyle("-fx-alignment: CENTER;");
        eHImg.setStyle("-fx-alignment: CENTER;");
        eHDate.setStyle("-fx-alignment: CENTER;");
        eHDay.setStyle("-fx-alignment: CENTER-RIGHT;");
        eHNight.setStyle("-fx-alignment: CENTER-RIGHT;");
        eHPDay.setStyle("-fx-alignment: CENTER-RIGHT;");
        eHPNight.setStyle("-fx-alignment: CENTER-RIGHT;");
        eHKwtDay.setStyle("-fx-alignment: CENTER-RIGHT;");
        eHKwtNight.setStyle("-fx-alignment: CENTER-RIGHT;");
        eHTotal.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private void initWTable(){
        wtId.setCellValueFactory(tableWHistory->new SimpleObjectProperty<>(tableWHistory.getValue().getWtId()));
        wtImg.setCellValueFactory(tableWHistory->new SimpleObjectProperty<>(tableWHistory.getValue().getImageView()));
        wtDate.setCellValueFactory(tableWHistory->new SimpleStringProperty(tableWHistory.getValue().getWtDate()));
        wValue.setCellValueFactory(tableWHistory->new SimpleStringProperty(tableWHistory.getValue().getWtValue()));
        wtPrice.setCellValueFactory(tableWHistory->new SimpleStringProperty(tableWHistory.getValue().getWtPrice()));
        wtM3.setCellValueFactory(tableWHistory->new SimpleStringProperty(tableWHistory.getValue().getWtM3()));
        wtTotal.setCellValueFactory(tableWHistory->new SimpleStringProperty(tableWHistory.getValue().getWtTotal()));
        wtId.setStyle("-fx-alignment: CENTER;");
        wtImg.setStyle("-fx-alignment: CENTER;");
        wtDate.setStyle("-fx-alignment: CENTER;");
        wValue.setStyle("-fx-alignment: CENTER-RIGHT;");
        wtPrice.setStyle("-fx-alignment: CENTER-RIGHT;");
        wtM3.setStyle("-fx-alignment: CENTER-RIGHT;");
        wtTotal.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private void initFirstValues(){
        eDate.setConverter(DateUtils.getStringConverter());
        eDate.setPromptText("dd.MM.yyyy");
        eDate.setValue(LocalDate.now());
        wDate.setConverter(DateUtils.getStringConverter());
        wDate.setPromptText("dd.MM.yyyy");
        wDate.setValue(LocalDate.now());
        bEFile.setText("");
        bEFile.setGraphic(new ImageView("./images/skrepka.jpg"));
        bWFile.setText("");
        bWFile.setGraphic(new ImageView("./images/skrepka.jpg"));
    }

    private void initDatas(){
        Date date = Date.valueOf(LocalDate.now());
        //Personal account.
        initASettingsByDate(date);
        //Electricity.
        initESettingsByDate(date);
        //Water.
        initWSettingsByDate(date);
    }

    private void initESettingsByDate(Date date){
        ePriceDay.setText("0");
        ePriceNight.setText("0");
        Answer answ = DataBaseUtility.getSettingsFirstValueByDateView(date,1);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error get electricity settings",answ.description + "\n You need reopen application");
        }else {
            List<Entities> list = answ.list;
            if (list.size() > 0) {
                SettingsEntity settings = (SettingsEntity) list.get(0);
                ePriceDay.setText(settings.getVal1());
                ePriceNight.setText(settings.getVal2());
            }
        }
    }

    private void initWSettingsByDate(Date date){
        wPrice.setText("0");
        Answer answ = DataBaseUtility.getSettingsFirstValueByDateView(date,2);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error get water settings",answ.description + "\n You need reopen application");
        }else {
            List<Entities> list = answ.list;
            if (list.size() > 0) {
                SettingsEntity settings = (SettingsEntity) list.get(0);
                wPrice.setText(settings.getVal1());
            }
        }
    }

    private void initASettingsByDate(Date date){
        Answer answ = DataBaseUtility.getSettingsFirstValueByDateView(date,3);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error get personal account settings",answ.description + "\n You need reopen application");
        }else{
            List<Entities> list = answ.list;
            if (list.size() > 0) {
                SettingsEntity settings = (SettingsEntity) list.get(0);
                lblAccount.setText(settings.getVal1());
            }
        }
    }

    private void initView(){
        Answer answ = DataBaseUtility.getList(Tables.VIEWS, new Condition[0]);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error initialization",answ.description + "\n Application will be close");
            close();
        }else if (answ.list.size() < 1){
            ViewsEntity[] views = new ViewsEntity[2];
            views[0] = new ViewsEntity();
            views[0].setName("Electricity");
            views[0].setId_v(1);
            views[1] = new ViewsEntity();
            views[1].setName("Water");
            views[1].setId_v(2);
            answ = DataBaseUtility.saveEntity(views);
            if (answ.answ == Answers.ERROR){
                InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error initialization","Application will be close");
                close();
            }
        }
    }

    public void clickSettings(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/settings_form.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void clickAbout(ActionEvent actionEvent) {
    }

    public void clickEDate(ActionEvent actionEvent) {
        Date date = Date.valueOf(eDate.getValue());
        if (date != null){
            initTables();
            initLateEPaymentValues();
            initESettingsByDate(date);
        }
    }

    public void eDayKeyTyped(KeyEvent keyEvent) {

    }

    public void eDayKeyReleased(KeyEvent keyEvent) {
        FormUtility.onlyNumbers(fEDayCurValue);
        double ePDay = FormUtility.parseDouble(ePriceDay.getText());
        if (ePDay > 0D){
            double ePrevVal = FormUtility.parseDouble(eDayPrevValue.getText());
            double eCurVal = FormUtility.parseDouble(fEDayCurValue.getText());
            double totalD = eCurVal - ePrevVal;
            eDayCurValueKwt.setText(df.format(totalD));
            eDayCostValue.setText(df.format(totalD*ePDay));
            calcETotal();
        }
    }

    public void calcETotal(){
        double total = FormUtility.parseDouble(eDayCostValue.getText()) +
                FormUtility.parseDouble(eNightCostValue.getText());
        eTotalPayment.setText(df.format(total));
    }

    public void eNightKeyReleased(KeyEvent keyEvent) {
        FormUtility.onlyNumbers(fENightCurValue);
        double ePNight = FormUtility.parseDouble(ePriceNight.getText());
        if (ePNight > 0D){
            double ePrevVal = FormUtility.parseDouble(eNightPrevValue.getText());
            double eCurVal = FormUtility.parseDouble(fENightCurValue.getText());
            double totalN = eCurVal - ePrevVal;
            eNightCurValueKwt.setText(df.format(totalN));
            eNightCostValue.setText(df.format(totalN*ePNight));
            calcETotal();
        }
    }

    public void clickEPayment(ActionEvent actionEvent) {
        int id_data;
        if (eFile != null){
            id_data = DataBaseUtility.saveFile(eFile);
        }else{
            id_data = 0;
        }
        if (eFile == null || (eFile != null && id_data > 0)) {
            PaymentsEntity[] payment = new PaymentsEntity[1];
            payment[0] = new PaymentsEntity();
            payment[0].setDate(Date.valueOf(eDate.getValue()));
            payment[0].setViewId(1);
            payment[0].setDataId(id_data);
            payment[0].setrVal1(fEDayCurValue.getText());
            payment[0].setrVal2(fENightCurValue.getText());
            payment[0].setpVal1(ePriceDay.getText());
            payment[0].setpVal2(ePriceNight.getText());
            payment[0].setVal1(eDayCurValueKwt.getText());
            payment[0].setVal2(eNightCurValueKwt.getText());
            Answer answ = DataBaseUtility.saveEntity(payment);
            if (answ.answ == Answers.ERROR) {
                InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error save payments", answ.description + "\n Error save payments");
            } else {
                History.initEPaymentHistory(tableEHistory, tableEPayments);
                initLateEPaymentValues();
                fEDayCurValue.setText("0");
                fENightCurValue.setText("0");
                eDayCurValueKwt.setText(".00");
                eNightCurValueKwt.setText(".00");
                eDayCostValue.setText(".00");
                eNightCostValue.setText(".00");
                lblEFileName.setText("none");
                lblEFileName.setStyle("-fx-text-fill: red");
            }
        }
    }

    public void clickEFile(ActionEvent actionEvent) {
        eFile = InterfaceBoxes.chooseFile(Main.primaryStage,DESCRIPTION_FILE, EXTENSIONS_FILE);
        if (eFile != null) {
            lblEFileName.setText(eFile.getName());
            lblEFileName.setStyle("-fx-text-fill: green");
        }
    }

    public void clickEHistory(MouseEvent mouseEvent) {
        EHTable row = tableEPayments.getSelectionModel().getSelectedItem();
        if (row != null && mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/e_payment_card_form.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            EPaymentCardController controller = fxmlLoader.getController();
            controller.initForm(this, row);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void clickWDate(Event event) {
        Date date = Date.valueOf(wDate.getValue());
        if (date != null){
            initWSettingsByDate(date);
            initLateWPaymentValues();
        }
    }

    private void close(){
        Stage s = (Stage) bEPayment.getScene().getWindow();
        s.close();
    }

    private Answer deleteFile(int id){
        DatasEntity data = new DatasEntity();
        data.setId(id);
        data.setName("");
        return DataBaseUtility.delete(data);
    }

    public void clickRemoveEHistory(MouseEvent mouseEvent) {
        EHTable row = tableEPayments.getSelectionModel().getSelectedItem();
        if (row != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete history");
            alert.setHeaderText("Delete row history");
            alert.setContentText("The history row will be deleted! Continue ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Answer answ = null;
                if (row.getDataId() > 0) {
                    answ = deleteFile(row.getDataId());
                    if (answ.answ == Answers.ERROR) {
                        InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error delete history", answ.description +
                                "\n Error delete history");
                        return;
                    }
                }
                PaymentsEntity entity = new PaymentsEntity();
                entity.setId(row.getId());
                entity.setDataId(row.getDataId());
                entity.setViewId(1);
                entity.setVal1(row.geteHDay());
                entity.setVal2(row.geteHNight());
                entity.setpVal1(row.geteHPDay());
                entity.setpVal2(row.geteHPNight());
                entity.setrVal1(row.geteHKwtDay());
                entity.setrVal2(row.geteHKwtNight());
                answ = DataBaseUtility.delete(entity);
                if (answ.answ == Answers.ERROR){
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error delete history",answ.description +
                            "\n Error delete history");
                }else{
                    initLateEPaymentValues();
                    History.initEPaymentHistory(tableEHistory, tableEPayments);
                }
            }
        }
    }

    public void wValueKeyReleased(KeyEvent keyEvent) {
        FormUtility.onlyNumbers(wCurValue);
        double price = FormUtility.parseDouble(wPrice.getText());
        if (price > 0D){
            double prevVal = FormUtility.parseDouble(wPrevVal.getText());
            double curVal = FormUtility.parseDouble(wCurValue.getText());
            double curValM3 = curVal - prevVal;
            wCurValueM3.setText(df.format(curValM3));
            wCurCost.setText(df.format(curValM3*price));
            calcWTotal();
        }
    }

    public void calcWTotal(){
        wTotal.setText(wCurCost.getText());
    }

    public void clickWPayment(ActionEvent event) {
        int id_data;
        if (wFile != null){
            id_data = DataBaseUtility.saveFile(wFile);
        }else{
            id_data = 0;
        }
        if (wFile == null || (wFile != null && id_data > 0)) {
            PaymentsEntity[] payment = new PaymentsEntity[1];
            payment[0] = new PaymentsEntity();
            payment[0].setDate(Date.valueOf(wDate.getValue()));
            payment[0].setViewId(2);
            payment[0].setDataId(0);
            payment[0].setrVal1(wCurValue.getText());
            payment[0].setrVal2("0");
            payment[0].setpVal1(wPrice.getText());
            payment[0].setpVal2("0");
            payment[0].setVal1(wCurValueM3.getText());
            payment[0].setVal2("0");
            Answer answ = DataBaseUtility.saveEntity(payment);
            if (answ.answ == Answers.ERROR) {
                InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error save payments", answ.description + "\n Error save payments");
            } else {
                History.initWPaymentHistory(tableWHistory, tableWPayment);
                initLateWPaymentValues();
                wCurValue.setText("0");
                wCurCost.setText(".00");
                wCurValueM3.setText(".00");
                lblWFileName.setText("none");
                lblWFileName.setStyle("-fx-text-fill: red");
            }
        }
    }

    public void clickWRemoveHistory(MouseEvent mouseEvent) {
        WHTable row = tableWPayment.getSelectionModel().getSelectedItem();
        if (row != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete history");
            alert.setHeaderText("Delete row history");
            alert.setContentText("The history row will be deleted! Continue ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Answer answ = null;
                if (row.getWtDataId() > 0) {
                    answ = deleteFile(row.getWtDataId());
                    if (answ.answ == Answers.ERROR) {
                        InterfaceBoxes.showMessage(Alert.AlertType.ERROR, "Error delete history", answ.description +
                                "\n Error delete history");
                        return;
                    }
                }
                PaymentsEntity entity = new PaymentsEntity();
                entity.setId(row.getWtId());
                entity.setDataId(0);
                entity.setViewId(2);
                entity.setVal1(row.getWtM3());
                entity.setVal2("0");
                entity.setpVal1(row.getWtPrice());
                entity.setpVal2("0");
                entity.setrVal1(row.getWtValue());
                entity.setrVal2("0");
                answ = DataBaseUtility.delete(entity);
                if (answ.answ == Answers.ERROR){
                    InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error delete history",answ.description + "\n Error delete history");
                }else{
                    initLateWPaymentValues();
                    History.initWPaymentHistory(tableWHistory, tableWPayment);
                }
            }
        }
    }

    public void clickWHistory(MouseEvent mouseEvent) {
        WHTable row = tableWPayment.getSelectionModel().getSelectedItem();
        if (row != null && mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/w_payment_card_form.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            WPaymentCardController controller = fxmlLoader.getController();
            controller.initForm(this, row);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void clickImg(TableColumn.CellEditEvent<EHTable, ImageView> cellEditEvent) {

    }

    public void cliclWImg(TableColumn.CellEditEvent<WHTable, ImageView> whTableImageViewCellEditEvent) {

    }

    public void clickWFile(ActionEvent event) {
        wFile = InterfaceBoxes.chooseFile(Main.primaryStage,DESCRIPTION_FILE, EXTENSIONS_FILE);
           if (wFile != null) {
            lblWFileName.setText(wFile.getName());
            lblWFileName.setStyle("-fx-text-fill: green");
        }
    }
}
