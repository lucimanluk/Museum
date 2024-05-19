package museum;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();

    private SellTickets se1lTicketsPane;
    private ManageInventory manageInventoryPane;
    private SoldTickets SoldTicketsPane;
    private FrameSelector parentPanel;

    public MainFrame(int admin, FrameSelector parentPanel) {
        this.parentPanel = parentPanel;
        se1lTicketsPane = new SellTickets();
        manageInventoryPane = new ManageInventory(admin);
        SoldTicketsPane = new SoldTickets();
        tabbedPane.addTab("Sell tickets", se1lTicketsPane);
        tabbedPane.addTab("Manage inventory", manageInventoryPane);
        tabbedPane.addTab("Ticket sales", SoldTicketsPane);

        this.add(tabbedPane);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parentPanel.resetFrame();
            }
        });
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }
}
