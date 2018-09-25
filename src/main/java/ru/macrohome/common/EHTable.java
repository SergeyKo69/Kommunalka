package ru.macrohome.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EHTable {
    private Integer id;
    private String eHDate;
    private String eHDay;
    private String eHNight;
    private String eHPDay;
    private String eHPNight;
    private String eHKwtDay;
    private String eHKwtNight;
    private String eHTotal;
    private Integer dataId;
    private ImageView imageView;

    public EHTable() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return eHDate;
    }

    public void setDate(String eHDate) {
        this.eHDate = eHDate;
    }

    public String geteHDay() {
        return eHDay;
    }

    public void seteHDay(String eHDay) {
        this.eHDay = eHDay;
    }

    public String geteHNight() {
        return eHNight;
    }

    public void seteHNight(String eHNight) {
        this.eHNight = eHNight;
    }

    public String geteHPDay() {
        return eHPDay;
    }

    public void seteHPDay(String eHPDay) {
        this.eHPDay = eHPDay;
    }

    public String geteHPNight() {
        return eHPNight;
    }

    public void seteHPNight(String eHPNight) {
        this.eHPNight = eHPNight;
    }

    public String geteHKwtDay() {
        return eHKwtDay;
    }

    public void seteHKwtDay(String eHKwtDay) {
        this.eHKwtDay = eHKwtDay;
    }

    public String geteHKwtNight() {
        return eHKwtNight;
    }

    public void seteHKwtNight(String eHKwtNight) {
        this.eHKwtNight = eHKwtNight;
    }

    public String geteHTotal() {
        return eHTotal;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public void seteHTotal(String eHTotal) {
        this.eHTotal = eHTotal;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImage(){
        setImageView(new ImageView(new Image("./images/skrepka.jpg")));
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}

