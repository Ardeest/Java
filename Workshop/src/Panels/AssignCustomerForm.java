package Panels;

import Data.Models.Customer;
import Data.Services.CustomerService;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AssignCustomerForm extends javax.swing.JPanel {

    private Map<JTextField, String> placeholders = new HashMap<>();

    private CustomerSelectionListener listener;

    public AssignCustomerForm(CustomerSelectionListener listener) {
        initComponents();
        this.listener = listener;

        addPlaceholder(searchTextField, "Buscar");

        tableCustomer.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                continueButton.setEnabled(tableCustomer.getSelectedRow() != -1);
            }
        });
        
        loadTable();
    }

    // ================= TABLE =================
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

    // ================= GET ID =================
    private int getSelectedCustomerId() {

        int row = tableCustomer.getSelectedRow();
        if (row == -1) return -1;

        return Integer.parseInt(tableCustomer.getValueAt(row, 0).toString());
    }

    // ================= INTERFACE =================
    public interface CustomerSelectionListener {
        void onCustomerSelected(int customerId);
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

        tableCustomer.clearSelection();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dummy = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        continueButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCustomer = new javax.swing.JTable();
        cancelButton = new javax.swing.JButton();

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
        jScrollPane3.setViewportView(tableCustomer);

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(continueButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(searchTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(continueButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        searchCustomer();

    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchCustomer();
        clearFields();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void continueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueButtonActionPerformed

        int id = getSelectedCustomerId();
        if (id == -1) return;

        if (listener != null) {
            listener.onCustomerSelected(id);
        }

        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_continueButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        dummy.requestFocusInWindow();
        
        if (tableCustomer.getSelectedRow() != -1) {
            tableCustomer.clearSelection();

        } else {

        }
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton continueButton;
    private javax.swing.JButton dummy;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable tableCustomer;
    // End of variables declaration//GEN-END:variables
}
