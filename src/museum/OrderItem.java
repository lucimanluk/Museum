/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.ArrayList;

public class OrderItem {

    private String timePeriod;
    private String discountType;
    private String ticketType;
    private String photoTax;
    private String videoTax;
    private double price;

    public OrderItem(String timePeriod, String discountType, String ticketType, String photoTax, String videoTax, double price) {
        this.timePeriod = timePeriod;
        this.discountType = discountType;
        this.ticketType = ticketType;
        this.photoTax = photoTax;
        this.videoTax = videoTax;
        this.price = price;
    }

    public String getTimePeriod() {
        return this.timePeriod;
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
}
