package controller;

import dao.StudentDao;
import model.Filter;
import model.Student;
import services.StudentService;

import java.sql.SQLException;
import java.util.ArrayList;

public class MemberController {
    private static MemberController instance = new MemberController();
    private StudentDao dao(){
        return new StudentDao();
    };
    private StudentService studentService(){
        return new StudentService();
    }
    public static MemberController getInstance() {
        return instance;
    }
    public boolean store(Student student) throws SQLException {
        if (student!= null){
            dao().insert(student);
            return true;
        }
        return false;
    }
    public ArrayList<Student> search(String value, Filter filter) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        if (filter != null){
            if (value.length() > 0 || filter.getId() != 1){
                students = studentService().search(value,filter);
            }else {
                students = (ArrayList<Student>) dao().getAll();
            }
        }else{
            students = (ArrayList<Student>) dao().getAll();
        }

        return students;
    }
    public Student findOne(String code) throws SQLException {
        Student student = new Student();
        student = studentService().findFirst(code);
        return student;
    }
    public boolean delete(String code) throws SQLException {
        if (dao().delete(code)){
            return true;
        }
        return false;
    }
    public boolean update(Student student, String code) throws SQLException {
        if (dao().update(student,code)){
            return true;
        }
        return false;
    }

}
