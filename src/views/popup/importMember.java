package views.popup;

import com.toedter.calendar.JDateChooser;
import controller.MemberController;
import model.Gender;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class importMember extends JDialog{
    private JPanel main;
    private JTextField ipName;
    private JLabel fname;
    private JLabel Jclassess;
    private JTextField ipClassess;
    private JLabel jCode;
    private JTextField ipStudentCode;
    private JComboBox comboxGender;
    private JLabel jAddress;
    private JTextField ipAddress;
    private JDateChooser dateChosee;
    private JButton importButton;
    private JButton resetButton;
    private JLabel test;

    public importMember(){
        this.setTitle("Import Student");
        this.setContentPane(main);
        this.setMinimumSize(new Dimension(350, 250));
        this.setPreferredSize(new Dimension(350, 250));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        intSettings();
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm().length() <= 0){
                    SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
                    String date = smp.format(dateChosee.getDate());
                    Gender gender = (Gender) comboxGender.getSelectedItem();
                    Student student = new Student();
                    student.setBirthOfDay(date);
                    student.setClasses(ipClassess.getText());
                    student.setMaSV(ipStudentCode.getText());
                    student.setName(ipName.getText());
                    student.setGender(gender.getId());
                    student.setAddress(ipAddress.getText());
                    try {
                        boolean st = MemberController.getInstance().store(student);
                        if (st){
                            dispose();
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(null,validateForm());
                }
            }
        });
    }
    private void intSettings(){
        comboxGender.addItem(new Gender(1,"Nam"));
        comboxGender.addItem(new Gender(2,"Nữ"));
    }
    private void createUIComponents() {
        dateChosee = new JDateChooser();
        dateChosee.updateUI();
    }
    private String validateForm(){
        String errors = "";
        if (ipName.getText().isEmpty()){
            errors += "Không để trống tên \n";
        }
        if (ipStudentCode.getText().isEmpty()){
            errors += "Không để trống mã HS \n";
        }
        if (ipAddress.getText().isEmpty()){
            errors += "Không để trống địa chỉ \n";
        }
        if (dateChosee.getDate() == null){
            errors += "Vui lòng chọn ngày sinh \n";
        }
        if (ipClassess.getText().isEmpty()){
            errors += "Vui lòng nhập lớp";
        }
        return errors;
    }
}
