package ru.macrohome.entity;

import ru.macrohome.common.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments", schema = "main")
public class PaymentsEntity extends Entities {
    private Integer id;
    private Date date;
    private Integer viewId;
    private Integer rVal1;
    private Integer rVal2;
    private Integer pVal1;
    private Integer pVal2;
    private Integer val1;
    private Integer val2;
    private Integer dataId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "view_id", nullable = false)
    public Integer getViewId() {
        return viewId;
    }

    public void setViewId(Integer viewId) {
        this.viewId = viewId;
    }

    @Basic
    @Column(name = "r_val1", nullable = true)
    public Object getrVal1() {
        return rVal1;
    }

    public void setrVal1(Integer rVal1) {
        this.rVal1 = rVal1;
    }

    @Basic
    @Column(name = "r_val2", nullable = true)
    public Integer getrVal2() {
        return rVal2;
    }

    public void setrVal2(Integer rVal2) {
        this.rVal2 = rVal2;
    }

    @Basic
    @Column(name = "p_val1", nullable = true)
    public Object getpVal1() {
        return pVal1;
    }

    public void setpVal1(Integer pVal1) {
        this.pVal1 = pVal1;
    }

    @Basic
    @Column(name = "p_val2", nullable = true)
    public Integer getpVal2() {
        return pVal2;
    }

    public void setpVal2(Integer pVal2) {
        this.pVal2 = pVal2;
    }

    @Basic
    @Column(name = "val1", nullable = true)
    public Integer getVal1() {
        return val1;
    }

    public void setVal1(Integer val1) {
        this.val1 = val1;
    }

    @Basic
    @Column(name = "val2", nullable = true)
    public Integer getVal2() {
        return val2;
    }

    public void setVal2(Integer val2) {
        this.val2 = val2;
    }

    @Basic
    @Column(name = "data_id", nullable = true)
    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentsEntity that = (PaymentsEntity) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (viewId != null ? !viewId.equals(that.viewId) : that.viewId != null) return false;
        if (rVal1 != null ? !rVal1.equals(that.rVal1) : that.rVal1 != null) return false;
        if (rVal2 != null ? !rVal2.equals(that.rVal2) : that.rVal2 != null) return false;
        if (pVal1 != null ? !pVal1.equals(that.pVal1) : that.pVal1 != null) return false;
        if (pVal2 != null ? !pVal2.equals(that.pVal2) : that.pVal2 != null) return false;
        if (val1 != null ? !val1.equals(that.val1) : that.val1 != null) return false;
        if (val2 != null ? !val2.equals(that.val2) : that.val2 != null) return false;
        if (dataId != null ? !dataId.equals(that.dataId) : that.dataId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (viewId != null ? viewId.hashCode() : 0);
        result = 31 * result + (rVal1 != null ? rVal1.hashCode() : 0);
        result = 31 * result + (rVal2 != null ? rVal2.hashCode() : 0);
        result = 31 * result + (pVal1 != null ? pVal1.hashCode() : 0);
        result = 31 * result + (pVal2 != null ? pVal2.hashCode() : 0);
        result = 31 * result + (val1 != null ? val1.hashCode() : 0);
        result = 31 * result + (val2 != null ? val2.hashCode() : 0);
        result = 31 * result + (dataId != null ? dataId.hashCode() : 0);
        return result;
    }
}
