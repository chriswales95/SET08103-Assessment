package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;
    static DatabaseHandler db;

    @BeforeAll
    static void init()
    {
        app = new App();
        db = DatabaseHandler.Instance();
        db.connect("localhost:33060");
    }

    @Test
     void testReportOne(){
        Report r = db.getReportOne();
    }

    @Test
    void testReportTwo(){
        CountryReport r = (CountryReport) db.getReportTwo("Africa");
        CountryReport.CountryReportItem i = r.get_reportsItems().get(0);
        assertEquals(111506000, i.get_population());
    }

    @Test
    void testReportThree(){
        CountryReport r = (CountryReport) db.getReportThree("Western Africa");

        CountryReport.CountryReportItem i = r.get_reportsItems().get(0);
        assertEquals("Nigeria", i.get_name());
    }

    @Test
    void testReportFour(){
        CountryReport r = (CountryReport) db.getReportFour(6);
        CountryReport.CountryReportItem item = r.get_reportsItems().get(3);

        assertEquals("Jakarta", item.get_capital());
    }

    @Test
    void testReportFive(){
        CountryReport r = (CountryReport) db.getReportFive("Europe", 30);
        assertEquals(30, r.get_reportsItems().size());
    }

    @Test
    void testReportSix(){
        CountryReport r = (CountryReport) db.getReportSix("Caribbean", 2);
        CountryReport.CountryReportItem item = r.get_reportsItems().get(1);
        assertEquals("Santo Domingo de Guzmán", item.get_capital());
    }

    @Test
    void testReportSeven() {
        Report r = db.getReportSeven();
    }

    @Test
    void testReportEight(){
        CityReport r = (CityReport) db.getReportEight("Asia");
        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals(10500000, i.get_population());
    }

    @Test
    void testReportNine(){
        CityReport r = (CityReport) db.getReportNine("Western Europe");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("Berliini", i.get_district());
    }

    @Test
    void testReportTen(){
        CityReport r = (CityReport) db.getReportTen("france");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals(2125246, i.get_population());
    }

    @Test
    void testReportEleven(){
        CityReport r = (CityReport) db.getReportEleven("Scotland");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals(619680, i.get_population());
    }

    @Test
    void testReportTwelve()
    {
        CityReport r = (CityReport) db.getReportTwelve(8);

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("Tokyo", i.get_name());
    }

    @Test
    void testReportThirteen()
    {
        CityReport r = (CityReport) db.getReportThirteen(5, "Europe");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("London", i.get_name());
    }
}