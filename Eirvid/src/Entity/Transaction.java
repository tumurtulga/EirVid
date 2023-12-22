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
public class Transaction {
    
    User userId;
    Rental rentId;

    public Transaction(User userId, Rental rentId) {
        this.userId = userId;
        this.rentId = rentId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Rental getRentId() {
        return rentId;
    }

    public void setRentId(Rental rentId) {
        this.rentId = rentId;
    }
    
    
    
}
