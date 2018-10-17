package ru.macrohome.client;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.macrohome.common.Answer;
import ru.macrohome.common.Answers;
import ru.macrohome.common.InterfaceBoxes;
import ru.macrohome.server.Connector;

import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;
    private Stage splashStage;
    private SplashController splashController;

    public class AnswerInitMain{
        private Answer answ;
        private Parent parent;

        public AnswerInitMain(Answer answ, Parent parent) {
            this.answ = answ;
            this.parent = parent;
        }
    }

    public void initSpash(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/splashScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        splashController = loader.getController();
        root.setStyle(
                "-fx-padding: 5; " +
                        "-fx-background-color: #bbe3ff; " +
                        "-fx-border-width:5; " +
                        "-fx-border-color: " +
                        "linear-gradient(" +
                        "to bottom, " +
                        "#2457d2, " +
                        "derive(chocolate, 50%)" +
                        ");"
        );
        root.setEffect(new DropShadow());
        splashStage = new Stage();
        splashStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/isotype.jpg")));
        splashStage.setScene(new Scene(root));
        splashStage.toFront();
        splashStage.initStyle(StageStyle.TRANSPARENT);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Task<AnswerInitMain> task = new Task<AnswerInitMain>() {
            @Override
            protected AnswerInitMain call() throws Exception {
                Answer answ = null;
                updateProgress(1, 6);
                updateMessage("load... main form");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/payments_MainForm.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                root.setStyle("-fx-background-color: #b7e9ff");
                MainController controller = loader.getController();
                updateProgress(2, 6);
                updateMessage("load... first values");
                answ = controller.initFirstValues();
                if (answ.answ == Answers.ERROR){
                    return new AnswerInitMain(answ,root);
                }
                updateProgress(3, 6);
                updateMessage("load... tables");
                answ = controller.initTables();
                if (answ.answ == Answers.ERROR){
                    return new AnswerInitMain(answ,root);
                }
                updateProgress(4, 6);
                updateMessage("load... datas");
                answ = controller.initDatas();
                if (answ.answ == Answers.ERROR){
                    return new AnswerInitMain(answ,root);
                }
                updateProgress(5, 6);
                updateMessage("load... electricity history");
                answ = controller.initLateEPaymentValues();
                if (answ.answ == Answers.ERROR){
                    return new AnswerInitMain(answ,root);
                }
                updateProgress(6, 6);
                updateMessage("load... water history");
                answ = controller.initLateWPaymentValues();
                if (answ.answ == Answers.ERROR){
                    return new AnswerInitMain(answ,root);
                }
                updateMessage("load... done");
                return new AnswerInitMain(answ,root);
            }
        };
        initSpash();
        showSplash(
                task,
                new InitCompletionHandler() {
                    @Override
                    public void complete() {
                        Main.this.showMainForm(task.valueProperty());
                    }
                }
        );
        new Thread(task).start();
    }

    public void showSplash(Task<?> task, InitCompletionHandler initCompletionHandler){
        splashController.getLblStatus().textProperty().bind(task.messageProperty());
        splashController.getPbStatus().progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED){
                    initCompletionHandler.complete();
                }
            }
        });
        splashStage.show();
    }

    public void showMainForm(ReadOnlyObjectProperty<AnswerInitMain> root){
        splashStage.hide();
        if (root.getValue().answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,
                    "Error init main form",root.getValue().answ.description);
        }else {
            Stage stage = new Stage();

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/isotype.jpg")));
            stage.setScene(new Scene(root.getValue().parent));
            Main.primaryStage = stage;
            stage.show();
        }
    }

    public interface InitCompletionHandler {
        void complete();
    }

    public void close(){
        primaryStage.close();
    }

    @Override
    public void stop(){
        Connector.closeSessionFactory();
    }

    public static void main(String[] args) {
       launch(args);
    }
}
