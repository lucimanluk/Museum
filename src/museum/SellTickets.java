package museum;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SellTickets extends JPanel implements ActionListener {
    private TimePanel timePanel = new TimePanel();
    private DiscountPanel discountPanel = new DiscountPanel();
    private TicketTable ticketTable;
    private JButton addDataButton = new JButton("Add data");
    private Database db;

    public SellTickets(Database db) {
        this.db = db;
        ticketTable = new TicketTable();
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));  
        topPanel.add(timePanel);
        topPanel.add(discountPanel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(ticketTable), BorderLayout.CENTER);
        addDataButton.addActionListener(this);
        this.add(addDataButton, BorderLayout.SOUTH);
    }

    class TimePanel extends JPanel {
        public JRadioButton buttonMorning = new JRadioButton("9-17");
        public JRadioButton buttonEvening = new JRadioButton("17-20");
        public ButtonGroup hourGroup = new ButtonGroup();

        public TimePanel() {
            setLayout(new FlowLayout());
            hourGroup.add(buttonMorning);
            hourGroup.add(buttonEvening);
            add(buttonMorning);
            add(buttonEvening);
        }
    }

    class DiscountPanel extends JPanel {
        public final JRadioButton buttonRetirees = new JRadioButton("Retirees");
        public final JRadioButton buttonSoldiers = new JRadioButton("Soldiers");
        public final JRadioButton buttonStudents = new JRadioButton("Students");
        public final JRadioButton buttonNoDiscount = new JRadioButton("No discount");
        public final ButtonGroup discountGroup = new ButtonGroup();
        
        public DiscountPanel() {
            setLayout(new FlowLayout());
            discountGroup.add(buttonRetirees);
            discountGroup.add(buttonSoldiers);
            discountGroup.add(buttonStudents);
            discountGroup.add(buttonNoDiscount);
            add(buttonRetirees);
            add(buttonSoldiers);
            add(buttonStudents);
            add(buttonNoDiscount);
        }
    }

    class TicketTable extends JTable {
        private final String[] columnNames = {"Ticket type", "Ticket description", "Ticket price"};
        private DefaultTableModel model;

        public TicketTable() {
            updateModel();
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.getTableHeader().setReorderingAllowed(false);
        }

        public void updateModel() {
            Object[][] data = getTableData();
            model = new DefaultTableModel(data, columnNames);
            this.setModel(model);
        }

        public Object[][] getTableData() {
            int size = db.ticketStoring.size();
            Object[][] data = new Object[size][3];
            for (int i = 0; i < size; i++) {
                data[i][0] = db.ticketStoring.get(i).getTicketType();
                data[i][1] = db.ticketStoring.get(i).getTicketDescription();
                data[i][2] = db.ticketStoring.get(i).getTicketPrice();
            }
            return data;
        }

        public String[] getColumnNames() {
            return columnNames;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDataButton) {
           db.insertData2();
           db.view();
           ticketTable.updateModel();
        }
    }
}
