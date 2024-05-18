package museum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class SoldTickets extends JPanel {

    private final String[] columnNames = {"ID", "Tickets sold", "Total price", "Group discount", "Payment type"};

    private JTable ordersTable;
    private DefaultTableModel model;
    private Object[][] data;
    private Database db = Database.getInstance();

    public SoldTickets() {
        data = getTableData();
        model = new DefaultTableModel(data, columnNames);
        ordersTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        this.add(scrollPane);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                db.view4();
                data = getTableData();
                model.setDataVector(data, columnNames);
                ordersTable.removeColumn(ordersTable.getColumnModel().getColumn(0));
            }
        });
        ordersTable.removeColumn(ordersTable.getColumnModel().getColumn(0));
    }

    public Object[][] getTableData() {
        return db.orderStoring.stream().map(item -> new Object[]{
            item.getID(),
            item.getTicketsSold(),
            item.getTotalPrice(),
            item.getGroupDiscount(),
            item.getPaymentType()
        })
                .toArray(Object[][]::new);
    }
}
