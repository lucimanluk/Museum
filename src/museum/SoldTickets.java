/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SoldTickets extends JPanel{
    private JTable ordersTable;
    private DefaultTableModel model;
    private Object[][] data;
    private final String[] columnNames = {"ID", "Tickets sold", "Total price", "Group discount", "Payment type"};
    private Database db = Database.getInstance();
    
    public SoldTickets() {
        model = new DefaultTableModel(data, columnNames);
        ordersTable = new JTable(model);
        this.add(new JScrollPane(ordersTable));
        ordersTable.removeColumn(ordersTable.getColumnModel().getColumn(0));
    }
}
