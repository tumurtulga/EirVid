/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eirvid;

import Database.Login;

/**
 * @author Mirae Yu
 * @author Yuna Jang
 * @author Tumurtulga Batjargal
 * @author Jeffersen Sousa Silva
 */
public class Eirvid {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        Login lg = new Login();
        lg.login();
    }

}
