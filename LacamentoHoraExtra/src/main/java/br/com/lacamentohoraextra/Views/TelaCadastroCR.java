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

import br.com.lacamentohoraextra.DAO.CentroResultadoDAO;
import br.com.lacamentohoraextra.DAO.ConexaoSQL;
import br.com.lacamentohoraextra.Models.CentroResultadoModel;
import br.com.lacamentohoraextra.utils.Globals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daviramos
 */
public class TelaCadastroCR extends javax.swing.JPanel {

    /**
     * Creates new form TelaCadastroCR
     *
     * @throws java.sql.SQLException
     */
    public TelaCadastroCR() throws SQLException {
        initComponents();
        init();

        lblTotalCR.setText("Total: " + Integer.toString(0));
    }

    public void init() {
        btnSalvarCR.addActionListener(e -> {
            String nomeCr = txtNomeCR.getText();
            String codigoCr = txtCodigoCR.getText();

            if (nomeCr.isEmpty() || codigoCr.isEmpty()) {
                JOptionPane.showMessageDialog(TelaCadastroCR.this,
                        "Por favor, preencha todos os campos.");
            } else {
                txtNomeCR.setText("");
                txtCodigoCR.setText("");

                Connection connection = ConexaoSQL.iniciarConexao();

                if (ConexaoSQL.status == true) {
                    String query = "INSERT INTO public.centro_resultados (nome_cr, codigo_cr, id_usuario) VALUES(?, ?, ?)";
                    PreparedStatement consultaSQL = null;

                    try {
                        consultaSQL = connection.prepareStatement(query);
                        consultaSQL.setString(1, nomeCr);
                        consultaSQL.setString(2, codigoCr);
                        consultaSQL.setInt(3, Globals.getUserID());
                        consultaSQL.execute();
                    }
                    catch (SQLException ex) {
                        Logger.getLogger(
                                ConexaoSQL.class.getName()).log(
                                Level.SEVERE,
                                ex.getMessage(),
                                ex);
                    }
                    finally {
                        if (consultaSQL != null) {
                            try {
                                consultaSQL.close();
                            }
                            catch (SQLException ex) {
                                Logger.getLogger(TelaCadastroCR.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (connection != null) {
                            try {
                                connection.close();
                            }
                            catch (SQLException ex) {
                                Logger.getLogger(TelaCadastroCR.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                        try {
                            popularTabela();
                        }
                        catch (SQLException ex) {
                            Logger.getLogger(TelaCadastroCR.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    JOptionPane.showMessageDialog(TelaCadastroCR.this,
                            "Centro de resultado salvo com sucesso!");
                }
            }
        });
    }

    public void popularTabela() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setNumRows(0);

        Thread thread = new Thread(() -> {
            try {
                Object colunas[] = new Object[3];
                CentroResultadoModel apontamentoModel = new CentroResultadoModel();

                ArrayList<CentroResultadoModel> listaCrs = new ArrayList<CentroResultadoModel>();
                listaCrs = (ArrayList<CentroResultadoModel>) CentroResultadoDAO.listarCRTabela();

                for (int i = 0; i < listaCrs.size(); i++) {
                    apontamentoModel = listaCrs.get(i);

                    colunas[0] = apontamentoModel.getId();
                    colunas[1] = apontamentoModel.getNome();
                    colunas[2] = apontamentoModel.getCodigo();

                    model.addRow(colunas);

                    lblTotalCR.setText("Total: " + listaCrs.size());
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(
                        TelaCadastroCR.class.getName()).log(
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

        txtNomeCR = new javax.swing.JTextField();
        txtCodigoCR = new javax.swing.JTextField();
        btnSalvarCR = new javax.swing.JButton();
        scrollPaneCR = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btnAtualizarCR = new javax.swing.JButton();
        lblTotalCR = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 0, 102));
        setMinimumSize(new java.awt.Dimension(752, 479));
        setPreferredSize(new java.awt.Dimension(752, 479));

        txtNomeCR.setBackground(new java.awt.Color(255, 255, 255));
        txtNomeCR.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtNomeCR.setForeground(new java.awt.Color(0, 51, 102));
        txtNomeCR.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Nome", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtNomeCR.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtNomeCR.setName("username"); // NOI18N

        txtCodigoCR.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoCR.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtCodigoCR.setForeground(new java.awt.Color(0, 51, 102));
        txtCodigoCR.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Código", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtCodigoCR.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtCodigoCR.setName("username"); // NOI18N

        btnSalvarCR.setBackground(new java.awt.Color(0, 51, 102));
        btnSalvarCR.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnSalvarCR.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarCR.setText("Salvar");
        btnSalvarCR.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalvarCR.setBorderPainted(false);
        btnSalvarCR.setName("enviar"); // NOI18N

        tabela.setBackground(new java.awt.Color(255, 255, 255));
        tabela.setForeground(new java.awt.Color(0, 0, 102));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Código"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPaneCR.setViewportView(tabela);

        btnAtualizarCR.setBackground(new java.awt.Color(255, 255, 255));
        btnAtualizarCR.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAtualizarCR.setForeground(new java.awt.Color(0, 0, 102));
        btnAtualizarCR.setText("Atualizar");
        btnAtualizarCR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        btnAtualizarCR.setBorderPainted(false);
        btnAtualizarCR.setName("enviar"); // NOI18N
        btnAtualizarCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarCRActionPerformed(evt);
            }
        });

        lblTotalCR.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalCR.setForeground(new java.awt.Color(0, 51, 102));
        lblTotalCR.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 752, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAtualizarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalCR))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtNomeCR, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtCodigoCR, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnSalvarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(scrollPaneCR, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGap(41, 41, 41)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNomeCR, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodigoCR, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(btnSalvarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                    .addComponent(scrollPaneCR, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAtualizarCR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalCR))
                    .addGap(21, 21, 21)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtualizarCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarCRActionPerformed
        try {
            popularTabela();
        }
        catch (SQLException ex) {
            Logger.getLogger(TelaCadastroCR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAtualizarCRActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarCR;
    private javax.swing.JButton btnSalvarCR;
    private javax.swing.JLabel lblTotalCR;
    private javax.swing.JScrollPane scrollPaneCR;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtCodigoCR;
    private javax.swing.JTextField txtNomeCR;
    // End of variables declaration//GEN-END:variables
}
