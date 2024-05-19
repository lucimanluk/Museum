package museum;

public class SoldTicket {

    private int id, orderId;
    private String hours, discountType, ticketType, photoTax, videoTax, paymentType;
    private double price;

    public SoldTicket(int id, int orderId, String hours, String discountType, String ticketType, String photoTax, String videoTax, double price, String paymentType) {
        this.id = id;
        this.orderId = orderId;
        this.hours = hours;
        this.discountType = discountType;
        this.ticketType = ticketType;
        this.photoTax = photoTax;
        this.videoTax = videoTax;
        this.price = price;
        this.paymentType = paymentType;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getOrderId() {
        return this.orderId;
    }
    
    public String getHours() {
        return this.hours;
    }
    
    public String getDiscountType() {
        return this.discountType;
    }
    
    public String getTicketType() {
        return this.ticketType;
    }
    
    public String getPhotoTax() {
        return this.photoTax;
    }
    
    public String getVideoTax() {
        return this.videoTax;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public String getPaymentType() {
        return this.paymentType;
    }
}
