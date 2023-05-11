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
package br.com.lacamentohoraextra;

import br.com.lacamentohoraextra.DAO.ConexaoSQL;
import br.com.lacamentohoraextra.Views.ApontamentoHistorico;
import br.com.lacamentohoraextra.Views.ApontamentoHoraExtra;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author daviramos
 */
public final class Colaborador extends javax.swing.JFrame {

    /**
     * Creates new form Colaborador
     */
    public Colaborador() {
        initComponents();
        init();
    }

    public void init() {
        showApontamento(ApontamentoHoraExtra.class);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container = new javax.swing.JPanel();
        sidebarmenu = new javax.swing.JPanel();
        btnApontamento = new javax.swing.JButton();
        btnHistorico = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(940, 600));
        setResizable(false);

        container.setBackground(new java.awt.Color(204, 255, 255));
        container.setForeground(new java.awt.Color(0, 51, 102));
        container.setMinimumSize(new java.awt.Dimension(940, 600));

        sidebarmenu.setBackground(new java.awt.Color(0, 51, 102));

        btnApontamento.setBackground(new java.awt.Color(255, 255, 255));
        btnApontamento.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnApontamento.setForeground(new java.awt.Color(153, 204, 255));
        btnApontamento.setText("Apontamento");
        btnApontamento.setBorder(null);
        btnApontamento.setContentAreaFilled(false);
        btnApontamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApontamentoActionPerformed(evt);
            }
        });

        btnHistorico.setBackground(new java.awt.Color(255, 255, 255));
        btnHistorico.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnHistorico.setForeground(new java.awt.Color(153, 204, 255));
        btnHistorico.setText("Histórico");
        btnHistorico.setBorder(null);
        btnHistorico.setContentAreaFilled(false);
        btnHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoricoActionPerformed(evt);
            }
        });

        btnSair.setBackground(new java.awt.Color(255, 255, 255));
        btnSair.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnSair.setForeground(new java.awt.Color(153, 204, 255));
        btnSair.setText("Sair");
        btnSair.setActionCommand("sair");
        btnSair.setBorder(null);
        btnSair.setContentAreaFilled(false);
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sidebarmenuLayout = new javax.swing.GroupLayout(sidebarmenu);
        sidebarmenu.setLayout(sidebarmenuLayout);
        sidebarmenuLayout.setHorizontalGroup(
            sidebarmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarmenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHistorico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApontamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidebarmenuLayout.setVerticalGroup(
            sidebarmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarmenuLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(btnApontamento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 353, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setForeground(new java.awt.Color(0, 51, 102));
        content.setPreferredSize(new java.awt.Dimension(764, 600));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 764, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addComponent(sidebarmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebarmenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(content, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnApontamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApontamentoActionPerformed
        showApontamento(ApontamentoHoraExtra.class);
    }//GEN-LAST:event_btnApontamentoActionPerformed

    private void btnHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoricoActionPerformed
        showApontamento(ApontamentoHistorico.class);
    }//GEN-LAST:event_btnHistoricoActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
        Login login = new Login();
        login.setVisible(true);

    }//GEN-LAST:event_btnSairActionPerformed

    private void showApontamento(Class<?> apontamentoClass) {
        try {
            JPanel apontamentoPanel = (JPanel) apontamentoClass.getDeclaredConstructor().newInstance();
            apontamentoPanel.setSize(content.getWidth(), content.getHeight());
            apontamentoPanel.setLocation(0, 0);

            content.removeAll();
            content.add(apontamentoPanel, BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            Logger.getLogger(
                    ConexaoSQL.class.getName()).log(
                    Level.SEVERE,
                    ex.getMessage(),
                    ex);
            JOptionPane.showMessageDialog(Colaborador.this, "Database connection failed: " + ex.getMessage());

        }
    }

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
            java.util.logging.Logger.getLogger(Colaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Colaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Colaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Colaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        FlatIntelliJLaf.registerCustomDefaultsSource("br.com.lacamentohoraextra.styles");
        FlatIntelliJLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Colaborador colaborador = new Colaborador();
            colaborador.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApontamento;
    private javax.swing.JButton btnHistorico;
    private javax.swing.JButton btnSair;
    private javax.swing.JPanel container;
    private javax.swing.JPanel content;
    private javax.swing.JPanel sidebarmenu;
    // End of variables declaration//GEN-END:variables
}
