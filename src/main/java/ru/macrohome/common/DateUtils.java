package ru.macrohome.common;

import javafx.scene.control.Alert;
import javafx.util.StringConverter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static String dateFormat = "dd.MM.yyyy";

    public static StringConverter getStringConverter(){
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

            @Override
            public String toString(LocalDate object) {
                if (object != null){
                    return dateFormatter.format(object);
                }else{
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        return converter;
    }

    public static String getDateInFormated(Date date){
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static Date getDateOfString(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            return new Date(simpleDateFormat.parse(dateString).getTime());
        } catch (ParseException e) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error parse string to date",e.getMessage());
        }
        return null;
    }
}
