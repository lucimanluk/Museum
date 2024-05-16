package museum;

import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Database instance;
    private Connection con;
    private Statement stmt;
    private Ticket ticket;
    private Item item;
    private Reservation reservation;
    public ArrayList<Ticket> ticketStoring = new ArrayList<Ticket>();
    public ArrayList<Item> itemStoring = new ArrayList<Item>();
    public ArrayList<Reservation> reservationStoring = new ArrayList<Reservation>();

    private Database() {
        try {
            String url = "jdbc:mysql://localhost:3306/emuseum";
            String username = "root";
            con = DriverManager.getConnection(url, username, "");
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "eroare din conexiune");
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Statement getStatement() {
        return stmt;
    }

    public void close() {
        try {
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "eroare la inchidere");
        }
    }

    public void insertData(String name, String description, String region, int year, int room) {
        String query = "INSERT INTO `exhibition items` (`Name`, `Description`, `Region of origin`, `Year of production`, `Room placement`) VALUES ('" + name + "', '" + description + "', '" + region + "', " + year + ", " + room + ")";
        try {
            stmt.executeUpdate(query);
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }

    public void insertData2(String type, String description, int price) {
        String query = "INSERT INTO `ticket type` (`Ticket type`, `Description`, `Price`) VALUES ('"
                + type + "', '" + description + "', " + price + ")";
        try {
            stmt.executeUpdate(query);
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }

    public void insertData3(String name, int phoneNumber, int numberOfTickets, String dateTimes) {
        String query = "INSERT INTO `reservations` (`Name`, `Phone number`, `Number of tickets`, `Date and time`) VALUES ('"
                + name + "', '" + phoneNumber + "', '" + numberOfTickets + "', '" + dateTimes + "')";
        try {
            stmt.executeUpdate(query);
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }

    public void deleteData(int id) {
        String query = "DELETE FROM `exhibition items` WHERE "
                + "`ID` = " + id;
        try {
            stmt.executeUpdate(query);
            System.out.println("Data deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error deleting data");
        }
    }

    public void deleteData2(int id) {
        String query = "DELETE FROM `ticket type` WHERE "
                + "`ID` = " + id;
        try {
            stmt.executeUpdate(query);
            System.out.println("Data deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error deleting data");
        }
    }

    public void view() {
        ticketStoring.clear();
        try {
            String insertquery = "SELECT * FROM `ticket type`";
            ResultSet result = stmt.executeQuery(insertquery);
            while (result.next()) {
                ticket = new Ticket(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), Double.parseDouble(result.getString(4)));
                ticketStoring.add(ticket);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void view2() {
        itemStoring.clear();
        try {
            String insertquery = "SELECT * FROM `exhibition items`";
            ResultSet result = stmt.executeQuery(insertquery);
            while (result.next()) {
                item = new Item(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
                itemStoring.add(item);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void view3() {
        reservationStoring.clear();
        try {
            String insertquery = "SELECT * FROM `reservations`";
            ResultSet result = stmt.executeQuery(insertquery);
            while (result.next()) {
                reservation = new Reservation(Integer.parseInt(result.getString(1)), result.getString(2), Integer.parseInt(result.getString(3)), Integer.parseInt(result.getString(4)), result.getString(5));
                reservationStoring.add(reservation);
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

}
