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
}

