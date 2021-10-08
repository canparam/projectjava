package daoFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class loadConfig {
    private static loadConfig instance = new loadConfig();
    public static loadConfig getInstance() {return instance; }
    public String HOST;
    public String PORT;
    public String DATATABSE;
    public String USERNAME;
    public String PASSWORD;
    private loadConfig(){
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = getClass().getResourceAsStream("/config/database.properties");
            properties.load(inputStream);
            HOST = properties.getProperty("HOST");
            PORT = properties.getProperty("PORT");
            DATATABSE = properties.getProperty("DATABASE");
            USERNAME = properties.getProperty("USERNAME");
            PASSWORD = properties.getProperty("PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
