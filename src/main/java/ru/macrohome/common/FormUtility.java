package ru.macrohome.common;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class FormUtility {
    public static void onlyNumbers(TextField field){
        field.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,20}([\\.]\\d{0,2})?")) {
                    field.setText(oldValue);
                }
            }
        });
    }

    public static double parseDouble(String field){
        if (field.equals("")){
            return 0D;
        }else{
            return Double.parseDouble(field);
        }
    }
}
