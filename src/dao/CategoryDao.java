package dao;

import daoFactory.DaoFactory;
import model.Category;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements IGenericDao<Category> {
    private static final String INSERT = "INSERT INTO categories(name) values (?)";

    private static final String ALL = "SELECT * FROM categories";

    private static final String DELETE = "DELETE FROM categories where id = ?";

    private static final String FIND_BY_ID = "SELECT * FROM categories WHERE id = ?";

    private static final String UPDATE = "UPDATE categories SET name=? WHERE name = ?";

    @Override
    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(ALL);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            categories.add(extractData(resultSet));
        }
        return categories;
    }

    @Override
    public Category insert(Category category) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(INSERT,
                PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, category.getName());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        int idGenerated = resultSet.getInt(1);
        category.setId(idGenerated);
        return category;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
    public boolean delete(String name) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        String sql = "DELETE FROM categories where name = ? ";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setString(1,name);
        int i = preparedStatement.executeUpdate();
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Category category, int id) throws SQLException {

        return false;
    }
    public boolean update(Category category, int id,String oldName) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(UPDATE);
        preparedStatement.setString(1,category.getName());
        preparedStatement.setString(2,oldName);
        int i = preparedStatement.executeUpdate();
        if (i==1){
            return true;
        }
        return false;
    }

    @Override
    public Category getOne(int id) throws SQLException {
        return null;
    }
    public Category getOne(String name) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        String query = "SELECT * from categories where name = ?" ;
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();
        Category category = null;
        while (resultSet.next()){
            category = extractData(resultSet);
        }
        return category;
    }
    public ArrayList<Category> customQuery(String sql, List<String> data) throws SQLException {
        List<Category> categories = new ArrayList<>();
        Connection c = DaoFactory.sqlServer().openConnect();
        String query = "SELECT * from categories " + sql;
        PreparedStatement preparedStatement = c.prepareStatement(query);
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            index++;
            preparedStatement.setString(index, data.get(i));
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            categories.add(extractData(resultSet));
        }
        preparedStatement.close();
        c.close();

        return (ArrayList<Category>) categories;
    }

    @Override
    public Category extractData(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
