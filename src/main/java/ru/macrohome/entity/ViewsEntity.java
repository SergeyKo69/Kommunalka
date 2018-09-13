package ru.macrohome.entity;

import ru.macrohome.common.Entities;

import javax.persistence.*;

@Entity
@Table(name = "views", schema = "main")
public class ViewsEntity extends Entities {
    private Integer id;
    private String name;
    private Integer id_v;


    @Basic
    @Column(name = "id_v")
    public Integer getId_v() {
        return id_v;
    }

    public void setId_v(Integer id_v) {
        this.id_v = id_v;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewsEntity that = (ViewsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
