package ru.macrohome.client;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.macrohome.common.*;
import ru.macrohome.entity.ViewsEntity;
import ru.macrohome.server.DataBaseUtility;

import java.io.IOException;
import java.time.LocalDate;

public class MainController {

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
    @FXML
    public Label lblAccount;
    @FXML
    public MenuItem bClose;
    @FXML
    public Menu bFile;

    @FXML
    public void initialize(){
        eDate.setValue(LocalDate.now());
        initTables();
    }

    public void clickClose(ActionEvent actionEvent) {
        close();
    }

    private void initTables(){
        initView();
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

    private void close(){
        Stage s = (Stage) bEPayment.getScene().getWindow();
        s.close();
    }
}
