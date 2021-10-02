package dao;

import daoFactory.DaoFactory;
import model.Book;
import model.Loan;
import model.Student;
import services.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookDao implements IGenericDao<Book> {
    private static String ALL = "select * from getBook WHERE bo_STT = 1";
    private static String WHERE = "SELECT * FROM getBook ";
    private static String Insert = "exec importBook ?,?,?,?,?,?,?,?,?";
    private static String LOAN = "exec loanBook ?,?,?,?,?,?,?,?";
    private static String DELETE = "UPDATE books set status = 2 WHERE book_ID = ?";
    private static String TOP10 = "select l.book_id,b.title,b.author,c.name, count(*) as total from loan l join books b on l.book_id = b.id  join categories c on b.category_id = c.id GROUP BY l.book_id,b.title,b.author,c.name ORDER BY total DESC";
    @Override
    public List<Book> getAll() throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(ALL);
        List<Book> books = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            books.add(extractData(rs));
        }
        return books;
    }

    @Override
    public Book insert(Book book) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(Insert,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,book.getBookID());
        preparedStatement.setInt(2,book.getCategory_id());
        preparedStatement.setInt(3,book.getQuantity());
        preparedStatement.setString(4,book.getTitle());
        preparedStatement.setString(5,book.getAuthor());
        preparedStatement.setInt(6,1);
        preparedStatement.setInt(7, UserSession.getInstance().getId());
        preparedStatement.setObject(8, book.getCreated_at());
        preparedStatement.setObject(9, book.getUpdated_at());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        int bk = resultSet.getInt(1);
        book.setId(bk);
        return book;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
    public boolean delete(String bookID) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(DELETE);
        preparedStatement.setString(1,bookID);
        int i = preparedStatement.executeUpdate();
        if (i==1){
            return true;
        }
        return false;
    }
    @Override
    public boolean update(Book book, int id) throws SQLException {
        return false;
    }

    @Override
    public Book getOne(int id) throws SQLException {
        return null;
    }

    @Override
    public Book extractData(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("bo_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setQuantity(resultSet.getInt("quantity"));
        book.setCreated_at(resultSet.getString("created_at"));
        book.setUpdated_at(resultSet.getString("updated_at"));
        book.setCateName(resultSet.getString("cate_name"));
        book.setCreated_byName(resultSet.getString("ad_name"));
        book.setBookID(resultSet.getString("bo_code"));
        return book;
    }
    public List<Book> getDelete() throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM getBook WHERE bo_STT = 2");
        List<Book> books = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            books.add(extractData(rs));
        }
        return books;
    }
    public boolean restoreBook(String book_id) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        String sql = "UPDATE books SET status = ? WHERE book_ID = ?";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2,book_id);
        int i = preparedStatement.executeUpdate();
        if (i == 1){
            return true;
        }
        return false;
    }
    public boolean loanBook(Book book,Student student, String start, String end) throws SQLException {
        UUID uuid = UUID.randomUUID();
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(LOAN);
        preparedStatement.setString(1,book.getBookID());
        preparedStatement.setInt(2,1);
        preparedStatement.setInt(3,UserSession.getInstance().getId());
        preparedStatement.setString(4,student.getMaSV());
        preparedStatement.setObject(5,start);
        preparedStatement.setObject(6,start);
        preparedStatement.setObject(7,end);
        preparedStatement.setString(8, String.valueOf(uuid));
        int i = preparedStatement.executeUpdate();
        if (i == 1){
            return true;
        }
        return false;
    }
    public ArrayList<Book> getTop10() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(TOP10);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Book book = new Book();
            book.setQuantity(rs.getInt("total"));
            book.setBookID(rs.getString("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setCateName(rs.getString("name"));
            books.add(book);
        }
        return books;
    }
    public List<Book> customQuery(String sql, List<String> data) throws SQLException {
        List<Book> books = new ArrayList<>();
        Connection c = DaoFactory.sqlServer().openConnect();
        String query = "SELECT * from getBook " + sql;
        PreparedStatement preparedStatement = c.prepareStatement(query);
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            index++;
            preparedStatement.setString(index, data.get(i));
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            books.add(extractData(resultSet));
        }
        preparedStatement.close();
        c.close();

        return books;
    }
}
