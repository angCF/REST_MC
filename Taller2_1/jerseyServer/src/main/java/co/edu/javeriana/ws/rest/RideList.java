package co.edu.javeriana.ws.rest;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rides")
public class RideList {
    
    private List<Ride> rides;
    
    public RideList() {}
    
    public RideList(List<Ride> rides) {
        this.rides = rides;
    }
    
    @XmlElement(name = "ride")
    public List<Ride> getRides() {
        return rides;
    }
    
    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }
}
