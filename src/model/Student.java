package model;

import dao.AdminDao;
import dao.StudentDao;
import daoFactory.DaoFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Student{
    private int id;
    private String maSV;
    private int gender;
    private LocalDate birthOfDay;
    private String classes;
    private String address;
    private String name;



    private StudentDao dao(){
        return new StudentDao();
    }
    public Student() {
    }

    public Student(String maSV, int gender, LocalDate birthOfDay, String classes) {
        super();
        this.maSV = maSV;
        this.gender = gender;
        this.birthOfDay = birthOfDay;
        this.classes = classes;
    }
    public Student(String maSV, int gender, String birthOfDay, String classes) {
        super();
        this.maSV = maSV;
        this.gender = gender;
        this.setBirthOfDay(birthOfDay);
        this.classes = classes;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public LocalDate getBirthOfDay() {
        return birthOfDay;
    }

    public void setBirthOfDay(LocalDate birthOfDay) {
        this.birthOfDay = birthOfDay;
    }
    public void setBirthOfDay(String birthOfDay) {
        this.birthOfDay = LocalDate.parse(birthOfDay, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", maSV='" + maSV + '\'' +
                ", gender=" + gender +
                ", birthOfDay=" + birthOfDay +
                ", classes='" + classes + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    //insert
    private void insert() throws SQLException {
        dao().insert(this);
    }
}
