package services;

import model.Admin;

import java.io.File;
import java.io.IOException;

public class UserSession {
    private static UserSession instance = new UserSession();
    private Admin admin;
    private String langguage;
    File file = new File("./tmp.dat");

    private UserSession() {

    }

    public static UserSession getInstance() {
        return instance;
    }
    public int getId(){
        return admin.getId();
    }
    public void SAVE(Admin admin) throws IOException, ClassNotFoundException {
        this.admin = admin;
    }
    public void saveLanguage(String lang){
        this.langguage = lang;
    }
    public String getLangguage(){
        return langguage;
    }
    public String getUsername() {
        return admin.getUsername();
    }

    public String getName() {
        return admin.getName();
    }

    public int getRoles() {
        return admin.getRoles();
    }

    public Admin getAll(){
        return admin;
    }
}
