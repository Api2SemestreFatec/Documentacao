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

import br.com.lacamentohoraextra.DAO.ConexaoSQL;
import br.com.lacamentohoraextra.Models.NivelAcessoEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author daviramos
 */
public class TelaCadastroUsuarios extends javax.swing.JPanel {

    /**
     * Creates new form TelaCdas
     */
    public TelaCadastroUsuarios() {
        initComponents();
        init();

        txtNomeUsuario.setToolTipText("Nome de usuário em minúsculo, sem espaço e sem caracteres especiais");
        txtSenha.setToolTipText("Senha deve conter uma letra maiúscula, um caractere especial, uma letra minúscula e sem espaço");
        txtEquipe.setToolTipText("Equipe deve ser uma palavra simples, sem espaços ou caracteres especiais");

        for (NivelAcessoEnum nivel : NivelAcessoEnum.values()) {
            System.out.println(nivel);
            cbxPerfil.addItem(nivel);
        }
    }

    public void cadastroUsuario() throws SQLException {

        String nomeUsuario = txtNomeUsuario.getText();
        String senha = txtSenha.getText();
        String equipe = txtEquipe.getText();
        String matricula = txtMatricula.getText();

        if (nomeUsuario.isEmpty() || senha.isEmpty() || equipe.isEmpty() || matricula.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
        } else {

            if (!nomeUsuario.matches("[a-z0-9]+")) {
                JOptionPane.showMessageDialog(null, "Nome de usuário inválido. Deve ser em minúsculo, sem espaço e sem caracteres especiais");
                return;
            }

            if (!senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$")) {
                JOptionPane.showMessageDialog(null, "Senha inválida. Deve conter pelo menos uma letra minúscula, uma letra maiúscula, um caractere especial e sem espaço");
                return;
            }

            if (!equipe.matches("^[a-zA-Z]+$")) {
                JOptionPane.showMessageDialog(null, "Equipe inválida. Deve ser uma palavra simples, sem espaços ou caracteres especiais");
                return;
            }

            NivelAcessoEnum nivelSelecionado = (NivelAcessoEnum) cbxPerfil.getSelectedItem();
            if (nivelSelecionado.equals(null)) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            }
            int nivelAcesso = Integer.parseInt(nivelSelecionado.getId());

            Connection connection = ConexaoSQL.iniciarConexao();

            if (ConexaoSQL.status == true) {
                txtNomeUsuario.setText("");
                txtSenha.setText("");
                txtEquipe.setText("");
                txtMatricula.setText("");
                cbxPerfil.setSelectedIndex(0);

                PreparedStatement insertSQL = null;

                try {
                    String query = "INSERT INTO usuarios (nome_usuario, senha, nivel_acesso, equipe, matricula) VALUES (?, ?, ?, ?, ?)";
                    insertSQL = connection.prepareStatement(query);
                    insertSQL.setString(1, nomeUsuario);
                    insertSQL.setString(2, senha);
                    insertSQL.setInt(3, nivelAcesso);
                    insertSQL.setString(4, equipe);
                    insertSQL.setString(5, matricula);
                    insertSQL.executeUpdate();
                }
                catch (SQLException e) {
                    Logger.getLogger(TelaCadastroUsuarios.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
                finally {
                    if (insertSQL != null) {
                        insertSQL.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }

                    JOptionPane.showMessageDialog(TelaCadastroUsuarios.this,
                            "Usuário cadastrado com sucesso!");
                }
            }
        }
    }

    private void init() {
        btnSalvarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastroUsuario();
                }
                catch (SQLException ex) {
                    Logger.getLogger(TelaCadastroUsuarios.class.getName()).log(Level.SEVERE, null, ex);
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

        txtMatricula = new javax.swing.JTextField();
        txtNomeUsuario = new javax.swing.JTextField();
        txtSenha = new javax.swing.JTextField();
        txtEquipe = new javax.swing.JTextField();
        cbxPerfil = new javax.swing.JComboBox();
        btnSalvarUsuario = new javax.swing.JButton();
        btnListarUsuarios = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 0, 102));
        setMinimumSize(new java.awt.Dimension(752, 479));
        setPreferredSize(new java.awt.Dimension(752, 479));

        txtMatricula.setBackground(new java.awt.Color(255, 255, 255));
        txtMatricula.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtMatricula.setForeground(new java.awt.Color(0, 0, 102));
        txtMatricula.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Matricula", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N

        txtNomeUsuario.setBackground(new java.awt.Color(255, 255, 255));
        txtNomeUsuario.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtNomeUsuario.setForeground(new java.awt.Color(0, 0, 102));
        txtNomeUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Nome de usuario", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N

        txtSenha.setBackground(new java.awt.Color(255, 255, 255));
        txtSenha.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtSenha.setForeground(new java.awt.Color(0, 0, 102));
        txtSenha.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Senha", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N

        txtEquipe.setBackground(new java.awt.Color(255, 255, 255));
        txtEquipe.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtEquipe.setForeground(new java.awt.Color(0, 0, 102));
        txtEquipe.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Equipe", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N

        cbxPerfil.setBackground(new java.awt.Color(255, 255, 255));
        cbxPerfil.setForeground(new java.awt.Color(0, 0, 102));
        cbxPerfil.setMaximumRowCount(5);
        cbxPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione o perfil de usuários..." }));
        cbxPerfil.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Perfil de usuário", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N

        btnSalvarUsuario.setBackground(new java.awt.Color(0, 51, 102));
        btnSalvarUsuario.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnSalvarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarUsuario.setText("Salvar");
        btnSalvarUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalvarUsuario.setBorderPainted(false);

        btnListarUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        btnListarUsuarios.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnListarUsuarios.setForeground(new java.awt.Color(0, 51, 102));
        btnListarUsuarios.setText("Lista de usuários");
        btnListarUsuarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102)));
        btnListarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(585, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnListarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtEquipe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                    .addComponent(txtNomeUsuario, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSenha))))
                        .addGap(38, 38, 38))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxPerfil)
                    .addComponent(txtEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarUsuariosActionPerformed
        TelaListaUsuarios listaUsuarios = new TelaListaUsuarios();
        listaUsuarios.setVisible(true);
    }//GEN-LAST:event_btnListarUsuariosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnListarUsuarios;
    private javax.swing.JButton btnSalvarUsuario;
    private javax.swing.JComboBox cbxPerfil;
    private javax.swing.JTextField txtEquipe;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNomeUsuario;
    private javax.swing.JTextField txtSenha;
    // End of variables declaration//GEN-END:variables
}
