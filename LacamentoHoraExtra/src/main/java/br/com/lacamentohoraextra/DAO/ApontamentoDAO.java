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

import br.com.lacamentohoraextra.Models.ApontamentoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daviramos
 */
public class ApontamentoDAO {

    public ApontamentoDAO() {
    }

    public static ArrayList<ApontamentoModel> listarApontamentosColaborador() throws SQLException {
        ArrayList<ApontamentoModel> listaDeApontamento = new ArrayList<>();
        Connection connection = ConexaoSQL.iniciarConexao();

        if (ConexaoSQL.status == true) {
            PreparedStatement consultaSQL;
            ResultSet resultSet;

            try {
                String query = "SELECT data_apontamento, hora_apontamento, cliente_projeto, justificativa, situacao FROM public.vw_apontamento";
                consultaSQL = connection.prepareStatement(query);

                resultSet = consultaSQL.executeQuery();

                while (resultSet.next()) {
                    ApontamentoModel apontamentoModel = new ApontamentoModel();

                    apontamentoModel.setData_apontamento(resultSet.getString("data_apontamento"));
                    apontamentoModel.setHora_apontamento(resultSet.getString("hora_apontamento"));
                    apontamentoModel.setCliente_projeto(resultSet.getString("cliente_projeto"));
                    apontamentoModel.setJustificativa(resultSet.getString("justificativa"));
                    apontamentoModel.setSituacao(resultSet.getString("situacao"));

                    listaDeApontamento.add(apontamentoModel);
                }
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
        return listaDeApontamento;
    }

    public void cadastrarApontamento(ApontamentoModel apontamento) {
        Connection connection = ConexaoSQL.iniciarConexao();

        if (ConexaoSQL.status == true) {
            String query = "INSERT INTO apontamentos (data_inicio, data_final, hora_inicio, hora_final, nome_cliente, nome_projeto, justificativa) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement consultaSQL = null;

            try {
                consultaSQL = connection.prepareStatement(query);

                consultaSQL.setString(1, apontamento.getDataInicialApontamento()); //colocar data inicial
                consultaSQL.setString(2, apontamento.getDataFinalApontamento()); //colocar data final ( ta dando erro pq ainda n foi criado a data final)
                consultaSQL.setString(3, apontamento.getHoraInicio()); // hora inicial
                consultaSQL.setString(4, apontamento.getHoraFinal()); // hora final
                consultaSQL.setString(5, apontamento.getSolicitante()); // nome do cliente
                consultaSQL.setString(6, apontamento.getProjeto()); // nome do projeto
                consultaSQL.setString(7, apontamento.getJustificativa()); // justificativa

                consultaSQL.execute();
            }
            catch (SQLException e) {
                Logger.getLogger(
                        ConexaoSQL.class.getName()).log(
                        Level.SEVERE,
                        e.getMessage(),
                        e);
            }
            finally {
                try {
                    if (consultaSQL != null) {
                        consultaSQL.close();
                    }
                }
                catch (SQLException e) {
                    Logger.getLogger(
                            ConexaoSQL.class.getName()).log(
                            Level.SEVERE,
                            e.getMessage(),
                            e);
                }

                try {
                    if (connection != null) {
                        connection.close();
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
        }
    }
}