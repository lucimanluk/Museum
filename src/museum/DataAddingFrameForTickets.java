package museum;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class DataAddingFrameForTickets extends JFrame implements ActionListener {

    private JLabel typeLabel = new JLabel("Ticket type");
    private JTextField typeField = new JTextField(20);
    private JLabel descriptionLabel = new JLabel("Ticket description");
    private JTextField descriptionField = new JTextField(20);
    private JLabel priceLabel = new JLabel("Ticket price");
    private JTextField priceField = new JTextField(20);
    private JButton addDataButton2 = new JButton("Add data");
    private JButton closeFrame = new JButton("Close");
    private SellTickets parentPanel;
    private Database db = Database.getInstance();

    public DataAddingFrameForTickets(SellTickets parentPanel) {
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
                parentPanel.insertInTicketTable(typeField.getText(), descriptionField.getText(), Integer.parseInt(priceField.getText()));
                parentPanel.refreshTableData();
            }
        } else if (e.getSource() == closeFrame) {
            dispose();
            parentPanel.resetFrame();
        }
    }
}
