package views.popup;

import controller.FormController;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class sigup extends JDialog {
    private JPanel panel1;
    private JTextField tname;
    private JTextField tusername;
    private JPasswordField tpassword;
    private JButton signUpButton;
    private JLabel labelName;
    private JLabel labelUsername;
    private JLabel labelPass;
    private JLabel erros;

    public sigup() {
        this.setLocationRelativeTo(null);
        this.setContentPane(panel1);
        this.setSize(350, 180);
        this.setMinimumSize(new Dimension(350, 180));
        this.setPreferredSize(new Dimension(350, 180));
        this.setResizable(false);
        this.pack();
        intSetting();
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm().length() <= 0) {
                    Admin admin = new Admin();
                    admin.setName(tname.getText());
                    admin.setUsername(tusername.getText());
                    admin.setPassword(new String(tpassword.getPassword()));
                    try {
                        boolean ad = FormController.getInstance().createAccount(admin);
                        if (ad) {
                            JOptionPane.showMessageDialog(null, "Đăng kí thành công");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại");
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    String er = validateForm();
                    JOptionPane.showMessageDialog(null, er);
                }
            }
        });
    }

    private void intSetting() {
        Icon icon = IconFontSwing.buildIcon(FontAwesome.USER, 15);
        Icon name = IconFontSwing.buildIcon(FontAwesome.USER_O, 15);
        Icon iconn = IconFontSwing.buildIcon(FontAwesome.KEY, 15);
        Icon sign = IconFontSwing.buildIcon(FontAwesome.SIGN_IN, 25, Color.decode("#ffb115"));
        Icon signu = IconFontSwing.buildIcon(FontAwesome.PLUS, 15, Color.decode("#ffb115"));
        labelPass.setIcon(iconn);
        labelName.setIcon(name);
        labelUsername.setIcon(icon);
        signUpButton.setIcon(signu);
        signUpButton.setFont(new Font("", Font.BOLD, 12));
    }

    private String validateForm() {
        String errors = "";
        if (tname.getText().isEmpty()) {
            errors += "\nKhông để trống tên";
        }
        if (tpassword.getPassword().length <= 0) {
            errors += "\nKhông để trống password";
        }
        if (tusername.getText().isEmpty()) {
            errors += "\nKhông để trống username";
        }
        return errors;
    }
}
