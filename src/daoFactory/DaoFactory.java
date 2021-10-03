package daoFactory;

import dao.StudentDao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DaoFactory {
    public abstract Connection openConnect();
//    public abstract StudentDao getStudentDao();
    public static DaoFactory sqlServer() {
        return new sqlServer();
    }
    public boolean isDbConnected() {
        try {
            Connection c = DaoFactory.sqlServer().openConnect();
            return c != null && !c.isClosed();
        } catch (SQLException ignored) {}
        return false;
    }
}
