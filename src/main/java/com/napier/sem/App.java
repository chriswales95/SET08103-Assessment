package com.napier.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

@SpringBootApplication
@RestController
public class App {

    private static Connection con = null;

    /**
     * Main method
     *
     * @param args args passed into main
     */
    public static void main(String[] args) {

        // Connect to database
        if (args.length < 1) {
            App.connect("localhost:33060");
        } else {
            App.connect(args[0]);
        }

        SpringApplication.run(App.class, args);
    }

    /**
     * Connect to the MySQL database.
     */
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
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    protected static void printReport(Report report) {

        if (report instanceof CountryReport) {
            CountryReport.printReportHeader();

            for (CountryReport.CountryReportItem item : ((CountryReport) report).get_reportsItems()) {
                System.out.printf(
                        CountryReport.getReportFormat(), item.get_code(), item.get_name(), item.get_continent(), item.get_region(), item.get_population(), item.get_capital());
                System.out.print("\n");
            }
        }

        if (report instanceof CityReport){
            CityReport.printReportHeader();
            for(CityReport.CityReportItem item : ((CityReport) report).get_reportsItems()){
                System.out.printf(CityReport.getReportFormat(), item.get_name(), item.get_country(), item.get_district(), item.get_population());
                System.out.print("\n");
            }
        }
    }

