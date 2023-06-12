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

import br.com.lacamentohoraextra.Login;
import br.com.lacamentohoraextra.Views.Dashboard;
import br.com.lacamentohoraextra.Views.TelaAprovacao;
import br.com.lacamentohoraextra.Views.TelaLancamento;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author daviramos
 */
public class Gestor extends javax.swing.JFrame {

    /**
     * Creates new form Gestor
     */
    public Gestor() {
        initComponents();
        init();
    }

    public void init() {
        showApontamento(TelaAprovacao.class);
    }
    
    public void selecionarBotao(JButton botaoSelecionado) {
        btnDashboard.setSelected(false);
        btnLancamento.setSelected(false);
        btnAprovacao.setSelected(false);
        
        botaoSelecionado.setSelected(true);
    }

    private void showApontamento(Class<?> gestorClass) {
        try {
            JPanel gestorPanel = (JPanel) gestorClass.getDeclaredConstructor().newInstance();
            gestorPanel.setSize(content.getWidth(), content.getHeight());
            gestorPanel.setLocation(0, 0);

            content.removeAll();
            content.add(gestorPanel, BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        }
        catch (InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException ex) {
            Logger.getLogger(
                    Gestor.class.getName()).log(
                    Level.SEVERE,
                    ex.getMessage(),
                    ex);
            JOptionPane.showMessageDialog(
                    Gestor.this,
                    "Database connection failed: " + ex.getMessage());

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

        container = new javax.swing.JPanel();
        sidebarmenu = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        btnLancamento = new javax.swing.JButton();
        btnAprovacao = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(940, 600));
        setName("gestorFrame"); // NOI18N
        setResizable(false);

        container.setBackground(new java.awt.Color(255, 255, 255));
        container.setForeground(new java.awt.Color(0, 0, 102));
        container.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        container.setMinimumSize(new java.awt.Dimension(940, 600));
        container.setPreferredSize(new java.awt.Dimension(940, 600));

        sidebarmenu.setBackground(new java.awt.Color(0, 51, 102));
        sidebarmenu.setForeground(new java.awt.Color(0, 51, 102));

        btnDashboard.setBackground(new java.awt.Color(255, 255, 255));
        btnDashboard.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(153, 204, 255));
        btnDashboard.setText("Dashboard");
        btnDashboard.setBorder(null);
        btnDashboard.setContentAreaFilled(false);
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        btnLancamento.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnLancamento.setForeground(new java.awt.Color(153, 204, 255));
        btnLancamento.setText("Lançamentos");
        btnLancamento.setBorder(null);
        btnLancamento.setContentAreaFilled(false);
        btnLancamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLancamentoActionPerformed(evt);
            }
        });

        btnAprovacao.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAprovacao.setForeground(new java.awt.Color(153, 204, 255));
        btnAprovacao.setText("Aprovar");
        btnAprovacao.setBorder(null);
        btnAprovacao.setContentAreaFilled(false);
        btnAprovacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprovacaoActionPerformed(evt);
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
                    .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLancamento, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(btnAprovacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidebarmenuLayout.setVerticalGroup(
            sidebarmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarmenuLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAprovacao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
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
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnAprovacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprovacaoActionPerformed
        selecionarBotao(btnAprovacao);
        showApontamento(TelaAprovacao.class);
    }//GEN-LAST:event_btnAprovacaoActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        selecionarBotao(btnDashboard);
        showApontamento(Dashboard.class);
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnLancamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLancamentoActionPerformed
        selecionarBotao(btnLancamento);
        showApontamento(TelaLancamento.class);
    }//GEN-LAST:event_btnLancamentoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatIntelliJLaf.registerCustomDefaultsSource("br.com.lacamentohoraextra.styles");
        FlatIntelliJLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gestor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAprovacao;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnLancamento;
    private javax.swing.JButton btnSair;
    private javax.swing.JPanel container;
    private javax.swing.JPanel content;
    private javax.swing.JPanel sidebarmenu;
    // End of variables declaration//GEN-END:variables
}