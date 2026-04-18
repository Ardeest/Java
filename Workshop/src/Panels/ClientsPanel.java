package Panels;

import javax.swing.table.DefaultTableModel;
import Data.Models.Customer;
import Data.Services.CustomerService;
import java.awt.Color;
import java.util.List;
import javax.swing.JTextField;
import Resources.Result;
import java.util.HashMap;
import java.util.Map;

public class ClientsPanel extends javax.swing.JPanel {
    
    private Map<JTextField, String> placeholders = new HashMap<>();
    private int selectedId = -1;

    public ClientsPanel() {      
        initComponents();
        
        tableCustomer.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRow();
            }
        });
        
        addPlaceholder(searchTextField, "Buscar");
        addPlaceholder(nameTextField, "Nombre");
        addPlaceholder(lastName1TextField, "Primer Apellido");
        addPlaceholder(lastName2TextField, "Segundo Apellido");
        addPlaceholder(idTextField, "Cédula");
        addPlaceholder(phoneTextField, "Teléfono");
        addPlaceholder(addressTextField, "Dirección");
        loadTable();
    }
    
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

        // quitar color gris (placeholder)
        nameTextField.setForeground(Color.BLACK);
        lastName1TextField.setForeground(Color.BLACK);
        lastName2TextField.setForeground(Color.BLACK);
        idTextField.setForeground(Color.BLACK);
        phoneTextField.setForeground(Color.BLACK);
        addressTextField.setForeground(Color.BLACK);
    }
    private void loadTable() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Cédula");
        model.addColumn("Nombre");
        model.addColumn("Primer Apellido");
        model.addColumn("Segundo Apellido");
        model.addColumn("Teléfono");
        model.addColumn("Dirección");

        CustomerService service = new CustomerService();
        List<Customer> list = service.getAll();

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
        tableCustomer.getColumnModel().getColumn(0).setWidth(0);
    }
     
    private void insertCustomer() {
        Customer c = new Customer();   
        c.setFirstName(getRealText(nameTextField));
        c.setLastName1(getRealText(lastName1TextField));
        c.setLastName2(getRealText(lastName2TextField));
        c.setIdCard(getRealText(idTextField));
        c.setPhone(getRealText(phoneTextField));
        c.setAddress(getRealText(addressTextField));

        CustomerService service = new CustomerService();
        Result result = service.addCustomer(c);

        javax.swing.JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
        }
    }

    private void clearFields() {
        for (Map.Entry<JTextField, String> entry : placeholders.entrySet()) {
            JTextField field = entry.getKey();
            String text = entry.getValue();

            field.setText(text);
            field.setForeground(Color.GRAY);
        }
    }
    
    private void addPlaceholder(JTextField field, String text) {

        placeholders.put(field, text);

        field.setText(text);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(text);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    private String getRealText(JTextField field) {
        String placeholder = placeholders.get(field);
        return field.getText().equals(placeholder) ? "" : field.getText();
    }
    
    private void searchCustomer() {

        String text = searchTextField.getText().trim();

        CustomerService service = new CustomerService();
        List<Customer> list = service.search(text);

        fillTable(list);
    }
    
    private void fillTable(List<Customer> list) {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Cédula");
        model.addColumn("Nombre");
        model.addColumn("Primer Apellido");
        model.addColumn("Segundo Apellido");
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
        tableCustomer.getColumnModel().getColumn(0).setWidth(0);
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
        phoneTextField = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();
        cleanFieldsButtons = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCustomer = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        addressTextField = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        searchTextField.addActionListener(this::searchTextFieldActionPerformed);

        searchButton.setText("Buscar");
        searchButton.addActionListener(this::searchButtonActionPerformed);

        updateButton.setText("Actualizar");
        updateButton.addActionListener(this::updateButtonActionPerformed);

        deleteButton.setText("Eliminar");
        deleteButton.addActionListener(this::deleteButtonActionPerformed);

        cleanFieldsButtons.setText("Borrar campos");
        cleanFieldsButtons.addActionListener(this::cleanFieldsButtonsActionPerformed);

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
        jScrollPane2.setViewportView(tableCustomer);

        addButton.setText("Agregar");
        addButton.addActionListener(this::addButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                    .addComponent(phoneTextField)
                    .addComponent(nameTextField)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton))
                    .addComponent(lastName1TextField)
                    .addComponent(lastName2TextField)
                    .addComponent(idTextField)
                    .addComponent(addressTextField)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cleanFieldsButtons)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchTextField)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastName1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastName2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(deleteButton)
                    .addComponent(addButton)
                    .addComponent(cleanFieldsButtons))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchCustomer();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        searchCustomer();
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        if (selectedId == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Desea actualizar este cliente?",
                "Confirmar",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

        Customer c = new Customer();
        c.setId(selectedId);
        c.setFirstName(getRealText(nameTextField));
        c.setLastName1(getRealText(lastName1TextField));
        c.setLastName2(getRealText(lastName2TextField));
        c.setIdCard(getRealText(idTextField));
        c.setPhone(getRealText(phoneTextField));
        c.setAddress(getRealText(addressTextField));

        CustomerService service = new CustomerService();
        Result result = service.update(c);

        javax.swing.JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
            selectedId = -1;
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void cleanFieldsButtonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanFieldsButtonsActionPerformed
        clearFields();
    }//GEN-LAST:event_cleanFieldsButtonsActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        insertCustomer();
        
    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (selectedId == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que desea eliminar este cliente?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

        CustomerService service = new CustomerService();
        Result result = service.delete(selectedId);

        javax.swing.JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            clearFields();
            selectedId = -1;
        }
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JButton cleanFieldsButtons;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField idTextField;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastName1TextField;
    private javax.swing.JTextField lastName2TextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField phoneTextField;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable tableCustomer;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
