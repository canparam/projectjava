package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book extends Category{
    private int id;
    private String title;
    private String author;
    private int category_id;
    private int created_by;
    private String created_byName;
    private String bookID;
    private int quantity;
    private LocalDate created_at;
    private LocalDate updated_at;
    private String cateName;
    public Book(){

    }
    public Book(int id, String title, String author, int category_id, int created_by, String bookID, int quantity, LocalDate created_at, LocalDate updated_at) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category_id = category_id;
        this.created_by = created_by;
        this.bookID = bookID;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    public Book(int id, String title, String author, int category_id, int created_by, String bookID, int quantity, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category_id = category_id;
        this.created_by = created_by;
        this.bookID = bookID;
        this.quantity = quantity;
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = LocalDate.parse(created_at, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = LocalDate.parse(updated_at, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCreated_byName() {
        return created_byName;
    }

    public void setCreated_byName(String created_byName) {
        this.created_byName = created_byName;
    }

    @Override
    public String toString() {
        return title;
    }
}
