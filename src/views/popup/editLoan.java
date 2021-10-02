package views.popup;

import dao.LoanDao;
import model.Gender;
import model.Loan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class editLoan extends JDialog{
    private JPanel main;
    private JTextField studentName;
    private JComboBox cbStatus;
    private JTextField bName;
    private JButton saveButton;
    private JButton closeButton;
    private Loan loan;
    private LoanDao loanDao(){
        return new LoanDao();
    }
    public editLoan(Loan loan){
        this.loan = loan;
        this.setContentPane(main);
        this.setSize(350, 150);
        this.setMinimumSize(new Dimension(350, 150));
        this.setPreferredSize(new Dimension(350, 150));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
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
                Gender gender = (Gender) cbStatus.getSelectedItem();
                try {
                    boolean i = loanDao().update(gender.getId(),loan.getId());
                    System.out.println(i);
                    if (i){
                        JOptionPane.showMessageDialog(null,"Thành công");
                        dispose();
                        return;
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
    }
    private void intData(){
        studentName.setText(loan.getcName());
        bName.setText(loan.getbName());
        cbStatus.addItem(new Gender(1,"Chưa trả"));
        cbStatus.addItem(new Gender(2,"Đã trả"));
        for (int i = 0; i < cbStatus.getItemCount(); i++) {
            Gender gender = (Gender) cbStatus.getItemAt(i);
            if (loan.getStatus() == gender.getId()){
                cbStatus.setSelectedItem(gender);
            }
        }
    }
}
