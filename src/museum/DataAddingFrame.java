package museum;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class DataAddingFrame extends JFrame implements ActionListener {

    private final JLabel nameLabel = new JLabel("Name");
    private JTextField nameField = new JTextField(20);
    private final JLabel descriptionLabel = new JLabel("Description");
    private JTextField descriptionField = new JTextField(20);
    private final JLabel regionLabel = new JLabel("Region of origin");
    private JTextField regionField = new JTextField(20);
    private final JLabel yearLabel = new JLabel("Year of production");
    private JTextField yearField = new JTextField(20);
    private final JLabel roomLabel = new JLabel("Room placement");
    private JTextField roomField = new JTextField(20);
    private final JButton addDataButton2 = new JButton("Add data");
    private final JButton closeFrame = new JButton("Close");
    private TableDataFetcher dataFetcher;
    private DefaultTableModel model;
    private String[] columnNames;
    private JTable tabelManagementInventory;
    private Database db;

    public DataAddingFrame(Database db, TableDataFetcher dataFetcher, DefaultTableModel model, String[] columnNames, JTable tabelManagementInventory) {
        this.db = db;
        this.dataFetcher = dataFetcher;
        this.model = model;
        this.columnNames = columnNames;
        this.tabelManagementInventory = tabelManagementInventory;
        setLayout(new GridLayout(6, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        addDataButton2.addActionListener(this);
        closeFrame.addActionListener(this);
        add(addDataButton2);
        add(closeFrame);

        setSize(800, 300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDataButton2) {
            if (!nameField.getText().isEmpty()
                    && !descriptionField.getText().isEmpty()
                    && !regionField.getText().isEmpty()
                    && !yearField.getText().isEmpty()
                    && !roomField.getText().isEmpty()) {
                db.insertData(nameField.getText(), descriptionField.getText(), regionField.getText(), Integer.parseInt(yearField.getText()), Integer.parseInt(roomField.getText()));
                db.view2();
                Object[][] newData = dataFetcher.fetchTableData();
                model.setDataVector(newData, columnNames);
                tabelManagementInventory.removeColumn(tabelManagementInventory.getColumnModel().getColumn(0));
            }
        } else if (e.getSource() == closeFrame) {
            dispose();
        }
    }
}
