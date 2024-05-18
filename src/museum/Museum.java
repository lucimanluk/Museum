package museum;

import javax.swing.*;

public class Museum {

    private final JFrame initialFrame = new JFrame("EMuseum");
    private final JTabbedPane tabbedPane = new JTabbedPane();
    public Database db =  Database.getInstance();

    public Museum() {
        db.view();
        db.view2();
        db.view3();
        SellTickets se1lTicketsPage = new SellTickets();
        ReservationPane reservationPage = new ReservationPane();
        ManageInventory managePage = new ManageInventory();
        SoldTickets SoldTicketsPage = new SoldTickets();

        tabbedPane.addTab("Sell tickets", se1lTicketsPage);
        tabbedPane.addTab("Reserve tickets", reservationPage);
        tabbedPane.addTab("Manage inventory", managePage);
        tabbedPane.addTab("Ticket sales", SoldTicketsPage);

        initialFrame.add(tabbedPane);
        initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialFrame.pack();
        initialFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Museum();
    }
}
