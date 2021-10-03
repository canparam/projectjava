package views;

import com.toedter.calendar.JDateChooser;
import controller.BookController;
import controller.FormController;
import controller.MemberController;
import dao.BookDao;
import dao.LoanDao;
import dao.StudentDao;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import model.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import services.BookService;
import services.LoanService;
import services.StudentService;
import services.UserSession;
import views.popup.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class main extends JFrame {

    private JPanel main;
    private JMenuItem changePass;
    private JMenuItem about;
    private JMenu function;
    private JButton bashboardButton;
    private JButton membersButton;
    private JButton booksButton;
    private JButton returnedButton;
    private JButton notReturnedButton;
    private JButton expirationSoonButton;
    private JPanel cardlayout;
    private JPanel B_dash;
    private JPanel B_menber;
    private JTable tablemem;
    private JButton addNewButton;
    private JButton EDITButton;
    private JTable memTable;
    private JButton deleteButton;
    private JTextField fsearch;
    private JButton buttonSearch;
    private JComboBox Cfilter;
    private JPanel B_books;
    private JButton ADDCATEGORYButton;
    private JTabbedPane tabCategory;
    private JPanel tabActive;
    private JPanel tabDelete;
    private JTable tableBooksAll;
    private JTable tableCate;
    private JButton categoryEdit;
    private JButton categoryDelete;
    private JScrollPane cateTable;
    private JButton addBookButton;
    private JButton DELETEButton;
    private JButton btnRestore;
    private JTable deleteTable;
    private JButton bookBtnSearch;
    private JComboBox selectBook;
    private JTextField bookSearch;
    private JButton issuedBtn;
    private JPanel B_issued;
    private JTextField issuaStudent;
    private JButton checkButton;
    private JDateChooser issueDate;
    private JDateChooser returnDate;
    private JComboBox issuaBookSelect;
    private JButton searchButton;
    private JTextField issuaName;
    private JTextField issuaCode;
    private JTextField issuaClass;
    private JTextField issuaBookName;
    private JTextField issuaBookCode;
    private JTextField issuaBookAuthor;
    private JTextField issuaBookQuantity;
    private JDateChooser issuDate;
    private JButton saveIssuaBtn;
    private JButton làmMớiButton;
    private JTextField issuaBookSearch;
    private JPanel issuaInfo;
    private JLabel isssueERROR;
    private JPanel list;
    private JTabbedPane tablelist;
    private JTable listTableAll;
    private JTable listTablereturn;
    private JButton editListBtn;
    private JPanel b_Search;
    private JTextField searchStundentCode;
    private JButton sBtn;
    private JTable searchListBook;
    private JTextField sTextName;
    private JTextField sTextCode;
    private JTextField sTextCount;
    private JPanel searchShow;
    private JTable tb1;
    private JPanel tableOne;
    private JTable tableTopBook;
    private JTable tableTopUser;
    private JTable tableTopNotReturn;
    private JPanel chartPanel;
    private JButton IMPORTMUILITIButton;
    private JButton EXPORTDATAButton;
    private JTable dashTableOne;
    private JTable tableIssua;
    private JLabel xxyy;
    private Book bookx;
    private Student studentx;
    private String startx;
    private String endx;

    private BookDao bookDao() {
        return new BookDao();
    }

    private BookService bookService() {
        return new BookService();
    }

    private StudentService studentService() {
        return new StudentService();
    }

    private LoanService loanService() {
        return new LoanService();
    }

    private LoanDao loanDao() {
        return new LoanDao();
    }

    CardLayout cardLayout = new CardLayout();

    private StudentDao studentDao() {
        return new StudentDao();
    }


    Locale localeEn = new Locale(UserSession.getInstance().getLangguage());
    ResourceBundle labels = ResourceBundle.getBundle("language/Language", localeEn);

    public main() throws SQLException {
        this.setTitle(labels.getString("title"));
        this.setContentPane(main);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1280, 800));
        this.setPreferredSize(new Dimension(1280, 800));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        cardlayout.setLayout(cardLayout);
        cardlayout.add(B_dash, "one");
        cardlayout.add(B_menber, "two");
        cardlayout.add(B_books, "screenBook");
        cardlayout.add(B_issued, "issued");
        cardlayout.add(tablelist, "list");
        cardlayout.add(b_Search, "search");
        iniSetting();
        BookTable(null);
        CategoryTable(null);
        DeleteTable(null);
        ListTableAll(null);
        listTableDel(null);
        searchListBook(null);
        dashTableOne();
        tableUser();
        tableTopReturn();
        issuaInfo.setVisible(false);
        chartLoad();
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Modal();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        changePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jDialog = new changepass();
                jDialog.setTitle(labels.getString("changepass"));
                jDialog.setModal(true);
                jDialog.setVisible(true);
            }
        });
        membersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createTable(tablegetAll());
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                cardLayout.show(cardlayout, "two");
            }
        });
        bashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardlayout, "one");
                try {
                    loadDashBroad();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        addNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog impr = new importMember();
                impr.setModal(true);
                impr.setVisible(true);
                ArrayList<Student> students = null;
                try {
                    students = MemberController.getInstance().search("", null);
                    createTable(students);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        EDITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = memTable.getModel();
                int index = memTable.getSelectedRow();
                String value = model.getValueAt(index, 2).toString();
                try {
                    Student student = MemberController.getInstance().findOne(value);
                    JDialog jd = new editMember(student);
                    jd.setModal(true);
                    jd.setVisible(true);
                    createTable(tablegetAll());
                } catch (SQLException | ParseException exception) {
                    exception.printStackTrace();
                }
            }
        });
