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
    private final JRadioButton hour2 = new JRadioButton("17-20 (10% higher price)");
    private final JRadioButton student = new JRadioButton("Student (50% off)");
    private final JRadioButton retiree = new JRadioButton("Retiree (50% off)");
    private final JRadioButton veteran = new JRadioButton("Veteran (75% off)");
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
    private final String[] paymentOptions = {"Cash", "Card"};
    private final JComboBox comboBoxPhoto = new JComboBox(options);
    private final JComboBox comboBoxVideo = new JComboBox(options);
    private final JComboBox comboBoxPayment = new JComboBox(paymentOptions);
    private DataAddingFrame2 frame;
    private Database db = Database.getInstance();
    private Object[][] data;
    private Object[][] data2;
    private OrderItem orderItem;
    private ArrayList<OrderItem> orderStoring = new ArrayList<OrderItem>();
    private final String[] columnNames = {"ID", "Ticket type", "Ticket description", "Ticket price"};
    private final String[] columnNames2 = {"Time period", "Discount type", "Ticket type", "Photo tax", "Video tax", "Price"};

    public SellTickets() {
        JPanel this1 = new JPanel();
        this1.setLayout(new GridBagLayout());
        data = getTableData();
        ticketTableModel = new DefaultTableModel(data, columnNames);
        ticketTable = new JTable(ticketTableModel);
        ticketTable.getTableHeader().setReorderingAllowed(false);
        orderTableModel = new DefaultTableModel(data2, columnNames2);
        orderTable = new JTable(orderTableModel);
        this.setLayout(new FlowLayout());
        this1.add(ticketCountLabel, createGridBagConstraints(0,0));
        this1.add(ticketCountTextField, createGridBagConstraints(1,0));
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
        this1.add(hour1, createGridBagConstraints(0,1));
        this1.add(hour2, createGridBagConstraints(1,1));
        this1.add(student, createGridBagConstraints(0,2));
        this1.add(retiree, createGridBagConstraints(1,2));
        this1.add(veteran, createGridBagConstraints(0,3));
        this1.add(noDiscount, createGridBagConstraints(1,3));
        this1.add(photoTaxLabel, createGridBagConstraints(0,4));
        this1.add(comboBoxPhoto, createGridBagConstraints(1,4));
        this1.add(videoTaxLabel, createGridBagConstraints(0,5));
        this1.add(comboBoxVideo, createGridBagConstraints(1,5));
        this.add(this1);
        this.add(new JScrollPane(ticketTable));
        addDataButton.addActionListener(this);
        deleteDataButton.addActionListener(this);
        addTicketsButton.addActionListener(this);
        deleteTicketsButton.addActionListener(this);
        this.add(addDataButton);
        this.add(deleteDataButton);
        this.add(new JScrollPane(orderTable));
        this.add(addTicketsButton);
        this.add(deleteTicketsButton);
        this.add(comboBoxPayment);
        ticketTable.removeColumn(ticketTable.getColumnModel().getColumn(0));
    }

    public Object[][] getTableData() {
        return db.ticketStoring.stream()
            .map(item -> new Object[] {
                item.getId(),
                item.getTicketType(),
                item.getTicketDescription(),
                item.getTicketPrice()
            })
            .toArray(Object[][]::new);
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

    public GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(2, 2, 2, 2);
        return gbc;
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
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        } else if (e.getSource() == addTicketsButton) {
            int[] selection = ticketTable.getSelectedRows();
            int ticketsNumber = Integer.parseInt(ticketCountTextField.getText());
            if (selection.length > 0) {
                for (int i = 0; i < selection.length; i++) {
                    for (int j = 0; j < ticketsNumber; j++) {
                        int modelIndex = ticketTable.convertRowIndexToModel(selection[i]);
                        double price = Double.parseDouble(ticketTableModel.getValueAt(modelIndex, 3).toString());
                        String ticketType = ticketTableModel.getValueAt(modelIndex, 1).toString();
                        if(hourGroup.getSelection().getActionCommand() == "17-20"){
                            price = price + (1.0/10.0)*price;
                        }
                        if (String.valueOf(comboBoxPhoto.getSelectedItem()) == "Yes") {
                            price = price + (1.0/20.0)*price;
                        }
                        if (String.valueOf(comboBoxVideo.getSelectedItem()) == "Yes") {
                            price = price + (1.0/10.0)*price;
                        }
                        if (discountGroup.getSelection().getActionCommand() == "student" || discountGroup.getSelection().getActionCommand() == "retiree") {
                            price = price/2;
                        }
                        else if(discountGroup.getSelection().getActionCommand() == "veteran"){
                            price = price/4;
                        }
                        orderStoring.add(orderItem = new OrderItem(hourGroup.getSelection().getActionCommand(), discountGroup.getSelection().getActionCommand(), ticketType , String.valueOf(comboBoxPhoto.getSelectedItem()), String.valueOf(comboBoxVideo.getSelectedItem()), price));
                    }
                }
                data2 = getTableData2();
                orderTableModel.setDataVector(data2, columnNames2);
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
                    System.out.println(orderStoring.size());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        }
    }
}
