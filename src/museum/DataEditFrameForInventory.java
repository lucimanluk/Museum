package museum;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class DataEditFrameForInventory extends JFrame implements ActionListener {

    private JLabel nameLabel = new JLabel("Name");
    private JTextField nameField = new JTextField(20);
    private JLabel descriptionLabel = new JLabel("Description");
    private JTextField descriptionField = new JTextField(20);
    private JLabel regionLabel = new JLabel("Region of origin");
    private JTextField regionField = new JTextField(20);
    private JLabel yearLabel = new JLabel("Year of production");
    private JTextField yearField = new JTextField(20);
    private JLabel roomLabel = new JLabel("Room placement");
    private JTextField roomField = new JTextField(20);
    private JButton editData = new JButton("Edit data");
    private JButton closeFrame = new JButton("Close");

    private JTable table;
    private DefaultTableModel model;
    private ManageInventory parentPanel;
    private Database db = Database.getInstance();

    public DataEditFrameForInventory(ManageInventory parentPanel, JTable table, DefaultTableModel model) {

        this.table = table;
        this.model = model;
        this.parentPanel = parentPanel;
        setLayout(new GridLayout(6, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        nameField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
        descriptionField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
        regionField.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
        yearField.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
        roomField.setText(table.getValueAt(table.getSelectedRow(), 4).toString());

        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionField);
        add(regionLabel);
        add(regionField);
        add(yearLabel);
        add(yearField);
        add(roomLabel);
        add(roomField);

        editData.addActionListener(this);
        closeFrame.addActionListener(this);
        add(editData);
        add(closeFrame);

        setSize(800, 300);
        setVisible(true);
    }

    public void updateExhibitionItemsTable(int id, String name, String description, String region, String year, String room) {
        String query = "UPDATE `exhibition items` SET "
                + "Name = '" + name + "', "
                + "Description = '" + description + "', "
                + "`Region of origin` = '" + region + "', "
                + "`Year of production` = '" + year + "', "
                + "`Room placement` = '" + room + "' "
                + "WHERE ID = " + id;
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data updated successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " - Problem to edit data");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editData) {
            int modelIndex = table.convertRowIndexToModel(table.getSelectedRow());
            int id = Integer.parseInt(model.getValueAt(modelIndex, 0).toString());
            String name = nameField.getText();
            String description = descriptionField.getText();
            String region = regionField.getText();
            String year = yearField.getText();
            String room = roomField.getText();

            updateExhibitionItemsTable(id, name, description, region, year, room);
            parentPanel.RefreshTableData();
            dispose();
        } else if (e.getSource() == closeFrame) {
            dispose();
        }
    }
}
