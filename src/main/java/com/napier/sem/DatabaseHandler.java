package com.napier.sem;

import java.sql.*;

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
    protected void connect(String location) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    protected void disconnect() {
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


    protected Report getReportOne() {

        // REPORT 1
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected Report getReportTwo(String continent) {
        // REPORT 2

        try {
            String strSelect = "";
            ResultSet rset = null;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected Report getReportThree(String region) {

        // REPORT 3
        try {
            String strSelect = "";
            ResultSet rset = null;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected Report getReportFour(int num) {
        // REPORT 4

        try {
            String strSelect = "";
            ResultSet rset = null;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected Report getReportFive(String continent, int num) {
        // REPORT 5

        try {
            String strSelect = "";
            ResultSet rset = null;


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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected Report getReportSix(String region, int num) {
        // REPORT 6

        try {

            String strSelect = "";
            ResultSet rset = null;

            strSelect = "select con.code, con.name, con.continent, con.region, con.population, cit.name as capital from country con join city cit on capital=id where region = ? order by population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, region);
            preparedStatement.setInt(2, num);

            rset = preparedStatement.executeQuery();
            CountryReport report = new CountryReport();
            while (rset.next()) {
                CountryReport.CountryReportItem item = report.new CountryReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getInt(5), rset.getString(6));
                report.addItemToReport(item);
            }
            return report;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected Report getReportSeven() {

        // REPORT 7
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code order by city.population DESC;";
            rset = stmt.executeQuery(strSelect);

            CityReport report = new CityReport();

            // Loop on result set and add report items to report
            while (rset.next()) {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }
            return report;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}