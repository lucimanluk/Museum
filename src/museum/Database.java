package museum;

import java.sql.*;
import java.util.ArrayList;
public class Database {
    private static Database instance;
    private Connection con;
    private Statement stmt;
    public ArrayList<String> ticketTypes1 = new ArrayList<String>();
    public ArrayList<String> ticketTypes2 = new ArrayList<String>();
    public ArrayList<String> ticketTypes3 = new ArrayList<String>();
    public ArrayList<String> name  = new ArrayList<String>();
    public ArrayList<String> description = new ArrayList<String>();
    public ArrayList<String> regionOfOrigin = new ArrayList<String>();
    public ArrayList<String> yearOfProduction = new ArrayList<String>();
    public ArrayList<String> roomPlacement = new ArrayList<String>();
    
    
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
    String query = "INSERT INTO `ticket type` (`Ticket type`, `Description`, `Price`) VALUES ('Platinum', 'I go poop.', 5)";
    try {
        stmt.executeUpdate(query);
        System.out.println("Data inserted successfully");
    } catch (SQLException e) {
        System.out.println(e.getMessage() + " - Error inserting data");
    }
}
  
public void view(){
    ticketTypes1.clear();
    ticketTypes2.clear();
    ticketTypes3.clear();
    try {
        String insertquery = "SELECT * FROM `ticket type`";
        ResultSet result = stmt.executeQuery(insertquery);
        while(result.next()){
            ticketTypes1.add(result.getString(2));
            ticketTypes2.add(result.getString(3));
            ticketTypes3.add(result.getString(4));         
        }
        System.out.println(ticketTypes1);
        System.out.println(ticketTypes2);
        System.out.println(ticketTypes3);
        
    } catch (SQLException ex) {
        System.out.println("Problem To Show Data");
    }
 }

public void view2(){
    name.clear();
    description.clear();
    regionOfOrigin.clear();
    yearOfProduction.clear();
    roomPlacement.clear();
    try {
        String insertquery = "SELECT * FROM `exhibition items`";
        ResultSet result = stmt.executeQuery(insertquery);
        while(result.next()){
            name.add(result.getString(2));
            description.add(result.getString(3));
            regionOfOrigin.add(result.getString(4));
            yearOfProduction.add(result.getString(5));
            roomPlacement.add(result.getString(6));
        }
 
        System.out.println(name);
        
    } catch (SQLException ex) {
        System.out.println("Problem To Show Data");
    }
 }

}

