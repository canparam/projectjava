package controller;

import dao.AdminDao;
import model.Admin;
import services.AdminService;
import services.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class FormController {
    private static FormController instance = new FormController();
    private Admin admin;
    private AdminDao dao() {
        return new AdminDao();
    }

    private AdminService adminService() {
        return new AdminService();
    }

    public static FormController getInstance() {
        return instance;
    }

    public Boolean Login(Object[] data) throws SQLException, IOException, ClassNotFoundException {
        String sql = "WHERE username = ? AND pass = ?";
        admin = (Admin) data[0];
        List<String> contai = new ArrayList<>();
        contai.add(admin.getUsername());
        contai.add(admin.getPassword());
        ArrayList<Admin> admins = (ArrayList<Admin>) this.dao().customQuery(sql, contai);
        if (admins.size() > 0) {
            if (data[1].equals(true)) {
                adminService().SAVE(admin);
            }
            UserSession.getInstance().SAVE(admins.get(0));
            return true;
        }
        return false;
    }

    public Admin getAbout() throws SQLException {
        Admin admin = dao().getOne(UserSession.getInstance().getId());
        return admin;
    }

    public boolean createAccount(Admin admin) throws SQLException {
        int unique = adminService().getUnique(admin.getUsername());
        if (unique == 1) {
            return false;
        } else {
            dao().insert(admin);
        }
        return true;
    }

    public boolean changePass(String oldpass, String newPass) throws SQLException {
        Admin ad = dao().getOne(UserSession.getInstance().getId());
        if (ad.getPassword().equals(oldpass)) {
            ad.setPassword(newPass);
            dao().update(ad, ad.getId());
            return true;
        }
        return false;
    }
}
