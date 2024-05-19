package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageInventory extends JPanel implements ActionListener {

    private static final String[] columnNames = {"ID", "Name", "Description", "Region of origin", "Year of production", "Room placement"};

    private Object[][] data;

    private JTable tabelManagementInventory;
    private DefaultTableModel model;
    private JButton addDataButton = new JButton("Add data");
    private JButton deleteDataButton = new JButton("Delete data");
    private JButton editItemButton = new JButton("Edit item");

    private DataAddingFrameForInventory frame;
    private DataEditFrameForInventory frame2;
    private Item item;
    public ArrayList<Item> itemStoring = new ArrayList<Item>();
    private Database db = Database.getInstance();

    public ManageInventory(int admin) {

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Item management"));

        getExhibitionItemsData();
        data = getTableData();
        model = new DefaultTableModel(data, columnNames);
        tabelManagementInventory = new JTable(model);
        tabelManagementInventory.getTableHeader().setReorderingAllowed(false);
        this.add(new JScrollPane(tabelManagementInventory), BorderLayout.CENTER);

        if (admin == 1) {
            addDataButton.addActionListener(this);
            deleteDataButton.addActionListener(this);
            editItemButton.addActionListener(this);

            JPanel buttons = new JPanel();
            buttons.setLayout(new GridLayout(1, 3));
            
            buttons.add(addDataButton);
            buttons.add(deleteDataButton);
            buttons.add(editItemButton);
            this.add(buttons, BorderLayout.SOUTH);
        } else {
            tabelManagementInventory.setDefaultEditor(Object.class, null);
        }
        tabelManagementInventory.removeColumn(tabelManagementInventory.getColumnModel().getColumn(0));
    }

    public void resetFrameAdding() {
        this.frame = null;
    }

    public void resetFrameEdit() {
        this.frame2 = null;
    }

    public Object[][] getTableData() {
        return itemStoring.stream()
                .map(item -> new Object[]{
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getRegionOfOrigin(),
            item.getYear(),
            item.getRoom()
        }).toArray(Object[][]::new);
    }

    public void insertIntoExhibitionTable(String name, String description, String region, int year, int room) {
        String query = "INSERT INTO `exhibition items` (`Name`, `Description`, `Region of origin`, `Year of production`, `Room placement`) VALUES ('" + name + "', '" + description + "', '" + region + "', " + year + ", " + room + ")";
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }

    public void deleteFromExhibitionItemsTable(int id) {
        String query = "DELETE FROM `exhibition items` WHERE "
                + "`ID` = " + id;
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error deleting data");
        }
    }

    public void getExhibitionItemsData() {
        itemStoring.clear();
        try {
            String insertquery = "SELECT * FROM `exhibition items`";
            ResultSet result = db.getStatement().executeQuery(insertquery);
            while (result.next()) {
                item = new Item(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
                itemStoring.add(item);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void RefreshTableData() {
        getExhibitionItemsData();
        Object[][] newData = getTableData();
        model.setDataVector(newData, columnNames);
        tabelManagementInventory.removeColumn(tabelManagementInventory.getColumnModel().getColumn(0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addDataButton) {
            if (frame == null) {
                frame = new DataAddingFrameForInventory(this);
            }
        } else if (e.getSource() == deleteDataButton) {
            int[] selection = tabelManagementInventory.getSelectedRows();
            if (selection.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected items?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selection.length - 1; i >= 0; i--) {
                        int viewIndex = selection[i];
                        int modelIndex = tabelManagementInventory.convertRowIndexToModel(viewIndex);
                        int idToDelete = Integer.parseInt(model.getValueAt(modelIndex, 0).toString());
                        deleteFromExhibitionItemsTable(idToDelete);
                        model.removeRow(modelIndex);
                    }
                    getExhibitionItemsData();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        } else if (e.getSource() == editItemButton) {
            if (tabelManagementInventory.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Please select an item to edit");
            } else if (tabelManagementInventory.getSelectedRowCount() > 1) {
                JOptionPane.showMessageDialog(null, "You have selected too many items to edit.");
            } else {
                if (frame2 == null) {
                    JTable table = this.tabelManagementInventory;
                    DefaultTableModel model = this.model;
                    frame2 = new DataEditFrameForInventory(this, table, model);;
                }
            }
        }
    }
}
