package dao;

import daoFactory.DaoFactory;
import model.Book;
import model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements IGenericDao<Student> {
    private static final String INSERT = "INSERT INTO students(name,birthofDay,maHS,gender,address,classess) values (?,?,?,?,?,?)";

    private static final String ALL = "SELECT * from students";

    private static final String DELETE = "DELETE FROM admin where id = ?";
    private static final String DELETEBYCODE = "DELETE FROM students where maHS = ?";

    private static final String FIND_BY_ID = "SELECT * FROM admin WHERE id = ?";

    private static final String UPDATE = "UPDATE students SET name=?,birthofDay =?,maHS=?,gender=?,address=?,classess=? WHERE id = ?";
    private static final String UPDATE_BY_CODE = "UPDATE students SET name=?,birthofDay=?,gender=?,address=?,classess=? WHERE maHS = ?";
    private static final String getTop10 = "SELECT l.student_id,s.name,s.maHS, COUNT(*) as counts from loan l JOIN students s on l.student_id = s.id GROUP BY l.student_id,s.name,s.maHS";
    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(ALL);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            students.add(extractData(resultSet));
        }
        return students;
    }

    @Override
    public Student insert(Student student) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(INSERT,
                PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setObject(2, student.getBirthOfDay());
        preparedStatement.setString(3, student.getMaSV());
        preparedStatement.setInt(4, student.getGender());
        preparedStatement.setString(5, student.getAddress());
        preparedStatement.setString(6, student.getClasses());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();

        int idGenerated = resultSet.getInt(1);
        student.setId(idGenerated);
        return student;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public boolean delete(String code) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(DELETEBYCODE);
        preparedStatement.setString(1, code);
        int i = preparedStatement.executeUpdate();
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Student student, int id) throws SQLException {
        return false;
    }

    public boolean update(Student student, String code) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(UPDATE_BY_CODE);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setObject(2, student.getBirthOfDay());
        preparedStatement.setInt(3, student.getGender());
        preparedStatement.setString(4, student.getAddress());
        preparedStatement.setString(5, student.getClasses());
        preparedStatement.setString(6, code);
        int i = preparedStatement.executeUpdate();
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Student getOne(int id) throws SQLException {
        return null;
    }
    public ArrayList<Student> getTop10() throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(getTop10);
        ArrayList<Student> students = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Student student = new Student();
            student.setName(resultSet.getString("name"));
            student.setMaSV(resultSet.getString("maHS"));
            student.setId(resultSet.getInt("counts"));
            students.add(student);
        }
        return students;
    }
    @Override
    public Student extractData(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        Timestamp timestamp = Timestamp.valueOf(resultSet.getString("birthofDay"));
        LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setAddress(resultSet.getString("address"));
        student.setMaSV(resultSet.getString("maHS"));
        student.setGender(resultSet.getInt("gender"));
        student.setClasses(resultSet.getString("classess"));
        student.setBirthOfDay(localDate);
        return student;
    }

    public List<Student> customQuery(String sql, List<String> data) throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection c = DaoFactory.sqlServer().openConnect();
        String query = "SELECT * from students " + sql;
        PreparedStatement preparedStatement = c.prepareStatement(query);
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            index++;
            preparedStatement.setString(index, data.get(i));
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            students.add(extractData(resultSet));
        }
        preparedStatement.close();
        c.close();
        return students;
    }
}
