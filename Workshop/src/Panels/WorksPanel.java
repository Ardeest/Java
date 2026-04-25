package Panels;

import Data.Models.Work;
import Data.Models.WorkDTO;
import Data.Services.WorkService;
import Resources.Result;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class WorksPanel extends javax.swing.JPanel {
    
    private MainPanel.DataUpdateListener listener;
    private WorkService workService; 
    private int selectedId = -1;


    public WorksPanel() {
        this(new WorkService()); 
    }

    public WorksPanel(WorkService workService) {
        initComponents(); 
        this.workService = workService;

        tableWork.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = tableWork.getSelectedRow() != -1;
                updateButton.setEnabled(hasSelection);
                deleteButton.setEnabled(hasSelection);
            }
        });

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        loadTable();
    }
    
    public void refreshData() {
        loadTable();
    }
    
    public void setListener(MainPanel.DataUpdateListener listener) {
        this.listener = listener;
    }
    
    private void loadTable() {
        List<WorkDTO> allWorks = workService.search("");
        fillTableDTO(allWorks);
    }

    private void fillTable(List<Work> list) {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Técnico ID");
        model.addColumn("Cliente ID");
        model.addColumn("Descripción");
        model.addColumn("Estado");
        model.addColumn("Fecha");

        for (Work w : list) {
            model.addRow(new Object[]{
                w.getId(),
                w.getTechnicianId(),
                w.getCustomerId(),
                w.getDescription(),
                w.getStatus(),
                w.getDateTime()
            });
        }

        tableWork.setModel(model);

        // Ocultar columna ID
        tableWork.getColumnModel().getColumn(0).setMinWidth(0);
        tableWork.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    // ===================== SELECT ROW =====================
    private void loadSelectedRow() {
        int row = tableWork.getSelectedRow();
        if (row == -1) return;

        selectedId = Integer.parseInt(tableWork.getValueAt(row, 0).toString());
    }

    // ===================== DELETE =====================
    private void deleteWork() {
        if (selectedId == -1) return;

        WorkService service = new WorkService();
        Result result = service.delete(selectedId);

        JOptionPane.showMessageDialog(this, result.getMessage());

        if (result.isSuccess()) {
            loadTable();
            selectedId = -1;
        }
    }
    
    // ===================== SEARCH =====================
    private void searchWork() {
        String text = searchTextField.getText().trim();

        if (text.isBlank()) {
            loadTable();
        } else {
            List<WorkDTO> results = workService.search(text);

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados");
                loadTable();
            } else {
                fillTableDTO(results);
            }
        }
    }
    
    private void fillTableDTO(List<WorkDTO> list) {
       DefaultTableModel model = new DefaultTableModel();

       model.addColumn("ID");
       model.addColumn("Técnico");
       model.addColumn("Cliente");
       model.addColumn("Descripción");
       model.addColumn("Estado");
       model.addColumn("Fecha");

       for (WorkDTO dto : list) {
           model.addRow(new Object[]{
               dto.getId(),
               dto.getTechnicianName(),
               dto.getCustomerName(),
               dto.getDescription(),
               dto.getStatus(),
               dto.getDateTime()
           });
       }

       tableWork.setModel(model);

       // Ocultar columna ID
       tableWork.getColumnModel().getColumn(0).setMinWidth(0);
       tableWork.getColumnModel().getColumn(0).setMaxWidth(0);
   }
    
     

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableWork = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1000, 550));
        setPreferredSize(new java.awt.Dimension(1000, 550));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        deleteButton.setText("Eliminar");
        deleteButton.setName("deleteButton"); // NOI18N
        deleteButton.addActionListener(this::deleteButtonActionPerformed);

        updateButton.setText("Actualizar");
        updateButton.setName("updateButton"); // NOI18N
        updateButton.addActionListener(this::updateButtonActionPerformed);

        addButton.setText("Agregar");
        addButton.setName("addButton"); // NOI18N
        addButton.addActionListener(this::addButtonActionPerformed);

        searchTextField.setName("searchTextField"); // NOI18N
        searchTextField.addActionListener(this::searchTextFieldActionPerformed);

        searchButton.setText("Buscar");
        searchButton.setName("searchButton"); // NOI18N
        searchButton.addActionListener(this::searchButtonActionPerformed);

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
        tableWork.setName("tableWork"); // NOI18N
        tableWork.setOpaque(false);
        jScrollPane1.setViewportView(tableWork);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(updateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(updateButton)
                    .addComponent(deleteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        searchWork();
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchWork();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        WorkDetailsForm form = new WorkDetailsForm(null);
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) null, "Nuevo Trabajo", true);
        dialog.setContentPane(form);
        dialog.pack();
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (form.isConfirmed()) {
            loadTable();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        loadSelectedRow();

        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un trabajo");
            return;
        }

        Work work = workService.getById(selectedId);
        if (work == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el trabajo");
            return;
        }

        WorkDetailsForm form = new WorkDetailsForm(work);

        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) null, "Editar Trabajo", true);
        dialog.setContentPane(form);
        dialog.pack();
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (form.isConfirmed()) {
            loadTable();
            selectedId = -1;
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        loadSelectedRow();
        deleteWork();
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable tableWork;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
