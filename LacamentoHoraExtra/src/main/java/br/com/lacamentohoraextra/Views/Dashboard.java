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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daviramos
 */
public class Dashboard extends javax.swing.JPanel {

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        init();
    }

    private void init() {

        carregarDadosComboboxes();
        executarConsulta(null, null, null);

        btnFiltrar.addActionListener(e -> {
            String nomeUsuario = cbxColaborador.getSelectedItem().toString();
            String cliente = cbxCliente.getSelectedItem().toString();
            String cr = cbxCR.getSelectedItem().toString();

            executarConsulta(nomeUsuario, cliente, cr);
        });

        Thread thread = new Thread(() -> {
            try {
                ArrayList<ApontamentoModel> listaDeApontamentos = ApontamentoDAO.dadosDashboard();

                if (!listaDeApontamentos.isEmpty()) {
                    ApontamentoModel apontamentoModel = listaDeApontamentos.get(0);

                    String horasPendentes = apontamentoModel.getHoraPendente();
                    String horasAprovadas = apontamentoModel.getHoraAprovada();
                    String horasNaoAprovadas = apontamentoModel.getHoraNaoAprovada();

                    SwingUtilities.invokeLater(() -> {
                        totalHorasPendentes.setText(horasPendentes);
                        totalHorasAprovadas.setText(horasAprovadas);
                        totalHorasNaoAprovadas.setText(horasNaoAprovadas);
                    });
                }

            }
            catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        });
        thread.start();
    }

    private void executarConsulta(String nomeUsuario, String cliente, String cr) {
        try {
            Connection conn = obterConexao();

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT vru.nome_usuario, vru.nome_cliente, vru.nome_projeto, vru.cr ");
            queryBuilder.append("FROM relatorio_de_apontamentos vru ");
            queryBuilder.append("WHERE 1 = 1 ");

            if (nomeUsuario != null && !nomeUsuario.isEmpty() && cbxColaborador.getSelectedIndex() != 0) {
                queryBuilder.append("AND vru.nome_usuario = '").append(nomeUsuario).append("' ");
            }

            if (cliente != null && !cliente.isEmpty() && cbxCliente.getSelectedIndex() != 0) {
                queryBuilder.append("AND vru.nome_cliente = '").append(cliente).append("' ");
            }

            if (cr != null && !cr.isEmpty() && cbxCR.getSelectedIndex() != 0) {
                queryBuilder.append("AND vru.cr = '").append(cr).append("' ");
            }

            String query = queryBuilder.toString();

            System.out.println(query);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            atualizarTabela(rs);

            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void carregarDadosComboboxes() {
        Thread thread = new Thread(() -> {
            try {
                Connection conn = obterConexao();

                String queryColaborador = "SELECT DISTINCT nome_usuario FROM relatorio_de_apontamentos";
                Statement stmtColaborador = conn.createStatement();
                ResultSet rsColaborador = stmtColaborador.executeQuery(queryColaborador);
                while (rsColaborador.next()) {
                    String colaborador = rsColaborador.getString("nome_usuario");
                    cbxColaborador.addItem(colaborador);
                }
                rsColaborador.close();
                stmtColaborador.close();

                String queryCliente = "SELECT DISTINCT nome_cliente FROM relatorio_de_apontamentos";
                Statement stmtCliente = conn.createStatement();
                ResultSet rsCliente = stmtCliente.executeQuery(queryCliente);
                while (rsCliente.next()) {
                    String cliente = rsCliente.getString("nome_cliente");
                    cbxCliente.addItem(cliente);
                }
                rsCliente.close();
                stmtCliente.close();

                String queryCR = "SELECT DISTINCT cr FROM relatorio_de_apontamentos";
                Statement stmtCR = conn.createStatement();
                ResultSet rsCR = stmtCR.executeQuery(queryCR);
                while (rsCR.next()) {
                    String cr = rsCR.getString("cr");
                    cbxCR.addItem(cr);
                }
                rsCR.close();
                stmtCR.close();

                conn.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        });
        thread.start();
    }

    private Connection obterConexao() throws SQLException {
        String url = "jdbc:postgresql://dpg-ch5addcs3fvqdin3e8j0-a.ohio-postgres.render.com:5432/apifatec";
        String usuario = "apifatec";
        String senha = "jjBMjNCvV4cWtd1WCv6ZuWZvqS6xeR9u";

        Connection conexao = DriverManager.getConnection(url, usuario, senha);
        return conexao;
    }

    private void atualizarTabela(ResultSet rs) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tabelaDashboard.getModel();
        model.setRowCount(0); // Limpar a tabela antes de adicionar os novos dados

        while (rs.next()) {
            String nomeUsuario = rs.getString("nome_usuario");
            String cliente = rs.getString("nome_cliente");
            String projeto = rs.getString("nome_projeto");
            String cr = rs.getString("cr");

            model.addRow(new Object[]{nomeUsuario, cliente, projeto, cr});
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardHoraPendente = new javax.swing.JPanel();
        lblHoraPendente = new javax.swing.JLabel();
        totalHorasPendentes = new javax.swing.JLabel();
        cardHoraAprovada = new javax.swing.JPanel();
        lblHoraAprovada = new javax.swing.JLabel();
        totalHorasAprovadas = new javax.swing.JLabel();
        cardHoraNaoAprovada = new javax.swing.JPanel();
        lblHoraNaoAprovada = new javax.swing.JLabel();
        totalHorasNaoAprovadas = new javax.swing.JLabel();
        scrollPanel = new javax.swing.JScrollPane();
        tabelaDashboard = new javax.swing.JTable();
        cbxColaborador = new javax.swing.JComboBox();
        lblTitulo = new javax.swing.JLabel();
        btnFiltrar = new javax.swing.JButton();
        cbxCliente = new javax.swing.JComboBox();
        cbxCR = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 0, 102));
        setMaximumSize(new java.awt.Dimension(764, 600));
        setMinimumSize(new java.awt.Dimension(764, 600));
        setPreferredSize(new java.awt.Dimension(764, 600));

        cardHoraPendente.setBackground(new java.awt.Color(245, 245, 245));
        cardHoraPendente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cardHoraPendente.setForeground(new java.awt.Color(55, 97, 139));
        cardHoraPendente.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cardHoraPendente.setPreferredSize(new java.awt.Dimension(238, 147));

        lblHoraPendente.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblHoraPendente.setForeground(new java.awt.Color(55, 97, 139));
        lblHoraPendente.setText("Horas extras pendentes");

        totalHorasPendentes.setBackground(new java.awt.Color(255, 255, 255));
        totalHorasPendentes.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        totalHorasPendentes.setForeground(new java.awt.Color(0, 51, 102));
        totalHorasPendentes.setText("00:00");

        javax.swing.GroupLayout cardHoraPendenteLayout = new javax.swing.GroupLayout(cardHoraPendente);
        cardHoraPendente.setLayout(cardHoraPendenteLayout);
        cardHoraPendenteLayout.setHorizontalGroup(
            cardHoraPendenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardHoraPendenteLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(cardHoraPendenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHoraPendente)
                    .addComponent(totalHorasPendentes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardHoraPendenteLayout.setVerticalGroup(
            cardHoraPendenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardHoraPendenteLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblHoraPendente)
                .addGap(18, 18, 18)
                .addComponent(totalHorasPendentes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cardHoraAprovada.setBackground(new java.awt.Color(245, 245, 245));
        cardHoraAprovada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cardHoraAprovada.setForeground(new java.awt.Color(55, 97, 139));
        cardHoraAprovada.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cardHoraAprovada.setPreferredSize(new java.awt.Dimension(238, 147));

        lblHoraAprovada.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblHoraAprovada.setForeground(new java.awt.Color(55, 97, 139));
        lblHoraAprovada.setText("Horas extras aprovadas");

        totalHorasAprovadas.setBackground(new java.awt.Color(255, 255, 255));
        totalHorasAprovadas.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        totalHorasAprovadas.setForeground(new java.awt.Color(0, 51, 102));
        totalHorasAprovadas.setText("00:00");

        javax.swing.GroupLayout cardHoraAprovadaLayout = new javax.swing.GroupLayout(cardHoraAprovada);
        cardHoraAprovada.setLayout(cardHoraAprovadaLayout);
        cardHoraAprovadaLayout.setHorizontalGroup(
            cardHoraAprovadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardHoraAprovadaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(cardHoraAprovadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHoraAprovada)
                    .addComponent(totalHorasAprovadas))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        cardHoraAprovadaLayout.setVerticalGroup(
            cardHoraAprovadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardHoraAprovadaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblHoraAprovada)
                .addGap(18, 18, 18)
                .addComponent(totalHorasAprovadas)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        cardHoraNaoAprovada.setBackground(new java.awt.Color(245, 245, 245));
        cardHoraNaoAprovada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        cardHoraNaoAprovada.setForeground(new java.awt.Color(55, 97, 139));
        cardHoraNaoAprovada.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N

        lblHoraNaoAprovada.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblHoraNaoAprovada.setForeground(new java.awt.Color(55, 97, 139));
        lblHoraNaoAprovada.setText("Horas extras não aprovadas");

        totalHorasNaoAprovadas.setBackground(new java.awt.Color(255, 255, 255));
        totalHorasNaoAprovadas.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        totalHorasNaoAprovadas.setForeground(new java.awt.Color(0, 51, 102));
        totalHorasNaoAprovadas.setText("00:00");

        javax.swing.GroupLayout cardHoraNaoAprovadaLayout = new javax.swing.GroupLayout(cardHoraNaoAprovada);
        cardHoraNaoAprovada.setLayout(cardHoraNaoAprovadaLayout);
        cardHoraNaoAprovadaLayout.setHorizontalGroup(
            cardHoraNaoAprovadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardHoraNaoAprovadaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(cardHoraNaoAprovadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHoraNaoAprovada)
                    .addComponent(totalHorasNaoAprovadas))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        cardHoraNaoAprovadaLayout.setVerticalGroup(
            cardHoraNaoAprovadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardHoraNaoAprovadaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblHoraNaoAprovada)
                .addGap(18, 18, 18)
                .addComponent(totalHorasNaoAprovadas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabelaDashboard.setBackground(new java.awt.Color(255, 255, 255));
        tabelaDashboard.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        tabelaDashboard.setForeground(new java.awt.Color(0, 0, 102));
        tabelaDashboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Colaborador", "Cliente", "Projeto", "Centro de resultado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabelaDashboard.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tabelaDashboard.setSelectionForeground(new java.awt.Color(0, 0, 102));
        scrollPanel.setViewportView(tabelaDashboard);

        cbxColaborador.setBackground(new java.awt.Color(255, 255, 255));
        cbxColaborador.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxColaborador.setForeground(new java.awt.Color(0, 0, 102));
        cbxColaborador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione..." }));
        cbxColaborador.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Colaborador", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        lblTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 153));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Dashboard");

        btnFiltrar.setBackground(new java.awt.Color(255, 255, 255));
        btnFiltrar.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        btnFiltrar.setForeground(new java.awt.Color(0, 0, 102));
        btnFiltrar.setText("Filtrar");
        btnFiltrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));

        cbxCliente.setBackground(new java.awt.Color(255, 255, 255));
        cbxCliente.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxCliente.setForeground(new java.awt.Color(0, 0, 102));
        cbxCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione..." }));
        cbxCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        cbxCR.setBackground(new java.awt.Color(255, 255, 255));
        cbxCR.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        cbxCR.setForeground(new java.awt.Color(0, 0, 102));
        cbxCR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione..." }));
        cbxCR.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "CR", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Liberation Sans", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxCR, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(260, 260, 260))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cardHoraPendente, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(cardHoraAprovada, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cardHoraNaoAprovada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(27, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cardHoraAprovada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(cardHoraPendente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(cardHoraNaoAprovada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxCR, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JPanel cardHoraAprovada;
    private javax.swing.JPanel cardHoraNaoAprovada;
    private javax.swing.JPanel cardHoraPendente;
    private javax.swing.JComboBox cbxCR;
    private javax.swing.JComboBox cbxCliente;
    private javax.swing.JComboBox cbxColaborador;
    private javax.swing.JLabel lblHoraAprovada;
    private javax.swing.JLabel lblHoraNaoAprovada;
    private javax.swing.JLabel lblHoraPendente;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JTable tabelaDashboard;
    private javax.swing.JLabel totalHorasAprovadas;
    private javax.swing.JLabel totalHorasNaoAprovadas;
    private javax.swing.JLabel totalHorasPendentes;
    // End of variables declaration//GEN-END:variables
}
