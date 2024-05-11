package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageInventory extends JPanel implements ActionListener {

    public JTable tabelManagementInvetory;
    public DefaultTableModel model;
    private final JButton addDataButton = new JButton("Add data");
    private final JButton deleteDataButton = new JButton("Delete data");
    private DataAddingFrame frame;
    public Object[][] data;
    public final String[] columnNames = {"ID", "Name", "Description", "Region of origin", "Year of production", "Room placement"};
    private Database db;

    //constructor
    public ManageInventory(Database db) {
        this.db = db;
        data = getTableData();
        model = new DefaultTableModel(data, columnNames);
        tabelManagementInvetory = new JTable(model);
        tabelManagementInvetory.getTableHeader().setReorderingAllowed(false);
        this.setLayout(new FlowLayout());
        this.add(new JScrollPane(tabelManagementInvetory));
        addDataButton.addActionListener(this);
        deleteDataButton.addActionListener(this);
        this.add(addDataButton);
        this.add(deleteDataButton);
        tabelManagementInvetory.removeColumn(tabelManagementInvetory.getColumnModel().getColumn(0));
    }

    /*public Object[][] getTableData() {
        // Using Stream API to transform the List to Object[][]
        return db.itemStoring.stream()
            .map(item -> new Object[] {
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getRegionOfOrigin(),
                item.getYear(),
                item.getRoom()
            })
            .toArray(Object[][]::new); // Collecting the results into a 2D array
    }*/
    //metoda de apelare a bazei de date
    public Object[][] getTableData() {
        int size = db.itemStoring.size();
        data = new Object[size][6];
        for (int i = 0; i < size; i++) {
            data[i][0] = db.itemStoring.get(i).getId();
            data[i][1] = db.itemStoring.get(i).getName();
            data[i][2] = db.itemStoring.get(i).getDescription();
            data[i][3] = db.itemStoring.get(i).getRegionOfOrigin();
            data[i][4] = db.itemStoring.get(i).getYear();
            data[i][5] = db.itemStoring.get(i).getRoom();
        }
        return data;
    }

    //metoda pentru a adauga sau a scoate data din tabel
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addDataButton) {
            frame = new DataAddingFrame(db);
        } else if (e.getSource() == deleteDataButton) {
            int[] selection = tabelManagementInvetory.getSelectedRows();
            if (selection.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected items?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selection.length - 1; i >= 0; i--) {
                        int viewIndex = selection[i];
                        int modelIndex = tabelManagementInvetory.convertRowIndexToModel(viewIndex);
                        int idToDelete = Integer.parseInt(model.getValueAt(modelIndex, 0).toString());
                        db.deleteData(idToDelete);
                        model.removeRow(modelIndex);
                    }
                    db.view2();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete");
            }
        }

    }
}