//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                TableModel model = memTable.getModel();
//                int index = memTable.getSelectedRow();
//                String value = model.getValueAt(index, 2).toString();
//                try {
//                    boolean stt = MemberController.getInstance().delete(value);
//                    System.out.println(stt);
//                    if (stt) {
//                        JOptionPane.showMessageDialog(null, labels.getString("delete"));
//                        createTable(tablegetAll());
//                    }
//                } catch (SQLException exception) {
//                    exception.printStackTrace();
//                }
//            }
//        });
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = fsearch.getText();
                Filter filter = (Filter) Cfilter.getSelectedItem();
                try {
                    ArrayList<Student> students = MemberController.getInstance().search(search, filter);
                    createTable(students);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        booksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardlayout, "screenBook");
                try {
                    BookTable(null);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        ADDCATEGORYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Category Name");
                Category category = new Category(0, name);
                boolean stt = false;
                try {
                    stt = BookController.getInstance().store(category);
                    if (stt) {
                        JOptionPane.showMessageDialog(null, "Thêm mới thành công");
                        CategoryTable(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Tên đã tồn tại");
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        categoryEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = tableCate.getModel();
                int index = tableCate.getSelectedRow();
                String value = model.getValueAt(index, 1).toString();
                String name = JOptionPane.showInputDialog("Edit category: " + value);
                Category category = new Category(0, name);
                if (name.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "Không để trống tên");
                    return;
                }
                if (name != null) {
                    try {
                        boolean get = BookController.getInstance().updateCategory(category, 0, value);
                        if (get) {
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                            CategoryTable(null);
                        } else {
                            JOptionPane.showMessageDialog(null, "Tên đã tồn tại");
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        categoryDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = tableCate.getModel();
                int index = tableCate.getSelectedRow();
                String value = model.getValueAt(index, 1).toString();
                try {
                    boolean stt = BookController.getInstance().deleteCategory(value);
                    if (stt) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công");
                        CategoryTable(null);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = null;
                try {
                    jd = new importBook();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                jd.setModal(true);
                jd.setVisible(true);
                try {
                    BookTable(null);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = tableBooksAll.getModel();
                int index = tableBooksAll.getSelectedRow();
                String value = model.getValueAt(index, 3).toString();
                try {
                    boolean stt = BookController.getInstance().deleteBook(value);
                    if (stt) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công");
                        BookTable(null);
                        DeleteTable(null);
                        loadBook(null);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnRestore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = deleteTable.getModel();
                int index = deleteTable.getSelectedRow();
                String value = model.getValueAt(index, 3).toString();
                try {
                    boolean stt = bookDao().restoreBook(value);
                    if (stt) {
                        JOptionPane.showMessageDialog(null, "Khôi phục thành công");
                        DeleteTable(null);
                        BookTable(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR");
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        bookBtnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = bookSearch.getText();
                Filter filter = (Filter) selectBook.getSelectedItem();
                try {
                    ArrayList<Book> books = bookService().search(value, filter);
                    BookTable(books);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        issuedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardlayout, "issued");
                try {
                    loadBook(null);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentCode = issuaStudent.getText();
                if (studentCode.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không để trống tên");
                    return;
                }
                try {
                    Student student = studentService().findFirst(studentCode);
                    if (student != null) {
                        issuaInfo.setVisible(true);
                        issuaClass.setText(student.getClasses());
                        issuaName.setText(student.getName());
                        issuaCode.setText(student.getMaSV());
                        studentx = student;
                        if (validateIssua() == 1) {
                            saveIssuaBtn.setEnabled(true);
                        } else {
                            saveIssuaBtn.setEnabled(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy học sinh");
                        issuaInfo.setVisible(false);
                        return;
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        issuaBookSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book book = (Book) issuaBookSelect.getSelectedItem();
                try {
                    loadDashBroad();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                if (book != null) {
                    issuaBookName.setText(book.getTitle());
                    issuaBookAuthor.setText(book.getAuthor());
                    issuaBookCode.setText(book.getBookID());
                    issuaBookQuantity.setText(book.getQuantity() + "");
                    bookx = book;
                    if (validateIssua() == 1) {
                        saveIssuaBtn.setEnabled(true);
                    }
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = issuaBookSearch.getText();
                try {
                    ArrayList<Book> books = bookService().search(value, new Filter(0, ""));
                    loadBook(books);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        issuDate.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
                String date = smp.format(evt.getNewValue());
                startx = date;
                if (validateIssua() == 1) {
                    saveIssuaBtn.setEnabled(true);
                } else {
                    saveIssuaBtn.setEnabled(false);
                }
            }
        });
        returnDate.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
                String date = smp.format(evt.getNewValue());
                endx = date;
                if (validateIssua() == 1) {
                    saveIssuaBtn.setEnabled(true);
                } else {
                    saveIssuaBtn.setEnabled(false);
                }
            }
        });
        saveIssuaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean stt = bookDao().loanBook(bookx, studentx, startx, endx);
                    if (stt) {
                        JOptionPane.showMessageDialog(null, "Save success");
                        return;
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        làmMớiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issuaStudent.setText("");
                issuaBookSearch.setText("");
                issuDate.setCalendar(null);
                returnDate.setCalendar(null);
            }
        });
        returnedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardlayout, "list");
                try {
                    ListTableAll(null);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        listTableAll.addComponentListener(new ComponentAdapter() {
        });
        listTableAll.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                editListBtn.setEnabled(true);
            }
        });
        editListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = listTableAll.getSelectedRow();
                String u_ID = listTableAll.getModel().getValueAt(row, 8).toString();
                Loan loan = null;
                try {
                    loan = loanDao().getOneByUUID(u_ID);
                    JDialog jd = new editLoan(loan);
                    jd.setModal(true);
                    jd.setVisible(true);
                    ListTableAll(null);
                    listTableDel(null);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        notReturnedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardlayout, "search");
            }
        });
        sBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = searchStundentCode.getText();
                if (search.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên");
                    return;
                }
                try {
                    ArrayList<Loan> loans = loanDao().getOne(search);
                    if (loans.size() <= 0) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy lịch sử");
                    } else {
                        int total = loans.size();
                        sTextCount.setText("" + total);
                        Loan loan = loans.get(0);
                        sTextName.setText(loan.getcName());
                        sTextCode.setText(loan.getMaHS());
                        searchListBook(loans);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
        IMPORTMUILITIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int r = chooser.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    String file = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        StringBuilder data = bookService().readFile(file);
                        ArrayList<Book> books = bookService().readData(data);
                        if (books == null) {
                            JOptionPane.showMessageDialog(null, "Không có dữ liệu nào để import hoặc sai định dạng file");
                            return;
                        }
                        JDialog jd = new tableImportBooks(books);
                        jd.setModal(true);
                        jd.setTitle("Danh sách import");
                        jd.setVisible(true);
                        BookTable(null);
                        DeleteTable(null);
                        loadDashBroad();
                    } catch (IOException | SQLException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        issuDate = new JDateChooser();
        issuDate.updateUI();
        returnDate = new JDateChooser();
        returnDate.updateUI();
    }

    private void iniSetting() throws SQLException {
        Icon iconn = IconFontSwing.buildIcon(FontAwesome.KEY, 12);
        Icon abouti = IconFontSwing.buildIcon(FontAwesome.USER_CIRCLE, 12);
        Icon dashBroad = IconFontSwing.buildIcon(FontAwesome.TACHOMETER, 15, Color.decode("#ffb115"));
        Icon users = IconFontSwing.buildIcon(FontAwesome.USERS, 15, Color.decode("#ffb115"));
        Icon books = IconFontSwing.buildIcon(FontAwesome.BOOK, 15, Color.decode("#ffb115"));
        Icon returned = IconFontSwing.buildIcon(FontAwesome.DATABASE, 15, Color.decode("#ffb115"));
        Icon notReturned = IconFontSwing.buildIcon(FontAwesome.SEARCH, 15, Color.decode("#ffb115"));
        Icon clock = IconFontSwing.buildIcon(FontAwesome.CLOCK_O, 15, Color.decode("#ffb115"));
        Icon search = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.decode("#ffb115"));
        Icon task = IconFontSwing.buildIcon(FontAwesome.TASKS, 20, Color.decode("#ffb115"));
        issuedBtn.setIcon(task);
        bashboardButton.setIcon(dashBroad);
        membersButton.setIcon(users);
        booksButton.setIcon(books);
        returnedButton.setIcon(returned);
        notReturnedButton.setIcon(notReturned);
        function.setText(labels.getString("function"));
        changePass.setText(labels.getString("changepass"));
        about.setText(labels.getString("about"));
        changePass.setIcon(iconn);
        changePass.setFont(new Font("", Font.PLAIN, 12));
        changePass.setBorder(new EmptyBorder(2, 2, 2, 2));
        about.setBorder(new EmptyBorder(2, 2, 2, 2));
        about.setIcon(abouti);
        about.setFont(new Font("", Font.PLAIN, 12));
        function.setBorder(new EmptyBorder(3, 3, 3, 3));
        buttonSearch.setIcon(search);
        Cfilter.addItem(new Filter(1, labels.getString("filterAll")));
        Cfilter.addItem(new Filter(2, labels.getString("filterByClass")));
        Cfilter.addItem(new Filter(3, labels.getString("filterByName")));
        Cfilter.addItem(new Filter(4, labels.getString("filterByAddress")));
        bookInt();
        bashboardButton.setText(labels.getString("bash"));
    }

    private void Modal() throws SQLException {
        Admin admin = FormController.getInstance().getAbout();
        JDialog about = new about(admin);
        about.setModal(true);
        about.setVisible(true);
    }

    private void bookInt() {
        selectBook.addItem(new Filter(0, "Lọc theo"));
        selectBook.addItem(new Filter(1, "Lọc theo tên"));
        selectBook.addItem(new Filter(2, "Lọc theo tác giả"));
        selectBook.addItem(new Filter(3, "Lọc theo danh mục"));
    }

    private ArrayList<Student> tablegetAll() throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        students = MemberController.getInstance().search("", null);
        return students;
    }

    private void createTable(ArrayList<Student> students) throws SQLException {
        String column[] = {"STT", labels.getString("tableName"), labels.getString("tableStudentCode"),
                labels.getString("tableClass"), labels.getString("tableGender"), labels.getString("tableAdress"), labels.getString("tableBirth")};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        memTable.setModel(tableModel);
        int count = 1;
        System.out.println(students.toString());
        for (int i = 0; i < students.size(); i++) {
            int stt = count++;
            String name = students.get(i).getName();
            String gender = students.get(i).getGender() == 1 ? "Nam" : "Nữ";
            LocalDate birthday = students.get(i).getBirthOfDay();
            String address = students.get(i).getAddress();
            String classess = students.get(i).getClasses();
            String ma = students.get(i).getMaSV();
            Object[] data = {stt, name, ma, classess, gender, address, birthday};
            tableModel.addRow(data);
        }
        memTable.setAutoCreateRowSorter(true);
    }

    private void BookTable(ArrayList<Book> books) throws SQLException {
        String column[] = {"STT", "Name", "Author", "Book Code", "Category", "create_By", "Quantity", "created_at", "updated_at"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        tableBooksAll.setModel(tableModel);
        ArrayList<Book> bookList = new ArrayList<>();
        if (books != null) {
            bookList = books;
        } else {
            bookList = (ArrayList<Book>) BookController.getInstance().getAllBooks();
        }
        int count = 1;
        for (int i = 0; i < bookList.size(); i++) {
            int stt = count++;
            String name = bookList.get(i).getTitle();
            String author = bookList.get(i).getAuthor();
            String cate = bookList.get(i).getCateName();
            String created_by = bookList.get(i).getCreated_byName();
            int quantity = bookList.get(i).getQuantity();
            LocalDate created = bookList.get(i).getCreated_at();
            LocalDate updated = bookList.get(i).getUpdated_at();
            String bookcode = bookList.get(i).getBookID();
            Object[] data = {stt, name, author, bookcode, cate, created_by, quantity, created, updated};
            tableModel.addRow(data);
        }
        memTable.setAutoCreateRowSorter(true);
    }

    private void CategoryTable(ArrayList<Category> categories) throws SQLException {
        String column[] = {"STT", "Name", "Cate ID"};
        ArrayList<Category> cate = new ArrayList<>();
        DefaultTableModel tablecate = new DefaultTableModel(column, 0);
        if (categories != null) {
            cate = categories;
        } else {
            cate = BookController.getInstance().search();
        }
        int count = 1;
        for (int i = 0; i < cate.size(); i++) {
            int stt = count++;
            Category category = new Category();
            category.setName(cate.get(i).getName());
            category.setId(cate.get(i).getId());
            Object[] data = {stt, category, category.getId()};
            tablecate.addRow(data);
        }
        tableCate.setModel(tablecate);
    }

    private void DeleteTable(ArrayList<Book> categories) throws SQLException {
        String column[] = {"STT", "Name", "Author", "Book Code", "Category", "create_By", "Quantity", "created_at", "updated_at"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        deleteTable.setModel(tableModel);
        ArrayList<Book> bookList = new ArrayList<>();
        if (categories != null) {
            bookList = categories;
        } else {
            bookList = (ArrayList<Book>) bookDao().getDelete();
        }
        int count = 1;
        for (int i = 0; i < bookList.size(); i++) {
            int stt = count++;
            String name = bookList.get(i).getTitle();
            String author = bookList.get(i).getAuthor();
            String cate = bookList.get(i).getCateName();
            String created_by = bookList.get(i).getCreated_byName();
            int quantity = bookList.get(i).getQuantity();
            LocalDate created = bookList.get(i).getCreated_at();
            LocalDate updated = bookList.get(i).getUpdated_at();
            String bookcode = bookList.get(i).getBookID();
            Object[] data = {stt, name, author, bookcode, cate, created_by, quantity, created, updated};
            tableModel.addRow(data);
        }
    }

    private void loadBook(ArrayList<Book> books) throws SQLException {
        ArrayList<Book> bookList = new ArrayList<>();
        if (books == null) {
            bookList = (ArrayList<Book>) BookController.getInstance().getAllBooks();
        } else {
            bookList = books;
        }
        issuaBookSelect.removeAllItems();

        bookList.forEach(e -> {
            issuaBookSelect.addItem(e);
        });

    }

    private void ListTableAll(ArrayList<Loan> loans) throws SQLException {
        String column[] = {"STT", "Name", "Student Code", "Book name", "By", "Ngày mượn", "Ngày trả", "Trạng thái", "UUID"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        listTableAll.setModel(tableModel);
        ArrayList<Loan> loans1 = (ArrayList<Loan>) loanDao().getAll();
        AtomicInteger count = new AtomicInteger(1);
        loans1.forEach(e -> {
            int stt = count.getAndIncrement();
            String id = e.getUuid();
            String name = e.getaName();
            String bName = e.getbName();
            String aName = e.getaName();
            String created_at = e.getCreate_at();
            String end_date = e.getEnd_date();
            String status = e.getStatus() == 1 ? "Chưa trả" : "Đã trả";
            String studentCode = e.getMaHS();
            Object[] data = {stt, name, studentCode, bName, aName, created_at, end_date, status, id};
            tableModel.addRow(data);
        });
    }

    private void listTableDel(ArrayList<Loan> loans) throws SQLException {
        String column[] = {"STT", "Name", "Student Code", "Book name", "By", "Ngày mượn", "Ngày trả", "Trạng thái", "u_ID"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        listTablereturn.setModel(tableModel);
        ArrayList<Loan> loans1 = (ArrayList<Loan>) loanDao().Status();
        AtomicInteger count = new AtomicInteger(1);
        loans1.forEach(e -> {
            int stt = count.getAndIncrement();
            int id = e.getId();
            String name = e.getaName();
            String bName = e.getbName();
            String aName = e.getaName();
            String created_at = e.getCreate_at();
            String end_date = e.getEnd_date();
            String status = "Đã trả";
            String studentCode = e.getMaHS();
            Object[] data = {stt, name, studentCode, bName, aName, created_at, end_date, status, id};
            tableModel.addRow(data);
        });
    }

    private void searchListBook(ArrayList<Loan> loans) {
        String column[] = {"STT", "Name", "Student Code", "Book name", "Ngày mượn", "Ngày trả", "Trạng thái"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        searchListBook.setModel(tableModel);
        AtomicInteger count = new AtomicInteger(1);
        if (loans != null) {
            loans.forEach(e -> {
                int stt = count.getAndIncrement();
                int id = e.getId();
                String name = e.getaName();
                String bName = e.getbName();
                String aName = e.getaName();
                String created_at = e.getCreate_at();
                String end_date = e.getEnd_date();
                String status = e.getStatus() == 1 ? "Chưa trả" : "Đã trả";
                String studentCode = e.getMaHS();
                Object[] data = {stt, name, studentCode, bName, created_at, end_date, status};
                tableModel.addRow(data);
            });
        }

    }

    private void dashTableOne() throws SQLException {
        String column[] = {"STT", "Tên sách", "Tác Giả", "Danh Mục", "Số lần mượn"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        ArrayList<Book> books = bookDao().getTop10();
        AtomicInteger count = new AtomicInteger(1);
        books.forEach(e -> {
            int stt = count.getAndIncrement();
            String bookName = e.getTitle();
            String author = e.getAuthor();
            String cate = e.getCateName();
            int quanty = e.getQuantity();
            Object[] data = {stt, bookName, author, cate, quanty};
            tableModel.addRow(data);
        });
        tableTopBook.setAutoCreateRowSorter(true);
        tableTopBook.setModel(tableModel);
    }

    private void tableUser() throws SQLException {
        String column[] = {"STT", "Tên", "Mã SV", "Số lần mượn"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        tableTopUser.setModel(tableModel);
        ArrayList<Student> students = studentDao().getTop10();
        AtomicInteger count = new AtomicInteger(1);
        students.forEach(e -> {
            int stt = count.getAndIncrement();
            String name = e.getName();
            String maHS = e.getMaSV();
            int total = e.getId();
            Object[] data = {stt, name, maHS, total};
            tableModel.addRow(data);
        });
    }

    private void tableTopReturn() throws SQLException {
        String column[] = {"STT", "TÊN", "Mã SV", "TÊN SÁCH", "NGÀY MƯỢN", "NGÀY HẾT HẠN"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        tableTopNotReturn.setModel(tableModel);
        ArrayList<Loan> loans1 = (ArrayList<Loan>) loanDao().getNotReturn();
        AtomicInteger count = new AtomicInteger(1);
        loans1.forEach(e -> {
            int stt = count.getAndIncrement();
            String name = e.getcName();
            String code = e.getMaHS();
            String book = e.getbName();
            String start_date = e.getCreate_at();
            String end_date = e.getEnd_date();
            Object[] data = {stt, name, code, book, start_date, end_date};
            tableModel.addRow(data);
        });
    }

    private void chartLoad() throws SQLException {
        CategoryPlot plot = new CategoryPlot();

        // Add the second dataset and render as lines
        CategoryItemRenderer baRenderer = new BarRenderer();
        plot.setDataset(1, createDataset());
        plot.setRenderer(1, baRenderer);
        // Set Axis
        plot.setDomainAxis(new CategoryAxis("Danh mục"));
        plot.setRangeAxis(new NumberAxis("Số liệu"));
        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("BIỂU ĐỒ TỔNG QUÁT");
        ChartPanel panel = new ChartPanel(chart);
        chartPanel.setLayout(new java.awt.BorderLayout());
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.validate();
    }

    private DefaultCategoryDataset createDataset() throws SQLException {

        int totalBook = bookDao().getAll().size();
        // First Series
        String series1 = "Tổng số sách";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(totalBook, series1, "");
        // Second Series
        int totalIssua = loanDao().Status().size();
        String series2 = "Đã trả";
        dataset.addValue(totalIssua, series2, "");
        String series3 = "Chưa trả";
        int totalNotRuturn = loanDao().getNotReturn().size();
        dataset.addValue(totalNotRuturn, series3, "");
        int totalStudent = studentDao().getAll().size();
        String series4 = "Tổng học sinh";
        dataset.addValue(totalStudent, series4, "");
        return dataset;
    }

    private int validateIssua() {
        System.out.println(bookx);
        System.out.println(studentx);
        System.out.println(startx);
        System.out.println(endx);
        if (bookx == null || studentx == null || startx == null || endx == null) {
            return 0;
        } else {
            try {
                Date start = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                        .parse(startx);
                Date end = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                        .parse(endx);
                ;
                if (start.compareTo(end) > 0) {
                    isssueERROR.setText("Ngay muon khong the nho hon ngay tra");
                    return -1;
                } else if (start.compareTo(end) == 0) {
                    isssueERROR.setText("Ngay muon khong the bang ngay tra");

                    return -2;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (bookx.getQuantity() == 0) {
            System.out.println(bookx.getQuantity());
            isssueERROR.setText("So luong sach da het");
            return -3;
        } else {
            isssueERROR.setText("");
        }
        return 1;
    }

    private void loadDashBroad() throws SQLException {
        chartLoad();
        createDataset();
        dashTableOne();
        tableTopReturn();
        tableUser();
    }
}
