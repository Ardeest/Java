package Panels;

import javax.swing.table.DefaultTableModel;
import Data.Models.Technician;
import Data.Models.WorkDTO;
import Data.Services.TechnicianService;
import Data.Services.WorkService;
import java.awt.Color;
import java.util.List;
import javax.swing.JTextField;
import Resources.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;

public class TechniciansPanel extends javax.swing.JPanel {

    private Map<JTextField, String> placeholders = new HashMap<>();
    private int selectedId = -1;

    public TechniciansPanel() {
        initComponents();

        ((AbstractDocument) phoneTextField.getDocument())
                .setDocumentFilter(new PhoneFilter());

        ((AbstractDocument) idTextField.getDocument())
                .setDocumentFilter(new IdCardFilter());

        tableTechnician.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRow();

                boolean hasSelection = tableTechnician.getSelectedRow() != -1;

                updateButton.setEnabled(hasSelection);
                deleteButton.setEnabled(hasSelection);
                addButton.setEnabled(!hasSelection);
                cleanFieldsButton.setEnabled(!hasSelection);
            }
        });

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        addPlaceholder(searchTextField, "Buscar");
        addPlaceholder(nameTextField, "Nombre");
        addPlaceholder(lastName1TextField, "Primer Apellido");
        addPlaceholder(lastName2TextField, "Segundo Apellido");

        loadTable();
    }

    // ================= TABLE =================

    private void loadTable() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Cédula");
        model.addColumn("Nombre");
        model.addColumn("Apellido 1");
        model.addColumn("Apellido 2");
        model.addColumn("Teléfono");

        TechnicianService service = new TechnicianService();
        List<Technician> list = service.getAll();

        for (Technician t : list) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getIdCard(),
                    t.getFirstName(),
                    t.getLastName1(),
                    t.getLastName2(),
                    t.getPhone()
            });
        }

        tableTechnician.setModel(model);

        tableTechnician.getColumnModel().getColumn(0).setMinWidth(0);
        tableTechnician.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    private void loadTechnicianWorks() {

        if (selectedId == -1) return;

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Cliente");
        model.addColumn("Fecha");
        model.addColumn("Estado");
        model.addColumn("Descripción");

        WorkService service = new WorkService();
        List<WorkDTO> list = service.getByTechnicianWithDetails(selectedId);

        for (WorkDTO w : list) {
            model.addRow(new Object[]{
                    w.getCustomerName(),
                    w.getDate(),
                    w.getStatus(),
                    w.getDescription()
            });
        }

        tableWorks.setModel(model);
    }

    private void loadSelectedRow() {
        int row = tableTechnician.getSelectedRow();
        if (row == -1) return;

        selectedId = Integer.parseInt(tableTechnician.getValueAt(row, 0).toString());

        idTextField.setText(tableTechnician.getValueAt(row, 1).toString());
        nameTextField.setText(tableTechnician.getValueAt(row, 2).toString());
        lastName1TextField.setText(tableTechnician.getValueAt(row, 3).toString());
        lastName2TextField.setText(tableTechnician.getValueAt(row, 4).toString());
        phoneTextField.setText(tableTechnician.getValueAt(row, 5).toString());

        setBlack();
    }

    // ================= CRUD =================

    private void insertTechnician() {

        Technician t = new Technician();
        t.setFirstName(getCleanText(nameTextField));
        t.setLastName1(getCleanText(lastName1TextField));
        t.setLastName2(getCleanText(lastName2TextField));
        t.setIdCard(cleanNumber(idTextField.getText()));
        t.setPhone(cleanNumber(phoneTextField.getText()));

        TechnicianService service = new TechnicianService();
        Result result = service.addTechnician(t);

        JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
        }
    }

    private void updateTechnician() {

        if (selectedId == -1) return;

        Technician t = new Technician();
        t.setId(selectedId);
        t.setFirstName(getCleanText(nameTextField));
        t.setLastName1(getCleanText(lastName1TextField));
        t.setLastName2(getCleanText(lastName2TextField));
        t.setIdCard(cleanNumber(idTextField.getText()));
        t.setPhone(cleanNumber(phoneTextField.getText()));

        TechnicianService service = new TechnicianService();
        Result result = service.update(t);

        JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
            selectedId = -1;
        }
    }

    private void deleteTechnician() {

        if (selectedId == -1) return;

        TechnicianService service = new TechnicianService();
        Result result = service.delete(selectedId);

        JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
            selectedId = -1;
        }
    }

    // ================= SEARCH =================

    private void searchTechnician() {

        String text = searchTextField.getText().trim();

        TechnicianService service = new TechnicianService();
        List<Technician> list;

        if (text.isBlank()) {
            list = service.getAll();
        } else {
            list = service.search(text);

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados");
                list = service.getAll();
            }
        }

        fillTable(list);
    }

    private void fillTable(List<Technician> list) {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Cédula");
        model.addColumn("Nombre");
        
        model.addColumn("Primer Apellido");
        model.addColumn("Segundo Apellido");
        model.addColumn("Teléfono");

        for (Technician t : list) {
            model.addRow(new Object[]{
                    t.getIdCard(),
                    t.getFirstName(),
                    t.getLastName1(),
                    t.getLastName2(),
                    t.getPhone()
            });
        }

        tableTechnician.setModel(model);
    }

    // ================= HELPERS =================

    private void clearFields() {
        for (Map.Entry<JTextField, String> e : placeholders.entrySet()) {
            e.getKey().setText(e.getValue());
            e.getKey().setForeground(Color.GRAY);
        }

        idTextField.setText("");
        phoneTextField.setText("");

        selectedId = -1;
        tableTechnician.clearSelection();
    }

    private void clearFieldsExceptSearch() {
        for (Map.Entry<JTextField, String> e : placeholders.entrySet()) {
            JTextField f = e.getKey();

            if (f == searchTextField) continue;

            f.setText(e.getValue());
            f.setForeground(Color.GRAY);
        }

        selectedId = -1;
        tableTechnician.clearSelection();
    }

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

    private String getCleanText(JTextField field) {
        String placeholder = placeholders.get(field);
        if (placeholder != null && field.getText().equals(placeholder)) return "";
        return field.getText();
    }

    private String cleanNumber(String value) {
        if (value == null) return "";
        return value.replaceAll("\\D", "");
    }

    private void setBlack() {
        nameTextField.setForeground(Color.BLACK);
        lastName1TextField.setForeground(Color.BLACK);
        lastName2TextField.setForeground(Color.BLACK);
        phoneTextField.setForeground(Color.BLACK);
        idTextField.setForeground(Color.BLACK);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        nameTextField = new javax.swing.JTextField();
        lastName1TextField = new javax.swing.JTextField();
        lastName2TextField = new javax.swing.JTextField();
        idTextField = new javax.swing.JTextField();
        updateButton = new javax.swing.JButton();
        phoneTextField = new javax.swing.JFormattedTextField();
        deleteButton = new javax.swing.JButton();
        cleanFieldsButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTechnician = new javax.swing.JTable();
        nameLabel = new javax.swing.JLabel();
        lastName1Label = new javax.swing.JLabel();
        lastName2Label = new javax.swing.JLabel();
        idCardLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        dummy = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableWorks = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "TECNICOS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 153, 153))); // NOI18N
        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(100, 652));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        searchButton.setText("Buscar");
        searchButton.addActionListener(this::searchButtonActionPerformed);

        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });

        lastName1TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lastName1TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lastName1TextFieldFocusLost(evt);
            }
        });

        lastName2TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lastName2TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lastName2TextFieldFocusLost(evt);
            }
        });

        idTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                idTextFieldFocusLost(evt);
            }
        });

        updateButton.setText("Actualizar");
        updateButton.addActionListener(this::updateButtonActionPerformed);

        phoneTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                phoneTextFieldFocusLost(evt);
            }
        });
        phoneTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                phoneTextFieldKeyTyped(evt);
            }
        });

        deleteButton.setText("Eliminar");
        deleteButton.addActionListener(this::deleteButtonActionPerformed);

        cleanFieldsButton.setText("Borrar campos");
        cleanFieldsButton.addActionListener(this::cleanFieldsButtonActionPerformed);

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
        tableTechnician.getAccessibleContext().setAccessibleDescription("");

        nameLabel.setForeground(new java.awt.Color(0, 51, 204));
        nameLabel.setText("Nombre");

        lastName1Label.setForeground(new java.awt.Color(0, 51, 204));
        lastName1Label.setText("Primer Apellido");

        lastName2Label.setForeground(new java.awt.Color(0, 51, 204));
        lastName2Label.setText("Segundo Apellido");

        idCardLabel.setForeground(new java.awt.Color(0, 51, 204));
        idCardLabel.setText("Cédula");

        phoneLabel.setForeground(new java.awt.Color(0, 51, 204));
        phoneLabel.setText("Teléfono");

        dummy.setText("jButton1");
        dummy.setMaximumSize(new java.awt.Dimension(0, 0));
        dummy.setMinimumSize(new java.awt.Dimension(0, 0));
        dummy.setPreferredSize(new java.awt.Dimension(0, 0));

        addButton.setText("Agregar");
        addButton.addActionListener(this::addButtonActionPerformed);

        tableWorks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cliente", "Fecha", "Estatus", "Descripción"
            }
        ));
        tableWorks.setEnabled(false);
        tableWorks.setFocusable(false);
        jScrollPane1.setViewportView(tableWorks);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastName1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastName1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lastName2Label)
                        .addGap(0, 131, Short.MAX_VALUE))
                    .addComponent(lastName2TextField)))
            .addGroup(layout.createSequentialGroup()
                .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneTextField))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(phoneLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 731, Short.MAX_VALUE)
                        .addComponent(dummy, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(idCardLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cleanFieldsButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lastName2Label)
                        .addComponent(lastName1Label)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastName1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastName2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(phoneLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addButton)
                            .addComponent(updateButton)
                            .addComponent(deleteButton)
                            .addComponent(cleanFieldsButton))
                        .addGap(35, 35, 35)
                        .addComponent(idCardLabel)
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(dummy, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchTechnician();
        clearFieldsExceptSearch();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateTechnician();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void cleanFieldsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanFieldsButtonActionPerformed
        clearFields();
    }//GEN-LAST:event_cleanFieldsButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteTechnician();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusGained
        nameLabel.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_nameTextFieldFocusGained

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        nameLabel.setForeground(Color.WHITE);
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void lastName1TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lastName1TextFieldFocusGained
        lastName1Label.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lastName1TextFieldFocusGained

    private void lastName1TextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lastName1TextFieldFocusLost
        lastName1Label.setForeground(Color.WHITE); 
    }//GEN-LAST:event_lastName1TextFieldFocusLost

    private void lastName2TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lastName2TextFieldFocusGained
        lastName2Label.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lastName2TextFieldFocusGained

    private void lastName2TextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lastName2TextFieldFocusLost
        lastName2Label.setForeground(Color.WHITE);
    }//GEN-LAST:event_lastName2TextFieldFocusLost

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained

    }//GEN-LAST:event_formFocusGained

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        dummy.requestFocusInWindow();
        
        if (tableTechnician.getSelectedRow() != -1) {
            tableTechnician.clearSelection();
            clearFields();
        } else {

        }
    }//GEN-LAST:event_formMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        insertTechnician();
    }//GEN-LAST:event_addButtonActionPerformed

    private void idTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idTextFieldFocusLost
        idCardLabel.setForeground(Color.WHITE);
    }//GEN-LAST:event_idTextFieldFocusLost

    private void idTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idTextFieldFocusGained
        idCardLabel.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_idTextFieldFocusGained

    private void phoneTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phoneTextFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneTextFieldKeyTyped

    private void phoneTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneTextFieldFocusLost
        phoneLabel.setForeground(Color.WHITE);
    }//GEN-LAST:event_phoneTextFieldFocusLost

    private void phoneTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneTextFieldFocusGained
        phoneLabel.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_phoneTextFieldFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cleanFieldsButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton dummy;
    private javax.swing.JLabel idCardLabel;
    private javax.swing.JTextField idTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lastName1Label;
    private javax.swing.JTextField lastName1TextField;
    private javax.swing.JLabel lastName2Label;
    private javax.swing.JTextField lastName2TextField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField phoneTextField;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable tableTechnician;
    private javax.swing.JTable tableWorks;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
