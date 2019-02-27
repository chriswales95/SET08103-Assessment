package com.napier.sem;

import java.sql.*;
import java.util.Scanner;

/**
 * Purpose of class: Database Handler class of type Singleton to deal with the database connection and reports
 */
public class DatabaseHandler {

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    /**
     * Instance of DatabaseHandler
     */
    private static DatabaseHandler _instance = null;

    /**
     * Method that instantiates _instance if null
     *
     * @return Instance of DatabaseHandler
     */
    public static DatabaseHandler Instance() {

        if (_instance == null)
            _instance = new DatabaseHandler();

        return _instance;
    }

    /**
     * Disabled constructor
     */
    private DatabaseHandler() {
    }

    /**
     * Connect to the MySQL database.
     */
    public void connect() {

        // try to load database driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // try to connect to database
        int retries = 6;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database.......");
            try {
                // Wait a bit for db to start
                Thread.sleep(5000);
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

    /**
     * ArrayList which returns report based on report number
     *
     * @param reportNumber the report that needs to be generated
     * @return results
     */
    public Report getReport(int reportNumber) {

        String strSelect = "";
        ResultSet rset = null;

        try {
            Statement stmt = con.createStatement();

            //Ensure valid report number
            if (reportNumber > 36 || reportNumber <= 0) {

                throw new Exception("Not a valid report number");
            }

            // REPORT 1
            if (reportNumber == 1) {

                strSelect =
                        "select con.code, con.name, con.continent, con.region, con.population, cit.name as capital from country con join city cit on capital=id order by population DESC;";
                rset = stmt.executeQuery(strSelect);
                CountryReport report = new CountryReport();

                // Loop on result set and add report items to report
                while (rset.next()) {
                    CountryReport.CountryReportItem item = report.new CountryReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getInt(5), rset.getString(6));
                    report.addItemToReport(item);
                }
                return report;
            }


            // REPORT 2
            if (reportNumber == 2) {

                Scanner scanner = new Scanner(System.in);
                System.out.println("\nEnter Continent: "); // Prompt user for input
                String continent = scanner.nextLine();
                strSelect =
                        "select con.code, con.name, con.continent, con.region, con.population, cit.name as capital from country con join city cit on capital=id where continent = ? order by population DESC;";

                PreparedStatement preparedStatement = con.prepareStatement(strSelect);
                preparedStatement.setString(1, continent);

                rset = preparedStatement.executeQuery();
                CountryReport report = new CountryReport();

                // Loop on result set and add report items to report
                while (rset.next()) {
                    CountryReport.CountryReportItem item = report.new CountryReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getInt(5), rset.getString(6));
                    report.addItemToReport(item);
                }
                return report;
            }

            // REPORT 3
            if (reportNumber == 3) {

                Scanner scanner = new Scanner(System.in);
                System.out.println("\nEnter a region: ");
                String region = scanner.nextLine();
                strSelect = "select con.code, con.name, con.continent, con.region, con.population, cit.name as capital from country con join city cit on capital=id where region = ? order by population DESC;";

                PreparedStatement preparedStatement = con.prepareStatement(strSelect);
                preparedStatement.setString(1, region);

                rset = preparedStatement.executeQuery();
                CountryReport report = new CountryReport();
                while (rset.next()) {
                    CountryReport.CountryReportItem item = report.new CountryReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getInt(5), rset.getString(6));
                    report.addItemToReport(item);
                }
                return report;
            }

            // REPORT 4
            if (reportNumber == 4) {

                Scanner scanner = new Scanner(System.in);
                System.out.println("\nEnter number: ");
                int num = scanner.nextInt();
                strSelect = "select con.code, con.name, con.continent, con.region, con.population, cit.name as capital from country con join city cit on capital=id order by population DESC LIMIT ?;";

                PreparedStatement preparedStatement = con.prepareStatement(strSelect);
                preparedStatement.setInt(1, num);

                rset = preparedStatement.executeQuery();
                CountryReport report = new CountryReport();
                while (rset.next()) {
                    CountryReport.CountryReportItem item = report.new CountryReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getInt(5), rset.getString(6));
                    report.addItemToReport(item);
                }
                return report;
            }

            // REPORT 5
            if (reportNumber == 5) {

                Scanner scanner = new Scanner(System.in);
                System.out.println("\nEnter Continent: ");
                String continent = scanner.nextLine();
                System.out.println("\nEnter number: ");
                int num = scanner.nextInt();
                strSelect = "select con.code, con.name, con.continent, con.region, con.population, cit.name as capital from country con join city cit on capital=id where continent = ? order by population DESC LIMIT ?;";

                PreparedStatement preparedStatement = con.prepareStatement(strSelect);
                preparedStatement.setString(1, continent);
                preparedStatement.setInt(2, num);

                rset = preparedStatement.executeQuery();
                CountryReport report = new CountryReport();
                while (rset.next()) {
                    CountryReport.CountryReportItem item = report.new CountryReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getInt(5), rset.getString(6));
                    report.addItemToReport(item);
                }
                return report;
            } else {
                System.out.println("Not implemented yet");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}