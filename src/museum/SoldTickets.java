package museum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SoldTickets extends JPanel {

    private final String[] columnNames = {"ID", "Tickets sold", "Total price", "Group discount", "Payment type"};

    private JTable ordersTable;
    private DefaultTableModel model;
    private Object[][] data;
    private Order order;
    private ArrayList<Order> orderStoring = new ArrayList<Order>();
    private Database db = Database.getInstance();

    public SoldTickets() {
        model = new DefaultTableModel(data, columnNames);
        ordersTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        this.add(scrollPane);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                getOrderData();
                data = getTableData();
                model.setDataVector(data, columnNames);
                ordersTable.removeColumn(ordersTable.getColumnModel().getColumn(0));
            }
        });
        ordersTable.removeColumn(ordersTable.getColumnModel().getColumn(0));
    }

    public Object[][] getTableData() {
        return orderStoring.stream().map(item -> new Object[]{
            item.getID(),
            item.getTicketsSold(),
            item.getTotalPrice(),
            item.getGroupDiscount(),
            item.getPaymentType()
        }).toArray(Object[][]::new);
    }

    public void getOrderData() {
        orderStoring.clear();
        try {
            String insertquery = "SELECT * FROM `orders`";
            ResultSet result = db.getStatement().executeQuery(insertquery);
            while (result.next()) {
                order = new Order(Integer.parseInt(result.getString(1)), Integer.parseInt(result.getString(2)), Double.parseDouble(result.getString(3)), result.getString(4), result.getString(5));
                orderStoring.add(order);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }
}
