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
import br.com.lacamentohoraextra.Models.ApontamentoModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daviramos
 */
public class ApontamentoHistorico extends javax.swing.JPanel {

    /**
     * Creates new form ApontamentoHistorico
     */
    public ApontamentoHistorico() {
        initComponents();
        lblTotal.setText("Total: " + Integer.toString(0));
    }

    public void popularTabela() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setNumRows(0);

        Thread thread = new Thread(() -> {
            try {
                Object colunas[] = new Object[6];
                ApontamentoModel apontamentoModel = new ApontamentoModel();

                ArrayList<ApontamentoModel> listaDeApontamentos = new ArrayList<ApontamentoModel>();
                listaDeApontamentos = ApontamentoDAO.listarApontamentosColaborador();
                
                for (int i = 0; i < listaDeApontamentos.size(); i++) {
                    apontamentoModel = listaDeApontamentos.get(i);

                    colunas[0] = apontamentoModel.getCliente_projeto();
                    colunas[1] = apontamentoModel.getDataInicialApontamento();
                    colunas[2] = apontamentoModel.getDataFinalApontamento();
                    colunas[3] = apontamentoModel.getIntervalo();
                    colunas[4] = apontamentoModel.getJustificativa();
                    colunas[5] = apontamentoModel.getSituacao();

                    model.addRow(colunas);

                    lblTotal.setText("Total: " + listaDeApontamentos.size());
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(
                        ApontamentoHistorico.class.getName()).log(
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
        scrollPanel = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btnAtualizarTabela = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 0, 102));
        setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(764, 600));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(764, 600));

        lblTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 153));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Histórico de apontamentos");

        tabela.setBackground(new java.awt.Color(255, 255, 255));
        tabela.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        tabela.setForeground(new java.awt.Color(0, 0, 102));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente e Projeto", "Data do apontamento", "Hora do apontamento", "Total de horas", "Justificativa", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabela.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tabela.setSelectionForeground(new java.awt.Color(0, 0, 102));
        scrollPanel.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(1).setMinWidth(120);
            tabela.getColumnModel().getColumn(2).setMinWidth(120);
            tabela.getColumnModel().getColumn(4).setMinWidth(150);
            tabela.getColumnModel().getColumn(5).setMinWidth(50);
        }

        btnAtualizarTabela.setBackground(new java.awt.Color(0, 51, 102));
        btnAtualizarTabela.setForeground(new java.awt.Color(255, 255, 255));
        btnAtualizarTabela.setText("Atualizar tabela");
        btnAtualizarTabela.setBorder(null);
        btnAtualizarTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarTabelaActionPerformed(evt);
            }
        });

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 102, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0");
        lblTotal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTotal)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAtualizarTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(555, 555, 555))
                    .addComponent(scrollPanel, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAtualizarTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotal)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtualizarTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarTabelaActionPerformed
        try {
            popularTabela();
        }
        catch (SQLException e) {
            Logger.getLogger(
                    ApontamentoHistorico.class.getName()).log(
                    Level.SEVERE,
                    e.getMessage(),
                    e);
        }
    }//GEN-LAST:event_btnAtualizarTabelaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarTabela;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JScrollPane scrollPanel;
    public static javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
