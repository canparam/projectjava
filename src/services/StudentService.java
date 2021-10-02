package services;

import dao.StudentDao;
import model.Filter;
import model.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private StudentDao dao() {
        return new StudentDao();
    }

    public Student findFirst(String value) throws SQLException {
        Student student = new Student();
        String sql = "WHERE maHS = ?";
        List<String> contai = new ArrayList<>();
        contai.add(value);
        try {
            student = dao().customQuery(sql, contai).get(0);
        } catch (Exception e) {
            return null;
        }
        return student;
    }

    public ArrayList<Student> search(String value, Filter filter) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "";
        List<String> container = new ArrayList<>();
        switch (filter.getId()) {
            case 2:
                sql = "WHERE classess = ?";
                container.add(value);
                break;
            case 3:
                sql = "WHERE name like ?";
                container.add("%" + value + "%");
                break;
            case 4:
                sql = "WHERE address = ?";
                container.add(value);
                break;
            default:
                sql = "WHERE name like ?";
                container.add("%" + value + "%");
                break;
        }
        students = (ArrayList<Student>) dao().customQuery(sql, container);
        return students;
    }
}
