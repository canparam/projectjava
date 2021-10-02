package daoFactory;

import dao.StudentDao;

import java.sql.Connection;
import java.sql.DriverManager;

public class sqlServer extends DaoFactory{
    private static String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=project;";
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String user = "cannv";
    private static String password = "123123123";
    @Override
    public Connection openConnect() {
        try {
            Class.forName(driver).newInstance();
            Connection connection = DriverManager.getConnection(url , user , password);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public StudentDao getStudentDao() {
//        return new StudentDao();
//    }
}
