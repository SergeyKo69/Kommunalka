package ru.macrohome.common;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WHTable {
    private Integer wtId;
    private Integer wtDataId;
    private String wtDate;
    private String wtValue;
    private String wtPrice;
    private String wtM3;
    private String wtTotal;
    private ImageView imageView;

    public Integer getWtId() {
        return wtId;
    }

    public void setWtId(Integer wtId) {
        this.wtId = wtId;
    }

    public String getWtDate() {
        return wtDate;
    }

    public void setWtDate(String wtDate) {
        this.wtDate = wtDate;
    }

    public String getWtValue() {
        return wtValue;
    }

    public void setWtValue(String wtValue) {
        this.wtValue = wtValue;
    }

    public String getWtPrice() {
        return wtPrice;
    }

    public void setWtPrice(String wtPrice) {
        this.wtPrice = wtPrice;
    }

    public String getWtM3() {
        return wtM3;
    }

    public void setWtM3(String wtM3) {
        this.wtM3 = wtM3;
    }

    public String getWtTotal() {
        return wtTotal;
    }

    public void setImage(){
        setImageView(new ImageView(getClass().getResource("/images/skrepka.jpg").toExternalForm()));
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setWtTotal(String wtTotal) {
        this.wtTotal = wtTotal;
    }

    public Integer getWtDataId() {
        return wtDataId;
    }

    public void setWtDataId(Integer wtDataId) {
        this.wtDataId = wtDataId;
    }
}
