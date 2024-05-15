package museum;

import javax.swing.*;

public class Museum {

    private final JFrame initialFrame = new JFrame("EMuseum");
    private final JTabbedPane tabbedPane = new JTabbedPane();
    public Database db = new Database();

    public Museum() {
        db.view();
        db.view2();
        SellTickets se1lTicketsPage = new SellTickets(db);
        ReservationPane reservationPage = new ReservationPane();
        ManageInventory managePage = new ManageInventory(db);
        JPanel page4 = new JPanel();

        tabbedPane.addTab("Sell tickets", se1lTicketsPage);
        tabbedPane.addTab("Reserve tickets", reservationPage);
        tabbedPane.addTab("Manage inventory", managePage);
        tabbedPane.addTab("Ticket sales", page4);

        initialFrame.add(tabbedPane);
        initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialFrame.pack();
        initialFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Museum();
    }
}
