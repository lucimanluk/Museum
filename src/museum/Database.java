package museum;

import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Database instance;
    private Connection con;
    private Statement stmt;

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
}
