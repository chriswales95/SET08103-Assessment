package com.napier.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Purpose of class: Main Application
 */
@SpringBootApplication
@RestController
public class App {

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    /**
     * Main method
     *
     * @param args args passed into main
     */
    public static void main(String[] args) {

        // Connect to database
        if (args.length < 1) {
            connect("localhost:33060");
        } else {
            connect(args[0]);
        }

        SpringApplication.run(App.class, args);
    }

    protected static void connect(String location) {
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


    @RequestMapping("r1")
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

    @RequestMapping("r2")
    protected Report getReportTwo(@RequestParam(value = "continent") String continent) {
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

    protected Report getReportEight(String continent) {

        // REPORT 8
        try {

            ResultSet rset = null;
            String select = "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where country.continent = ? order by city.population DESC;";

            PreparedStatement preparedStatement = con.prepareStatement(select);
            preparedStatement.setString(1, continent);
            rset = preparedStatement.executeQuery();

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

    protected Report getReportNine(String region) {

        // REPORT 9
        try {

            ResultSet rset = null;
            String select = "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where country.region = ? order by city.population DESC;";

            PreparedStatement preparedStatement = con.prepareStatement(select);
            preparedStatement.setString(1, region);
            rset = preparedStatement.executeQuery();

            CityReport report = new CityReport();
            while (rset.next()){
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected Report getReportTen(String country) {

        // REPORT 10
        try {

            ResultSet rset = null;

            String select =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where country.name = ? order by city.population DESC;";
            PreparedStatement preparedStatement = con.prepareStatement(select);
            preparedStatement.setString(1, country);
            rset = preparedStatement.executeQuery();
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

    protected Report getReportEleven(String district) {

        // REPORT 11
        try {
            ResultSet rset = null;

            String select =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where district = ? order by city.population DESC;";
            PreparedStatement preparedStatement = con.prepareStatement(select);
            preparedStatement.setString(1, district);
            rset = preparedStatement.executeQuery();
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

    protected Report getReportTwelve(int num)  // REPORT 12
    {
        try {
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setInt(1, num);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next())
            {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report;
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    protected Report getReportThirteen(int num, String continent)  // REPORT 13
    {
        try {
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where continent = ? order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, continent);
            preparedStatement.setInt(2, num);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next())
            {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report;
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    protected Report getReportFourteen(int num, String region)  // REPORT 14
    {
        try
        {
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where region = ? order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, region);
            preparedStatement.setInt(2, num);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next())
            {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report;
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    protected Report getReportFifteen(int num, String country)  // REPORT 15
    {
        try
        {
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where country.name = ? order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, country);
            preparedStatement.setInt(2, num);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next())
            {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report;
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    protected Report getReportSixteen(int num, String district)  // REPORT 16
    {
        try
        {
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on CountryCode=code where city.district = ? order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, district);
            preparedStatement.setInt(2, num);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next())
            {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report;
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    protected Report getReportSeventeen() {

        // REPORT 17
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on id=capital order by city.population DESC;";
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

    protected Report getReportEighteen(String continent)  // REPORT 18
    {
        try {
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on id=capital where continent = ? order by city.population DESC;;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, continent);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

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

    protected Report getReportNineteen(String region)  // REPORT 19
    {
        try {
            String strSelect = "";
            ResultSet rset = null;

            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on id=capital where region = ? order by city.population DESC;;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, region);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

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

