package Panels;

import Data.Models.Customer;
import Data.Models.Technician;
import Data.Models.Work;
import Data.Services.CustomerService;
import Data.Services.TechnicianService;
import Data.Services.WorkService;
import java.awt.Color;

public class WorksPanel extends javax.swing.JPanel {

    private int selectedCustomerId = -1;
    private int selectedTechnicianId = -1;

    public WorksPanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerTextField = new javax.swing.JTextField();
        technicianTextField = new javax.swing.JTextField();
        assignTechnicianButton = new javax.swing.JButton();
        assignCustomerButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableWork = new javax.swing.JTable();
        statusComboBox = new javax.swing.JComboBox<>();
        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        dateTimePicker1 = new com.github.lgooddatepicker.components.DateTimePicker();
        nameTextField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        assignWorkButton = new javax.swing.JButton();
        workTextField = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        customerTextField.setEnabled(false);

        technicianTextField.setEnabled(false);

        assignTechnicianButton.setText("Asignar Técnico");
        assignTechnicianButton.setFocusPainted(false);
        assignTechnicianButton.addActionListener(this::assignTechnicianButtonActionPerformed);

        assignCustomerButton.setText("Asignar Cliente");
        assignCustomerButton.addActionListener(this::assignCustomerButtonActionPerformed);

        tableWork.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Técnico", "Cliente", "Estatus", "Fecha"
            }
        ));
        tableWork.setOpaque(false);
        jScrollPane1.setViewportView(tableWork);

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        statusComboBox.addActionListener(this::statusComboBoxActionPerformed);

        deleteButton.setText("Eliminar");

        updateButton.setText("Actualizar");

        addButton.setText("Agregar");

        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });

        nameLabel.setForeground(new java.awt.Color(0, 51, 204));
        nameLabel.setText("Descripción");

        assignWorkButton.setText("Asignar Trabajo");
        assignWorkButton.addActionListener(this::assignWorkButtonActionPerformed);

        workTextField.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
                    .addComponent(nameTextField)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(customerTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(technicianTextField)
                            .addComponent(workTextField)
                            .addComponent(dateTimePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(assignCustomerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(assignTechnicianButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(assignWorkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(technicianTextField)
                    .addComponent(assignTechnicianButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assignCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(workTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assignWorkButton))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateTimePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nameLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addButton)
                        .addComponent(deleteButton)
                        .addComponent(updateButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void assignTechnicianButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignTechnicianButtonActionPerformed
        AssignTechnicianForm form = new AssignTechnicianForm(new AssignTechnicianForm.TechnicianSelectionListener() {
            @Override
            public void onTechnicianSelected(int technicianId) {

                selectedCustomerId = technicianId;

                TechnicianService service = new TechnicianService();
                Technician t = service.getById(technicianId);

                if (t != null) {
                    technicianTextField.setText(t.getFirstName() + " " + t.getLastName1());
                }
            }
        });

        java.awt.Frame parent = (java.awt.Frame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        javax.swing.JDialog dialog = new javax.swing.JDialog(
                parent,
                "Seleccionar técnico",
                true
        );

        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_assignTechnicianButtonActionPerformed

    private void assignCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignCustomerButtonActionPerformed

        AssignCustomerForm form = new AssignCustomerForm(new AssignCustomerForm.CustomerSelectionListener() {
            @Override
            public void onCustomerSelected(int customerId) {

                selectedCustomerId = customerId;

                CustomerService service = new CustomerService();
                Customer c = service.getById(customerId);

                if (c != null) {
                    customerTextField.setText(c.getFirstName() + " " + c.getLastName1());
                }
            }
        });

        java.awt.Frame parent = (java.awt.Frame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        javax.swing.JDialog dialog = new javax.swing.JDialog(
                parent,
                "Seleccionar cliente",
                true
        );

        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_assignCustomerButtonActionPerformed

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusComboBoxActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusGained
        nameLabel.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_nameTextFieldFocusGained

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        nameLabel.setForeground(Color.WHITE);
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void assignWorkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignWorkButtonActionPerformed
        AssignWorkForm form = new AssignWorkForm(workId -> {

            WorkService service = new WorkService();
            Work w = service.getById(workId);

            if (w != null) {
                selectedTechnicianId = w.getTechnicianId();
                selectedCustomerId = w.getCustomerId();

                TechnicianService tService = new TechnicianService();
                Technician t = tService.getById(w.getTechnicianId());

                CustomerService cService = new CustomerService();
                Customer c = cService.getById(w.getCustomerId());

                if (t != null) {
                    technicianTextField.setText(t.getFirstName() + " " + t.getLastName1());
                }

                if (c != null) {
                    customerTextField.setText(c.getFirstName() + " " + c.getLastName1());
                }

                workTextField.setText(String.valueOf(w.getId()));
            }
        });

        java.awt.Frame parent = (java.awt.Frame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        javax.swing.JDialog dialog = new javax.swing.JDialog(
                parent,
                "Seleccionar trabajo",
                true
        );

        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_assignWorkButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton assignCustomerButton;
    private javax.swing.JButton assignTechnicianButton;
    private javax.swing.JButton assignWorkButton;
    private javax.swing.JTextField customerTextField;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JTable tableWork;
    private javax.swing.JTextField technicianTextField;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField workTextField;
    // End of variables declaration//GEN-END:variables
}
