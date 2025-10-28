package clinica.config;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        Properties props = new Properties();
        
        try (InputStream input = new FileInputStream("app.properties")) {
            
            props.load(input); 

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.pass");

            if (URL == null || USER == null || PASS == null) {
                throw new IOException("El archivo 'app.properties' está incompleto.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Error fatal: No se pudo leer 'app.properties'.\n" + e.getMessage(),
                "Error de Configuración",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}