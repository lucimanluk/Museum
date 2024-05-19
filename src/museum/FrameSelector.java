package museum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSelector extends JFrame {

    private JButton mainFrameButton = new JButton("Main frame");
    private JButton reservationPaneButton = new JButton("Reservation");

    public FrameSelector(String title, int admin) {
        
        super(title);
        
        this.setLayout(new GridLayout(2, 1));

        mainFrameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new MainFrame(admin); 
                            }
                        });
                    }
                });
                t.start();
            }
        });
        
        reservationPaneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {         
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {             
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new ReservationPane();  
                            }
                        });
                    }
                });
                t.start();
            }
        });

        this.add(mainFrameButton);
        this.add(reservationPaneButton);

        this.setVisible(true);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
