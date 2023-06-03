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
import br.com.lacamentohoraextra.DAO.ClienteDAO;
import br.com.lacamentohoraextra.DAO.ConexaoSQL;
import br.com.lacamentohoraextra.DAO.UsuariosDAO;
import br.com.lacamentohoraextra.Models.CentroResultadoModel;
import br.com.lacamentohoraextra.Models.ClienteModel;
import br.com.lacamentohoraextra.Models.UsuariosModel;
import br.com.lacamentohoraextra.utils.Globals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author daviramos
 */
public class TelaCadastroProjeto extends javax.swing.JPanel {

    /**
     * Creates new form TelaCadastroProjeto
     */
    public TelaCadastroProjeto() {
        initComponents();
        init();
    }

    public void init() {
        btnSalvarProjeto.addActionListener(e -> {

            String codigoProjeto = txtCodigoProjeto.getText();
            String nomeProjeto = txtNomeProjeto.getText();
            String clienteProjeto = cbxClienteProjeto.getSelectedItem().toString();
            String verba = txtVerbaProjeto.getText();
            String crProjeto = cbxCRProjeto.getSelectedItem().toString();
            String equipeProjeto = cbxEquipeProjeto.getSelectedItem().toString();

            if (codigoProjeto.isEmpty() || nomeProjeto.isEmpty() || clienteProjeto.isEmpty()
                    || verba.isEmpty() || crProjeto.isEmpty() || equipeProjeto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            } else {
                txtCodigoProjeto.setText("");
                txtNomeProjeto.setText("");
                cbxClienteProjeto.setSelectedIndex(0);
                txtVerbaProjeto.setText("");
                cbxCRProjeto.setSelectedIndex(0);
                cbxEquipeProjeto.setSelectedIndex(0);

                Connection connection = ConexaoSQL.iniciarConexao();

                if (ConexaoSQL.status == true) {
                    String query = "INSERT INTO public.projeto (nome, cliente, verba, cr, equipe, codigo_projeto, id_usuario) VALUES(?, ?, ?, ?, ?, ?, ?);";
                    PreparedStatement consultaSQL = null;

                    try {
                        consultaSQL = connection.prepareStatement(query);
                        consultaSQL.setString(1, nomeProjeto);
                        consultaSQL.setString(2, clienteProjeto);
                        consultaSQL.setString(3, verba);
                        consultaSQL.setString(4, crProjeto);
                        consultaSQL.setString(5, equipeProjeto);
                        consultaSQL.setString(6, codigoProjeto);
                        consultaSQL.setInt(7, Globals.getUserID());
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
                    JOptionPane.showMessageDialog(TelaCadastroProjeto.this,
                            "Projeto salvo com sucesso!");
                }
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCodigoProjeto = new javax.swing.JTextField();
        txtNomeProjeto = new javax.swing.JTextField();
        cbxClienteProjeto = new javax.swing.JComboBox();
        txtVerbaProjeto = new javax.swing.JTextField();
        cbxCRProjeto = new javax.swing.JComboBox();
        cbxEquipeProjeto = new javax.swing.JComboBox();
        btnSalvarProjeto = new javax.swing.JButton();
        btnListarProjeto = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 0, 102));
        setMinimumSize(new java.awt.Dimension(752, 479));

        txtCodigoProjeto.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtCodigoProjeto.setForeground(new java.awt.Color(0, 51, 102));
        txtCodigoProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Código", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtCodigoProjeto.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtCodigoProjeto.setName("username"); // NOI18N

        txtNomeProjeto.setBackground(new java.awt.Color(255, 255, 255));
        txtNomeProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtNomeProjeto.setForeground(new java.awt.Color(0, 51, 102));
        txtNomeProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Nome do projeto", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtNomeProjeto.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtNomeProjeto.setName("username"); // NOI18N

        cbxClienteProjeto.setBackground(new java.awt.Color(255, 255, 255));
        cbxClienteProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxClienteProjeto.setForeground(new java.awt.Color(0, 0, 102));
        cbxClienteProjeto.setMaximumRowCount(5);
        cbxClienteProjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione um cliente" }));
        cbxClienteProjeto.setAutoscrolls(true);
        cbxClienteProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N
        cbxClienteProjeto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbxClienteProjetoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        txtVerbaProjeto.setBackground(new java.awt.Color(255, 255, 255));
        txtVerbaProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtVerbaProjeto.setForeground(new java.awt.Color(0, 51, 102));
        txtVerbaProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Verba", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtVerbaProjeto.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtVerbaProjeto.setName("username"); // NOI18N

        cbxCRProjeto.setBackground(new java.awt.Color(255, 255, 255));
        cbxCRProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxCRProjeto.setForeground(new java.awt.Color(0, 0, 102));
        cbxCRProjeto.setMaximumRowCount(5);
        cbxCRProjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione um CR" }));
        cbxCRProjeto.setAutoscrolls(true);
        cbxCRProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Centro de resultados", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N
        cbxCRProjeto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbxCRProjetoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        cbxEquipeProjeto.setBackground(new java.awt.Color(255, 255, 255));
        cbxEquipeProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxEquipeProjeto.setForeground(new java.awt.Color(0, 0, 102));
        cbxEquipeProjeto.setMaximumRowCount(5);
        cbxEquipeProjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione uma equipe" }));
        cbxEquipeProjeto.setAutoscrolls(true);
        cbxEquipeProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Equipe", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N
        cbxEquipeProjeto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbxEquipeProjetoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        btnSalvarProjeto.setBackground(new java.awt.Color(0, 51, 102));
        btnSalvarProjeto.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnSalvarProjeto.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarProjeto.setText("Salvar");
        btnSalvarProjeto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalvarProjeto.setBorderPainted(false);
        btnSalvarProjeto.setName("enviar"); // NOI18N

        btnListarProjeto.setBackground(new java.awt.Color(255, 255, 255));
        btnListarProjeto.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnListarProjeto.setForeground(new java.awt.Color(0, 0, 102));
        btnListarProjeto.setText("Lista de projetos");
        btnListarProjeto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        btnListarProjeto.setName("enviar"); // NOI18N
        btnListarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarProjetoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigoProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnSalvarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnListarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(txtNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(cbxClienteProjeto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtVerbaProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(cbxCRProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(cbxEquipeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtCodigoProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxClienteProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVerbaProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCRProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEquipeProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarProjetoActionPerformed
        TelaListaProjeto lista = new TelaListaProjeto();
        lista.setVisible(true);
    }//GEN-LAST:event_btnListarProjetoActionPerformed

    private void cbxEquipeProjetoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbxEquipeProjetoAncestorAdded
        Thread thread = new Thread(() -> {
            UsuariosDAO usuario = new UsuariosDAO();
            try {
                List<UsuariosModel> listaUsuarios = usuario.listarUsuarios();
                cbxEquipeProjeto.removeAll();

                HashSet<String> nomesUnicos = new HashSet<>();

                for (UsuariosModel c : listaUsuarios) {
                    if (!nomesUnicos.contains(c)) {
                        nomesUnicos.add(String.valueOf(c));
                        cbxEquipeProjeto.addItem(c);
                    }
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(ApontamentoHoraExtra.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        thread.start();
    }//GEN-LAST:event_cbxEquipeProjetoAncestorAdded

    private void cbxClienteProjetoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbxClienteProjetoAncestorAdded
        Thread thread = new Thread(() -> {
            ClienteDAO cliente = new ClienteDAO();
            try {
                List<ClienteModel> listaDeClientes = cliente.listarClientes();
                cbxClienteProjeto.removeAll();

                HashSet<String> nomesUnicos = new HashSet<>();

                for (ClienteModel c : listaDeClientes) {
                    if (!nomesUnicos.contains(c)) {
                        nomesUnicos.add(String.valueOf(c));
                        cbxClienteProjeto.addItem(c);
                    }
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(ApontamentoHoraExtra.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        thread.start();
    }//GEN-LAST:event_cbxClienteProjetoAncestorAdded

    private void cbxCRProjetoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbxCRProjetoAncestorAdded
        Thread thread = new Thread(() -> {
            CentroResultadoDAO cliente = new CentroResultadoDAO();
            try {
                List<CentroResultadoModel> listaCRs = cliente.listarCRs();
                cbxCRProjeto.removeAll();

                HashSet<String> nomesUnicos = new HashSet<>();

                for (CentroResultadoModel c : listaCRs) {
                    if (!nomesUnicos.contains(c)) {
                        nomesUnicos.add(String.valueOf(c));
                        cbxCRProjeto.addItem(c);
                    }
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(ApontamentoHoraExtra.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        thread.start();
    }//GEN-LAST:event_cbxCRProjetoAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnListarProjeto;
    private javax.swing.JButton btnSalvarProjeto;
    private javax.swing.JComboBox cbxCRProjeto;
    private javax.swing.JComboBox cbxClienteProjeto;
    private javax.swing.JComboBox cbxEquipeProjeto;
    private javax.swing.JTextField txtCodigoProjeto;
    private javax.swing.JTextField txtNomeProjeto;
    private javax.swing.JTextField txtVerbaProjeto;
    // End of variables declaration//GEN-END:variables
}
