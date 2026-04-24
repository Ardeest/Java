package Panels;

import Data.Models.Technician;
import Data.Services.TechnicianService;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AssignTechnicianForm extends javax.swing.JPanel {

    private Map<JTextField, String> placeholders = new HashMap<>();

    private TechnicianSelectionListener listener;

    public AssignTechnicianForm(TechnicianSelectionListener listener) {
        initComponents();
        this.listener = listener;

        addPlaceholder(searchTextField, "Buscar");

        tableTechnician.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                continueButton.setEnabled(tableTechnician.getSelectedRow() != -1);
            }
        });
        
        loadTable();
    }

    // ================= TABLE =================
    private void loadTable() {
        TechnicianService service = new TechnicianService();
        fillTable(service.getAll());
    }

    private void fillTable(List<Technician> list) {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Cédula");
        model.addColumn("Nombre");
        model.addColumn("Apellido 1");
        model.addColumn("Apellido 2");
        model.addColumn("Teléfono");

        for (Technician t : list) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getIdCard(),
                    t.getFirstName(),
                    t.getLastName1(),
                    t.getLastName2(),
                    t.getPhone(),
            });
        }

        tableTechnician.setModel(model);

        tableTechnician.getColumnModel().getColumn(0).setMinWidth(0);
        tableTechnician.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    // ===================== SEARCH =====================
    private void searchTechnician() {
        String text = searchTextField.getText().trim();

        TechnicianService service = new TechnicianService();
        List<Technician> list;

        if (text.isBlank()) {
            list = service.getAll();
        } else {
            list = service.search(text);

            if (list.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "No se encontraron resultados");
                list = service.getAll();
            }
        }

        fillTable(list);
    }

    // ================= GET ID =================
    private int getSelectedTechnicianId() {

        int row = tableTechnician.getSelectedRow();
        if (row == -1) return -1;

        return Integer.parseInt(tableTechnician.getValueAt(row, 0).toString());
    }

    // ================= INTERFACE =================
    public interface TechnicianSelectionListener {
        void onTechnicianSelected(int technicianId);
    }

    // ================= PLACEHOLDER =================
    private void addPlaceholder(JTextField field, String text) {

        placeholders.put(field, text);

        field.setText(text);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(text);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    private void clearFields() {
        for (Map.Entry<JTextField, String> e : placeholders.entrySet()) {
            e.getKey().setText(e.getValue());
            e.getKey().setForeground(Color.GRAY);
        }

        tableTechnician.clearSelection();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dummy = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        continueButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTechnician = new javax.swing.JTable();

        dummy.setText("jButton1");
        dummy.setMaximumSize(new java.awt.Dimension(0, 0));
        dummy.setMinimumSize(new java.awt.Dimension(0, 0));
        dummy.setPreferredSize(new java.awt.Dimension(0, 0));

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        searchTextField.addActionListener(this::searchTextFieldActionPerformed);

        searchButton.setText("Buscar");
        searchButton.addActionListener(this::searchButtonActionPerformed);

        continueButton.setText("Continuar");
        continueButton.addActionListener(this::continueButtonActionPerformed);

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        tableTechnician.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cédula", "Nombre", "Primer Apellido", "Segundo Apellido", "Teléfono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableTechnician);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
                        .addComponent(continueButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(searchTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(continueButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        searchTechnician();

    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchTechnician();
        clearFields();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void continueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueButtonActionPerformed

        int id = getSelectedTechnicianId();
        if (id == -1) return;

        if (listener != null) {
            listener.onTechnicianSelected(id);
        }

        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_continueButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        dummy.requestFocusInWindow();
        
        if (tableTechnician.getSelectedRow() != -1) {
            tableTechnician.clearSelection();

        } else {

        }
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton continueButton;
    private javax.swing.JButton dummy;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable tableTechnician;
    // End of variables declaration//GEN-END:variables
}
