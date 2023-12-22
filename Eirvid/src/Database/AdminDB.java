/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Entity.Admin;
import Entity.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Mirae Yu
 * @author Yuna Jang
 * @author Tumurtulga Batjargal
 * @author Jeffersen Sousa Silva
 */
public class AdminDB extends ConnectionDB {

    Admin logAdmin;
    
    /*
    Constructor
    */

    public AdminDB(Admin logAdmin) {
        this.logAdmin = logAdmin;
    }

    public void menuAdminChoice() throws ClassNotFoundException {

        System.out.println("----------------------------------");
        System.out.println("ADMIN " + "'" + logAdmin.getUsername() + "'" + " LOGGED IN.");
        System.out.println("-------------------------");
        System.out.println("------LOGIN AS AN ADMIN--");
        System.out.println("-------------------------");
        System.out.println();
        System.out.println("1. EDIT MY PROFILE");
        System.out.println("2. ACCESS REGULAR USER LIST");
        System.out.println("3. REMOVE REGULAR USER");
        System.out.println("4. REVIEW REGULAR USER");
        System.out.println("5. LOG-OUT");
        System.out.println("Your choice: ");
        boolean quit = false;
        while (!quit) {
            try {
                Scanner sc = new Scanner(System.in);
                int input = sc.nextInt();
                switch (input) {
                    case 1:
                        modifyAdmin();
                        break;
                    case 2:
                        accessUser();
                        break;
                    case 3:
                        deleteUser();
                        break;
                    case 4:
                        reviewUser();
                        break;
                    case 5:
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
    
    /*
    reviews what user did and what equations.
    Only admin is able to see all users activity.
    */

    public void reviewUser() {
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement access = con.prepareStatement("SELECT "
                    + "equation_data.id, "
                    + "user_data.username, "
                    + "equation_data.equation, "
                    + "equation_data.solution, "
                    + "equation_data.date FROM equation_data INNER JOIN user_data ON "
                    + "equation_data.username=user_data.username;");
            access.execute();
            ResultSet rs = access.executeQuery();
            System.out.format("| %-2s | %-15s | %-40s | %-40s | %-15s |%n", "id", "username", "equation", "solution", "date");
            while (rs.next()) {
                int dbid = rs.getInt("id");
                String dbusername = rs.getString("username");
                String dbequation = rs.getString("equation");
                String dbsolution = rs.getString("solution");
                String dbdate = rs.getString("date");
                System.out.format("| %-2s | %-15s | %-40s | %-40s | %-15s |%n",
                        dbid, dbusername, dbequation, dbsolution, dbdate);
            }
            access.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
    deletes users from the list.
    Only admin is able to delete users from the list.
    */

    public void deleteUser() throws ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        String dbusername = "";
        System.out.println("Enter your username: ");
        String name = sc.next();
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            if (name.equals(dbusername)) {
                PreparedStatement delete = con.prepareStatement("DELETE FROM user_data, equation_data  WHERE username = '" + name + "'"
                );
                delete.executeUpdate();
            } else {
                System.out.println("User does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
    accesses all user list and shows it
    Only admin is able to access all user list.
    */

    public void accessUser() throws ClassNotFoundException {
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement access = con.prepareStatement("SELECT * FROM user_data");
            access.execute();
            ResultSet rs = access.executeQuery();
            System.out.format("| %-2s | %-15s | %-15s | %-15s | %-15s | %-10s |%n", "id", "username", "password", "firstname", "lastname", "level");
            while (rs.next()) {
                int dbid = rs.getInt("id");
                String dbusername = rs.getString("username");
                String dbpassword = rs.getString("password");
                String dbfirstname = rs.getString("firstname");
                String dblastname = rs.getString("lastname");
                Level dblevel = Level.valueOf(rs.getString("level"));
                
                System.out.format("| %-2s | %-15s | %-15s | %-15s | %-15s | %-10s |%n",
                        dbid, dbusername, dbpassword, dbfirstname, dblastname, dblevel);
            }
            access.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
    editing own profile. Also it shows admins own database.
    */

    private void modifyAdmin() {
        
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement access = con.prepareStatement("SELECT * FROM admin_data WHERE id='" + logAdmin.getId() + "'");
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
        String newEmail = sc.nextLine();
        System.out.println("Enter new username: ");
        String newUname = sc.nextLine();
        System.out.println("Enter new password: ");
        String newPass = sc.nextLine();
        System.out.println("Enter new firstname: ");
        String newFname = sc.nextLine();
        System.out.println("Enter new lastname: ");
        String newLname = sc.nextLine();
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement update = con.prepareStatement("UPDATE admin_data SET "
                    + "email = '" + newEmail + "', "
                    + "username = '" + newUname + "', "
                    + "password = '" + newPass + "', "
                    + "firstname = '" + newFname + "', "
                    + "lastname = '" + newLname + "' WHERE "
                    + "id = '" + logAdmin.getId() + "'");
            update.executeUpdate();
            System.out.println("ADMIN PROFILE EDITED.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    inserting database into sql 
    Once inserted, this is not necessarily needed.
     */
    
    public void insertAdminData() throws ClassNotFoundException {

        String email = "admin@admin.ie";
        String username = "admin";
        String password = "admin";
        String fname = "Tumurtulga";
        String lname = "Batjargal";
        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement insert = con.prepareStatement("INSERT INTO admin_data (email, username, password, firstname, lastname, level) VALUES ("
                    + "'" + email + "', '" + username + "', '" + password + "', '" + fname + "', '" + lname + "', '" + Level.admin + "')"
            );
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    creating a table in sql dabatase
    Once table is created, this is not necessarily needed.
     */
    
    public void createAdminTable() throws ClassNotFoundException {

        try {
            Connection con = DriverManager.getConnection(db_url, db_username, db_password);
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS admin_data ("
                    + "id int NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                    + "email varchar(255) UNIQUE,"
                    + "username varchar(255) UNIQUE,"
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
}
