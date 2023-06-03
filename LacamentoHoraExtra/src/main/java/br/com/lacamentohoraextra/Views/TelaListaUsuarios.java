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

import br.com.lacamentohoraextra.DAO.UsuariosDAO;
import br.com.lacamentohoraextra.Models.UsuariosModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author daviramos
 */
public class TelaListaUsuarios extends javax.swing.JFrame {

    /**
     * Creates new form TelaListaUsuarios
     */
    public TelaListaUsuarios() {
        initComponents();

        lblTotal.setText("Total: " + Integer.toString(0));

        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
            try {
                popularTabela();
            }
            catch (SQLException ex) {
                Logger.getLogger(TelaListaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void popularTabela() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tblListaUsuarios.getModel();
        model.setNumRows(0);

        Thread thread = new Thread(() -> {
            try {
                Object colunas[] = new Object[4];
                UsuariosModel usuariosModel = new UsuariosModel();

                ArrayList<UsuariosModel> listaUsuarios = new ArrayList<UsuariosModel>();
                listaUsuarios = UsuariosDAO.listarUsuariosTabela();

                for (int i = 0; i < listaUsuarios.size(); i++) {
                    usuariosModel = listaUsuarios.get(i);

                    colunas[0] = usuariosModel.getId();
                    colunas[1] = usuariosModel.getNomeUsuario();
                    colunas[2] = usuariosModel.getEquipe();
                    colunas[3] = usuariosModel.getMatricula();

                    model.addRow(colunas);

                    lblTotal.setText("Total: " + listaUsuarios.size());
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

    // TODO: Não está com os campos corretos ainda 
    public void exportarParaCSV() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblListaUsuarios.getModel();
            int rowCount = model.getRowCount();

            if (rowCount == 0) {
                // Não há dados para exportar
                return;
            }

            // Obter o diretório da pasta de documentos do usuário
            String filePath = "";

            if (SystemUtils.IS_OS_WINDOWS) {
                String userHome = System.getProperty("user.home");
                filePath = userHome + File.separator + "Documentos" + File.separator + "usuarios.csv";
            } else if (SystemUtils.IS_OS_LINUX) {
                String userHome = System.getProperty("user.home");
                filePath = userHome + File.separator + "Documentos" + File.separator + "usuarios.csv";
            } else {
                // Sistema operacional não suportado
                return;
            }

            FileWriter writer = new FileWriter(filePath);

            // Escrever o cabeçalho do arquivo CSV
            writer.append("ID,Nome de Usuário,Equipe,Matrícula");
            writer.append("\n");

            for (int i = 0; i < rowCount; i++) {
                Object[] rowData = new Object[model.getColumnCount()];
                for (int j = 0; j < model.getColumnCount(); j++) {
                    rowData[j] = model.getValueAt(i, j);
                }

                // Escrever os dados no arquivo CSV
                writer.append(rowData[0].toString());
                writer.append(",");
                writer.append(rowData[1].toString());
                writer.append(",");
                writer.append(rowData[2].toString());
                writer.append(",");
                writer.append(rowData[3].toString());
                writer.append("\n");
            }

            writer.flush();
            writer.close();

            // Exibir uma mensagem de sucesso
            JOptionPane.showMessageDialog(null, "Os dados foram exportados para o arquivo CSV com sucesso!");

        }
        catch (IOException ex) {
            // Tratar exceção de IO
            Logger.getLogger(TelaListaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
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

        pContent = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        scrollPaneListaUsuarios = new javax.swing.JScrollPane();
        tblListaUsuarios = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(764, 600));

        pContent.setBackground(new java.awt.Color(255, 255, 255));
        pContent.setForeground(new java.awt.Color(0, 0, 102));
        pContent.setMinimumSize(new java.awt.Dimension(764, 600));
        pContent.setPreferredSize(new java.awt.Dimension(764, 600));

        lblTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 153));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Lista de Usuários");

        tblListaUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        tblListaUsuarios.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        tblListaUsuarios.setForeground(new java.awt.Color(0, 0, 102));
        tblListaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Colaborador", "Equipe", "Matricula"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListaUsuarios.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tblListaUsuarios.setSelectionForeground(new java.awt.Color(0, 0, 102));
        scrollPaneListaUsuarios.setViewportView(tblListaUsuarios);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 102));
        jButton1.setText("Exportar Relatório");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Liberation Sans", 0, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 0, 102));
        lblTotal.setText("0");

        javax.swing.GroupLayout pContentLayout = new javax.swing.GroupLayout(pContent);
        pContent.setLayout(pContentLayout);
        pContentLayout.setHorizontalGroup(
            pContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pContentLayout.createSequentialGroup()
                        .addGroup(pContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pContentLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblTotal))
                            .addGroup(pContentLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(pContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(scrollPaneListaUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                                    .addGroup(pContentLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(44, 44, 44)))
                .addContainerGap())
        );
        pContentLayout.setVerticalGroup(
            pContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrollPaneListaUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaListaUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pContent;
    private javax.swing.JScrollPane scrollPaneListaUsuarios;
    private javax.swing.JTable tblListaUsuarios;
    // End of variables declaration//GEN-END:variables
}
