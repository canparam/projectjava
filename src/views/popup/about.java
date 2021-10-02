package views.popup;

import model.Admin;
import services.AdminService;
import services.UserSession;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class about extends JDialog {
    private JPanel aboutMain;
    private JTextField tName;
    private JTextField tuserName;
    private JTextField trole;
    private JLabel name;
    private JLabel username;
    private JLabel role;
    private Admin admin;
    Locale localeEn = new Locale(UserSession.getInstance().getLangguage());
    ResourceBundle labels = ResourceBundle.getBundle("language/Language", localeEn);
    private AdminService AdminService(){
        return new AdminService();
    }
    public about(Admin admin){
        this.admin = admin;
        this.setTitle(labels.getString("about"));
        this.setContentPane(aboutMain);
        this.setMinimumSize(new Dimension(350, 150));
        this.setPreferredSize(new Dimension(350, 150));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        intnSetting();

    }
    public void intnSetting(){
        tName.setText(admin.getName());
        tName.setEnabled(false);
        tuserName.setEnabled(false);
        trole.setEnabled(false);
        tuserName.setText(admin.getUsername());
        if (admin.getRoles() == 1){
            trole.setText(String.valueOf(AdminService.Roles.Admin));
        }else {
            trole.setText(String.valueOf(AdminService.Roles.Staff));
        }
        name.setText(labels.getString("name"));
        username.setText(labels.getString("username"));
        role.setText(labels.getString("role"));
    }
}
