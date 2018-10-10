package ru.macrohome.client;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import ru.macrohome.common.InterfaceBoxes;

import java.awt.image.BufferedImage;
import java.io.*;


public class PDFViewController {
    private float scale = 1.0F;
    private PDDocument document;
    private PDFRenderer renderer;
    private int curPage = 0;

    @FXML
    public Button bBack;
    @FXML
    public TextField fNPage;
    @FXML
    public Label fCountPage;
    @FXML
    public Button bNext;
    @FXML
    public ImageView imgView;
    @FXML
    public Button bClose;

    public void beforeClosing(){
        closeDocument();
    }

    public void openByteArray(byte[] arr) {
        try {
            document = PDDocument.load(arr);
            renderer = new PDFRenderer(document);
            fCountPage.setText(String.valueOf(document.getPages().getCount()));
            fNPage.setText("1");
            curPage = 0;
            loadPage();
        } catch (IOException e) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error open or load pdf document",e.getMessage());
        }
    }

    private void closeDocument(){
        if (document != null){
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickBack(ActionEvent event) {
        if (curPage > 0) {
            curPage--;
            fNPage.setText(String.valueOf(curPage + 1));
            loadPage();
        }
    }

    public void clickNext(ActionEvent event) {
        if (document.getPages().getCount() - 1 > curPage) {
            curPage++;
            fNPage.setText(String.valueOf(curPage + 1));
            loadPage();
        }
    }

    public void clickClose(ActionEvent event) {
        close();
    }

    public void close(){
        closeDocument();
        Stage s = (Stage)bClose.getScene().getWindow();
        s.close();
    }

    public void loadPage(){
        BufferedImage img = null;
        try {
            img = renderer.renderImage(curPage, 1.0F, ImageType.RGB);
            imgView.setImage(SwingFXUtils.toFXImage(img,null));
            imgView.setFitWidth(img.getWidth());
            imgView.setFitHeight(img.getHeight());
            Stage stage = (Stage)bClose.getScene().getWindow();
            stage.setWidth(imgView.getFitWidth() + 2);
            stage.setHeight(imgView.getFitHeight() + 35);
        } catch (IOException e) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error open or load pdf document",e.getMessage());
        }
    }
}
