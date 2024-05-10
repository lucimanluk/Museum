package museum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageInventory extends JPanel {
    private JTable tabelManagementInvetory;
    private Object[][] data;
    
    public ManageInventory(Database db){
    String[] columnNames = {"Name", "Description", "Region of origin", "Year of production", "Room placement"};
    Object[][] data = getTableData(db);
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    tabelManagementInvetory = new JTable(model);
     tabelManagementInvetory.getTableHeader().setReorderingAllowed(false);
     this.setLayout(new BorderLayout()); 
     this.add(new JScrollPane(tabelManagementInvetory));   
}
    
      public Object[][] getTableData(Database db) {
    int size = db.name.size();
    data = new Object[size][5];
    for (int i = 0; i<size; i++) {
        for (int j = 0; j<5; j++) {
            if(j == 0) {
            data[i][j] = db.name.get(i);
            }
            else if (j == 1) {
                data[i][j] = db.description.get(i);
            } 
            else if (j == 2) {
                data[i][j] = db.regionOfOrigin.get(i);
            }
             else if (j == 3) {
                data[i][j] = db.yearOfProduction.get(i);
            } else if (j == 4) {
                data[i][j] = db.roomPlacement.get(i);
            }
            
        }    
    }
    return data;
}   
    
}
