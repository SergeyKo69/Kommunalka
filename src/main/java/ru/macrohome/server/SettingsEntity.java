package ru.macrohome.server;

import javax.persistence.*;

@Entity
@Table(name = "settings", schema = "main", catalog = "")
public class SettingsEntity {
    private short id;
    private Object viewId;
    private Object date;
    private Object val1;
    private Object val2;

    @Id
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "view_id", nullable = false)
    public Object getViewId() {
        return viewId;
    }

    public void setViewId(Object viewId) {
        this.viewId = viewId;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    @Basic
    @Column(name = "val1", nullable = true, length = 50)
    public Object getVal1() {
        return val1;
    }

    public void setVal1(Object val1) {
        this.val1 = val1;
    }

    @Basic
    @Column(name = "val2", nullable = true, length = 50)
    public Object getVal2() {
        return val2;
    }

    public void setVal2(Object val2) {
        this.val2 = val2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingsEntity that = (SettingsEntity) o;

        if (id != that.id) return false;
        if (viewId != null ? !viewId.equals(that.viewId) : that.viewId != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (val1 != null ? !val1.equals(that.val1) : that.val1 != null) return false;
        if (val2 != null ? !val2.equals(that.val2) : that.val2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (viewId != null ? viewId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (val1 != null ? val1.hashCode() : 0);
        result = 31 * result + (val2 != null ? val2.hashCode() : 0);
        return result;
    }
}
