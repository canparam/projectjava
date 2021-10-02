package services;

import dao.AdminDao;
import model.Admin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    private AdminDao dao;
    File file = new File("./data.dat");

    public static enum Roles{
        Admin,Staff
    }

    public AdminService() {

    }
    private AdminDao dao() {
        return new AdminDao();
    }

    public void SAVE(Admin admin) {
        try {
            if (!file.exists()) file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(admin);

            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Admin UPDATE(){
        try {
            if(file.exists()){
                FileInputStream fileIn = new FileInputStream(file.getAbsoluteFile());
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Admin obj = (Admin) objectIn.readObject();
                return obj;
            }

        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void DeleteRemember() throws IOException, ClassNotFoundException {
        if(file.exists()){
            Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
        }
    }
    public int getUnique(String username) throws SQLException {
        List<String> contai = new ArrayList<>();
        String sql = "WHERE username = ?";
        contai.add(username);
        ArrayList<Admin> admins = (ArrayList<Admin>) this.dao().customQuery(sql,contai);
        if (admins.size() > 0){
            return 1;
        }
        return 0;
    }

}
