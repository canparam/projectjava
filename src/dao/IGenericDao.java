package dao;

import model.Admin;
import model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IGenericDao<T> {
    public List<T> getAll() throws SQLException;

    public T insert(T t) throws SQLException;

    public boolean delete(int id);

    public boolean update(T t, int id) throws SQLException;

    public T getOne(int id) throws SQLException;

    public T extractData(ResultSet resultSet) throws SQLException ;
}
