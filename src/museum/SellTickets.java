package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class SellTickets extends JPanel implements ActionListener {

    private final JLabel ticketCountLabel = new JLabel("Enter number of tickets");
    private final JLabel photoTaxLabel = new JLabel("Photography tax");
    private final JLabel videoTaxLabel = new JLabel("Videography tax");
    private JTextField ticketCountTextField = new JTextField(30);
    private final JRadioButton hour1 = new JRadioButton("9-17");
    private final JRadioButton hour2 = new JRadioButton("17-20");
    private final JRadioButton student = new JRadioButton("Student(50% off)");
    private final JRadioButton retiree = new JRadioButton("Retiree(50% off)");
    private final JRadioButton veteran = new JRadioButton("Veteran(100% off)");
    private final JRadioButton noDiscount = new JRadioButton("No discount");
    private final ButtonGroup hourGroup = new ButtonGroup();
    private final ButtonGroup discountGroup = new ButtonGroup();
    private JTable ticketTable;
    private JTable orderTable;
    private DefaultTableModel ticketTableModel;
    private DefaultTableModel orderTableModel;
    private final JButton addDataButton = new JButton("Add data");
    private final JButton deleteDataButton = new JButton("Delete data");
    private final JButton addTicketsButton = new JButton("Add to order");
    private final JButton deleteTicketsButton = new JButton("Delete items");
    private final String[] options = {"No", "Yes"};
    private final JComboBox comboBoxPhoto = new JComboBox(options);
    private final JComboBox comboBoxVideo = new JComboBox(options);
    private DataAddingFrame2 frame;
    private Database db;
    private Object[][] data;
    private Object[][] data2;
    private OrderItem orderItem;
    private ArrayList<OrderItem> orderStoring = new ArrayList<OrderItem>();
    private final String[] columnNames = {"ID", "Ticket type", "Ticket description", "Ticket price"};
    private final String[] columnNames2 = {"Time period", "Discount type", "Ticket type", "Photo tax", "Video tax", "Price"};

    public SellTickets(Database db) {
        this.db = db;
        data = getTableData();
        ticketTableModel = new DefaultTableModel(data, columnNames);
        ticketTable = new JTable(ticketTableModel);
        ticketTable.getTableHeader().setReorderingAllowed(false);
        orderTableModel = new DefaultTableModel(data2, columnNames2);
        orderTable = new JTable(orderTableModel);
        this.setLayout(new FlowLayout());
        this.add(ticketCountLabel);
        this.add(ticketCountTextField);
        hour1.setActionCommand("9-17");
        hour2.setActionCommand("17-20");
        hourGroup.add(hour1);
        hourGroup.add(hour2);
        discountGroup.add(student);
        discountGroup.add(retiree);
        discountGroup.add(veteran);
        discountGroup.add(noDiscount);
        student.setActionCommand("student");
        retiree.setActionCommand("retiree");
        veteran.setActionCommand("veteran");
        noDiscount.setActionCommand("no discount");
        this.add(hour1);
        this.add(hour2);
        this.add(student);
        this.add(retiree);
        this.add(veteran);
        this.add(noDiscount);
        this.add(photoTaxLabel);
        this.add(comboBoxPhoto);
        this.add(videoTaxLabel);
        this.add(comboBoxVideo);
        this.add(new JScrollPane(ticketTable));
        addDataButton.addActionListener(this);
        deleteDataButton.addActionListener(this);
        addTicketsButton.addActionListener(this);
        this.add(addDataButton);
        this.add(deleteDataButton);
        this.add(new JScrollPane(orderTable));
        this.add(addTicketsButton);
        this.add(deleteTicketsButton);
        ticketTable.removeColumn(ticketTable.getColumnModel().getColumn(0));
    }

    public Object[][] getTableData() {
        int size = db.ticketStoring.size();
        data = new Object[size][4];
        for (int i = 0; i < size; i++) {
            data[i][0] = db.ticketStoring.get(i).getId();
            data[i][1] = db.ticketStoring.get(i).getTicketType();
            data[i][2] = db.ticketStoring.get(i).getTicketDescription();
            data[i][3] = db.ticketStoring.get(i).getTicketPrice();
        }
        return data;
    }

    public Object[][] getTableData2() {
        int size = orderStoring.size();
        data = new Object[size][6];
        for (int i = 0; i < size; i++) {
            data[i][0] = orderStoring.get(i).getTimePeriod();
            data[i][1] = orderStoring.get(i).getDiscountType();
            data[i][2] = orderStoring.get(i).getTicketType();
            data[i][3] = orderStoring.get(i).getPhotoTax();
            data[i][4] = orderStoring.get(i).getVideoTax();
            data[i][5] = orderStoring.get(i).getPrice();
        }
        return data;
    }

    public void resetFrame() {
        this.frame = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDataButton) {
            if (frame == null) {
                frame = new DataAddingFrame2(db, this::getTableData, ticketTableModel, columnNames, ticketTable, this);
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
                        db.deleteData2(idToDelete);
                        ticketTableModel.removeRow(modelIndex);
                    }
                    db.view();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete");
            }
        } else if (e.getSource() == addTicketsButton) {
            int[] selection = ticketTable.getSelectedRows();
            int ticketsNumber = Integer.parseInt(ticketCountTextField.getText());
            if (selection.length > 0) {
                for (int i = 0; i < selection.length; i++) {
                    for (int j = 0; j < ticketsNumber; j++) {
                        double price = db.ticketStoring.get(i).getTicketPrice();
                        if (String.valueOf(comboBoxPhoto.getSelectedItem()) == "Yes") {
                            price = price + 20;
                        }
                        if (String.valueOf(comboBoxVideo.getSelectedItem()) == "Yes") {
                            price = price + 20;
                        }
                        if (discountGroup.getSelection().getActionCommand() == "student") {
                            price = price / 2;
                        }
                        orderStoring.add(orderItem = new OrderItem(hourGroup.getSelection().getActionCommand(), discountGroup.getSelection().getActionCommand(), db.ticketStoring.get(i).getTicketType(), String.valueOf(comboBoxPhoto.getSelectedItem()), String.valueOf(comboBoxVideo.getSelectedItem()), price));
                        data2 = getTableData2();
                        orderTableModel.setDataVector(data2, columnNames2);
                    }
                }
            }
        }
    }
}
