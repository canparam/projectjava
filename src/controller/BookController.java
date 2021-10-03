package controller;

import dao.BookDao;
import dao.CategoryDao;
import model.Book;
import model.Category;
import services.BookService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    private static BookController instance = new BookController();

    public static BookController getInstance() {
        return instance;
    }

    private CategoryDao categoryDao() {
        return new CategoryDao();
    }

    private BookService bookSerive() { return new BookService(); }
    private BookDao bookDao() { return new BookDao(); }
    public ArrayList<Category> search() throws SQLException {
        ArrayList<Category> categories = (ArrayList<Category>) categoryDao().getAll();
        return categories;
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = bookDao().getAll();
        return books;
    }

    public boolean store(Category category) throws SQLException {
        boolean get = bookSerive().checkUnique(category.getName());
        if (get) return false;
        category.insert();
        return true;
    }

    public boolean storeBook(Book book) throws SQLException {
        Book b = bookDao().insert(book);
        return true;
    }

    public boolean updateCategory(Category category, int i, String oldname) throws SQLException {
        boolean get = bookSerive().checkUnique(category.getName());
        if (get) return false;
        categoryDao().update(category, 0, oldname);
        return true;
    }

    public boolean deleteCategory(String name) throws SQLException {
        boolean delete = categoryDao().delete(name);
        if (delete) return true;
        return false;
    }

    public boolean deleteBook(String book_id) throws SQLException {
        boolean stt = bookDao().delete(book_id);
        if (stt) return true;
        return false;
    }
}
