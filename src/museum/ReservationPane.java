package museum;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class ReservationPane extends JPanel implements KeyListener {

    private final JLabel firstNameLabel = new JLabel("First name");
    private JTextField firstNameTextField = new JTextField(20);
    private final JLabel lastNameLabel = new JLabel("Last name");
    private JTextField lastNameTextField = new JTextField(20);
    private final JLabel phoneNumberLabel = new JLabel("Phone number");
    private JTextField phoneNumberTextField = new JTextField(20);
    private JPanel namePanel = new JPanel();
    private final JLabel ticketAmountLabel = new JLabel("Select ticket amount");
    private final String[] ticketCount = {"1", "2", "3", "4", "5"};
    private JComboBox ticketAmountBox = new JComboBox(ticketCount);
    private final JLabel dateTimeLabel = new JLabel("Date and time");
    private JTextField dateTimeTextField = new JTextField(20);
    private LocalDateTime currentTime = LocalDateTime.now();
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private String formattedDate = currentTime.format(myFormatObj);
    private final JButton addReservationButton = new JButton ("Add reservation");
    private Object[][] data;
    private final String[] columnNames = {"Name", "Phone number", "Number of tickets", "Date and time"};
    private DefaultTableModel model;
    private JTable reservationTable;

    public ReservationPane() {

        this.setLayout(new GridLayout(2,1));
        phoneNumberTextField.addKeyListener(this);
        dateTimeTextField = new JTextField(formattedDate);
        namePanel.setLayout(new GridBagLayout());
        ticketAmountBox.setPreferredSize(new Dimension(50, 25));
        namePanel.add(firstNameLabel, createGridBagConstraints(0, 0));
        namePanel.add(firstNameTextField, createGridBagConstraints(1, 0));
        namePanel.add(lastNameLabel, createGridBagConstraints(0, 1));
        namePanel.add(lastNameTextField, createGridBagConstraints(1, 1));
        namePanel.add(phoneNumberLabel, createGridBagConstraints(0, 2));
        namePanel.add(phoneNumberTextField, createGridBagConstraints(1, 2));
        namePanel.add(ticketAmountLabel, createGridBagConstraints(0, 3));
        namePanel.add(ticketAmountBox, createGridBagConstraints(1, 3));
        namePanel.add(dateTimeLabel, createGridBagConstraints(0, 4));
        namePanel.add(dateTimeTextField, createGridBagConstraints(1, 4));
        namePanel.add(addReservationButton, createGridBagConstraints(1,5));
        this.add(namePanel);
        model = new DefaultTableModel(data, columnNames);
        reservationTable = new JTable(model);
        this.add(new JScrollPane(reservationTable));
    }

    public GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(2, 2, 2, 2);
        return gbc;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == phoneNumberTextField) {
            char c = e.getKeyChar();
            String existingText = phoneNumberTextField.getText();
            if (existingText.length() == 0 && c != '0') {
                e.consume();
            }
            if (!Character.isDigit(c) || existingText.length() >= 10) {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
