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
    public ArrayList<Ticket> ticketStoring = new ArrayList<Ticket>();
    public ArrayList<Item> itemStoring = new ArrayList<Item>();
    
    public Database(){
        try{
            String url="jdbc:mysql://localhost:3306/emuseum";
            String username="root";
            con=DriverManager.getConnection(url, username, "");
            stmt=con.createStatement();
        }catch(SQLException e){System.out.println(e.getMessage()+"eroare din conexiune");}
    }
    
    public Statement getStatement(){
        return stmt;
    }
    public void close(){
        try{
            stmt.close();
            con.close();
         
        }catch(SQLException e){System.out.println(e.getMessage()+"eroare la inchidere");}
    }
    public static Database getInstance(){
        if(instance==null) instance=new Database();
        return instance;
    }
    
  public void insertData() {
    String query = "INSERT INTO `exhibition items` (`Name`, `Description`, `Region of origin`, `Year of production`, `Room placement`) VALUES ('Platinum', 'I go poop.','Platinum', 5,6)";
    try {
        stmt.executeUpdate(query);
        System.out.println("Data inserted successfully");
    } catch (SQLException e) {
        System.out.println(e.getMessage() + " - Error inserting data");
    }
}
  
     
  public void insertData2() {
    String query = "INSERT INTO `ticket type` (`Ticket type`, `Description`, `Price`) VALUES ('Platinum', 'I go poop.',6)";
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


  
public void view(){
    ticketStoring.clear();
    try {
        String insertquery = "SELECT * FROM `ticket type`";
        ResultSet result = stmt.executeQuery(insertquery);
        while(result.next()){
            ticket = new Ticket (result.getString(2), result.getString(3), Double.parseDouble(result.getString(4)));
            ticketStoring.add(ticket);
        }
    } catch (SQLException ex) {
        System.out.println("Problem To Show Data");
    }
 }

public void view2(){
    itemStoring.clear();
    try {
        String insertquery = "SELECT * FROM `exhibition items`";
        ResultSet result = stmt.executeQuery(insertquery);
        while(result.next()){
            item = new Item (Integer.parseInt(result.getString(1)),result.getString(2), result.getString(3), result.getString(4), Integer.parseInt(result.getString(5)), Integer.parseInt(result.getString(6)));
            itemStoring.add(item);
        }      
    } catch (SQLException ex) {
        System.out.println("Problem To Show Data");
    }
 }

}

