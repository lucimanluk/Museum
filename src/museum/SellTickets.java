package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SellTickets extends JPanel {
    private TimePanel timePanel = new TimePanel();
    private DiscountPanel discountPanel = new DiscountPanel();
    private TicketTable ticketTable;

    public SellTickets(Database db) {
        ticketTable = new TicketTable(db);
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));  
        topPanel.add(timePanel);
        topPanel.add(discountPanel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(ticketTable), BorderLayout.CENTER);
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
        public final String[] columnNames = {"Ticket type", "Ticket price", "Ticket description"};
        public Object[][] data;

        public TicketTable(Database db) {
            data = getTableData(db);
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            this.setModel(model);
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.getTableHeader().setReorderingAllowed(false);
        }

        public Object[][] getTableData(Database db) {
            int size = db.ticketTypes1.size();
            data = new Object[size][3];
            for (int i = 0; i < size; i++) {
                data[i][0] = db.ticketTypes1.get(i);
                data[i][1] = db.ticketTypes2.get(i);
                data[i][2] = db.ticketTypes3.get(i);
            }
            return data;
        }
    }
}
