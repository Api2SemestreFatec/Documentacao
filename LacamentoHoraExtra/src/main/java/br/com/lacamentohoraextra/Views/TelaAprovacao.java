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
package br.com.lacamentohoraextra.Views;

import br.com.lacamentohoraextra.DAO.ApontamentoDAO;
import br.com.lacamentohoraextra.DAO.ConexaoSQL;
import br.com.lacamentohoraextra.Models.ApontamentoModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daviramos
 */
public class TelaAprovacao extends javax.swing.JPanel {

    /**
     * Creates new form TelaAprovacao
     */
    public TelaAprovacao() {
        initComponents();
    }

    public void popularTabela() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tabelaAprovacao.getModel();
        model.setNumRows(0);

        Thread thread;
        thread = new Thread(() -> {
            try {
                Object colunas[] = new Object[8];
                ApontamentoModel apontamentoModel = new ApontamentoModel();

                ArrayList<ApontamentoModel> listaDeApontamentos = new ArrayList<ApontamentoModel>();
                listaDeApontamentos = ApontamentoDAO.listarApontamentosGestor();

                for (int i = 0; i < listaDeApontamentos.size(); i++) {
                    apontamentoModel = listaDeApontamentos.get(i);

                    colunas[0] = apontamentoModel.getIdApontamento();
                    colunas[1] = apontamentoModel.getCliente_projeto();
                    colunas[2] = apontamentoModel.getDataInicialApontamento();
                    colunas[3] = apontamentoModel.getDataFinalApontamento();
                    colunas[4] = apontamentoModel.getIntervalo();
                    colunas[5] = apontamentoModel.getJustificativa();
                    colunas[6] = apontamentoModel.getNomeUsuario();
                    colunas[7] = apontamentoModel.getSituacao();

                    model.addRow(colunas);

                }

                tabelaAprovacao.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int linhaSelecionada = tabelaAprovacao.getSelectedRow();

                        if (e.getClickCount() == 2) {
                            String situacao = null;
                            situacao = model.getValueAt(linhaSelecionada, 7).toString();
                            int idDataRow = (int) model.getValueAt(linhaSelecionada, 0);

                            String situacaoUpdate = JOptionPane.showInputDialog(
                                    TelaAprovacao.this,
                                    "Solicitação de aprovação de horas extras \nEntre com a resposta: ", situacao);

                            if (situacaoUpdate != null) {
                                model.setValueAt(situacaoUpdate, linhaSelecionada, 7);
                                Connection connection = ConexaoSQL.iniciarConexao();

                                if (ConexaoSQL.status == true) {

                                    try {
                                        String query = "UPDATE apontamentos SET situacao = ? WHERE id_apontamento = ?";
                                        PreparedStatement consultaSQL = null;
                                        consultaSQL = connection.prepareStatement(query);

                                        consultaSQL.setString(1, situacaoUpdate);
                                        consultaSQL.setInt(2, idDataRow);
                                        consultaSQL.executeUpdate();
                                        consultaSQL.close();
                                        consultaSQL.close();
                                        
                                        JOptionPane.showMessageDialog(TelaAprovacao.this, "Atualizado com sucesso.");
                                    }
                                    catch (Exception ex) {
                                        JOptionPane.showMessageDialog(TelaAprovacao.this, "Não possível atualizar o dado.");
                                    }
                                    finally {
                                        try {
                                            if (connection != null) {
                                                connection.close();
                                            }

                                        }
                                        catch (SQLException ex) {
                                            Logger.getLogger(
                                                    TelaAprovacao.class.getName()).log(
                                                            Level.SEVERE,
                                                            ex.getMessage(),
                                                            ex);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
            catch (SQLException ex) {
                Logger.getLogger(
                        TelaAprovacao.class.getName()).log(
                                Level.SEVERE,
                                ex.getMessage(),
                                ex);
            }
        });
        thread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnAtualizar = new javax.swing.JButton();
        scrollPanel = new javax.swing.JScrollPane();
        tabelaAprovacao = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 51, 102));
        setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(764, 600));
        setPreferredSize(new java.awt.Dimension(764, 600));

        lblTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 153));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Aprovação");

        btnAtualizar.setBackground(new java.awt.Color(0, 51, 102));
        btnAtualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnAtualizar.setText("Atualizar tabela");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        tabelaAprovacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente e Projeto", "Data Hora Inicio", "Data Hora Fim", "Total de horas", "Justificativa", "Colaborador", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaAprovacao.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tabelaAprovacao.setSelectionForeground(new java.awt.Color(0, 0, 102));
        scrollPanel.setViewportView(tabelaAprovacao);
        if (tabelaAprovacao.getColumnModel().getColumnCount() > 0) {
            tabelaAprovacao.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAtualizar)
                    .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAtualizar)
                .addGap(18, 18, 18)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        try {
            popularTabela();
        }
        catch (SQLException e) {
            Logger.getLogger(
                    TelaAprovacao.class.getName()).log(
                    Level.SEVERE,
                    e.getMessage(),
                    e);
        }
    }//GEN-LAST:event_btnAtualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JTable tabelaAprovacao;
    // End of variables declaration//GEN-END:variables
}
