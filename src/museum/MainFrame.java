package museum;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();

    private SellTickets se1lTicketsPane;
    private ManageInventory manageInventoryPane;
    private SoldTickets SoldTicketsPane;

    public MainFrame(int admin) {

        se1lTicketsPane = new SellTickets();
        manageInventoryPane = new ManageInventory(admin);
        SoldTicketsPane = new SoldTickets();
        tabbedPane.addTab("Sell tickets", se1lTicketsPane);
        tabbedPane.addTab("Manage inventory", manageInventoryPane);
        tabbedPane.addTab("Ticket sales", SoldTicketsPane);

        this.add(tabbedPane);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }
}
