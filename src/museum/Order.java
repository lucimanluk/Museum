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

    private int id, ticketsSold;
    private double totalPrice;
    private String groupDiscount, paymentType;

    public Order(int id, int ticketsSold, double totalPrice, String groupDiscount, String paymentType) {
        this.id = id;
        this.ticketsSold = ticketsSold;
        this.totalPrice = totalPrice;
        this.groupDiscount = groupDiscount;
        this.paymentType = paymentType;
    }

    public int getID() {
        return this.id;
    }

    public int getTicketsSold() {
        return this.ticketsSold;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public String getGroupDiscount() {
        return this.groupDiscount;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

}
