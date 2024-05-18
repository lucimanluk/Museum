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

    private JTable tabelManagementInvetory;
    private DefaultTableModel model;
    private JButton addDataButton = new JButton("Add data");
    private JButton deleteDataButton = new JButton("Delete data");
    private DataAddingFrameForInventory frame;

    private Item item;
    public ArrayList<Item> itemStoring = new ArrayList<Item>();
    private Database db = Database.getInstance();

    public ManageInventory() {

        this.setLayout(new FlowLayout());

        getExhibitionItemsData();
        data = getTableData();
        model = new DefaultTableModel(data, columnNames);
        tabelManagementInvetory = new JTable(model);
        tabelManagementInvetory.getTableHeader().setReorderingAllowed(false);
        this.add(new JScrollPane(tabelManagementInvetory));

        addDataButton.addActionListener(this);
        deleteDataButton.addActionListener(this);

        this.add(addDataButton);
        this.add(deleteDataButton);

        tabelManagementInvetory.removeColumn(tabelManagementInvetory.getColumnModel().getColumn(0));
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
        })
                .toArray(Object[][]::new);
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
        tabelManagementInvetory.removeColumn(tabelManagementInvetory.getColumnModel().getColumn(0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addDataButton) {
            frame = new DataAddingFrameForInventory(this);
        } else if (e.getSource() == deleteDataButton) {
            int[] selection = tabelManagementInvetory.getSelectedRows();
            if (selection.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected items?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selection.length - 1; i >= 0; i--) {
                        int viewIndex = selection[i];
                        int modelIndex = tabelManagementInvetory.convertRowIndexToModel(viewIndex);
                        int idToDelete = Integer.parseInt(model.getValueAt(modelIndex, 0).toString());
                        deleteFromExhibitionItemsTable(idToDelete);
                        model.removeRow(modelIndex);
                    }
                    getExhibitionItemsData();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        }

    }
}
