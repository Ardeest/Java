package Panels;

import Data.Models.Customer;
import Data.Models.Technician;
import Data.Models.Work;
import Data.Models.WorkStatus;
import Data.Services.CustomerService;
import Data.Services.TechnicianService;
import Data.Services.WorkService;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class WorkDetailsForm extends javax.swing.JPanel {
    
    private Work work;
    private boolean confirmed = false;
    private int selectedTechnicianId = -1;
    private int selectedCustomerId = -1;
    
    public WorkDetailsForm(Work existingWork) {
        initComponents();
        // Verifica si es una edición (existingWork) o una creación (null)
        this.work = (existingWork != null) ? existingWork : new Work();
        setupComboBox();
        setupData();
    }
    
    private void setupComboBox() {
        // Limpia el combo
        jComboBox1.removeAllItems();

        // Carga el objeto Enum directamente, no el String
        for (WorkStatus status : WorkStatus.values()) {
            jComboBox1.addItem(status);
        }
    }
    
    private void setupData() {
        if (work.getId() != 0) {
            // MODO EDICIÓN
            selectedTechnicianId = work.getTechnicianId();
            selectedCustomerId = work.getCustomerId();

            Technician tech = new TechnicianService().getById(selectedTechnicianId);
            Customer cust = new CustomerService().getById(selectedCustomerId);

            technicianTextField.setText(tech != null ? tech.getFirstName() + " " + tech.getLastName1() : "");
            CustomerTextField.setText(cust != null ? cust.getFirstName() + " " + cust.getLastName1() : "");

            DescriptionTextField.setText(work.getDescription());

            // CORRECCIÓN: Manejo de fecha y hora
            if (work.getDateTime() != null) {
                dateTimePicker1.getDatePicker().setDate(work.getDateTime().toLocalDate());
                dateTimePicker1.getTimePicker().setTime(work.getDateTime().toLocalTime());
            }

            jComboBox1.setSelectedItem(work.getStatus());
        } else {
            // MODO CREACIÓN
            dateTimePicker1.getDatePicker().setDate(java.time.LocalDate.now());
            dateTimePicker1.getTimePicker().setTime(java.time.LocalTime.now());
            jComboBox1.setSelectedItem(WorkStatus.PENDING);
        }
    }
    
    // ==================== GETTERS ====================
    public boolean isConfirmed() {
        return confirmed;
    }

    public Work getWork() {
        return work;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        technicianTextField = new javax.swing.JTextField();
        AssignTechnicianButton = new javax.swing.JButton();
        CustomerTextField = new javax.swing.JTextField();
        AssignCustomerButton = new javax.swing.JButton();
        DescriptionTextField = new javax.swing.JTextField();
        dateTimePicker1 = new com.github.lgooddatepicker.components.DateTimePicker();
        jComboBox1 = new javax.swing.JComboBox<>();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        technicianTextField.setEnabled(false);
        technicianTextField.addActionListener(this::technicianTextFieldActionPerformed);

        AssignTechnicianButton.setText("Asignar Técnico");
        AssignTechnicianButton.addActionListener(this::AssignTechnicianButtonActionPerformed);

        CustomerTextField.setEnabled(false);

        AssignCustomerButton.setText("Asignar Cliente");
        AssignCustomerButton.addActionListener(this::AssignCustomerButtonActionPerformed);

        saveButton.setText("Guardar");
        saveButton.addActionListener(this::saveButtonActionPerformed);

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DescriptionTextField)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(CustomerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(technicianTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AssignTechnicianButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AssignCustomerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(dateTimePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(technicianTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AssignTechnicianButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CustomerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AssignCustomerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTimePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void technicianTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_technicianTextFieldActionPerformed

    }//GEN-LAST:event_technicianTextFieldActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        confirmed = false;
        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void AssignTechnicianButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AssignTechnicianButtonActionPerformed
    AssignTechnicianForm form = new AssignTechnicianForm(techId -> {
           selectedTechnicianId = techId;
           Technician t = new TechnicianService().getById(techId);
           if (t != null) {
               technicianTextField.setText(t.getFirstName() + " " + t.getLastName1());
           }
       });

       // Cambia la creación a 'null' para evitar errores de jerarquía
       javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) null, "Seleccionar Técnico", true);

       dialog.setContentPane(form);
       dialog.pack();
       dialog.setLocationRelativeTo(null); // Esto lo centra en la pantalla
       dialog.setVisible(true);
    }//GEN-LAST:event_AssignTechnicianButtonActionPerformed

    private void AssignCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AssignCustomerButtonActionPerformed
        AssignCustomerForm form = new AssignCustomerForm(custId -> {
            selectedCustomerId = custId;
            Customer c = new CustomerService().getById(custId);
            if (c != null) {
                CustomerTextField.setText(c.getFirstName() + " " + c.getLastName1());
            }
        });

        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) null, "Seleccionar Cliente", true);
        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(javax.swing.SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
    }//GEN-LAST:event_AssignCustomerButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

        // Validación de campos
        if (selectedTechnicianId <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un técnico");
            return;
        }
        if (selectedCustomerId <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente");
            return;
        }
        if (DescriptionTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una descripción");
            return;
        }

        // CORRECCIÓN: Obtener fecha y hora por separado
        var date = dateTimePicker1.getDatePicker().getDate();
        var time = dateTimePicker1.getTimePicker().getTime();

        if (date == null || time == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha y hora válidas");
            return;
        }

        // Llenar datos del trabajo
        work.setTechnicianId(selectedTechnicianId);
        work.setCustomerId(selectedCustomerId);
        work.setDescription(DescriptionTextField.getText().trim());
        work.setDateTime(java.time.LocalDateTime.of(date, time)); // Combinar fecha y hora
        work.setStatus((WorkStatus) jComboBox1.getSelectedItem());

        // Guardar en BD
        WorkService service = new WorkService();
        var result = work.getId() == 0 ? service.create(work) : service.update(work);

        if (result.isSuccess()) {
            confirmed = true;
            javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
        } else {
            JOptionPane.showMessageDialog(this, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_saveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AssignCustomerButton;
    private javax.swing.JButton AssignTechnicianButton;
    private javax.swing.JTextField CustomerTextField;
    private javax.swing.JTextField DescriptionTextField;
    private javax.swing.JButton cancelButton;
    private com.github.lgooddatepicker.components.DateTimePicker dateTimePicker1;
    private javax.swing.JComboBox<WorkStatus> jComboBox1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField technicianTextField;
    // End of variables declaration//GEN-END:variables
}
