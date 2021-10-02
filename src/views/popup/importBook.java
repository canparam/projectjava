package views.popup;

import controller.BookController;
import model.Book;
import model.Category;
import services.BookService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class importBook extends JDialog{
    private JPanel main;
    private JTextField bookname;
    private JTextField bookAuthor;
    private JTextField bookCode;
    private JComboBox book_cate;
    private JButton booksave;
    private JButton bookclose;
    private JTextField bookQuantity;

    private BookService bookSerive(){
        return new BookService();
    }
    public importBook() throws SQLException {
        this.setTitle("Import Book");
        this.setContentPane(main);
        this.setMinimumSize(new Dimension(350, 300));
        this.setPreferredSize(new Dimension(350, 300));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        intData();
        booksave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (valid().length() <= 0){
                    Book book = new Book();
                    book.setTitle(bookname.getText());
                    Category category = (Category) book_cate.getSelectedItem();
                    book.setCategory_id(category.getId());
                    book.setQuantity(Integer.parseInt(bookQuantity.getText()));
                    book.setBookID(bookCode.getText());
                    book.setAuthor(bookAuthor.getText());
                    LocalDateTime now = LocalDateTime.now();
                    String a = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    book.setCreated_at(a);
                    book.setUpdated_at(a);
                    try {
                        boolean stt = BookController.getInstance().storeBook(book);
                        if (stt){
                            JOptionPane.showMessageDialog(null,"Lưu thành công");
                            dispose();
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }

                }else {
                    JOptionPane.showMessageDialog(null,valid());
                }
            }
        });
        bookclose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private void intData() throws SQLException {
        ArrayList<Category> categories = bookSerive().getAll();
        categories.forEach(e ->{
            book_cate.addItem(e);
        });

    }
    private String valid(){
        String erros = "";
        if (bookname.getText().isEmpty()){
            erros += "Tên không để trống \n";
        }
        if (bookAuthor.getText().isEmpty()){
            erros += "Không để trống tác giả \n";
        }
        if (bookCode.getText().isEmpty()){
            erros += "Không để trống mã sách \n";
        }
        if (bookQuantity.getText().isEmpty()){
            erros += "Không để trống số lượng \n";
        }else {
            try {
                int a = Integer.parseInt(bookQuantity.getText());
            }catch (NumberFormatException e){
                erros += "Số lượng vui lòng nhập số \n";
            }
        }

        return erros;
    }
}
