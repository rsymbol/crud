package ru.crudgroup.crud.models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Основной класс пользователей
 */
@Entity
@Table(name = "user", schema = "userbase")
public class User {
    /** характеристики пользователя */
    private int id;                     //внутренний ID
    private String name;                //имя
    private int age;                    //возраст
    private boolean admin;              //является ли админом
    private Timestamp createdDate;      //дата внесения в базу

    /** конструктор по умолчанию (для hebirnate)*/
    public User() {
    }

    /** инициализация характеристик*/
    public User(int id, String name, int age, boolean admin, Timestamp createdDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.admin = admin;
        this.createdDate = createdDate;
    }

    /**
     * стандартные сеттеры и геттеры для hebirnate)
     * аннотации для связки с БД (для hebirnate)
     */
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "isAdmin", nullable = false)
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Basic
    @Column(name = "createdDate", nullable = false)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /** переопределение для работы с классом User*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (id != that.id) return false;
        if (age != that.age) return false;
        if (admin != that.admin) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }

    /** переопределение для работы с классом User*/
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    /** переопределение для работы с классом User*/
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", admin=" + admin +
                ", createdDate=" + createdDate +
                '}';
    }

}
