package museum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

public class SoldTickets extends JPanel implements ActionListener{

    private final String[] columnNames = {"ID", "Tickets sold", "Total price", "Group discount", "Payment type"};
    private final String[] columnNames2 = {"ID", "Order ID", "Hours", "Discount type", "Ticket type", "Photo tax", "Video tax", "Price", "Payment type"};

    private int nrTicketsSold = 0;
    private double totalIncome = 0;
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
    String currentDateFormatted = dateFormat.format(currentDate);

    private Object[][] data;
    private Object[][] data2;

    private JLabel nrTicketsSoldLabel = new JLabel("Number of tickets sold: " + nrTicketsSold);
    private JLabel totalIncomeLabel = new JLabel("Income from sold tickets: " + totalIncome);
    private JButton deleteButton = new JButton("Delete data");
    private JTable ordersTable;
    private DefaultTableModel model;
    private JTable ticketsSoldTable;
    private DefaultTableModel model2;

    private Order order;
    private SoldTicket soldTicket;
    private ArrayList<Order> orderStoring = new ArrayList<Order>();
    private ArrayList<SoldTicket> soldTicketsStoring = new ArrayList<SoldTicket>();
    private Database db = Database.getInstance();

    public SoldTickets() {
        model = new DefaultTableModel(data, columnNames);
        ordersTable = new JTable(model);
        model2 = new DefaultTableModel(data2, columnNames2);
        ticketsSoldTable = new JTable(model2);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel tablePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Orders"));
        tablePanel.add(new JScrollPane(ordersTable));
        tablePanel.add(new JScrollPane(ticketsSoldTable));
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.SOUTH);

        JPanel statisticsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        statisticsPanel.add(nrTicketsSoldLabel);
        statisticsPanel.add(totalIncomeLabel);

        this.add(panel, BorderLayout.CENTER);
        this.add(statisticsPanel, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                getOrderData();
                data = getTableData();
                model.setDataVector(data, columnNames);
                getSoldTicketsData();
                data2 = getTableData2();
                model2.setDataVector(data2, columnNames2);
                ticketsSoldTable.removeColumn(ticketsSoldTable.getColumnModel().getColumn(0));
                nrTicketsSold = 0;
                totalIncome = 0;
                getIncomeAndSoldTickets();
                nrTicketsSoldLabel.setText("Number of tickets sold in " + currentDateFormatted + ": " + nrTicketsSold);
                totalIncomeLabel.setText("Total income in " + currentDateFormatted + ": " + totalIncome);
            }
        });
        deleteButton.addActionListener(this);
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

    public Object[][] getTableData2() {
        return soldTicketsStoring.stream().map(item -> new Object[]{
            item.getId(),
            item.getOrderId(),
            item.getHours(),
            item.getDiscountType(),
            item.getTicketType(),
            item.getPhotoTax(),
            item.getVideoTax(),
            item.getPrice(),
            item.getPaymentType()
        }).toArray(Object[][]::new);
    }

    public void getIncomeAndSoldTickets() {
        for (int i = 0; i < orderStoring.size(); i++) {
            nrTicketsSold = nrTicketsSold + orderStoring.get(i).getTicketsSold();
            totalIncome = totalIncome + orderStoring.get(i).getTotalPrice();
        }
    }

    public void refreshTableData() {
        getOrderData();
        Object[][] newData = getTableData();
        model.setDataVector(newData, columnNames);

        getSoldTicketsData();
        Object[][] newData2 = getTableData2();
        model2.setDataVector(newData2, columnNames2);
        ticketsSoldTable.removeColumn(ticketsSoldTable.getColumnModel().getColumn(0));
    }

    public void getOrderData() {
        orderStoring.clear();
        try {
            String insertquery = "SELECT * FROM `orders`";
            ResultSet result = db.getStatement().executeQuery(insertquery);
            while (result.next()) {
                order = new Order(result.getInt(1), result.getInt(2), result.getDouble(3), result.getString(4), result.getString(5));
                orderStoring.add(order);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void getSoldTicketsData() {
        soldTicketsStoring.clear();
        try {
            int i = 0;
            String query = "SELECT * FROM `ticket sales`";
            ResultSet result = db.getStatement().executeQuery(query);
            while (result.next()) {
                soldTicket = new SoldTicket(result.getInt(1), result.getInt(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getDouble(8), result.getString(9));
                soldTicketsStoring.add(soldTicket);
            }
        } catch (SQLException ex) {
            System.out.println("Problem to get data");
        }
    }

    public void deleteOrders(int id) {
        String query = "DELETE FROM `orders` WHERE "
                + "`ID` = " + id;
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error deleting data");
        }
    }

    public void deleteTicketsSold(int id) {
        String query = "DELETE FROM `ticket sales` WHERE "
                + "`Order ID` = " + id;
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error deleting data");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            int[] selection = ordersTable.getSelectedRows();
            if (selection.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected items?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selection.length - 1; i >= 0; i--) {
                        int viewIndex = selection[i];
                        int idToDelete = Integer.parseInt(model.getValueAt(viewIndex, 0).toString());
                        for (int j = ticketsSoldTable.getRowCount()-1; j >= 0; j--) {
                            int idComparer = Integer.parseInt(model2.getValueAt(j, 1).toString());
                            if (idComparer == idToDelete) {
                                nrTicketsSold = nrTicketsSold - 1;
                                double substractPrice = Double.parseDouble(model2.getValueAt(j,7).toString());
                                totalIncome =totalIncome - substractPrice;
                                deleteTicketsSold(idToDelete);
                            }
                        }
                        deleteOrders(idToDelete);
                        refreshTableData();
                    }                  
                nrTicketsSoldLabel.setText("Number of tickets sold in " + currentDateFormatted + ": " + nrTicketsSold);
                totalIncomeLabel.setText("Total income in " + currentDateFormatted + ": " + totalIncome);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item to delete.");
            }
        }
    }
}
