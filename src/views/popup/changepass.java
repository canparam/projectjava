package views.popup;

import controller.FormController;
import services.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class changepass extends JDialog {
    private JPanel panel;
    private JPasswordField oldPass;
    private JPasswordField newPass;
    private JButton saveButton;
    private JLabel loldpass;
    private JLabel lnewpass;
    Locale localeEn = new Locale(UserSession.getInstance().getLangguage());
    ResourceBundle labels = ResourceBundle.getBundle("language/Language", localeEn);

    public changepass() {
        this.setContentPane(panel);
        this.setSize(350, 180);
        this.setMinimumSize(new Dimension(350, 180));
        this.setPreferredSize(new Dimension(350, 180));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        intSetting();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validated().length() <= 0) {
                    try {
                        boolean ch = FormController.getInstance().changePass(new String(oldPass.getPassword()),
                                new String(newPass.getPassword()));
                        if (ch) {
                            JOptionPane.showMessageDialog(null, "Thay đổi mật khẩu thành công", "Noti", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Mật khẩu cũ không đúng", "Noti", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, validated());
                }
            }
        });
    }

    private void intSetting() {
        loldpass.setText(labels.getString("oldpass"));
        lnewpass.setText(labels.getString("newpass"));
    }

    private String validated() {
        String errors = "";
        if (oldPass.getPassword().length <= 0) {
            errors += "Không để trống mk cũ \n";
        }
        if (newPass.getPassword().length <= 0) {
            errors += "Không để trống mk mới \n";
        }
        return errors;
    }
}
