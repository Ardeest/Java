package Panels;

import java.awt.CardLayout;
import javax.swing.JButton;


public class MainPanel extends javax.swing.JFrame {
    
    private final ClientsPanel clientsPanel;
    private final WorksPanel worksPanel;
    private final LogsPanel logsPanel;
    private final TechniciansPanel techniciansPanel;
    
    public MainPanel() {
        initComponents();

        clientsPanel = new ClientsPanel();
        worksPanel = new WorksPanel();
        techniciansPanel = new TechniciansPanel();
        logsPanel = new LogsPanel();

        displayPanel.add(clientsPanel, "clients");
        displayPanel.add(techniciansPanel, "technics");
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

    }

        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuPanel = new javax.swing.JPanel();
        displayPanel = new javax.swing.JPanel();
        ButtonPanel = new javax.swing.JPanel();
        clientsButton = new javax.swing.JButton();
        technicsButton = new javax.swing.JButton();
        worksButton = new javax.swing.JButton();
        logsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Empresa");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(700, 400));
        setSize(new java.awt.Dimension(1000, 800));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        MenuPanel.setBackground(new java.awt.Color(255, 255, 255));
        MenuPanel.setPreferredSize(new java.awt.Dimension(0, 0));

        displayPanel.setBackground(new java.awt.Color(255, 255, 255));
        displayPanel.setPreferredSize(new java.awt.Dimension(900, 800));
        displayPanel.setLayout(new java.awt.CardLayout());

        ButtonPanel.setMinimumSize(new java.awt.Dimension(100, 400));
        ButtonPanel.setPreferredSize(new java.awt.Dimension(100, 500));

        clientsButton.setBackground(new java.awt.Color(204, 204, 204));
        clientsButton.setText("Clientes");
        clientsButton.setBorderPainted(false);
        clientsButton.setPreferredSize(new java.awt.Dimension(70, 23));
        clientsButton.setSelected(true);
        clientsButton.addActionListener(this::clientsButtonActionPerformed);

        technicsButton.setText("Técnicos");
        technicsButton.setBorderPainted(false);
        technicsButton.addActionListener(this::technicsButtonActionPerformed);

        worksButton.setText("Trabajos");
        worksButton.setBorderPainted(false);
        worksButton.addActionListener(this::worksButtonActionPerformed);

        logsButton.setText("Historial");
        logsButton.setBorderPainted(false);
        logsButton.setDefaultCapable(false);
        logsButton.addActionListener(this::logsButtonActionPerformed);

        javax.swing.GroupLayout ButtonPanelLayout = new javax.swing.GroupLayout(ButtonPanel);
        ButtonPanel.setLayout(ButtonPanelLayout);
        ButtonPanelLayout.setHorizontalGroup(
            ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonPanelLayout.createSequentialGroup()
                .addGroup(ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ButtonPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(clientsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ButtonPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(technicsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                    .addGroup(ButtonPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(worksButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ButtonPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ButtonPanelLayout.setVerticalGroup(
            ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(technicsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(worksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addComponent(ButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
        );

        getContentPane().add(MenuPanel);

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
    private javax.swing.JPanel ButtonPanel;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JButton clientsButton;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton logsButton;
    private javax.swing.JButton technicsButton;
    private javax.swing.JButton worksButton;
    // End of variables declaration//GEN-END:variables
}
