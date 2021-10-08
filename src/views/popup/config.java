package views.popup;

import daoFactory.DaoFactory;
import daoFactory.loadConfig;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class config extends JDialog {
    private JPanel main;
    private JTextField inputHost;
    private JTextField inputPost;
    private JTextField inputDataBase;
    private JTextField inputUser;
    private JTextField inputPass;
    private JButton btnSave;
    private JButton btnTest;
    private static String user = loadConfig.getInstance().USERNAME;
    private static String password = loadConfig.getInstance().PASSWORD;
    private static String port = loadConfig.getInstance().PORT;
    private static String host = loadConfig.getInstance().HOST;
    private static String database = loadConfig.getInstance().DATATABSE;

    public config() {
        this.setContentPane(main);
        this.setMinimumSize(new Dimension(400, 300));
        this.setPreferredSize(new Dimension(400, 300));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.pack();
        intLoad();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Properties properties = new Properties();
                String sql = "src/config/database.properties";
                try (OutputStream outputStream = new FileOutputStream(sql)) {
                    properties.setProperty("HOST", inputHost.getText());
                    properties.setProperty("PORT", inputPost.getText());
                    properties.setProperty("DATABASE", inputDataBase.getText());
                    properties.setProperty("USERNAME", inputUser.getText());
                    properties.setProperty("PASSWORD", inputPass.getText());
                    properties.store(outputStream, null);
                    JOptionPane.showMessageDialog(null,"Lưu thành công");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(DaoFactory.sqlServer().isDbConnected());
            }
        });
    }

    private void intLoad() {
        Icon test = IconFontSwing.buildIcon(FontAwesome.WRENCH, 15);
        Icon Save = IconFontSwing.buildIcon(FontAwesome.TASKS, 15);
        btnSave.setIcon(Save);
        btnTest.setIcon(test);
        inputHost.setText(host);
        inputPost.setText(port);
        inputDataBase.setText(database);
        inputPass.setText(password);
        inputUser.setText(user);
    }
}
