package services;

import dao.BookDao;
import dao.CategoryDao;
import model.Book;
import model.Category;
import model.Filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookService {

    private CategoryDao categoryDao() {
        return new CategoryDao();
    }

    private BookDao bookDao() {
        return new BookDao();
    }

    public boolean checkUnique(String name) throws SQLException {
        Category category = categoryDao().getOne(name);
        if (category != null) {
            return true;
        }
        return false;
    }

    public ArrayList<Category> getAll() throws SQLException {
        ArrayList<Category> categories = (ArrayList<Category>) categoryDao().getAll();
        return categories;
    }

    public ArrayList<Book> search(String value, Filter filter) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "";
        List<String> container = new ArrayList<>();

        switch (filter.getId()) {
            case 1:
                sql = "WHERE bo_STT = 1 AND title like ? ";
                container.add("%" + value + "%");
                break;
            case 2:
                sql = "WHERE bo_STT = 1 AND author like ?";
                container.add("%" + value + "%");
                break;
            case 3:
                sql = "WHERE bo_STT = 1 AND cate_name like ? ";
                container.add("%" + value + "%");
                break;
            default:
                sql = "WHERE bo_STT = 1 AND title like ?";
                container.add("%" + value + "%");
                break;
        }
        books = (ArrayList<Book>) bookDao().customQuery(sql, container);
        return books;
    }

    ;

    public ArrayList<Book> getTop10() throws SQLException {
        ArrayList<Book> books = bookDao().getTop10();
        Map<Book, Integer> map = new HashMap<Book, Integer>();
        for (Book book : books) {
            map.put(book, Collections.frequency(books, book.getBookID()));
        }
        return books;
    }

    public StringBuilder readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return sb;
    }

    public ArrayList<Book> readData(StringBuilder list) {
        StringBuilder data = list;
        if (data.length() <= 0) return null;
        ArrayList<Book> books = new ArrayList<>();
        String[] listData = data.toString().split("\n");
        LocalDateTime now = LocalDateTime.now();
        String a = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (String listDatum : listData) {
//            String replace = listDatum.replace("|"," ");
            String[] object = listDatum.split("\\|");
            if (object.length < 5 || object.length > 5) return null;

            Book book = new Book();
            book.setTitle(object[0]);
            book.setAuthor(object[1]);
            book.setBookID(object[2]);
            book.setCategory_id(Integer.parseInt(object[3]));
            book.setQuantity(Integer.parseInt(object[4]));
            book.setCreated_at(a);
            book.setUpdated_at(a);
            books.add(book);
        }
        return books;
    }

}
