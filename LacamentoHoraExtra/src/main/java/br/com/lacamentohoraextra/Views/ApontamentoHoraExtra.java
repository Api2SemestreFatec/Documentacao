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
import br.com.lacamentohoraextra.utils.LimitarDocumento;
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
public class ApontamentoHoraExtra extends javax.swing.JPanel {

    /**
     * Creates new form ApontamentoHoraExtra
     */
    public ApontamentoHoraExtra() {
        initComponents();
        init();
    }

    private void init() {
        int limiteCaracteresData = 10;
        int limiteCaracteresHora = 5;
        int limiteCaracteresNomeCliente = 100;
        int limiteCaracteresMotivo = 200;
        txtDataInicial.setDocument(new LimitarDocumento(limiteCaracteresData));
        txtDataFinal.setDocument(new LimitarDocumento(limiteCaracteresData));
        txtHoraInicial.setDocument(new LimitarDocumento(limiteCaracteresHora));
        txtHoraFinal.setDocument(new LimitarDocumento(limiteCaracteresHora));
        txtCliente.setDocument(new LimitarDocumento(limiteCaracteresNomeCliente));
        txtProjeto.setDocument(new LimitarDocumento(limiteCaracteresNomeCliente));
        txaJustificativa.setDocument(new LimitarDocumento(limiteCaracteresMotivo));

        btnEnviar.addActionListener(e -> {
            String dataHorasExtras = txtDataInicial.getText();
            String dataFinal = txtDataFinal.getText();
            String horaInicio = txtHoraInicial.getText();
            String horaFim = txtHoraFinal.getText();
            String nomeCliente = txtCliente.getText();
            String nomeProjeto = txtProjeto.getText();
            String motivoHorasExtras = txaJustificativa.getText();

            if (dataHorasExtras.length() < 10) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Data inicial deve ser preenchida conforme exemplo.");
                return;
            }
            if (dataFinal.length() < 10) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Data final deve ser preenchida conforme exemplo.");
                return;
            }

            if (horaInicio.length() < 5) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Horario inicial deve ser preenchido conforme exemplo.");
                return;
            }
            if (horaFim.length() < 5) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Horario final deve ser preenchido conforme exemplo.");
                return;
            }

            // Verifica se algum campo está em branco
            if (dataHorasExtras.isEmpty()
                    || dataFinal.isEmpty()
                    || horaInicio.isEmpty()
                    || horaFim.isEmpty()
                    || nomeCliente.isEmpty()
                    || nomeProjeto.isEmpty()
                    || motivoHorasExtras.isEmpty()) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Por favor, preencha todos os campos antes de lançar as horas extras.");
            } else {
                // Reseta os campos de texto e área de texto para novo preenchimento
                txtDataInicial.setText("");
                txtDataFinal.setText("");
                txtHoraInicial.setText("");
                txtHoraFinal.setText("");
                txtCliente.setText("");
                txtProjeto.setText("");
                txaJustificativa.setText("");

                Connection connection = ConexaoSQL.iniciarConexao();

                if (ConexaoSQL.status == true) {
                    String query = "INSERT INTO apontamentos (data_inicio, data_final, hora_inicio, hora_final, nome_cliente, nome_projeto, justificativa) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement consultaSQL = null;

                    try {
                        consultaSQL = connection.prepareStatement(query);

                        consultaSQL.setString(1, dataHorasExtras); //colocar data inicial
                        consultaSQL.setString(2, dataFinal); //colocar data final ( ta dando erro pq ainda n foi criado a data final)
                        consultaSQL.setString(3, horaInicio); // hora inicial
                        consultaSQL.setString(4, horaFim); // hora final
                        consultaSQL.setString(5, nomeCliente); // nome do cliente
                        consultaSQL.setString(6, nomeProjeto); // nome do projeto
                        consultaSQL.setString(7, motivoHorasExtras); // justificativa

                        consultaSQL.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(
                                ConexaoSQL.class.getName()).log(
                                Level.SEVERE,
                                ex.getMessage(),
                                ex);
                    } finally {
                        try {
                            if (consultaSQL != null) {
                                consultaSQL.close();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(
                                    ConexaoSQL.class.getName()).log(
                                    Level.SEVERE,
                                    ex.getMessage(),
                                    ex);
                        }

                        try {
                            if (connection != null) {
                                connection.close();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(
                                    ConexaoSQL.class.getName()).log(
                                    Level.SEVERE,
                                    ex.getMessage(),
                                    ex);
                        }
                    }
                }

                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Horas extras lançadas com sucesso!");
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

        lblTitulo = new javax.swing.JLabel();
        txtDataInicial = new javax.swing.JTextField();
        txtDataFinal = new javax.swing.JTextField();
        txtHoraInicial = new javax.swing.JTextField();
        txtHoraFinal = new javax.swing.JTextField();
        txtCliente = new javax.swing.JTextField();
        txtProjeto = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        scpJustificativa = new javax.swing.JScrollPane();
        txaJustificativa = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 51, 102));
        setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(764, 600));

        lblTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 153));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Apontamento");

        txtDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        txtDataInicial.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtDataInicial.setForeground(new java.awt.Color(0, 51, 102));
        txtDataInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Data inicial", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtDataInicial.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtDataInicial.setName("txtDataInicial"); // NOI18N

        txtDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        txtDataFinal.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtDataFinal.setForeground(new java.awt.Color(0, 51, 102));
        txtDataFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Data final", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtDataFinal.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtDataFinal.setName("username"); // NOI18N

        txtHoraInicial.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraInicial.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtHoraInicial.setForeground(new java.awt.Color(0, 51, 102));
        txtHoraInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Hora inicial", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtHoraInicial.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtHoraInicial.setName("username"); // NOI18N

        txtHoraFinal.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraFinal.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtHoraFinal.setForeground(new java.awt.Color(0, 51, 102));
        txtHoraFinal.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Hora final", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtHoraFinal.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtHoraFinal.setName("username"); // NOI18N

        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(0, 51, 102));
        txtCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtCliente.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtCliente.setName("username"); // NOI18N

        txtProjeto.setBackground(new java.awt.Color(255, 255, 255));
        txtProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtProjeto.setForeground(new java.awt.Color(0, 51, 102));
        txtProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Projeto", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtProjeto.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtProjeto.setName("username"); // NOI18N

        btnEnviar.setBackground(new java.awt.Color(0, 51, 102));
        btnEnviar.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("Enviar");
        btnEnviar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEnviar.setBorderPainted(false);
        btnEnviar.setName("enviar"); // NOI18N

        txaJustificativa.setBackground(new java.awt.Color(255, 255, 255));
        txaJustificativa.setColumns(20);
        txaJustificativa.setForeground(new java.awt.Color(0, 0, 102));
        txaJustificativa.setRows(5);
        txaJustificativa.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Justificativa", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txaJustificativa.setCaretColor(new java.awt.Color(0, 51, 102));
        scpJustificativa.setViewportView(txaJustificativa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(txtHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtHoraFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                                        .addComponent(txtDataInicial, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(scpJustificativa, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpJustificativa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scpJustificativa;
    private javax.swing.JTextArea txaJustificativa;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDataFinal;
    private javax.swing.JTextField txtDataInicial;
    private javax.swing.JTextField txtHoraFinal;
    private javax.swing.JTextField txtHoraInicial;
    private javax.swing.JTextField txtProjeto;
    // End of variables declaration//GEN-END:variables
}
