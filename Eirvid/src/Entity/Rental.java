/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author deece
 */
public class Rental {
    
    int rentId;
    Movie titleId;

    public Rental(int rentId, Movie titleId) {
        this.rentId = rentId;
        this.titleId = titleId;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public Movie getTitleId() {
        return titleId;
    }

    public void setTitleId(Movie titleId) {
        this.titleId = titleId;
    }
    
    
    
    
}
