package daoFactory;

import dao.StudentDao;

import java.sql.Connection;

public abstract class DaoFactory {
    public abstract Connection openConnect();
//    public abstract StudentDao getStudentDao();
    public static DaoFactory sqlServer() {
        return new sqlServer();
    }
}
