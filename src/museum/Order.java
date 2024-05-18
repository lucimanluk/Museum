/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

/**
 *
 * @author Florin
 */
public class Order {
    private int id;
    private int ticketsSold;
    private int totalPrice;
    private boolean groupDiscount;
    private String paymentType;
    
    public Order(int id, int ticketsSold, int totalPrice, boolean groupDiscount, String paymentType) {
        this.id = id;
        this.ticketsSold = ticketsSold;
        this.totalPrice = totalPrice;
        this.groupDiscount = groupDiscount;
        this.paymentType = paymentType;
    }
    
    public int id() {
        return this.id;
    }
    
    public int getTicketsSold() {
        return this.ticketsSold;
    }
    
    public int getTotalPrice() {
        return this.totalPrice;
    }    
    
    public boolean getGroupDiscount() {
        return this.groupDiscount;
    }
    
    public String paymentType() {
        return this.paymentType;
    }
    
}
