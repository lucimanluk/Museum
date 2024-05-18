package museum;

public class Ticket {

    private int Id;
    private String ticketType, ticketDescription;
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

    public String getTicketDescription() {
        return this.ticketDescription;
    }

    public String getTicketType() {
        return this.ticketType;
    }

    public double getTicketPrice() {
        return this.ticketPrice;
    }
}
