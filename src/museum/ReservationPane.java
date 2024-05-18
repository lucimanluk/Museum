package museum;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class ReservationPane extends JPanel implements KeyListener, ActionListener {

    private JLabel firstNameLabel = new JLabel("First name");
    private JTextField firstNameTextField = new JTextField(20);
    private JLabel lastNameLabel = new JLabel("Last name");
    private JTextField lastNameTextField = new JTextField(20);
    private JLabel phoneNumberLabel = new JLabel("Phone number");
    private JTextField phoneNumberTextField = new JTextField(20);
    private JPanel namePanel = new JPanel();
    private JLabel ticketAmountLabel = new JLabel("Select ticket amount");
    private String[] ticketCount = {"1", "2", "3", "4", "5"};
    private JComboBox ticketAmountBox = new JComboBox(ticketCount);
    private JLabel dateTimeLabel = new JLabel("Date and time");
    private JTextField dateTimeTextField = new JTextField(20);
    private LocalDateTime currentTime = LocalDateTime.now();
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private String formattedDate = currentTime.format(myFormatObj);
    private final JButton addReservationButton = new JButton("Add reservation");
    private Object[][] data;
    private final String[] columnNames = {"Id", "Name", "Phone number", "Number of tickets", "Date and time"};
    private Reservation reservation;
    private ArrayList<Reservation> reservationStoring = new ArrayList<Reservation>();
    private DefaultTableModel model;
    private JTable reservationTable;
    private Database db = Database.getInstance();

    public ReservationPane() {

        this.setLayout(new GridLayout(2, 1));

        dateTimeTextField = new JTextField(formattedDate);
        ticketAmountBox.setPreferredSize(new Dimension(50, 25));

        phoneNumberTextField.addKeyListener(this);
        addReservationButton.addActionListener(this);

        namePanel.setLayout(new GridBagLayout());
        namePanel.setBorder(BorderFactory.createTitledBorder("Reservation information"));

        getReservationsData();
        data = getTableData();
        model = new DefaultTableModel(data, columnNames);
        reservationTable = new JTable(model);

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
        namePanel.add(addReservationButton, createGridBagConstraints(1, 5));

        this.add(namePanel);
        this.add(new JScrollPane(reservationTable));

        reservationTable.removeColumn(reservationTable.getColumnModel().getColumn(0));
    }

    public GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(2, 2, 2, 2);
        return gbc;
    }

    public Object[][] getTableData() {
        return reservationStoring.stream().map(item -> new Object[]{
            item.getId(),
            item.getName(),
            item.getPhoneNumber(),
            item.getNumberOfTickets(),
            item.getDateTime()
        }).toArray(Object[][]::new);
    }

    public void getReservationsData() {
        reservationStoring.clear();
        try {
            String insertquery = "SELECT * FROM `reservations`";
            ResultSet result = db.getStatement().executeQuery(insertquery);
            while (result.next()) {
                reservation = new Reservation(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), Integer.parseInt(result.getString(4)), result.getString(5));
                reservationStoring.add(reservation);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void insertIntoReservationsTable(String name, String phoneNumber, int numberOfTickets, String dateTimes) {
        String query = "INSERT INTO `reservations` (`Name`, `Phone number`, `Number of tickets`, `Date and time`) VALUES ('"
                + name + "', '" + phoneNumber + "', '" + numberOfTickets + "', '" + dateTimes + "')";
        try {
            db.getStatement().executeUpdate(query);
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addReservationButton) {
            if (!firstNameTextField.getText().isEmpty()
                    && !lastNameTextField.getText().isEmpty()
                    && phoneNumberTextField.getText().length() == 10
                    && !dateTimeTextField.getText().isEmpty()) {
                String name = lastNameTextField.getText() + " " + firstNameTextField.getText();
                insertIntoReservationsTable(name, phoneNumberTextField.getText(), Integer.parseInt(ticketAmountBox.getSelectedItem().toString()), dateTimeTextField.getText());
                getReservationsData();
                data = getTableData();
                model.setDataVector(data, columnNames);
                reservationTable.removeColumn(reservationTable.getColumnModel().getColumn(0));
            } else if (phoneNumberTextField.getText().length() <= 10
                    && phoneNumberTextField.getText().length() >= 1
                    && !firstNameTextField.getText().isEmpty()
                    && !lastNameTextField.getText().isEmpty()
                    && !dateTimeTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number not long enough.");
            } else {
                JOptionPane.showMessageDialog(null, "Please fill all the fiellds.");
            }
        }
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