    /*This is report one and it is designed to produce a report of all the countries in the world organised by
    the largest population to the smallest. Firstly we use a try statement and then we try and call the report, this is done
    by calling on the array list which holds the 'CountryReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'country' table which we can use to combine that table with the 'city' table and then produce
    the necessary report. After we have intialised the report we then do a sql statement which accesses the tables
    we need. Firstly we call upon the code, name, continent, region and population from the country table, we then
    join the city and country tables on the capital form the country report and the name from the city report.
    This then allows us to see the name of a country's capital city instead of just the id. We then order the report
    by the population of each country and then finish our sql statement. Finally we then create a new 'CountryReport'
    and using this we get a string from that report which it sends back to us (these strings are just the code, name, region etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_one")
    protected ArrayList<CountryReport.CountryReportItem> getReportOne() {

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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report two and it is designed to produce a report of all the countries in a continent organised by
    the largest population to the smallest. Firstly, we need a parameter passed into the function named 'continent', the user will
    provide this when entering the url which will allow us to refine our report down to the continent we require. We then use a try
    statement and then we try and call the report, this is done by calling on the array list which holds the 'CountryReport' which is
    what we need to call upon the correct fields from the database. This report gives us access to the 'country' table which we can use
    to combine that table with the 'city' table and then produce the necessary report. After we have intialised the report we then do a
    sql statement which accesses the tables we need. Firstly we call upon the code, name, continent, region and population from the country
    table, we then join the city and country tables on the capital form the country report and the name from the city report.
    This then allows us to see the name of a country's capital city instead of just the id. We then state to the sql that we do not know the
    continent that we are searching for so we use a question mark to tell the statement this, we then order the report
    by the population of each country and then finish our sql statement. Next we must prepare the statement by telling it we have a parameter
    named 'continent' and which parameter it is (in this case 1). Finally we then create a new 'CountryReport'
    and using this we get a string from that report which it sends back to us (these strings are just the code, name, region etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_two")
    protected ArrayList<CountryReport.CountryReportItem> getReportTwo(@RequestParam(value = "continent") String continent) {
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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    /*This is report three and it is designed to produce a report of all the countries in a region organised by
    the largest population to the smallest. Firstly, we need a parameter passed into the function named 'region', the user will
    provide this when entering the url which will allow us to refine our report down to the region we require. We then use a try
    statement and then we try and call the report, this is done by calling on the array list which holds the 'CountryReport' which is
    what we need to call upon the correct fields from the database. This report gives us access to the 'country' table which we can use
    to combine that table with the 'city' table and then produce the necessary report. After we have intialised the report we then do a
    sql statement which accesses the tables we need. Firstly we call upon the code, name, continent, region and population from the country
    table, we then join the city and country tables on the capital form the country report and the name from the city report.
    This then allows us to see the name of a country's capital city instead of just the id. We then state to the sql that we do not know the
    region that we are searching for so we use a question mark to tell the statement this, we then order the report
    by the population of each country and then finish our sql statement. Next we must prepare the statement by telling it we have a parameter
    named 'region' and which parameter it is (in this case 1). Finally we then create a new 'CountryReport'
    and using this we get a string from that report which it sends back to us (these strings are just the code, name, region etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_three")
    protected ArrayList<CountryReport.CountryReportItem> getReportThree(@RequestParam(value = "region") String region) {

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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report four and it is designed to produce a report of the top N populated countries in the world where N is provided by
    the user. Firstly, we need a parameter passed into the function named 'number', the user will provide this when entering the url
    which will allow us to refine our report down to the select amount of countries we require. We then use a try statement and then we try and call the report,
    this is done by calling on the array list which holds the 'CountryReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'country' table which we can use to combine that table with the 'city' table and then produce the necessary report.
    After we have intialised the report we then do a sql statement which accesses the tables we need.
    Firstly we call upon the code, name, continent, region and population from the country table, we then join the city and country tables on the capital
    form the country report and the name from the city report. This then allows us to see the name of a country's capital city instead of just the id.
    We then state to the sql that we do not know the number that we are refining down to so we use a question mark to tell the statement this, we then order the report
    by the population of each country and then finish our sql statement. Next we must prepare the statement by telling it we have a parameter
    named 'number' and which parameter it is (in this case 1). Finally we then create a new 'CountryReport'
    and using this we get a string from that report which it sends back to us (these strings are just the code, name, region etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_four")
    protected ArrayList<CountryReport.CountryReportItem> getReportFour(@RequestParam(value = "number") String number) {
        // REPORT 4

        int num = Integer.parseInt(number);
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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report five and it is designed to produce a report of the top N populated countries in a continent where N is provided by
    the user. Firstly, we need parameters passed into the function named 'continent' and 'number', the user will provide these when entering the url
    which will allow us to refine our report down to the select amount of countries we require from the selected continent. We then use a try statement and then we try and call the report,
    this is done by calling on the array list which holds the 'CountryReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'country' table which we can use to combine that table with the 'city' table and then produce the necessary report.
    After we have intialised the report we then do a sql statement which accesses the tables we need.
    Firstly we call upon the code, name, continent, region and population from the country table, we then join the city and country tables on the capital
    form the country report and the name from the city report. This then allows us to see the name of a country's capital city instead of just the id.
    We then state to the sql that we do not know the continent or the number that we are refining down to so we use question marks to tell the statement this, we then order the report
    by the population of each country and then finish our sql statement. Next we must prepare the statement by telling it we have parameters
    named 'continent' and 'number' and which parameter it is (in this case 1 and 2). Finally we then create a new 'CountryReport'
    and using this we get a string from that report which it sends back to us (these strings are just the code, name, region etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_five")
    protected ArrayList<CountryReport.CountryReportItem> getReportFive(@RequestParam(value = "continent") String continent, @RequestParam(value = "number") String number) {
        // REPORT 5

        int num = Integer.parseInt(number);
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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report six and it is designed to produce a report of the top N populated countries in a region where N is provided by
    the user. Firstly, we need parameters passed into the function named 'region' and 'number', the user will provide these when entering the url
    which will allow us to refine our report down to the select amount of countries we require from the selected region. We then use a try statement and then we try and call the report,
    this is done by calling on the array list which holds the 'CountryReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'country' table which we can use to combine that table with the 'city' table and then produce the necessary report.
    After we have intialised the report we then do a sql statement which accesses the tables we need.
    Firstly we call upon the code, name, continent, region and population from the country table, we then join the city and country tables on the capital
    form the country report and the name from the city report. This then allows us to see the name of a country's capital city instead of just the id.
    We then state to the sql that we do not know the region or the number that we are refining down to so we use question marks to tell the statement this, we then order the report
    by the population of each country and then finish our sql statement. Next we must prepare the statement by telling it we have parameters
    named 'region' and 'number' and which parameter it is (in this case 1 and 2). Finally we then create a new 'CountryReport'
    and using this we get a string from that report which it sends back to us (these strings are just the code, name, region etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_six")
    protected ArrayList<CountryReport.CountryReportItem> getReportSix(@RequestParam(value = "region") String region, @RequestParam(value = "number") String number) {
        // REPORT 6

        int num = Integer.parseInt(number);
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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report seven and it is designed to produce a report of All the cities in the world organised by largest population to smallest.
    Firstly we use a try statement and then we try and call the report, this is done by calling on the array list which holds the 'CityReport'
    which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce
    the necessary report. After we have intialised the report we then do a sql statement which accesses the tables
    we need. Firstly we call upon the name, district and population from the city table and name from the country table, we then
    join the city and country tables on the CountryCode form the city report and the code from the country report.
    This then allows us to see the name of a city's country instead of just the id. We then order the report
    by the population of each city and then finish our sql statement. Finally we then create a new 'CityReport'
    and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_seven")
    protected ArrayList<CityReport.CityReportItem> getReportSeven() {

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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report eight and it is designed to produce a report of All the cities in a continent organised by largest population to smallest.
    Firstly, we need a parameter passed into the function named 'continent', the user will provide this when entering the url
    which will allow us to refine our report down to the cities in a continent of the users choosing.
    Then we use a try statement and then we try and call the report, this is done by calling on the array list which holds the 'CityReport'
    which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce
    the necessary report. After we have intialised the report we then do a sql statement which accesses the tables
    we need. Firstly we call upon the name, district and population from the city table and name from the country table, we then
    join the city and country tables on the CountryCode form the city report and the code from the country report.
    This then allows us to see the name of a city's country instead of just the id. We then state that the continent is unkwnown so we make
    'continent' equal a question and then order the report by the population of each city and then finish our sql statement.
    Next we must prepare the statement by telling it we have a parameter named 'continent' and which parameter it is (in this case 1).
    Finally we then create a new 'CityReport' and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_eight")
    protected ArrayList<CityReport.CityReportItem> getReportEight(@RequestParam(value = "continent") String continent) {

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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report nine and it is designed to produce a report of All the cities in a region organised by largest population to smallest.
    Firstly, we need a parameter passed into the function named 'region', the user will provide this when entering the url
    which will allow us to refine our report down to the cities in a region of the users choosing.
    Then we use a try statement and then we try and call the report, this is done by calling on the array list which holds the 'CityReport'
    which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce
    the necessary report. After we have intialised the report we then do a sql statement which accesses the tables
    we need. Firstly we call upon the name, district and population from the city table and name from the country table, we then
    join the city and country tables on the CountryCode form the city report and the code from the country report.
    This then allows us to see the name of a city's country instead of just the id. We then state that the region is unkwnown so we make
    'region' equal a question and then order the report by the population of each city and then finish our sql statement.
    Next we must prepare the statement by telling it we have a parameter named 'region' and which parameter it is (in this case 1).
    Finally we then create a new 'CityReport' and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_nine")
    protected ArrayList<CityReport.CityReportItem> getReportNine(@RequestParam(value = "region") String region) {

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

            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report ten and it is designed to produce a report of All the cities in a country organised by largest population to smallest.
    Firstly, we need a parameter passed into the function named 'country', the user will provide this when entering the url
    which will allow us to refine our report down to the cities in a country of the users choosing.
    Then we use a try statement and then we try and call the report, this is done by calling on the array list which holds the 'CityReport'
    which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce
    the necessary report. After we have intialised the report we then do a sql statement which accesses the tables
    we need. Firstly we call upon the name, district and population from the city table and name from the country table, we then
    join the city and country tables on the CountryCode form the city report and the code from the country report.
    This then allows us to see the name of a city's country instead of just the id. We then state that the country is unkwnown so we make
    'country' equal a question and then order the report by the population of each city and then finish our sql statement.
    Next we must prepare the statement by telling it we have a parameter named 'country' and which parameter it is (in this case 1).
    Finally we then create a new 'CityReport' and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_ten")
    protected ArrayList<CityReport.CityReportItem> getReportTen(@RequestParam(value = "country") String country) {

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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report eleven and it is designed to produce a report of All the cities in a district organised by largest population to smallest.
    Firstly, we need a parameter passed into the function named 'district', the user will provide this when entering the url
    which will allow us to refine our report down to the cities in a district of the users choosing.
    Then we use a try statement and then we try and call the report, this is done by calling on the array list which holds the 'CityReport'
    which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce
    the necessary report. After we have intialised the report we then do a sql statement which accesses the tables
    we need. Firstly we call upon the name, district and population from the city table and name from the country table, we then
    join the city and country tables on the CountryCode form the city report and the code from the country report.
    This then allows us to see the name of a city's country instead of just the id. We then state that the district is unkwnown so we make
    'district' equal a question and then order the report by the population of each city and then finish our sql statement.
    Next we must prepare the statement by telling it we have a parameter named 'district' and which parameter it is (in this case 1).
    Finally we then create a new 'CityReport' and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_eleven")
    protected ArrayList<CityReport.CityReportItem> getReportEleven(@RequestParam(value = "district") String district) {

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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*This is report twelve and it is designed to produce a report of the top N populated cities in the world where N is provided by
    the user. Firstly, we need a parameter passed into the function named 'number', the user will provide this when entering the url
    which will allow us to refine our report down to the select amount of cities we require. We then use a try statement and then we try and call the report,
    this is done by calling on the array list which holds the 'CityReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce the necessary report.
    After we have intialised the report we then do a sql statement which accesses the tables we need.
    Firstly we call upon the name, district, population and name from the country table, we then join the city and country tables on the CountryCode
    form the city table and the code from the country table. This then allows us to see if a city shown is a capital city.
    We then state to the sql that we do not know the number that we are refining down to so we use a question mark to tell the statement this, we then order the report
    by the population of each city and then finish our sql statement. Next we must prepare the statement by telling it we have a parameter
    named 'number' and which parameter it is (in this case 1). Finally we then create a new 'CityReport'
    and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_twelve")
    protected ArrayList<CityReport.CityReportItem> getReportTwelve(@RequestParam(value = "number") int num)  // REPORT 12
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

            return report.get_reportsItems();
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*This is report thirteen and it is designed to produce a report of the top N populated cities in a continent where N is provided by
    the user. Firstly, we need two parameters passed into the function named 'number' and 'continent', the user will provide these when entering the url
    which will allow us to refine our report down to the select amount of cities we require from the continent selected. We then use a try statement and then we try and call the report,
    this is done by calling on the array list which holds the 'CityReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce the necessary report.
    After we have intialised the report we then do a sql statement which accesses the tables we need.
    Firstly we call upon the name, district, population and name from the country table, we then join the city and country tables on the CountryCode
    form the city table and the code from the country table. This then allows us to see if a city shown is a capital city.
    We then state to the sql that we only want a report from a certain continent so we set the continent to a question mark and we do not know the number that
    we are refining down to so we use a question mark for this aswell, to tell the statement this, we then order the report
    by the population of each city and then finish our sql statement. Next we must prepare the statement by telling it we have two parameters
    named 'number' and 'continent' and which parameters they are (in this case 1 and 2). Finally we then create a new 'CityReport'
    and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_thirteen")
    protected ArrayList<CityReport.CityReportItem> getReportThirteen(@RequestParam(value = "number")int num, @RequestParam(value = "continent")String continent)  // REPORT 13
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

            return report.get_reportsItems();
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*This is report fourteen and it is designed to produce a report of the top N populated cities in a region where N is provided by
    the user. Firstly, we need two parameters passed into the function named 'number' and 'region', the user will provide these when entering the url
    which will allow us to refine our report down to the select amount of cities we require from the region selected. We then use a try statement and then we try and call the report,
    this is done by calling on the array list which holds the 'CityReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce the necessary report.
    After we have intialised the report we then do a sql statement which accesses the tables we need.
    Firstly we call upon the name, district, population and name from the country table, we then join the city and country tables on the CountryCode
    form the city table and the code from the country table. This then allows us to see if a city shown is a capital city.
    We then state to the sql that we only want a report from a certain region so we set the region to a question mark and we do not know the number that
    we are refining down to so we use a question mark for this aswell, to tell the statement this, we then order the report
    by the population of each city and then finish our sql statement. Next we must prepare the statement by telling it we have two parameters
    named 'number' and 'region' and which parameters they are (in this case 1 and 2). Finally we then create a new 'CityReport'
    and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_Fourteen")
    protected ArrayList<CityReport.CityReportItem> getReportFourteen(@RequestParam(value = "number") int num, @RequestParam(value = "region") String region)  // REPORT 14
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

            return report.get_reportsItems();
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*This is report fifteen and it is designed to produce a report of the top N populated cities in a country where N is provided by
    the user. Firstly, we need two parameters passed into the function named 'number' and 'country', the user will provide these when entering the url
    which will allow us to refine our report down to the select amount of cities we require from the country selected. We then use a try statement and then we try and call the report,
    this is done by calling on the array list which holds the 'CityReport' which is what we need to call upon the correct fields from the database.
    This report gives us access to the 'city' table which we can use to combine that table with the 'country' table and then produce the necessary report.
    After we have intialised the report we then do a sql statement which accesses the tables we need.
    Firstly we call upon the name, district, population and name from the country table, we then join the city and country tables on the CountryCode
    form the city table and the code from the country table. This then allows us to see if a city shown is a capital city.
    We then state to the sql that we only want a report from a certain country so we set the country to a question mark and we do not know the number that
    we are refining down to so we use a question mark for this aswell, to tell the statement this, we then order the report
    by the population of each city and then finish our sql statement. Next we must prepare the statement by telling it we have two parameters
    named 'number' and 'country' and which parameters they are (in this case 1 and 2). Finally we then create a new 'CityReport'
    and using this we get a string from that report which it sends back to us (these strings are just the name, district etc).
    Once this has been completed we then return the report, however if the try statement fails then we catch this by sending back
    an error report to the user, we then return null and then that is the end of the function.*/
    @RequestMapping("report_fifteen")
    protected ArrayList<CityReport.CityReportItem> getReportFifteen(@RequestParam(value = "number") int num, @RequestParam(value = "country") String country)  // REPORT 15
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

