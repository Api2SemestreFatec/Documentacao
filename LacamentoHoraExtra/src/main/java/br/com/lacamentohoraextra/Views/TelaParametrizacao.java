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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daviramos
 */
public class TelaParametrizacao extends javax.swing.JPanel {

    /**
     * Creates new form TelaParametrizacao
     */
    public TelaParametrizacao() {
        initComponents();

        cmbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarPercentual();
            }
        });

        btnSalvarParametrizacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inserirParametrizacaoHoras();
            }
        });
    }

    private Connection obterConexao() throws SQLException {
        String url = "jdbc:postgresql://dpg-ch5addcs3fvqdin3e8j0-a.ohio-postgres.render.com:5432/apifatec";
        String usuario = "apifatec";
        String senha = "jjBMjNCvV4cWtd1WCv6ZuWZvqS6xeR9u";

        Connection conexao = DriverManager.getConnection(url, usuario, senha);
        return conexao;
    }

    private void carregarClientes() {
        Thread thread = new Thread(() -> {
            try {
                Connection conn = obterConexao();
                String sql = "SELECT c.nome FROM cliente c inner join projeto p on c.nome = p.cliente group by c.nome";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String nomeCliente = rs.getString("nome");
                    cmbCliente.addItem(nomeCliente);
                }

                rs.close();
                stmt.close();
                conn.close();

            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os clientes.");
            }
        });
        thread.start();
    }

    private void carregarProjetos() {
        Thread thread = new Thread(() -> {
            try {

                Connection conn = obterConexao();
                String sql = "SELECT p.nome FROM projeto p inner join cliente c on c.nome = p.cliente group by p.nome ";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String nomeProjeto = rs.getString("nome");
                    cmbProjeto.addItem(nomeProjeto);
                }

                rs.close();
                stmt.close();
                conn.close();

            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os projetos.");
            }
        });
        thread.start();
    }

    private void atualizarPercentual() {
        String tipoHora = (String) cmbTipo.getSelectedItem();

        if (tipoHora.equals("Sobreaviso")) {
            txtPercentual.setText("30");
        } else if (tipoHora.equals("Hora Extra Diurno")) {
            txtPercentual.setText("75");
        } else if (tipoHora.equals("Hora Extra Noturno")) {
            txtPercentual.setText("75");
        }
    }

    private String calcularValorHoraExtra(double verbaProjeto, double percentual) {
        double valorHoraExtra = (verbaProjeto * percentual / 100);
        long novo = (long) valorHoraExtra;
        return String.valueOf(novo);
    }

    private void inserirParametrizacaoHoras() {
        String cliente = (String) cmbCliente.getSelectedItem();
        String projeto = (String) cmbProjeto.getSelectedItem();
        String dataFechamento = txtDataFechamento.getText();
        String tipoHora = (String) cmbTipo.getSelectedItem();
        String periodo = (String) cmbPeriodo.getSelectedItem();
        double percentual = Double.parseDouble(txtPercentual.getText());
        double adicionalNoturno = Double.parseDouble(txtAdicionalNoturno.getText());
        String horarioInicio = txtHoraInicio.getText();
        String horarioFim = txtHoraFim.getText();

        try {
            percentual = Double.parseDouble(txtPercentual.getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida para percentual");
        }

        try {
            adicionalNoturno = Double.parseDouble(txtAdicionalNoturno.getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida para adicional noturno");
        }

        if (cliente == null || cliente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Cliente está vazio");
        }

        if (projeto == null || projeto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Projeto está vazio");
        }

        if (dataFechamento == null || dataFechamento.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Data de Fechamento está vazio");
        }

        if (tipoHora == null || tipoHora.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Tipo de Hora está vazio");
        }

        if (periodo == null || periodo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Período está vazio");
        }

        if (horarioInicio == null || horarioInicio.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Horário de Início está vazio");
        }

        if (horarioFim == null || horarioFim.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Horário de Fim está vazio");
        }

        try {

            Connection conn = obterConexao();

            String sqlVerba = "SELECT verba FROM projeto WHERE nome = ?";

            PreparedStatement stmtVerba = conn.prepareStatement(sqlVerba);
            stmtVerba.setString(1, projeto);
            ResultSet rsVerba = stmtVerba.executeQuery();

            if (rsVerba.next()) {
                double verbaProjeto = rsVerba.getDouble("verba");
                String valorHoraExtra = calcularValorHoraExtra(verbaProjeto, percentual);

                String sqlInserir = "INSERT INTO parametrizacao_horas (cliente, projeto, data_fechamento, tipo_hora, periodo, percentual, adicional_noturno, horario_inicio, horario_fim, valor_hora_extra) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmtInserir = conn.prepareStatement(sqlInserir)) {
                    stmtInserir.setString(1, cliente);
                    stmtInserir.setString(2, projeto);
                    stmtInserir.setString(3, dataFechamento);
                    stmtInserir.setString(4, tipoHora);
                    stmtInserir.setString(5, periodo);
                    stmtInserir.setDouble(6, percentual);
                    stmtInserir.setDouble(7, adicionalNoturno);
                    stmtInserir.setString(8, horarioInicio);
                    stmtInserir.setString(9, horarioFim);
                    stmtInserir.setString(10, valorHoraExtra);

                    stmtInserir.executeUpdate();

                    rsVerba.close();
                    stmtVerba.close();
                }
                conn.close();

                cmbCliente.setSelectedIndex(0);
                cmbProjeto.setSelectedIndex(0);
                txtDataFechamento.setText("");
                cmbTipo.setSelectedIndex(0);
                cmbPeriodo.setSelectedIndex(0);
                txtPercentual.setText("");
                txtAdicionalNoturno.setText("");
                txtHoraInicio.setText("");
                txtHoraFim.setText("");

                JOptionPane.showMessageDialog(this, "Parametrização de horas inserida com sucesso.");
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível obter a verba do projeto.");
            }

        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao inserir a parametrização de horas.");
        }
    }

    public void popularTabela() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setNumRows(0);

        Thread thread = new Thread(() -> {
            try {
                
                Connection conn = obterConexao();
                
                String query = "SELECT cliente, projeto, data_fechamento, tipo_hora, periodo, percentual, adicional_noturno, horario_inicio, horario_fim FROM public.vw_dados_parametrizacao";
                
                PreparedStatement consultaSQL = conn.prepareStatement(query);
                ResultSet resultSet = consultaSQL.executeQuery();

                while (resultSet.next()) {

                    String setCliente = resultSet.getString("cliente");
                    String setProjeto = resultSet.getString("projeto");
                    String setData_fechamento = resultSet.getString("data_fechamento");
                    String setTipo_hora = resultSet.getString("tipo_hora");
                    String setPeriodo = resultSet.getString("periodo");
                    String setPercentual = resultSet.getString("percentual");
                    String setAdicional_noturno = resultSet.getString("adicional_noturno");
                    String setHorario_inicio = resultSet.getString("horario_inicio");
                    String setHorario_fim = resultSet.getString("horario_fim");
                    
                    model.addRow(new Object[]{setCliente, setProjeto, setData_fechamento, setTipo_hora, setPeriodo, setPercentual, setAdicional_noturno, setHorario_inicio, setHorario_fim});
                }
                
                resultSet.close();
                consultaSQL.close();
                conn.close();
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os dados na tabela.");
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

        cmbTipo = new javax.swing.JComboBox<>();
        cmbCliente = new javax.swing.JComboBox<>();
        cmbProjeto = new javax.swing.JComboBox<>();
        cmbPeriodo = new javax.swing.JComboBox<>();
        txtDataFechamento = new javax.swing.JTextField();
        txtHoraInicio = new javax.swing.JTextField();
        txtHoraFim = new javax.swing.JTextField();
        txtAdicionalNoturno = new javax.swing.JTextField();
        txtPercentual = new javax.swing.JTextField();
        scrollPane = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btnSalvarParametrizacao = new javax.swing.JButton();
        btnAtualizarTabela = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 0, 102));
        setMinimumSize(new java.awt.Dimension(752, 479));
        setPreferredSize(new java.awt.Dimension(752, 479));

        cmbTipo.setBackground(new java.awt.Color(255, 255, 255));
        cmbTipo.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cmbTipo.setForeground(new java.awt.Color(0, 0, 102));
        cmbTipo.setMaximumRowCount(4);
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione um tipo...", "Sobreaviso", "Hora Extra Diurno", "Hora Extra Noturno" }));
        cmbTipo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Tipo", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        cmbCliente.setBackground(new java.awt.Color(255, 255, 255));
        cmbCliente.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cmbCliente.setForeground(new java.awt.Color(0, 0, 102));
        cmbCliente.setMaximumRowCount(5);
        cmbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione um cliente..." }));
        cmbCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        cmbCliente.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cmbClienteAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        cmbProjeto.setBackground(new java.awt.Color(255, 255, 255));
        cmbProjeto.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cmbProjeto.setForeground(new java.awt.Color(0, 0, 102));
        cmbProjeto.setMaximumRowCount(5);
        cmbProjeto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione um projeto..." }));
        cmbProjeto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Projeto", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N
        cmbProjeto.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cmbProjetoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        cmbPeriodo.setBackground(new java.awt.Color(255, 255, 255));
        cmbPeriodo.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cmbPeriodo.setForeground(new java.awt.Color(0, 0, 102));
        cmbPeriodo.setMaximumRowCount(5);
        cmbPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o período...", "Semanal", "Mensal" }));
        cmbPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Período", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        txtDataFechamento.setBackground(new java.awt.Color(255, 255, 255));
        txtDataFechamento.setForeground(new java.awt.Color(0, 0, 102));
        txtDataFechamento.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Data de fechamento", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        txtHoraInicio.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraInicio.setForeground(new java.awt.Color(0, 0, 102));
        txtHoraInicio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Hora de início", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        txtHoraFim.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraFim.setForeground(new java.awt.Color(0, 0, 102));
        txtHoraFim.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Hora de fim", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        txtAdicionalNoturno.setBackground(new java.awt.Color(255, 255, 255));
        txtAdicionalNoturno.setForeground(new java.awt.Color(0, 0, 102));
        txtAdicionalNoturno.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Adiconal noturno", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        txtPercentual.setBackground(new java.awt.Color(255, 255, 255));
        txtPercentual.setForeground(new java.awt.Color(0, 0, 102));
        txtPercentual.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Percentual", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        tabela.setBackground(new java.awt.Color(255, 255, 255));
        tabela.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        tabela.setForeground(new java.awt.Color(0, 0, 102));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente", "Projeto", "Periodo", "Data Fechamento", "Tipo", "Período", "Percentual", "Hora início", "Hora fim"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tabela.setSelectionForeground(new java.awt.Color(0, 0, 102));
        tabela.setShowGrid(false);
        scrollPane.setViewportView(tabela);

        btnSalvarParametrizacao.setBackground(new java.awt.Color(0, 51, 102));
        btnSalvarParametrizacao.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnSalvarParametrizacao.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarParametrizacao.setText("Salvar");
        btnSalvarParametrizacao.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSalvarParametrizacao.setBorderPainted(false);

        btnAtualizarTabela.setBackground(new java.awt.Color(255, 255, 255));
        btnAtualizarTabela.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAtualizarTabela.setForeground(new java.awt.Color(0, 0, 102));
        btnAtualizarTabela.setText("Atualizar tabela");
        btnAtualizarTabela.setBorder(null);
        btnAtualizarTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarTabelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvarParametrizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtualizarTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDataFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtHoraFim, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtAdicionalNoturno, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPercentual))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPane))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoraFim, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAdicionalNoturno, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPercentual, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvarParametrizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtualizarTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbClienteAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cmbClienteAncestorAdded
        carregarClientes();
    }//GEN-LAST:event_cmbClienteAncestorAdded

    private void cmbProjetoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cmbProjetoAncestorAdded
        carregarProjetos();
    }//GEN-LAST:event_cmbProjetoAncestorAdded

    private void btnAtualizarTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarTabelaActionPerformed
        try {
            popularTabela();
        }
        catch (SQLException e) {
            Logger.getLogger(
               TelaParametrizacao.class.getName()).log(
                Level.SEVERE,
                e.getMessage(),
                e);
        }
    }//GEN-LAST:event_btnAtualizarTabelaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarTabela;
    private javax.swing.JButton btnSalvarParametrizacao;
    private javax.swing.JComboBox<String> cmbCliente;
    private javax.swing.JComboBox<String> cmbPeriodo;
    private javax.swing.JComboBox<String> cmbProjeto;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtAdicionalNoturno;
    private javax.swing.JTextField txtDataFechamento;
    private javax.swing.JTextField txtHoraFim;
    private javax.swing.JTextField txtHoraInicio;
    private javax.swing.JTextField txtPercentual;
    // End of variables declaration//GEN-END:variables
}
