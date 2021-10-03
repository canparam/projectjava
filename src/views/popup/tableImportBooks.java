package views.popup;

import dao.BookDao;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import model.Book;
import services.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class tableImportBooks extends JDialog {
    private JPanel panel1;
    private JTable tableImport;
    private JButton btnSave;
    private JButton đóngButton;
    private JProgressBar process;
    private ArrayList<Book> books;

    private BookService bookService() {
        return new BookService();
    }

    private BookDao bookDao() {
        return new BookDao();
    }

    public tableImportBooks(ArrayList<Book> books) {
        this.books = books;
        this.setLocationRelativeTo(null);
        this.setContentPane(panel1);
        this.setSize(750, 500);
        this.setMinimumSize(new Dimension(750, 500));
        this.setPreferredSize(new Dimension(750, 500));
        this.pack();
        process.setStringPainted(true);
        intData();
        đóngButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = books.size();
                int i = 0;
                process.setValue(0);
                process.setMaximum(count);
                btnSave.setEnabled(false);
                đóngButton.setEnabled(false);
                for (Book book : books){
                    i++;
                    try {
                        Thread.sleep(40);
                        bookDao().insert(book);
                        process.setValue(i);
                        process.update(process.getGraphics());
                        if (i == count){
                            JOptionPane.showMessageDialog(null,"Import thành công");
                            btnSave.setEnabled(true);
                            đóngButton.setEnabled(true);
                        }

                    } catch (InterruptedException | SQLException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }

            }
        });
    }

    private void intData() {
        Icon save = IconFontSwing.buildIcon(FontAwesome.UPLOAD, 15, Color.decode("#ffb115"));
        Icon close = IconFontSwing.buildIcon(FontAwesome.WINDOW_CLOSE, 15, Color.decode("#ffb115"));
        btnSave.setIcon(save);
        đóngButton.setIcon(close);
        String column[] = {"STT", "Tên sách","Mã sách", "Tác giả", "Danh mục", "Số lượng"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        tableImport.setModel(tableModel);
        AtomicInteger count = new AtomicInteger(1);
        books.forEach(e -> {
            int stt = count.getAndIncrement();
            String name = e.getTitle();
            String author = e.getAuthor();
            int id = e.getCategory_id();
            int quantity = e.getQuantity();
            String code = e.getBookID();
            Object[] data = {stt, name,code, author, id, quantity};
            tableModel.addRow(data);
        });
        tableImport.setAutoCreateRowSorter(true);
    }
}
