/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();

    private SellTickets se1lTicketsPane;
    private ReservationPane reservationPane;
    private ManageInventory manageInventoryPane;
    private SoldTickets SoldTicketsPane;

    public MainFrame(int admin) {

        se1lTicketsPane = new SellTickets();
        reservationPane = new ReservationPane();
        manageInventoryPane = new ManageInventory(admin);
        SoldTicketsPane = new SoldTickets();
        tabbedPane.addTab("Sell tickets", se1lTicketsPane);
        tabbedPane.addTab("Reserve tickets", reservationPane);
        tabbedPane.addTab("Manage inventory", manageInventoryPane);
        tabbedPane.addTab("Ticket sales", SoldTicketsPane);

        this.add(tabbedPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }
}
