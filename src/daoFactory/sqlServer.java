package daoFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class sqlServer extends DaoFactory {

    private static String url = "jdbc:sqlserver://" + loadConfig.getInstance().HOST + ":" + loadConfig.getInstance().PORT + ";" + "databaseName=" + loadConfig.getInstance().DATATABSE + ";";
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String user = loadConfig.getInstance().USERNAME;
    private static String password = loadConfig.getInstance().PASSWORD;

    @Override
    public Connection openConnect() {
        try {
            Class.forName(driver).newInstance();
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    //    @Override
//    public StudentDao getStudentDao() {
//        return new StudentDao();
//    }

}