            return report.get_reportsItems();
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping("report_sixteen")
    protected ArrayList<CityReport.CityReportItem> getReportSixteen(@RequestParam(value = "number") int num, @RequestParam(value = "district") String district)  // REPORT 16
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

            return report.get_reportsItems();
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping("report_seventeen")
    protected ArrayList<CityReport.CityReportItem> getReportSeventeen() {

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
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping("report_eighteen")
    protected ArrayList<CityReport.CityReportItem> getReportEighteen(@RequestParam(value = "continent") String continent)  // REPORT 18
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

            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping("report_nineteen")
    protected ArrayList<CityReport.CityReportItem> getReportNineteen(@RequestParam(value = "region") String region)  // REPORT 19
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

            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping("report_twenty")
    protected ArrayList<CityReport.CityReportItem> getReportTwenty(@RequestParam(value = "number") String number)  // REPORT 20
    {
        try {
            String strSelect = "";
            ResultSet rset = null;

            int num = Integer.parseInt(number);
            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on id=capital order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setInt(1, num);

            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next()) {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping("report_twenty_one")
    protected ArrayList<CityReport.CityReportItem> getReportTwentyOne(@RequestParam(value = "continent") String continent, @RequestParam(value = "number") String number)  // REPORT 21
    {
        try {
            String strSelect = "";
            ResultSet rset = null;

            int num = Integer.parseInt(number);
            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on id=capital where continent= ? order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, continent);
            preparedStatement.setInt(2, num);


            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next()) {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping("report_twenty_two")
    protected ArrayList<CityReport.CityReportItem> getReportTwentyTwo(@RequestParam(value = "region") String region, @RequestParam(value = "number") String number)  // REPORT 22
    {
        try {
            String strSelect = "";
            ResultSet rset = null;

            int num = Integer.parseInt(number);
            strSelect =
                    "select city.name, country.name, city.district, city.population from city city join country country on id=capital where region= ? order by city.population DESC LIMIT ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, region);
            preparedStatement.setInt(2, num);


            rset = preparedStatement.executeQuery();
            CityReport report = new CityReport();

            while (rset.next()) {
                CityReport.CityReportItem item = report.new CityReportItem(rset.getString(1), rset.getString(2), rset.getString(3), rset.getInt(4));
                report.addItemToReport(item);
            }

            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping("report_twenty_three")
    protected ArrayList<PopulationReport.PopulationReportItem> getReportTwentyThree()  // REPORT 23
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect = "Select country.continent, SUM(country.population) as regionPopulation, SUM((select SUM(population) from city where countrycode = country.code)) AS popInCity, (SUM((select SUM(population) from city where countrycode = country.code)) / SUM(country.population))*100 as percentInCities , (sum(country.population)-SUM((select SUM(population) from city where countrycode = country.code))) as popNotInCity, ((sum(country.population)-SUM((select SUM(population) from city where countrycode = country.code))) / SUM(country.population))*100 as percentNotInCities from country GROUP BY country.continent;";
            rset = stmt.executeQuery(strSelect);

            PopulationReport report = new PopulationReport();

            // Loop on result set and add report items to report
            while (rset.next()) {

                PopulationReport.PopulationReportItem item = report.new PopulationReportItem(rset.getString(1), rset.getLong(2), rset.getLong(3), rset.getFloat(4), rset.getLong(5), rset.getFloat(6));
                report.addItemToReport(item);
            }
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @RequestMapping("report_twenty_four")
    protected ArrayList<PopulationReport.PopulationReportItem> getReportTwentyFour()  // REPORT 24
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect = "Select country.region, SUM(country.population) as regionPopulation, SUM((select SUM(population) from city where countrycode = country.code)) AS popInCity, (SUM((select SUM(population) from city where countrycode = country.code)) / SUM(country.population))*100 as percentInCities , (sum(country.population)-SUM((select SUM(population) from city where countrycode = country.code))) as popNotInCity, ((sum(country.population)-SUM((select SUM(population) from city where countrycode = country.code))) / SUM(country.population))*100 as percentNotInCities from country GROUP BY country.region;";
            rset = stmt.executeQuery(strSelect);

            PopulationReport report = new PopulationReport();

            // Loop on result set and add report items to report
            while (rset.next()) {

                PopulationReport.PopulationReportItem item = report.new PopulationReportItem(rset.getString(1), rset.getInt(2), rset.getInt(3), rset.getFloat(4), rset.getInt(5), rset.getFloat(6));
                report.addItemToReport(item);
            }
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @RequestMapping("report_twenty_five")
    protected ArrayList<PopulationReport.PopulationReportItem> getReportTwentyFive()  // REPORT 25
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect = "select country.name, country.population, country.population-sum(city.population) as 'pop not in city', ((country.population-sum(city.population))/country.population)*100 as '%', country.population - (country.population-sum(city.population)) as 'pop in city', ((country.population - (country.population-sum(city.population)))/country.population)*100 as '%' from country country join city city on country.code = city.countrycode where city.countrycode = country.code GROUP BY country.name, country.population ORDER BY country.name;";
            rset = stmt.executeQuery(strSelect);

            PopulationReport report = new PopulationReport();

            // Loop on result set and add report items to report
            while (rset.next()) {

                PopulationReport.PopulationReportItem item = report.new PopulationReportItem(rset.getString(1), rset.getInt(2), rset.getInt(3), rset.getFloat(4), rset.getInt(5), rset.getFloat(6));
                report.addItemToReport(item);
            }
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @RequestMapping("report_twenty_six")
    protected ArrayList<TotalPopulationReport.TotalPopulationReportItem> getReportTwentySix()  // REPORT 26
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect = "SELECT sum(population) AS 'World Population' FROM country;";
            rset = stmt.executeQuery(strSelect);

            TotalPopulationReport report = new TotalPopulationReport();

            // Loop on result set and add report items to report
            while (rset.next()) {

                TotalPopulationReport.TotalPopulationReportItem item = report.new TotalPopulationReportItem("World Population", rset.getLong(1));
                report.addItemToReport(item);
            }
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @RequestMapping("report_twenty_seven")
    protected ArrayList<TotalPopulationReport.TotalPopulationReportItem> getReportTwentySeven(@RequestParam(value = "continent") String continent)  // REPORT 27
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect = "SELECT sum(population) AS 'Continent Population' FROM country WHERE continent = ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, continent);

            rset = preparedStatement.executeQuery();

            TotalPopulationReport report = new TotalPopulationReport();

            // Loop on result set and add report items to report
            while (rset.next()) {

                TotalPopulationReport.TotalPopulationReportItem item = report.new TotalPopulationReportItem("Continent Population", rset.getLong(1));
                report.addItemToReport(item);
            }
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @RequestMapping("report_twenty_eight")
    protected ArrayList<TotalPopulationReport.TotalPopulationReportItem> getReportTwentyEight(@RequestParam(value = "region") String region)  // REPORT 28
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect = "SELECT sum(population) AS 'Region Population' FROM country WHERE region = ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, region);

            rset = preparedStatement.executeQuery();

            TotalPopulationReport report = new TotalPopulationReport();

            // Loop on result set and add report items to report
            while (rset.next()) {

                TotalPopulationReport.TotalPopulationReportItem item = report.new TotalPopulationReportItem("Country Population", rset.getLong(1));
                report.addItemToReport(item);
            }
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @RequestMapping("report_twenty_nine")
    protected ArrayList<TotalPopulationReport.TotalPopulationReportItem> getReportTwentyNine(@RequestParam(value = "country") String country)  // REPORT 28
    {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "";
            ResultSet rset = null;

            strSelect = "SELECT sum(population) AS 'Country Population' FROM country WHERE name = ?;";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, country);

            rset = preparedStatement.executeQuery();

            TotalPopulationReport report = new TotalPopulationReport();

            // Loop on result set and add report items to report
            while (rset.next()) {

                TotalPopulationReport.TotalPopulationReportItem item = report.new TotalPopulationReportItem("Country Population", rset.getLong(1));
                report.addItemToReport(item);
            }
            return report.get_reportsItems();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}

