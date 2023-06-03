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
import br.com.lacamentohoraextra.DAO.ProjetoDAO;
import br.com.lacamentohoraextra.Models.CentroResultadoModel;
import br.com.lacamentohoraextra.Models.ClienteModel;
import br.com.lacamentohoraextra.Models.ProjetoModel;
import br.com.lacamentohoraextra.utils.Globals;
import br.com.lacamentohoraextra.utils.LimitarDocumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
        txaJustificativa.setDocument(new LimitarDocumento(limiteCaracteresMotivo));

        btnEnviar.addActionListener(e -> {
            String dataHorasExtras = txtDataInicial.getText();
            String dataFinal = txtDataFinal.getText();
            String horaInicio = txtHoraInicial.getText();
            String horaFim = txtHoraFinal.getText();
            String nomeCliente = cbxCliente.getSelectedItem().toString();
            String nomeProjeto = cbxProjeto.getSelectedItem().toString();
            String motivoHorasExtras = txaJustificativa.getText();
            String solicitante = txtSolicitante.getText();
            String cr = cbxCR.getSelectedItem().toString();

            if (dataHorasExtras.length() < 10) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Data inicial deve ser preenchida conforme exemplo: 01-01-1900.");
                return;
            }
            if (dataFinal.length() < 10) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Data final deve ser preenchida conforme exemplo: 01-01-1900.");
                return;
            }

            if (horaInicio.length() < 5) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Horario inicial deve ser preenchido conforme exemplo: 00:00." + horaInicio.length());
                return;
            }
            if (horaFim.length() < 5) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Horario final deve ser preenchido conforme exemplo: 00:00.");
                return;
            }

            // Verifica se algum campo está em branco
            if (dataHorasExtras.isEmpty()
                    || dataFinal.isEmpty()
                    || horaInicio.isEmpty()
                    || horaFim.isEmpty()
                    || nomeCliente.isEmpty()
                    || nomeProjeto.isEmpty()
                    || motivoHorasExtras.isEmpty()
                    || solicitante.isEmpty()
                    || cr.equals(null)) {
                JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                        "Por favor, preencha todos os campos antes de lançar as horas extras.");
            } else {
                // Reseta os campos de texto e área de texto para novo preenchimento
                txtDataInicial.setText("");
                txtDataFinal.setText("");
                txtHoraInicial.setText("");
                txtHoraFinal.setText("");
                cbxCliente.setSelectedIndex(0);
                cbxProjeto.setSelectedIndex(0);
                txaJustificativa.setText("");
                txtSolicitante.setText("");
                cbxCR.setSelectedIndex(0);

                Connection connection = ConexaoSQL.iniciarConexao();

                if (ConexaoSQL.status == true) {

                    String query = "INSERT INTO apontamentos "
                            + "(data_inicio, data_final, hora_inicio, hora_final, nome_cliente, nome_projeto, justificativa, id_usuario, solicitante, cr) "
                            + "VALUES (TO_DATE(?,'DD-MM-YYYY'), TO_DATE(?,'DD-MM-YYYY'), to_timestamp(?, 'HH24:MI:SS')::TIME, to_timestamp(?, 'HH24:MI:SS')::TIME, ?, ?, ?, ?, ?, ?)";
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
                        consultaSQL.setInt(8, Globals.getUserID()); // ID do usuario logado
                        consultaSQL.setString(9, solicitante);
                        consultaSQL.setString(10, cr);

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
                        try {
                            if (consultaSQL != null) {
                                consultaSQL.close();
                            }
                        }
                        catch (SQLException ex) {
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
                        }
                        catch (SQLException ex) {
                            Logger.getLogger(
                                    ConexaoSQL.class.getName()).log(
                                    Level.SEVERE,
                                    ex.getMessage(),
                                    ex);
                        }
                    }
                    JOptionPane.showMessageDialog(ApontamentoHoraExtra.this,
                            "Horas extras lançadas com sucesso!");
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

        lblTitulo = new javax.swing.JLabel();
        txtDataInicial = new javax.swing.JTextField();
        txtDataFinal = new javax.swing.JTextField();
        txtHoraInicial = new javax.swing.JTextField();
        txtHoraFinal = new javax.swing.JTextField();
        txtSolicitante = new javax.swing.JTextField();
        cbxCliente = new javax.swing.JComboBox();
        cbxProjeto = new javax.swing.JComboBox();
        btnEnviar = new javax.swing.JButton();
        scpJustificativa = new javax.swing.JScrollPane();
        txaJustificativa = new javax.swing.JTextArea();
        cbxCR = new javax.swing.JComboBox();

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

        txtSolicitante.setBackground(new java.awt.Color(255, 255, 255));
        txtSolicitante.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        txtSolicitante.setForeground(new java.awt.Color(0, 51, 102));
        txtSolicitante.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Solicitante", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        txtSolicitante.setDisabledTextColor(new java.awt.Color(0, 0, 102));
        txtSolicitante.setName("username"); // NOI18N

        cbxCliente.setBackground(new java.awt.Color(255, 255, 255));
        cbxCliente.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxCliente.setForeground(new java.awt.Color(0, 0, 102));
        cbxCliente.setMaximumRowCount(0);
        cbxCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione um cliente..." }));
        cbxCliente.setAutoscrolls(true);
        cbxCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        cbxCliente.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbxClienteAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        cbxProjeto.setBackground(new java.awt.Color(255, 255, 255));
        cbxProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxProjeto.setForeground(new java.awt.Color(0, 0, 102));
        cbxProjeto.setMaximumRowCount(0);
        cbxProjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione um projeto..." }));
        cbxProjeto.setAutoscrolls(true);
        cbxProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Projeto", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        cbxProjeto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbxProjetoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

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

        cbxCR.setBackground(new java.awt.Color(255, 255, 255));
        cbxCR.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxCR.setForeground(new java.awt.Color(0, 0, 102));
        cbxCR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione um CR..." }));
        cbxCR.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Centro de resultados", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N
        cbxCR.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbxCRAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

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
                            .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(txtHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtHoraFinal))
                                    .addComponent(txtDataInicial, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(cbxCR, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(scpJustificativa, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxCR))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpJustificativa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxProjetoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbxProjetoAncestorAdded
        Thread thread = new Thread(() -> {
            ProjetoDAO proj = new ProjetoDAO();

            try {
                List<ProjetoModel> listaDeProjetos = proj.listarNomeProjeto();
                cbxProjeto.removeAll();

                for (ProjetoModel p : listaDeProjetos) {
                    cbxProjeto.addItem(p);
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(ApontamentoHoraExtra.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
    }//GEN-LAST:event_cbxProjetoAncestorAdded

    private void cbxClienteAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbxClienteAncestorAdded
        Thread thread = new Thread(() -> {
            ClienteDAO cliente = new ClienteDAO();
            try {
                List<ClienteModel> listaDeClientes = cliente.listarClientesNome();
                cbxCliente.removeAll();

                for (ClienteModel c : listaDeClientes) {
                    cbxCliente.addItem(c);
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(ApontamentoHoraExtra.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
    }//GEN-LAST:event_cbxClienteAncestorAdded

    private void cbxCRAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbxCRAncestorAdded
        Thread thread = new Thread(() -> {
            CentroResultadoDAO centroResultadoDAO = new CentroResultadoDAO();

            try {
                List<CentroResultadoModel> listaCr = centroResultadoDAO.listarCRs();

                cbxCR.removeAll();

                for (CentroResultadoModel c : listaCr) {
                    cbxCR.addItem(c);
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(ApontamentoHoraExtra.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        thread.start();
    }//GEN-LAST:event_cbxCRAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JComboBox cbxCR;
    private javax.swing.JComboBox cbxCliente;
    private javax.swing.JComboBox cbxProjeto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scpJustificativa;
    private javax.swing.JTextArea txaJustificativa;
    private javax.swing.JTextField txtDataFinal;
    private javax.swing.JTextField txtDataInicial;
    private javax.swing.JTextField txtHoraFinal;
    private javax.swing.JTextField txtHoraInicial;
    private javax.swing.JTextField txtSolicitante;
    // End of variables declaration//GEN-END:variables
}
