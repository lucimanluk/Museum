/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSelector extends JFrame {

    private JButton MainFrame = new JButton("Main frame");
    private JButton reservationPane = new JButton("Reservation");

    public FrameSelector(String title, int admin) {
        
        super(title);
        
        this.setLayout(new GridLayout(2, 1));

        MainFrame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame(admin);
            }
        });
        
        reservationPane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationPane();
            }
        });

        this.add(MainFrame);
        this.add(reservationPane);

        this.setVisible(true);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
