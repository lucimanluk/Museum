package museum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSelector extends JFrame {

    private JButton mainFrameButton = new JButton("Main frame");
    private JButton reservationPaneButton = new JButton("Reservation");

    private MainFrame mainFrame;
    private ReservationPane reservationPane;

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
                                if (mainFrame == null) {
                                    mainFrame = new MainFrame(admin, FrameSelector.this);
                                }
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
                                if (reservationPane == null) {
                                    reservationPane = new ReservationPane(FrameSelector.this);
                                }
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
        this.setSize(600,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void resetFrame() {
        this.mainFrame = null;
    }

    public void resetReservation() {
        this.reservationPane = null;
    }
}
