/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entity.User;
import Entity.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Mirae Yu
 * @author Yuna Jang
 * @author Tumurtulga Batjargal
 * @author Jeffersen Sousa Silva
 */
public class UserDB extends ConnectionDB {

    User logUser;

    public UserDB(User logUser) {
        this.logUser = logUser;
    }

    public void menuUserChoice() throws ClassNotFoundException {

        System.out.println("-------------------------");
        System.out.println("------REGULAR USER-------");
        System.out.println("-------------------------");
        System.out.println("USER " + "'" + logUser.getUsername() + "'" + " LOGGED IN.");
        System.out.println("1. EDIT MY PROFILE");
        System.out.println("2. RENT MOVIE");
        System.out.println("3. RENTED MOVIES");
        System.out.println("4. LOG-OUT");
        System.out.println("Your choice: ");
        boolean quit = false;
        while (!quit) {
            try {
                Scanner sc = new Scanner(System.in);
                int input = sc.nextInt();
                switch (input) {
                    case 1:
                        modifyUser();
                        break;
                    case 2:
                        rentMovie();
                        break;
                    case 3:
                        rentedMovie();
                        break;
                    case 4:
                        quit = true;
                        Login lg = new Login();
                        lg.login();
                        break;
                    default:
                        System.out.println("ENTER BETWEEN 1 - 4!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("ENTER INTEGER!!!");
            }
        }
    }

    private void modifyUser() {

        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement access = con.prepareStatement("SELECT * FROM user_data WHERE id='" + logUser.getId() + "'");
            access.execute();
            ResultSet rs = access.executeQuery();
            while (rs.next()) {
                int dbid = rs.getInt("id");
                String dbemail = rs.getString("email");
                String dbusername = rs.getString("username");
                String dbpassword = rs.getString("password");
                String dbfirstname = rs.getString("firstname");
                String dblastname = rs.getString("lastname");
                Level dblevel = Level.valueOf(rs.getString("level"));
                System.out.println("YOUR CURRENT PROFILE");
                System.out.format("| %-2s | %-15s | %-15s | %-15s | %-15s | %-15s | %-10s |%n", "id", "email", "username", "password", "firstname", "lastname", "level");
                System.out.format("| %-2s | %-15s | %-15s | %-15s | %-15s | %-15s | %-10s |%n",
                        dbid, dbemail, dbusername, dbpassword, dbfirstname, dblastname, dblevel);
            }
            access.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new email: ");
        String newemail = sc.nextLine();
        System.out.println("Enter new username: ");
        String newusername = sc.nextLine();
        System.out.println("Enter new password: ");
        String newpassword = sc.nextLine();
        System.out.println("Enter new firstname: ");
        String newfirstname = sc.nextLine();
        System.out.println("Enter new lastname: ");
        String newlastname = sc.nextLine();
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement update = con.prepareStatement("UPDATE user_data SET "
                    + "email = '" + newemail + "', "
                    + "username = '" + newusername + "', "
                    + "password = '" + newpassword + "', "
                    + "firstname = '" + newfirstname + "', "
                    + "lastname = '" + newlastname + "' WHERE "
                    + "id = '" + logUser.getId() + "'");
            update.executeUpdate();
            System.out.println("USER PROFILE EDITED.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void solveEquation() throws ClassNotFoundException {

        System.out.println("-------------------------");
        System.out.println("------EQUATION TYPE------");
        System.out.println("-------------------------");
        System.out.println("1. 2x2");
        System.out.println("2. 3x3");
        System.out.println("3. BACK");
        System.out.println("Your choice: ");

        boolean quit = false;
//        while (!quit) {
//            boolean saver = false;
//            try {
//                TwoVariables two;
//                ThreeVariables three;
//                String userInput = "";
//                String solution = "";
//                Scanner sc = new Scanner(System.in);
//                int input = sc.nextInt();
//                switch (input) {
//                    case 1:
//                        userInput = "";
//                        for (int i = 0; i < 2; i++) {
//                            System.out.print("Enter equation #" + (i + 1) + ": ");
//                            if (i != 0) {
//                                userInput += ", ";
//                            }
//                            userInput = userInput + sc.next().trim();
//                        }
//                        two = new TwoVariables(userInput);
//                        solution = "(x, y) " + Arrays.toString(two.solveTwo());
//                        System.out.println(solution);
//                        saver = true;
//                        break;
//                    case 2:
//                        userInput = "";
//                        for (int i = 0; i < 3; i++) {
//                            System.out.print("Enter equation #" + (i + 1) + ": ");
//                            if (i != 0) {
//                                userInput += ", ";
//                            }
//                            userInput = userInput + sc.next().trim();
//                        }
//                        three = new ThreeVariables(userInput);
//                        solution = "(x, y, z) " + Arrays.toString(three.solveThree());
//                        System.out.println(solution);
//                        saver = true;
//                        break;
//                    case 3:
//                        menuUserChoice();
//                        quit = true;
//                        sc.close();
//                        break;
//                    default:
//                        System.out.println("WRONG INPUT, TRY AGAIN");
//                        break;
//                }
//                if (saver) {
//                    try {
//                        Connection con = DriverManager.getConnection(db_url, db_username, db_password);
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//                        Date date = new Date();
//                        PreparedStatement insert = con.prepareStatement("INSERT INTO equation_data (username, equation, solution, date) VALUES ("
//                                + "'" + logUser.getUsername() + "', '" + userInput + "', '" + solution + "', '" + formatter.format(date) + "')"
//                        );
//                        insert.executeUpdate();
//                        System.out.println("EQUATION INSERTED TO THE DATABASE");
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    saver = false;
//                }
//            } catch (InputMismatchException ex) {
//                System.out.println("Bad Input!!! Must be an integer");
//            }
//        }
//    }
//
//    private void accessEquation() {
//
//        try {
//            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
//            PreparedStatement access = con.prepareStatement("SELECT "
//                    + "equation_data.id, "
//                    + "user_data.username, "
//                    + "equation_data.equation, "
//                    + "equation_data.solution, "
//                    + "equation_data.date FROM equation_data INNER JOIN user_data ON "
//                    + "equation_data.username=user_data.username WHERE user_data.id='" + logUser.getId() + "';");
//            access.execute();
//            ResultSet rs = access.executeQuery();
//            System.out.format("| %-2s | %-15s | %-40s | %-40s | %-15s |%n", "id", "username", "equation", "solution", "date");
//            while (rs.next()) {
//                int dbid = rs.getInt("id");
//                String dbusername = rs.getString("username");
//                String dbequation = rs.getString("equation");
//                String dbsolution = rs.getString("solution");
//                String dbdate = rs.getString("date");
//                System.out.format("| %-2s | %-15s | %-40s | %-40s | %-15s |%n",
//                        dbid, dbusername, dbequation, dbsolution, dbdate);
//            }
//            access.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void createUserTable() throws ClassNotFoundException {
//        try {
//            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
//            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS user_data ("
//                    + "id int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
//                    + "email varchar (255),"
//                    + "username varchar(255),"
//                    + "password varchar(255),"
//                    + "firstname varchar(255),"
//                    + "lastname varchar(255),"
//                    + "level varchar(55));"
//            );
//            create.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
    }

    private void rentMovie() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void rentedMovie() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
