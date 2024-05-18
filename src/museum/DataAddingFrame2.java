/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class DataAddingFrame2 extends JFrame implements ActionListener {

    private final JLabel typeLabel = new JLabel("Ticket type");
    private JTextField typeField = new JTextField(20);
    private final JLabel descriptionLabel = new JLabel("Ticket description");
    private JTextField descriptionField = new JTextField(20);
    private final JLabel priceLabel = new JLabel("Ticket price");
    private JTextField priceField = new JTextField(20);
    private final JButton addDataButton2 = new JButton("Add data");
    private final JButton closeFrame = new JButton("Close");
    private SellTickets parentPanel;
    private Database db;
    private TableDataFetcher dataFetcher;
    private DefaultTableModel model;
    private String[] columnNames;
    private JTable tabelManagementInventory;

    public DataAddingFrame2(Database db, TableDataFetcher dataFetcher, DefaultTableModel model, String[] columnNames, JTable tabelManagementInventory, SellTickets parentPanel) {
        this.db = db;
        this.dataFetcher = dataFetcher;
        this.model = model;
        this.columnNames = columnNames;
        this.tabelManagementInventory = tabelManagementInventory;
        this.parentPanel = parentPanel;
        setLayout(new GridLayout(4, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
         addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            parentPanel.resetFrame();
        }
    });
        
        add(typeLabel);
        add(typeField);
        add(descriptionLabel);
        add(descriptionField);
        add(priceLabel);
        add(priceField);
        
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
            if (!typeField.getText().isEmpty()
                    && !descriptionField.getText().isEmpty()
                    && !priceField.getText().isEmpty()) {
                db.insertData2(typeField.getText(), descriptionField.getText(), Integer.parseInt(priceField.getText()));
                db.view();
                Object[][] newData = dataFetcher.fetchTableData();
                model.setDataVector(newData, columnNames);
                tabelManagementInventory.removeColumn(tabelManagementInventory.getColumnModel().getColumn(0));
            }
        } else if (e.getSource() == closeFrame) {
            dispose();
            parentPanel.resetFrame();
        }
    }
}
