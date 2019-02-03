package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * Purpose of class: Database Handler class to deal with the database
 */
public class DatabaseHandler {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {

        try {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 3;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database.......");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database using root and password
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "pass");
                System.out.println("Successfully connected!\n");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
                System.out.println("--------------------------------------");
                System.out.println("Connection to database closed");
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public ArrayList<Report> getReport(int reportNumber) {

        ArrayList<Report> results = new ArrayList<>();

        String strSelect = "";
        ResultSet rset = null;

        try {

            Statement stmt = con.createStatement();

            if (reportNumber > 34 || reportNumber <= 0){

                throw new Exception("Not a valid report number");
            }
            switch (reportNumber) {

                case 1:
                    strSelect =
                            "select code, name, continent, region, population, capital from country order by population DESC;";
                    rset = stmt.executeQuery(strSelect);
                    while (rset.next()) {
                        CountryReport r = new CountryReport(
                                rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(4),rset.getInt(5));
                        results.add(r);
                    }
                    return results;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return results;
    }
}

