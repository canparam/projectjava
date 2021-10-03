package dao;

import daoFactory.DaoFactory;
import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao implements IGenericDao<Admin> {
    private static final String INSERT = "INSERT INTO admin(uname,username,pass,role_id) values (?,?,?,1)";

    private static final String ALL = "SELECT * form admin";

    private static final String DELETE = "DELETE FROM admin where id = ?";

    private static final String FIND_BY_ID = "SELECT * FROM admin WHERE id = ?";

    private static final String UPDATE = "UPDATE admin SET uname=?,username =?,pass=?,role_id=? WHERE id = ?";


    @Override
    public List<Admin> getAll() throws SQLException {
        return null;
    }

    @Override
    public Admin insert(Admin admin) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(INSERT,
                PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,admin.getName());
        preparedStatement.setString(2,admin.getUsername());
        preparedStatement.setObject(3,admin.getPassword());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();

        int idGenerated = resultSet.getInt(1);
        admin.setId(idGenerated);

        return admin;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Admin admin, int id) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(UPDATE);
        preparedStatement.setString(1,admin.getName());
        preparedStatement.setString(2,admin.getUsername());
        preparedStatement.setString(3,admin.getPassword());
        preparedStatement.setInt(4,admin.getRoles());
        preparedStatement.setInt(5,id);
        preparedStatement.executeUpdate();
        return false;
    }

    @Override
    public Admin getOne(int id) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(FIND_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Admin admin = new Admin();
        while (resultSet.next()) {
            admin = extractData(resultSet);
        }
        preparedStatement.close();
        return admin;
    }

    @Override
    public Admin extractData(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setName(resultSet.getString("uname"));
        admin.setId(resultSet.getInt("id"));
        admin.setUsername(resultSet.getString("username"));
        admin.setPassword(resultSet.getString("pass"));
        admin.setRoles(resultSet.getInt("role_id"));
        return admin;
    }

    public List<Admin> customQuery(String sql, List<String> data) throws SQLException {
        List<Admin> admins = new ArrayList<>();
        Connection c = DaoFactory.sqlServer().openConnect();
        String query = "SELECT * from admin " + sql;
        PreparedStatement preparedStatement = c.prepareStatement(query);
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            index++;
            preparedStatement.setString(index, data.get(i));
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            admins.add(extractData(resultSet));
        }
        preparedStatement.close();
        c.close();
        return admins;
    }
}
