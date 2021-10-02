package views;

import controller.FormController;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import model.Admin;
import model.Language;
import services.AdminService;
import services.UserSession;
import views.popup.sigup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

public class form extends JFrame {
    private JPanel form;
    private JPanel jform;
    private JTextField username;
    private JPasswordField password;
    private JComboBox language;
    private JLabel iconUser;
    private JLabel iconPass;
    private JLabel iconLanguage;
    private JButton btnLogin;
    private JProgressBar process;
    private JCheckBox remember;
    private JLabel noti;
    private JButton signUpButton;

    private AdminService adminService() {
        return new AdminService();
    }

    public form() throws InterruptedException {
        super("Phần mềm quản lý thư viện");
        this.setContentPane(form);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(350, 500));
        this.setPreferredSize(new Dimension(350, 500));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.intSetting();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Loading();
                if (validateForm().length() <= 0) {
                    noti.setText("");
                    Admin admin = new Admin();
                    admin.setUsername(username.getText());
                    admin.setPassword(new String(password.getPassword()));
                    btnLogin.setEnabled(false);
                    Language lang = (Language) language.getSelectedItem();
                    Object[] data = {
                            admin, false
                    };
                    if (remember.getModel().isSelected()) {
                        data[1] = true;
                    }
                    try {
                        Boolean respon = FormController.getInstance().Login(data);
                        if (respon.equals(true)){
                            if (lang.getId() == 1){
                                UserSession.getInstance().saveLanguage("vi");
                            }else {
                                UserSession.getInstance().saveLanguage("en");
                            }
                            JFrame main = new main();
                            main.setVisible(true);
                            dispose();
                        }else {
                            noti.setText("Sai tên đăng nhập hoặc mật khẩu");
                        }
                    } catch (IOException | SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    noti.setText(validateForm());
                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sigupModal();
            }
        });
    }

    private void intSetting() throws InterruptedException {
        Icon icon = IconFontSwing.buildIcon(FontAwesome.USER, 15);
        Icon iconn = IconFontSwing.buildIcon(FontAwesome.KEY, 15);
        Icon lang = IconFontSwing.buildIcon(FontAwesome.GLOBE, 15);
        Icon sign = IconFontSwing.buildIcon(FontAwesome.SIGN_IN, 25, Color.decode("#ffb115"));
        Icon reg = IconFontSwing.buildIcon(FontAwesome.SIGN_IN, 15);
        Icon rem = IconFontSwing.buildIcon(FontAwesome.CHECK_CIRCLE, 15);
        Icon signu = IconFontSwing.buildIcon(FontAwesome.USER_PLUS, 25, Color.decode("#ffb115"));
        iconUser.setIcon(icon);
        iconPass.setIcon(iconn);
        iconLanguage.setIcon(lang);
        btnLogin.setIcon(sign);
        btnLogin.setFont(new Font("", Font.BOLD, 12));
        signUpButton.setIcon(signu);
        signUpButton.setFont(new Font("", Font.BOLD, 12));

        Vector<Language> list = new Vector<>();
        Language tv = new Language(1, "Tiếng việt");
        Language eng = new Language(2, "English");
        list.add(tv);
        list.add(eng);
        language.setModel(new DefaultComboBoxModel(list));
        language.setBorder(new EmptyBorder(0, 5, 0, 5));
        if (adminService().UPDATE() != null) {
            remember.setSelected(true);
            username.setText(adminService().UPDATE().getUsername());
            password.setText(adminService().UPDATE().getPassword());
        }

    }

    private String validateForm() {
        String errors = "";
        if (username.getText().isEmpty()) {
            errors += "Không để trống username \n";
        }
        if (password.getPassword().length <= 0) {
            errors += "Không để trống password \n";
        }
        return errors;
    }

    public void Loading() {

        final SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            private long sleepTime = 100;

            protected Void doInBackground() throws Exception {
                int progress = 0;
                setProgress(progress);
                while (progress < 100) {
                    progress += 10;
                    progress = Math.min(100, progress);
                    setProgress(progress);
                    Thread.sleep(sleepTime);
                }
                return null;
            }
        };
        mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pcEvt) {
                if ("progress".equals(pcEvt.getPropertyName())) {
                    process.setValue(mySwingWorker.getProgress());
                    process.setString(mySwingWorker.getProgress() + "%");
                }
                if ("state".equals(pcEvt.getPropertyName())) {
                    if (pcEvt.getNewValue() == SwingWorker.StateValue.DONE) {
                        btnLogin.setEnabled(true);
                    }
                }
            }
        });
        mySwingWorker.execute();
    }
    private void sigupModal(){
        JDialog jDialog = new sigup();
        jDialog.setTitle("Sign Up Account");
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }

}
