package museum;


public class Ticket {

private String ticketType;
private String ticketDescription;
private double ticketPrice;

public Ticket(String ticketType, String ticketDescription, double ticketPrice) {
    this.ticketType = ticketType;
    this.ticketDescription = ticketDescription;
    this.ticketPrice = ticketPrice;
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
