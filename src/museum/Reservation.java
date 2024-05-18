/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museum;

public class Reservation {

    private int id, numberOfTickets;
    private String name,phoneNumber, dateTime;

    public Reservation(int id, String name, String phoneNumber, int numberOfTickets, String dateTime) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.numberOfTickets = numberOfTickets;
        this.dateTime = dateTime;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getNumberOfTickets() {
        return this.numberOfTickets;
    }

    public String getDateTime() {
        return this.dateTime;
    }
}
