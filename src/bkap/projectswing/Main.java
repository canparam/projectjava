package bkap.projectswing;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import views.form;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        IconFontSwing.register(FontAwesome.getIconFont());
        System.setProperty("sun.java2d.opengl", "true");
        java.net.URL url = ClassLoader.getSystemResource("com/xyz/resources/camera.png");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch(Exception ignored){}
        JFrame myapp = new form();

        myapp.setVisible(true);
    }
}
