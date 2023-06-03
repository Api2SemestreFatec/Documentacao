package br.com.lacamentohoraextra.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoSQL {

    public static Boolean status = false;

    public ConexaoSQL() {
    }

    public static Connection iniciarConexao() {
        Connection connection = null;

        try {
            String driverName = "org.postgresql.Driver";

            Class.forName(driverName);

            Properties propriedades = new Properties();
            InputStream input = ConexaoSQL.class.getClassLoader().getResourceAsStream("conexao.properties");

            propriedades.load(input);

            String host = propriedades.getProperty("db.host");
            String database = propriedades.getProperty("db.name");
            String user = propriedades.getProperty("db.user");
            String password = propriedades.getProperty("db.password");

            String url = "jdbc:postgresql://" + host + "/" + database;

            connection = DriverManager.getConnection(url, user, password);

            status = connection != null;

            return connection;
        }
        catch (ClassNotFoundException | IOException | SQLException e) {
            status = false;
            Logger.getLogger(ConexaoSQL.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }
}
