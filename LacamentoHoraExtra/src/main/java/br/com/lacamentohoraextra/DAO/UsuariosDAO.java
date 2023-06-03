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

import br.com.lacamentohoraextra.Models.ClienteModel;
import br.com.lacamentohoraextra.Models.UsuariosModel;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daviramos
 */
public class UsuariosDAO {

    public UsuariosDAO() {
    }

    /**
     *
     * @return @throws SQLException
     */
    public static ArrayList<UsuariosModel> listarUsuarios() throws SQLException {
        ArrayList<UsuariosModel> lista = new ArrayList<>();
        Connection connection = ConexaoSQL.iniciarConexao();

        if (ConexaoSQL.status == true) {
            PreparedStatement consultaSQL;
            ResultSet resultSet;
            try {
                String query = "SELECT equipe FROM vw_usuarios group by equipe";
                consultaSQL = connection.prepareStatement(query);
                resultSet = consultaSQL.executeQuery();

                while (resultSet.next()) {
                    UsuariosModel usuario = new UsuariosModel();

                    usuario.setEquipe(resultSet.getString("equipe"));

                    lista.add(usuario);
                }
                connection.close();
            }
            catch (SQLException e) {
                Logger.getLogger(
                        ConexaoSQL.class.getName()).log(
                        Level.SEVERE,
                        e.getMessage(),
                        e);
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        return lista;
    }

    public static ArrayList<UsuariosModel> listarUsuariosTabela() throws SQLException {
        ArrayList<UsuariosModel> lista = new ArrayList<>();
        Connection connection = ConexaoSQL.iniciarConexao();

        if (ConexaoSQL.status == true) {
            PreparedStatement consultaSQL;
            ResultSet resultSet;
            try {
                String query = "SELECT id, nome_usuario, equipe, matricula FROM public.vw_usuarios";
                consultaSQL = connection.prepareStatement(query);
                resultSet = consultaSQL.executeQuery();

                while (resultSet.next()) {
                    UsuariosModel usuario = new UsuariosModel();

                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNomeUsuario(resultSet.getString("nome_usuario"));
                    usuario.setEquipe(resultSet.getString("equipe"));
                    usuario.setMatricula(resultSet.getString("matricula"));

                    lista.add(usuario);
                }
                connection.close();
            }
            catch (SQLException e) {
                Logger.getLogger(
                        ConexaoSQL.class.getName()).log(
                        Level.SEVERE,
                        e.getMessage(),
                        e);
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        return lista;
    }

    public static ArrayList<UsuariosModel> listarUsuariosRelatorio(int id) throws SQLException {
        // Implementação para listar usuários por ID
        ArrayList<UsuariosModel> listaUsuarios = new ArrayList<>();
        Connection connection = ConexaoSQL.iniciarConexao();

        if (ConexaoSQL.status == true) {
            PreparedStatement consultaSQL;
            ResultSet resultSet;
            try {
                String query = "SELECT id, nome_usuario, equipe, matricula FROM public.vw_usuarios WHERE id = ?";
            consultaSQL = connection.prepareStatement(query);
            consultaSQL.setInt(1, id);
                resultSet = consultaSQL.executeQuery();

                while (resultSet.next()) {
                    UsuariosModel usuario = new UsuariosModel();

                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNomeUsuario(resultSet.getString("nome_usuario"));
                    usuario.setEquipe(resultSet.getString("equipe"));
                    usuario.setMatricula(resultSet.getString("matricula"));

                    listaUsuarios.add(usuario);
                }
                connection.close();
            }
            catch (SQLException e) {
                Logger.getLogger(
                        ConexaoSQL.class.getName()).log(
                        Level.SEVERE,
                        e.getMessage(),
                        e);
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        return listaUsuarios;
    }

    public void cadastrarUsuario(UsuariosModel usuario) throws SQLException {
        Connection connection = ConexaoSQL.iniciarConexao();

        if (ConexaoSQL.status == true) {
            PreparedStatement insertSQL = null;

            try {
                String query = "INSERT INTO usuarios (nome_usuario, senha, nivel_acesso, equipe, matricula) VALUES (?, ?, ?, ?, ?)";
                insertSQL = connection.prepareStatement(query);
                insertSQL.setString(1, usuario.getNomeUsuario());
                insertSQL.setString(2, usuario.getSenha());
                insertSQL.setInt(3, usuario.getNivelAcesso());
                insertSQL.setString(4, usuario.getEquipe());
                insertSQL.setString(5, usuario.getMatricula());
                insertSQL.executeUpdate();
            }
            catch (SQLException e) {
                Logger.getLogger(ConexaoSQL.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            finally {
                if (insertSQL != null) {
                    insertSQL.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }
    
    
    public static void extrairRelatorioCSV() throws SQLException, IOException {
        ArrayList<UsuariosModel> listaUsuarios = listarUsuarios();
        String csvFile = "relatorio-usuario.csv";
        FileWriter writer = new FileWriter(csvFile);

        // Escrever o cabeçalho do CSV
        writer.append("Matrícula, Nome, Verba, Quantidade de Horas, Cliente, CR, Projeto, Justificativa");
        writer.append("\n");

        for (UsuariosModel usuario : listaUsuarios) {
            // Extrair as informações do usuário
            String matricula = usuario.getMatricula();
            String nome = usuario.getNomeUsuario();
            // Obter outras informações relevantes do usuário

            // Escrever os dados do usuário no CSV
            writer.append(matricula);
            writer.append(",");
            writer.append(nome);
            // Escrever as outras informações relevantes do usuário

            writer.append("\n");
        }

        writer.flush();
        writer.close();
    }
}
