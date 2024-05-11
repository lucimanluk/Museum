package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class SellTickets extends JPanel implements ActionListener {

    private JTable ticketTable;
    private DefaultTableModel model;
    private final JButton addDataButton = new JButton("Add data");
    private final JButton deleteDataButton = new JButton("Delete data");
    private DataAddingFrame2 frame;
    private Database db;
    public Object[][] data;
    public final String[] columnNames = {"ID", "Ticket type", "Ticket description", "Ticket price"};

    public SellTickets(Database db) {
        this.db = db;
        data = getTableData();
        model = new DefaultTableModel(data, columnNames);
        ticketTable = new JTable(model);
        ticketTable.getTableHeader().setReorderingAllowed(false);
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(ticketTable), BorderLayout.CENTER);
        addDataButton.addActionListener(this);
        deleteDataButton.addActionListener(this);
        this.add(addDataButton, BorderLayout.SOUTH);
        this.add(deleteDataButton, BorderLayout.EAST);
        ticketTable.removeColumn(ticketTable.getColumnModel().getColumn(0));
    }

    public Object[][] getTableData() {
        int size = db.ticketStoring.size();
        data = new Object[size][4];
        for (int i = 0; i < size; i++) {
            data[i][0] = db.ticketStoring.get(i).getId();
            data[i][1] = db.ticketStoring.get(i).getTicketType();
            data[i][2] = db.ticketStoring.get(i).getTicketDescription();
            data[i][3] = db.ticketStoring.get(i).getTicketPrice();
        }
        return data;
    }
    
    public void resetFrame() {
    this.frame = null;
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDataButton) {
        if(frame == null)
            this.frame = new DataAddingFrame2(db, this::getTableData, model, columnNames, ticketTable, this);
        } else if (e.getSource() == deleteDataButton) {
            int[] selection = ticketTable.getSelectedRows();
            if (selection.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected items?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selection.length - 1; i >= 0; i--) {
                        int viewIndex = selection[i];
                        int modelIndex = ticketTable.convertRowIndexToModel(viewIndex);
                        int idToDelete = Integer.parseInt(model.getValueAt(modelIndex, 0).toString());
                        db.deleteData2(idToDelete);
                        model.removeRow(modelIndex);
                    }
                    db.view();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete");
            }
        }
    }
}
