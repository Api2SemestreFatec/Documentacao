/*
 * The MIT License
 *
 * Copyright 2023 daviramos.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.lacamentohoraextra.DAO;

import br.com.lacamentohoraextra.utils.Globals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author daviramos
 */
public class LoginDAO {

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static String getPerfil(String username, String password) throws SQLException {
        Connection connection = ConexaoSQL.iniciarConexao();
        String perfil = null;

        if (ConexaoSQL.status == true) {
            PreparedStatement consultaSQL;

            try {
                String query = "SELECT id, nivel_acesso FROM usuarios WHERE nome_usuario = ? AND senha = ?";
                consultaSQL = connection.prepareStatement(query);
                consultaSQL.setString(1, username);
                consultaSQL.setString(2, password);

                try (ResultSet resultSet = consultaSQL.executeQuery()) {
                    if (resultSet.next()) {
                        perfil = resultSet.getString("nivel_acesso");
                        Globals.setUserID(resultSet.getInt("id"));
                    } else {
                        JOptionPane.showMessageDialog(null,"Verifique os campos.");
                    }
                }
                catch (SQLException e) {
                    Logger.getLogger(
                            ConexaoSQL.class.getName()).log(
                            Level.SEVERE,
                            e.getMessage(),
                            e);
                }
            }
            catch (SQLException e) {
                Logger.getLogger(
                        ConexaoSQL.class.getName()).log(
                        Level.SEVERE,
                        e.getMessage(),
                        e);
                connection.close();
            }
        }
        return perfil;
    }
//
//    private String getUserRole(
//            Connection connection,
//            String username,
//            String password) throws SQLException {
//
//        // Faz uma consulta no banco de dados para obter o papel do usuário
//        String sql = "SELECT role FROM usuarios WHERE username = ? AND password = ?";
//
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, username);
//            statement.setString(2, password);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getString("role");
//                } else {
//                    throw new SQLException("Invalid username or password");
//                }
//            }
//        }
//    }
}
