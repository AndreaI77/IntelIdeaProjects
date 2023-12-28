package flightsfx.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *  class to define a flight object
 */
public class Flight {
    private String number;
    private String destination;
    private LocalDateTime departure;
    private LocalTime duration;

    /**
     * Constructor. Returns the flight object with the property number
     * @param number - String representig a flight number
     */
    public Flight(String number) {
        this.number = number;
    }

    /**
     *Constructor. Creates flight object with all atributes set
     * @param number - String representig a flight number
     * @param destination -String destination
     * @param departure LocalDateTime object with format dd/MM/yyyy HH:mm
     * @param duration LocalTime object with format HH:mm
     */
    public Flight(String number, String destination, LocalDateTime departure, LocalTime duration) {
        this.number = number;
        this.destination = destination;
        this.departure = departure;
        this.duration = duration;
    }

    /**
     *getter of the attribute number
     * @return flight number as String
     */
    public String getNumber() {
        return number;
    }

    /**
     *setter  of the attribute number
     * @param number String representing flight number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * getter of the attribute destination
     * @return returns destination of flight as String
     */
    public String getDestination() {
        return destination;
    }

    /**
     * setter of the attribute destination
     * @param destination String representing the destination property of the object
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalTime getDuration2() {
        return duration;
    }

    /**
     * getter of the attribute departure
     * @return return the departure attribute as String with the format dd/MM/yyyy HH:mm
     */
    public String getDeparture() {
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return departure.format(fm);

    }

    /**
     * setter of the attribute departure
     * @param departure needs a LoclaDateTime object with format dd/MM/yyyy HH:mm
     */
    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    /**
     * getter of the attribute duration
     * @return returns duration attribute as String with format H:mm
     */
    public String getDuration() {
        DateTimeFormatter tmFormat = DateTimeFormatter.ofPattern("H:mm");
        return duration.format(tmFormat);
    }

    /**
     * getter of the attribute duration with another format
     * @return returns duration attribute as String with format HH:mm
     */
    public String getDurationFormat(){
        DateTimeFormatter longFormat = DateTimeFormatter.ofPattern("HH:mm");
        return duration.format(longFormat);
    }

    /**
     * setter of the property duration
     * @param duration needs a LocalTime object with the format HH:mm
     */
    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    /**
     * toString method of the class Flight
     * @return String representing the object
     */
    @Override
    public String toString(){

        return this.number+";"+this.destination+";"+this.departure.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+";"+this.duration.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
