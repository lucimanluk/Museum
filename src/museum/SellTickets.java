package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SellTickets extends JPanel implements ActionListener {

    private static final String[] columnNames = {"ID", "Ticket type", "Ticket description", "Ticket price"};
    private static final String[] columnNames2 = {"Time period", "Discount type", "Ticket type", "Photo tax", "Video tax", "Price"};
    private static final String[] options = {"No", "Yes"};
    private static final String[] paymentOptions = {"Cash", "Card"};

    private Object[][] data;
    private Object[][] data2;
    private int orderItemCount = 0;
    private double totalCost = 0;

    private JLabel ticketCountLabel = new JLabel("Enter number of tickets");
    private JTextField ticketCountTextField = new JTextField(20);
    private JRadioButton hour1 = new JRadioButton("9-17");
    private JRadioButton hour2 = new JRadioButton("17-20 (10% higher price)");
    private ButtonGroup hourGroup = new ButtonGroup();
    private JRadioButton student = new JRadioButton("Student (50% off)");
    private JRadioButton retiree = new JRadioButton("Retiree (50% off)");
    private JRadioButton veteran = new JRadioButton("Veteran (75% off)");
    private JRadioButton noDiscount = new JRadioButton("No discount");
    private ButtonGroup discountGroup = new ButtonGroup();
    private JLabel photoTaxLabel = new JLabel("Photography tax");
    private JComboBox comboBoxPhoto = new JComboBox(options);
    private JLabel videoTaxLabel = new JLabel("Videography tax");
    private JComboBox comboBoxVideo = new JComboBox(options);
    private JTable ticketTable;
    private JTable orderTable;
    private DefaultTableModel ticketTableModel;
    private DefaultTableModel orderTableModel;
    private JButton addDataButton = new JButton("Add data");
    private JButton deleteDataButton = new JButton("Delete data");
    private JComboBox comboBoxPayment = new JComboBox(paymentOptions);
    private JLabel cartTotalItems = new JLabel("Number of tickets: " + orderItemCount + " (group sale not applied)");
    private JLabel totalCostLabel = new JLabel("Price:" + totalCost);
    private JButton addTicketsButton = new JButton("Add to order");
    private JButton deleteTicketsButton = new JButton("Delete items");
    private JButton placeOrderButton = new JButton("Place order");
    private DataAddingFrameForTickets frame;

    private Ticket ticket;
    private OrderItem orderItem;
    public ArrayList<Ticket> ticketStoring = new ArrayList<Ticket>();
    private ArrayList<OrderItem> orderStoring = new ArrayList<OrderItem>();
    private Database db = Database.getInstance();

    public SellTickets() {

        //Panel layout.
        this.setLayout(new BorderLayout());

        //Setting commands for buttons.
        hour1.setActionCommand("9-17");
        hour2.setActionCommand("17-20");
        student.setActionCommand("student");
        retiree.setActionCommand("retiree");
        veteran.setActionCommand("veteran");
        noDiscount.setActionCommand("no discount");

        //Adding buttons to their respective groups.
        hourGroup.add(hour1);
        hourGroup.add(hour2);
        discountGroup.add(student);
        discountGroup.add(retiree);
        discountGroup.add(veteran);
        discountGroup.add(noDiscount);

        //Creating the table with ticket types and populating it.
        getTicketTypeData();
        data = getTableData();
        ticketTableModel = new DefaultTableModel(data, columnNames);
        ticketTable = new JTable(ticketTableModel);
        ticketTable.getTableHeader().setReorderingAllowed(false);

        //Creating the table which holds the order items.
        orderTableModel = new DefaultTableModel(data2, columnNames2);
        orderTable = new JTable(orderTableModel);

        //Adding action listeners to buttons.
        addDataButton.addActionListener(this);
        deleteDataButton.addActionListener(this);
        addTicketsButton.addActionListener(this);
        deleteTicketsButton.addActionListener(this);
        placeOrderButton.addActionListener(this);

        //Creating the panel which holds the order customisation options.
        JPanel orderSpecifications = new JPanel();
        orderSpecifications.setLayout(new GridBagLayout());
        orderSpecifications.add(ticketCountLabel, createGridBagConstraints(0, 0));
        orderSpecifications.add(ticketCountTextField, createGridBagConstraints(1, 0));
        orderSpecifications.add(hour1, createGridBagConstraints(0, 1));
        orderSpecifications.add(hour2, createGridBagConstraints(1, 1));
        orderSpecifications.add(student, createGridBagConstraints(0, 2));
        orderSpecifications.add(retiree, createGridBagConstraints(1, 2));
        orderSpecifications.add(veteran, createGridBagConstraints(0, 3));
        orderSpecifications.add(noDiscount, createGridBagConstraints(1, 3));
        orderSpecifications.add(photoTaxLabel, createGridBagConstraints(0, 4));
        orderSpecifications.add(comboBoxPhoto, createGridBagConstraints(1, 4));
        orderSpecifications.add(videoTaxLabel, createGridBagConstraints(0, 5));
        orderSpecifications.add(comboBoxVideo, createGridBagConstraints(1, 5));

        //Creating the panel which holds the ticket types and the add/delete data buttons.
        JPanel ticketSelection = new JPanel();
        ticketSelection.setBorder(BorderFactory.createTitledBorder("Ticket options"));
        ticketSelection.setLayout(new BorderLayout());
        ticketSelection.add(new JScrollPane(ticketTable), BorderLayout.NORTH);

        JPanel ticketTableButtons = new JPanel();
        ticketTableButtons.setLayout(new GridLayout(1, 2));
        ticketTableButtons.add(addDataButton);
        ticketTableButtons.add(deleteDataButton);

        ticketSelection.add(ticketTableButtons, BorderLayout.SOUTH);

        //Creating the panel which holds the order items table other details about 
        //the price/nr of items + buttons.
        JPanel orderPlacement = new JPanel();
        orderPlacement.setBorder(BorderFactory.createTitledBorder("Cart"));
        orderPlacement.setLayout(new BorderLayout());
        orderPlacement.add(new JScrollPane(orderTable), BorderLayout.CENTER);

        JPanel orderPlacementButtons = new JPanel();
        orderPlacementButtons.setLayout(new GridLayout(6, 1));
        orderPlacementButtons.add(addTicketsButton);
        orderPlacementButtons.add(deleteTicketsButton);
        orderPlacementButtons.add(cartTotalItems);
        orderPlacementButtons.add(totalCostLabel);
        orderPlacementButtons.add(comboBoxPayment);
        orderPlacementButtons.add(placeOrderButton);

        orderPlacement.add(orderPlacementButtons, BorderLayout.SOUTH);

        //Adding the panels with the tables in a panel for more customization.
        JPanel tables = new JPanel();
        tables.setLayout(new GridLayout(1, 2));
        tables.add(ticketSelection);
        tables.add(orderPlacement);

        this.add(orderSpecifications, BorderLayout.NORTH);
        this.add(tables, BorderLayout.CENTER);

        //Removing the ID column from the tickets table.
        ticketTable.removeColumn(ticketTable.getColumnModel().getColumn(0));
    }

    public Object[][] getTableData() {
        return ticketStoring.stream()
                .map(item -> new Object[]{
            item.getId(),
            item.getTicketType(),
            item.getTicketDescription(),
            item.getTicketPrice()
        }).toArray(Object[][]::new);
    }

    public Object[][] getTableData2() {
        return orderStoring.stream()
                .map(item -> new Object[]{
            item.getTimePeriod(),
            item.getDiscountType(),
            item.getTicketType(),
            item.getPhotoTax(),
            item.getVideoTax(),
            item.getPrice(),}).toArray(Object[][]::new);
    }

    public void resetFrame() {
        this.frame = null;
    }

    public GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(2, 2, 2, 2);
        return gbc;
    }

    public void insertInTicketTable(String type, String description, int price) {
        String query = "INSERT INTO `ticket type` (`Ticket type`, `Description`, `Price`) VALUES ('"
                + type + "', '" + description + "', " + price + ")";
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }

    public void insertInOrdersTable(int ticketsSold, double totalPrice, String groupDiscount, String paymentType) {
        String query = "INSERT INTO `orders` (`Tickets sold`, `Total price`, `Group discount`, `Payment type`) VALUES ('"
                + ticketsSold + "', '" + totalPrice + "', '" + groupDiscount + "', '" + paymentType + "')";
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }

    public void getTicketTypeData() {
        ticketStoring.clear();
        try {
            String insertquery = "SELECT * FROM `ticket type`";
            ResultSet result = db.getStatement().executeQuery(insertquery);
            while (result.next()) {
                ticket = new Ticket(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), Double.parseDouble(result.getString(4)));
                ticketStoring.add(ticket);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void deleteFromTicketTypeTable(int id) {
        String query = "DELETE FROM `ticket type` WHERE "
                + "`ID` = " + id;
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error deleting data");
        }
    }

    public void refreshTableData() {
        getTicketTypeData();
        Object[][] newData = getTableData();
        ticketTableModel.setDataVector(newData, columnNames);
        ticketTable.removeColumn(ticketTable.getColumnModel().getColumn(0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDataButton) {
            if (frame == null) {
                frame = new DataAddingFrameForTickets(this);
            }
        } else if (e.getSource() == deleteDataButton) {
            int[] selection = ticketTable.getSelectedRows();
            if (selection.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected items?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selection.length - 1; i >= 0; i--) {
                        int viewIndex = selection[i];
                        int modelIndex = ticketTable.convertRowIndexToModel(viewIndex);
                        int idToDelete = Integer.parseInt(ticketTableModel.getValueAt(modelIndex, 0).toString());
                        deleteFromTicketTypeTable(idToDelete);
                        ticketTableModel.removeRow(modelIndex);
                    }
                    getTicketTypeData();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        } else if (e.getSource() == addTicketsButton) {
            int[] selection = ticketTable.getSelectedRows();
            int ticketsNumber = Integer.parseInt(ticketCountTextField.getText());
            if (selection.length > 0) {
                int initialOrderSize = orderStoring.size();
                int itemsToAdd = selection.length * ticketsNumber;
                orderItemCount = orderItemCount + itemsToAdd;
                for (int i = 0; i < selection.length; i++) {
                    for (int j = 0; j < ticketsNumber; j++) {
                        int modelIndex = ticketTable.convertRowIndexToModel(selection[i]);
                        double price = Double.parseDouble(ticketTableModel.getValueAt(modelIndex, 3).toString());
                        String ticketType = ticketTableModel.getValueAt(modelIndex, 1).toString();
                        if (hourGroup.getSelection().getActionCommand() == "17-20") {
                            price = price + (1.0 / 10.0) * price;
                        }
                        if (String.valueOf(comboBoxPhoto.getSelectedItem()) == "Yes") {
                            price = price + (1.0 / 20.0) * price;
                        }
                        if (String.valueOf(comboBoxVideo.getSelectedItem()) == "Yes") {
                            price = price + (1.0 / 10.0) * price;
                        }
                        if (discountGroup.getSelection().getActionCommand() == "student" || discountGroup.getSelection().getActionCommand() == "retiree") {
                            price = price / 2;
                        } else if (discountGroup.getSelection().getActionCommand() == "veteran") {
                            price = price / 4;
                        }
                        orderStoring.add(orderItem = new OrderItem(hourGroup.getSelection().getActionCommand(), discountGroup.getSelection().getActionCommand(), ticketType, String.valueOf(comboBoxPhoto.getSelectedItem()), String.valueOf(comboBoxVideo.getSelectedItem()), price));
                    }
                }
                
                if (initialOrderSize < 10 && orderItemCount >= 10) {
                    int orderLength = orderStoring.size();
                    for (int i = 0; i < orderLength; i++) {
                        double price = orderStoring.get(i).getPrice();
                        orderStoring.get(i).setPrice(price - (1.0 / 10.0) * price);
                    }
                } else if (orderStoring.size() >= 10) {
                    for (int i =  orderStoring.size() - itemsToAdd; i < orderStoring.size(); i++) {
                        double price = orderStoring.get(i).getPrice();
                        orderStoring.get(i).setPrice(price - (1.0 / 10.0) * price);
                    }
                }
                data2 = getTableData2();
                orderTableModel.setDataVector(data2, columnNames2);
                totalCost = 0;
                for (int i = 0; i < orderItemCount; i++) {
                    totalCost = totalCost + orderStoring.get(i).getPrice();
                }
                if(orderItemCount >= 10) {
                    cartTotalItems.setText("Number of tickets: " + orderItemCount + " (group sale applied)");                  
                } else {
                    cartTotalItems.setText("Number of tickets: " + orderItemCount + " (group sale not applied)");
                }
                totalCostLabel.setText("Price: " + totalCost);
            }
        } else if (e.getSource() == deleteTicketsButton) {
            int[] selection = orderTable.getSelectedRows();
            if (selection.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected items?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = selection.length - 1; i >= 0; i--) {
                        int viewIndex = selection[i];
                        int modelIndex = orderTable.convertRowIndexToModel(viewIndex);
                        orderTableModel.removeRow(modelIndex);
                        orderStoring.remove(viewIndex);
                    }
                    orderItemCount = orderTableModel.getRowCount();
                    if (orderItemCount < 10 && cartTotalItems.getText().contains("group sale applied")) {                       
                        for (int i = 0; i < orderItemCount; i++) {
                            double price = orderStoring.get(i).getPrice();
                            orderStoring.get(i).setPrice(price / (0.9));
                        }
                    }
                    data2 = getTableData2();
                    orderTableModel.setDataVector(data2, columnNames2);
                    totalCost = 0;
                    for (int i = 0; i < orderItemCount; i++) {
                        totalCost = totalCost + orderStoring.get(i).getPrice();
                    }
                    if (orderItemCount >= 10) {
                        cartTotalItems.setText("Number of tickets: " + orderItemCount + " (group sale applied)");
                    }
                    else {
                        cartTotalItems.setText("Number of tickets: " + orderItemCount + " (group sale not applied)");
                    }
                    totalCostLabel.setText("Price: " + totalCost);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        } else if (e.getSource() == placeOrderButton) {
            if (orderItemCount > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to palce the order?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String groupDiscountValidation = "No";
                    if (orderItemCount >= 10) {
                        groupDiscountValidation = "Yes";
                    }
                    insertInOrdersTable(orderItemCount, totalCost, groupDiscountValidation, String.valueOf(comboBoxPayment.getSelectedItem()));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please place items into the cart.");
            }
        }
    }
}
