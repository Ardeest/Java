package Panels;

import javax.swing.table.DefaultTableModel;
import Data.Models.Customer;
import Data.Services.CustomerService;
import Panels.MainPanel.DataUpdateListener;
import java.awt.Color;
import java.util.List;
import javax.swing.JTextField;
import Resources.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.AbstractDocument;

    public class ClientsPanel extends javax.swing.JPanel {

    private Map<JTextField, String> placeholders = new HashMap<>();
    private int selectedId = -1;
    private DataUpdateListener listener;

    private CustomerService customerService;

    // Constructor por defecto (requerido por el editor de diseño de NetBeans)
    public ClientsPanel() {
        this(new CustomerService());
    }

    // Constructor con inyección de dependencias (usado para tus tests)
    public ClientsPanel(CustomerService customerService) {
        this.customerService = customerService; // Asigna el servicio recibido
        initComponents();

        // Filtros de documentos
        ((AbstractDocument) phoneTextField.getDocument())
                .setDocumentFilter(new PhoneFilter());

        ((AbstractDocument) idTextField.getDocument())
                .setDocumentFilter(new IdCardFilter());

        // Configuración del listener de la tabla
        tableCustomer.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRow();
                boolean hasSelection = tableCustomer.getSelectedRow() != -1;
                updateButton.setEnabled(hasSelection);
                deleteButton.setEnabled(hasSelection);
                addButton.setEnabled(!hasSelection);
                cleanFieldsButton.setEnabled(!hasSelection);
            }
        });

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Inicialización de placeholders
        addPlaceholder(searchTextField, "Buscar");
        addPlaceholder(nameTextField, "Nombre");
        addPlaceholder(lastName1TextField, "Primer Apellido");
        addPlaceholder(lastName2TextField, "Segundo Apellido");
        addPlaceholder(addressTextField, "Dirección");

        loadTable();
    }
    
    public void setListener(DataUpdateListener listener) {
        this.listener = listener;
    }

    // ===================== LOAD TABLE =====================
    private void loadTable() {
        CustomerService service = new CustomerService();
        fillTable(service.getAll());
    }

    private void fillTable(List<Customer> list) {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Cédula");
        model.addColumn("Nombre");
        model.addColumn("Apellido 1");
        model.addColumn("Apellido 2");
        model.addColumn("Teléfono");
        model.addColumn("Dirección");

        for (Customer c : list) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getIdCard(),
                    c.getFirstName(),
                    c.getLastName1(),
                    c.getLastName2(),
                    c.getPhone(),
                    c.getAddress()
            });
        }

        tableCustomer.setModel(model);

        tableCustomer.getColumnModel().getColumn(0).setMinWidth(0);
        tableCustomer.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    // ===================== SELECT ROW =====================
    private void loadSelectedRow() {
        int row = tableCustomer.getSelectedRow();
        if (row == -1) return;

        selectedId = Integer.parseInt(tableCustomer.getValueAt(row, 0).toString());

        idTextField.setText(tableCustomer.getValueAt(row, 1).toString());
        nameTextField.setText(tableCustomer.getValueAt(row, 2).toString());
        lastName1TextField.setText(tableCustomer.getValueAt(row, 3).toString());
        lastName2TextField.setText(tableCustomer.getValueAt(row, 4).toString());
        phoneTextField.setText(tableCustomer.getValueAt(row, 5).toString());
        addressTextField.setText(tableCustomer.getValueAt(row, 6).toString());

        setBlack();
    }

    // ===================== INSERT =====================
    private void insertCustomer() {
        Customer c = new Customer();

        c.setFirstName(getCleanText(nameTextField));
        c.setLastName1(getCleanText(lastName1TextField));
        c.setLastName2(getCleanText(lastName2TextField));
        c.setIdCard(cleanNumber(idTextField.getText()));
        c.setPhone(cleanNumber(phoneTextField.getText()));
        c.setAddress(getCleanText(addressTextField));

        CustomerService service = new CustomerService();
        Result result = service.addCustomer(c);

        javax.swing.JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
        }
        
        if (this.listener != null) {
            this.listener.onUpdate();
        }
    }

    // ===================== UPDATE =====================
    private void updateCustomer() {
        if (selectedId == -1) return;

        Customer c = new Customer();
        c.setId(selectedId);
        c.setFirstName(getCleanText(nameTextField));
        c.setLastName1(getCleanText(lastName1TextField));
        c.setLastName2(getCleanText(lastName2TextField));
        c.setIdCard(cleanNumber(idTextField.getText()));
        c.setPhone(cleanNumber(phoneTextField.getText()));
        c.setAddress(getCleanText(addressTextField));

        CustomerService service = new CustomerService();
        Result result = service.update(c);

        javax.swing.JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
            selectedId = -1;
        }
        
        if (this.listener != null) {
            this.listener.onUpdate();
        }
    }

    // ===================== DELETE =====================
    private void deleteCustomer() {
        if (selectedId == -1) return;

        CustomerService service = new CustomerService();
        Result result = service.delete(selectedId);

        javax.swing.JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
            selectedId = -1;
        }
        
        if (this.listener != null) {
            this.listener.onUpdate();
        }
    }

    // ===================== SEARCH =====================
    private void searchCustomer() {
        String text = searchTextField.getText().trim();

        CustomerService service = new CustomerService();
        List<Customer> list;

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

    // ===================== CLEAR =====================
    private void clearFields() {
        for (Map.Entry<JTextField, String> e : placeholders.entrySet()) {
            e.getKey().setText(e.getValue());
            e.getKey().setForeground(Color.GRAY);
        }

        idTextField.setText("");
        phoneTextField.setText("");

        selectedId = -1;
        tableCustomer.clearSelection();
    }

    private void clearFieldsExceptSearch() {
        for (Map.Entry<JTextField, String> e : placeholders.entrySet()) {
            JTextField f = e.getKey();

            if (f == searchTextField) continue;

            f.setText(e.getValue());
            f.setForeground(Color.GRAY);
        }

        selectedId = -1;
        tableCustomer.clearSelection();
    }

    // ===================== PLACEHOLDER =====================
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

    // ===================== UTILS =====================
    private String getCleanText(JTextField field) {
        String p = placeholders.get(field);
        if (p != null && field.getText().equals(p)) return "";
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
        addressTextField.setForeground(Color.BLACK);
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
        tableCustomer = new javax.swing.JTable();
        addressTextField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        lastName1Label = new javax.swing.JLabel();
        lastName2Label = new javax.swing.JLabel();
        idCardLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        dummy = new javax.swing.JButton();
        addButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1000, 550));
        setPreferredSize(new java.awt.Dimension(1000, 550));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        searchTextField.setName("searchTextField"); // NOI18N
        searchTextField.addActionListener(this::searchTextFieldActionPerformed);

        searchButton.setText("Buscar");
        searchButton.setName("searchButton"); // NOI18N
        searchButton.addActionListener(this::searchButtonActionPerformed);

        nameTextField.setName("nameTextField"); // NOI18N
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });

        lastName1TextField.setName("lastName1TextField"); // NOI18N
        lastName1TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lastName1TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lastName1TextFieldFocusLost(evt);
            }
        });

        lastName2TextField.setName("lastName2TextField"); // NOI18N
        lastName2TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lastName2TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lastName2TextFieldFocusLost(evt);
            }
        });

        idTextField.setName("idTextField"); // NOI18N
        idTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                idTextFieldFocusLost(evt);
            }
        });

        updateButton.setText("Actualizar");
        updateButton.setName("updateButton"); // NOI18N
        updateButton.addActionListener(this::updateButtonActionPerformed);

        phoneTextField.setName("phoneTextField"); // NOI18N
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
        deleteButton.setName("deleteButton"); // NOI18N
        deleteButton.addActionListener(this::deleteButtonActionPerformed);

        cleanFieldsButton.setText("Borrar campos");
        cleanFieldsButton.setName("cleanFieldsButton"); // NOI18N
        cleanFieldsButton.addActionListener(this::cleanFieldsButtonActionPerformed);

        tableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cédula", "Nombre", "Primer Apellido", "Segundo Apellido", "Teléfono", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCustomer.setName("tableCustomer"); // NOI18N
        tableCustomer.setPreferredSize(new java.awt.Dimension(1000, 1000));
        tableCustomer.setShowGrid(false);
        jScrollPane2.setViewportView(tableCustomer);
        tableCustomer.getAccessibleContext().setAccessibleDescription("");

        addressTextField.setName("addressTextField"); // NOI18N
        addressTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addressTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                addressTextFieldFocusLost(evt);
            }
        });

        nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        nameLabel.setText("Nombre");

        lastName1Label.setForeground(new java.awt.Color(255, 255, 255));
        lastName1Label.setText("Primer Apellido");

        lastName2Label.setForeground(new java.awt.Color(255, 255, 255));
        lastName2Label.setText("Segundo Apellido");

        idCardLabel.setForeground(new java.awt.Color(255, 255, 255));
        idCardLabel.setText("Cédula");

        phoneLabel.setForeground(new java.awt.Color(255, 255, 255));
        phoneLabel.setText("Teléfono");

        addressLabel.setForeground(new java.awt.Color(255, 255, 255));
        addressLabel.setText("Dirección");

        dummy.setText("jButton1");
        dummy.setMaximumSize(new java.awt.Dimension(0, 0));
        dummy.setMinimumSize(new java.awt.Dimension(0, 0));
        dummy.setPreferredSize(new java.awt.Dimension(0, 0));

        addButton.setText("Agregar");
        addButton.setName("addButton"); // NOI18N
        addButton.addActionListener(this::addButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(idCardLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastName1Label)
                            .addComponent(phoneLabel))
                        .addGap(171, 171, 171))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lastName1TextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lastName2Label)
                        .addGap(0, 394, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lastName2TextField)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addressTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dummy, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(updateButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cleanFieldsButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(phoneTextField))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addressLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(lastName1Label)
                    .addComponent(lastName2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lastName1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lastName2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idCardLabel)
                    .addComponent(phoneLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dummy, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cleanFieldsButton)
                            .addComponent(deleteButton)
                            .addComponent(updateButton)
                            .addComponent(addButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchCustomer();
        clearFieldsExceptSearch();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        searchCustomer();
        clearFieldsExceptSearch();
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateCustomer();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void cleanFieldsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanFieldsButtonActionPerformed
        clearFields();
    }//GEN-LAST:event_cleanFieldsButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteCustomer();
        
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

    private void addressTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressTextFieldFocusGained
        addressLabel.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_addressTextFieldFocusGained

    private void addressTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressTextFieldFocusLost
        addressLabel.setForeground(Color.WHITE);
    }//GEN-LAST:event_addressTextFieldFocusLost

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        dummy.requestFocusInWindow();
        
        if (tableCustomer.getSelectedRow() != -1) {
            tableCustomer.clearSelection();
            clearFields();
        } else {

        }
    }//GEN-LAST:event_formMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        insertCustomer();
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
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JButton cleanFieldsButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton dummy;
    private javax.swing.JLabel idCardLabel;
    private javax.swing.JTextField idTextField;
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
    private javax.swing.JTable tableCustomer;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
