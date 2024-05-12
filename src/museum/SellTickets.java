package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class SellTickets extends JPanel implements ActionListener {

    private final JLabel ticketCountLabel = new JLabel("Enter number of tickets");
    private JTextField ticketCountTextField = new JTextField(30);
    private final JRadioButton hour1 = new JRadioButton("9-17");
    private final  JRadioButton hour2 = new  JRadioButton("17-20");
    private final JRadioButton student = new JRadioButton("Student(50% off)");
    private final  JRadioButton retiree = new  JRadioButton("Retiree(50% off)");
    private final JRadioButton veteran = new JRadioButton("Veteran(100% off)");
    private final  JRadioButton noDiscount = new  JRadioButton("No discount");
    private final JRadioButton videoTax = new JRadioButton("Videography tax");
    private final JRadioButton photoTax = new JRadioButton("Photography tax");
    private final ButtonGroup hourGroup = new ButtonGroup();  
    private final ButtonGroup discountGroup = new ButtonGroup();
    private final ButtonGroup aditionalTaxes = new ButtonGroup();
    private JTable ticketTable;
    private JTable orderTable;
    private DefaultTableModel ticketTableModel;
    private DefaultTableModel orderTableModel;
    private final JButton addDataButton = new JButton("Add data");
    private final JButton deleteDataButton = new JButton("Delete data");
    private final JButton addTicketsButton = new JButton("Add to order");
    private final JButton deleteTicketsButton = new JButton("Delete items");
    private DataAddingFrame2 frame;
    private Database db;
    private Object[][] data;
    private Object[][] data2;
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
        hourGroup.add(hour1);
        hourGroup.add(hour2);
        discountGroup.add(student);
        discountGroup.add(retiree);
        discountGroup.add(veteran);
        discountGroup.add(noDiscount);
        aditionalTaxes.add(photoTax);
        aditionalTaxes.add(videoTax);
        this.add(hour1);
        this.add(hour2);
        this.add(student);
        this.add(retiree);
        this.add(veteran);
        this.add(noDiscount);
        this.add(photoTax);
        this.add(videoTax);
        this.add(new JScrollPane(ticketTable));
        addDataButton.addActionListener(this);
        deleteDataButton.addActionListener(this);
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
    
    public void resetFrame() {
    this.frame = null;
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDataButton) {
        if(frame == null)
            frame = new DataAddingFrame2(db, this::getTableData, ticketTableModel, columnNames, ticketTable, this);
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
        }
    }
}
