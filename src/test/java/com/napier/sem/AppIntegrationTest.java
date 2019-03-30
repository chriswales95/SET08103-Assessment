package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060");
    }

    @Test
     void testReportOne(){
        ArrayList<CountryReport.CountryReportItem> r =  app.getReportOne();
        int size =r.size();
        assertTrue(size >0);

    }

    @Test
    void testReportTwo(){
        CountryReport r = (CountryReport) app.getReportTwo("Africa");
        CountryReport.CountryReportItem i = r.get_reportsItems().get(0);
        assertEquals(111506000, i.get_population());
    }

    @Test
    void testReportThree(){
        CountryReport r = (CountryReport) app.getReportThree("Western Africa");

        CountryReport.CountryReportItem i = r.get_reportsItems().get(0);
        assertEquals("Nigeria", i.get_name());
    }

    @Test
    void testReportFour(){
        CountryReport r = (CountryReport) app.getReportFour(6);
        CountryReport.CountryReportItem item = r.get_reportsItems().get(3);

        assertEquals("Jakarta", item.get_capital());
    }

    @Test
    void testReportFive(){
        CountryReport r = (CountryReport) app.getReportFive("Europe", 30);
        assertEquals(30, r.get_reportsItems().size());
    }

    @Test
    void testReportSix(){
        CountryReport r = (CountryReport) app.getReportSix("Caribbean", 2);
        CountryReport.CountryReportItem item = r.get_reportsItems().get(1);
        assertEquals("Santo Domingo de Guzmán", item.get_capital());
    }

    @Test
    void testReportSeven() {
        CityReport r = (CityReport) app.getReportSeven();
        CityReport.CityReportItem item = r.get_reportsItems().get(0);
        assertEquals(10500000, item.get_population());
    }

    @Test
    void testReportEight(){
        CityReport r = (CityReport) app.getReportEight("Asia");
        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals(10500000, i.get_population());
    }

    @Test
    void testReportNine(){
        CityReport r = (CityReport) app.getReportNine("Western Europe");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("Berliini", i.get_district());
    }

    @Test
    void testReportTen(){
        CityReport r = (CityReport) app.getReportTen("france");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals(2125246, i.get_population());
    }

    @Test
    void testReportEleven(){
        CityReport r = (CityReport) app.getReportEleven("Scotland");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals(619680, i.get_population());
    }

    @Test
    void testReportTwelve()
    {
        CityReport r = (CityReport) app.getReportTwelve(8);

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("Mumbai (Bombay)", i.get_name());
    }

    @Test
    void testReportThirteen()
    {
        CityReport r = (CityReport) app.getReportThirteen(5, "Europe");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("Moscow", i.get_name());
    }

    @Test
    void testReportFourteen()
    {
        CityReport r = (CityReport) app.getReportFourteen(5, "Caribbean");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("La Habana", i.get_name());
    }
    
    @Test
    void testReportFifteen()
    {
        CityReport r = (CityReport) app.getReportFifteen(5, "germany");

        CityReport.CityReportItem i = r.get_reportsItems().get(0);
        assertEquals("Berlin", i.get_name());
    }

    @Test
    void testReportSixteen()
    {
        CityReport r = (CityReport) app.getReportSixteen(3, "Noord-Brabant");

        CityReport.CityReportItem i = r.get_reportsItems().get(2);
        assertEquals("Netherlands", i.get_country());
    }

    @Test
    void testReportSeventeen()
    {
        CityReport r = (CityReport) app.getReportSeventeen();

        CityReport.CityReportItem i = r.get_reportsItems().get(2);
        assertEquals("Mexico", i.get_country());
    }

    @Test
    void testReportEighteen()
    {
        CityReport r = (CityReport) app.getReportEighteen("Europe");

        CityReport.CityReportItem i = r.get_reportsItems().get(2);
        assertEquals("Germany", i.get_country());
    }

    @Test
    void testReportNineteen()
    {
        CityReport r = (CityReport) app.getReportNineteen("Caribbean");

        CityReport.CityReportItem i = r.get_reportsItems().get(2);
        assertEquals("Haiti", i.get_country());
    }
}