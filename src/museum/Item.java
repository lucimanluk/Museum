package museum;

public class Item {
    private int id;
    private String name;
    private String description;
    private String regionOfOrigin;
    private int year;
    private int room;
    
    public Item(int id, String name, String description, String regionOfOrigin, int year, int room) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.regionOfOrigin = regionOfOrigin;
        this.year = year;
        this.room = room;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getRegionOfOrigin() {
        return this.regionOfOrigin;
    }
    
    public int getYear() {
        return this.year;
    }
    
    public int getRoom() {
        return this.room;
    }  
}
