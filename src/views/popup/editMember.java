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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class editMember extends JDialog{
    private JPanel main;
    private JLabel fname;
    private JTextField ipName;
    private JLabel Jclassess;
    private JTextField ipClassess;
    private JLabel jCode;
    private JTextField ipStudentCode;
    private JComboBox comboxGender;
    private JLabel jAddress;
    private JTextField ipAddress;
    private JButton saveButton;
    private JButton closeButton;
    private JDateChooser dateChosee;
    private Student studentx;
    public editMember(Student student) throws ParseException {
        this.setTitle("Sửa thông tin");
        this.setContentPane(main);
        this.setMinimumSize(new Dimension(350, 250));
        this.setPreferredSize(new Dimension(350, 250));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.studentx = student;
        intData();
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        saveButton.addActionListener(new ActionListener() {
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
                        boolean st = MemberController.getInstance().update(student,studentx.getMaSV());
                        if (st){
                            JOptionPane.showMessageDialog(null,"Sửa thành công");
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
    private void createUIComponents() {
        dateChosee = new JDateChooser();
        dateChosee.updateUI();
    }
    private void intData() throws ParseException {
        ipName.setText(studentx.getName());
        ipStudentCode.setText(studentx.getMaSV());
        ipAddress.setText(studentx.getAddress());
        ipClassess.setText(studentx.getClasses());
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(studentx.getBirthOfDay().toString());
        dateChosee.setDate(date);
        comboxGender.addItem(new Gender(1,"Nam"));
        comboxGender.addItem(new Gender(2,"Nữ"));
        for (int i = 0; i < comboxGender.getItemCount(); i++) {
            Gender gender = (Gender) comboxGender.getItemAt(i);
            if (studentx.getGender() == gender.getId()){
                comboxGender.setSelectedItem(gender);
            }
        }
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
