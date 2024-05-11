package museum;


public class Ticket {

private int Id;
private String ticketType;
private String ticketDescription;
private double ticketPrice;

public Ticket(int Id, String ticketType, String ticketDescription, double ticketPrice) {
    this.Id = Id;
    this.ticketType = ticketType;
    this.ticketDescription = ticketDescription;
    this.ticketPrice = ticketPrice;
}
   
public int getId() {
    return this.Id;
}

public String getTicketType() {
    return this.ticketType;
}

public double getTicketPrice() {
    return this.ticketPrice;
}

public String getTicketDescription() {
    return this.ticketDescription;
}

public void setId(int Id) {
    this.Id = Id;
}

public void setTicketType(String ticketType) {
    this.ticketType = ticketType;
}

public void setTicketPrice(int ticketPrice) {
    this.ticketPrice = ticketPrice;
}

public void setTicketDescription(String ticketDescription) {
    this.ticketDescription = ticketDescription;
}
}
