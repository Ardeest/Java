package UI;

import Panels.*;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JButton;

public class MainPanel extends javax.swing.JFrame {
    
    private final ClientsPanel clientsPanel;
    private final WorksPanel worksPanel;
    private final LogsPanel logsPanel;
    private final TechnicsPanel technicsPanel;
    
    public MainPanel() {
        initComponents();

        clientsPanel = new ClientsPanel();
        worksPanel = new WorksPanel();
        technicsPanel = new TechnicsPanel();
        logsPanel = new LogsPanel();

        displayPanel.add(clientsPanel, "clients");
        displayPanel.add(technicsPanel, "technics");
        displayPanel.add(worksPanel, "works");
        displayPanel.add(logsPanel, "logs");
        
        showPanel("clients");
        setActive(clientsButton);
    }
    
    private void showPanel(String name) {
        CardLayout cardLayout = (CardLayout) displayPanel.getLayout();
        cardLayout.show(displayPanel, name);
    } 

    private void setActive(JButton active) {
        JButton[] buttons = {clientsButton, technicsButton, worksButton, logsButton};

        for (JButton b : buttons) {
            b.setBackground(new Color(180, 180, 180));
        }

        active.setBackground(Color.WHITE);
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayPanel = new javax.swing.JPanel();
        MenuPanel = new javax.swing.JPanel();
        clientsButton = new javax.swing.JButton();
        technicsButton = new javax.swing.JButton();
        worksButton = new javax.swing.JButton();
        logsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        displayPanel.setLayout(new java.awt.CardLayout());

        MenuPanel.setBackground(new java.awt.Color(102, 102, 102));

        clientsButton.setBackground(new java.awt.Color(204, 204, 204));
        clientsButton.setText("Clientes");
        clientsButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        clientsButton.setBorderPainted(false);
        clientsButton.setContentAreaFilled(false);
        clientsButton.setFocusPainted(false);
        clientsButton.setOpaque(true);
        clientsButton.addActionListener(this::clientsButtonActionPerformed);

        technicsButton.setText("Técnicos");
        technicsButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        technicsButton.setBorderPainted(false);
        technicsButton.setContentAreaFilled(false);
        technicsButton.setFocusPainted(false);
        technicsButton.setOpaque(true);
        technicsButton.addActionListener(this::technicsButtonActionPerformed);

        worksButton.setText("Trabajos");
        worksButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        worksButton.setBorderPainted(false);
        worksButton.setContentAreaFilled(false);
        worksButton.setFocusPainted(false);
        worksButton.setOpaque(true);
        worksButton.addActionListener(this::worksButtonActionPerformed);

        logsButton.setText("Historial");
        logsButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        logsButton.setBorderPainted(false);
        logsButton.setContentAreaFilled(false);
        logsButton.setFocusPainted(false);
        logsButton.setOpaque(true);
        logsButton.addActionListener(this::logsButtonActionPerformed);

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(worksButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(technicsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(clientsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(clientsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(technicsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(worksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(319, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 125, Short.MAX_VALUE)
                .addComponent(displayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(MenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 351, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(MenuPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logsButtonActionPerformed
        showPanel("logs");
        setActive(logsButton);
    }//GEN-LAST:event_logsButtonActionPerformed

    private void worksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_worksButtonActionPerformed
        showPanel("works");
        setActive(worksButton);
    }//GEN-LAST:event_worksButtonActionPerformed

    private void technicsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technicsButtonActionPerformed
        showPanel("technics");
        setActive(technicsButton);

    }//GEN-LAST:event_technicsButtonActionPerformed

    private void clientsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientsButtonActionPerformed
        showPanel("clients");
        setActive(clientsButton);
    }//GEN-LAST:event_clientsButtonActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new MainPanel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JButton clientsButton;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton logsButton;
    private javax.swing.JButton technicsButton;
    private javax.swing.JButton worksButton;
    // End of variables declaration//GEN-END:variables
}
