/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entity.User;
import Entity.Level;
import java.io.FileReader;
import java.io.IOException;
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

    private void rentMovie() throws ClassNotFoundException {

        System.out.println("-------------------------");
        System.out.println("------MOVIE RENTAL------");
        System.out.println("-------------------------");
        System.out.println("1. MOVIES LIST");
        System.out.println("2. RENT MOVIES");
        System.out.println("3. BACK");
        System.out.println("Your choice: ");

        boolean quit = false;
        while (!quit) {
            boolean saver = false;
            try {
                String userInput = "";
                String solution = "";
                Scanner sc = new Scanner(System.in);
                int input = sc.nextInt();
                switch (input) {
                    case 1:
                        userInput = "";
                        String csvFile = "C:/Users/yjang/OneDrive/Desktop/Eirvid/Eirvid/Movie_Metadata.csv";

                        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
                            String[] nextLine;
                            while ((nextLine = reader.readNext()) != null) {
                                // Access each column of the current line
                                for (String column : nextLine) {
                                    System.out.print(column + " ");
                                }
                                System.out.println(); // Move to the next line
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        menuUserChoice();
                        quit = true;
                        sc.close();
                        break;
                    default:
                        System.out.println("WRONG INPUT, TRY AGAIN");
                        break;
                }
                if (saver) {
                    try {
                        Connection con = DriverManager.getConnection(db_url, db_username, db_password);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        Date date = new Date();
                        PreparedStatement insert = con.prepareStatement("INSERT INTO equation_data (username, equation, solution, date) VALUES ("
                                + "'" + logUser.getUsername() + "', '" + userInput + "', '" + solution + "', '" + formatter.format(date) + "')"
                        );
                        insert.executeUpdate();
                        System.out.println("EQUATION INSERTED TO THE DATABASE");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    saver = false;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Bad Input!!! Must be an integer");
            }
        }

    }

    private void rentedMovie() {
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement access = con.prepareStatement("SELECT "
                    + "movie_data.id, "
                    + "user_data.username, "
                    + "movie_data.original_title, "
                    + "movie_data.original_language, "
                    + "movie_data.price, "
                    + "movie_data.date FROM movie_data INNER JOIN user_data ON "
                    + "movie_data.username=user_data.username WHERE user_data.id='" + logUser.getId() + "';");
            access.execute();
            ResultSet rs = access.executeQuery();
            System.out.format("| %-2s | %-15s | %-40s | %-40s | %-15s |%n", "id", "username", "original_title", "price", "date");
            while (rs.next()) {
                int dbid = rs.getInt("id");
                String dbusername = rs.getString("username");
                String dboriginal_title = rs.getString("original_title");
                String dbprice = rs.getString("price");
                String dbdate = rs.getString("date");
                System.out.format("| %-2s | %-15s | %-40s | %-40s | %-15s |%n",
                        dbid, dbusername, dboriginal_title, dbprice, dbdate);
            }
            access.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUserTable() throws ClassNotFoundException {
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS user_data ("
                    + "id int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                    + "email varchar (255),"
                    + "username varchar(255),"
                    + "password varchar(255),"
                    + "firstname varchar(255),"
                    + "lastname varchar(255),"
                    + "level varchar(55));"
            );
            create.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private static class CSVReader {

        public CSVReader(FileReader fileReader) {
        }
    }

}
