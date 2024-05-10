package museum;

import javax.swing.*;

public class Museum {
    private final JFrame initialFrame =  new JFrame("EMuseum");  
    private final JTabbedPane tabbedPane = new JTabbedPane();
    public Database db = new Database();

        public Museum() {
        db.view();
        db.view2();
        SellTickets page1 = new SellTickets(db);
        JPanel page2 = new JPanel();
        ManageInventory page3 = new ManageInventory(db);
        JPanel page4 = new JPanel();
     
        
        tabbedPane.addTab("Sell tickets", page1);      
        tabbedPane.addTab("Reserve tickets", page2);
        tabbedPane.addTab("Manage inventory", page3);
        tabbedPane.addTab("Ticket sales", page4);
        
        initialFrame.add(tabbedPane);
        initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialFrame.pack();
        initialFrame.setVisible(true);
    }
    public static void main (String[] args) {
        new Museum();
    }
}
