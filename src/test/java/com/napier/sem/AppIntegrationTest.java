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
        ArrayList<CountryReport.CountryReportItem> r = app.getReportOne();
        int size =r.size();
        assertTrue(size >0);

    }

    @Test
    void testReportTwo(){
        ArrayList<CountryReport.CountryReportItem> r =  app.getReportTwo("Africa");
        CountryReport.CountryReportItem i = r.get(0);
        assertEquals(111506000, i.get_population());
    }

    @Test
    void testReportThree(){
        ArrayList<CountryReport.CountryReportItem> r =  app.getReportThree("Western Africa");

        CountryReport.CountryReportItem i = r.get(0);
        assertEquals("Nigeria", i.get_name());
    }

    @Test
    void testReportFour(){
        ArrayList<CountryReport.CountryReportItem> r =  app.getReportFour("6");
        CountryReport.CountryReportItem item = r.get(3);

        assertEquals("Jakarta", item.get_capital());
    }

    @Test
    void testReportFive(){
        ArrayList<CountryReport.CountryReportItem> r =  app.getReportFive("Europe", "30");
        assertEquals(30, r.size());
    }

    @Test
    void testReportSix(){
        ArrayList<CountryReport.CountryReportItem> r = app.getReportSix("Caribbean", "2");
        CountryReport.CountryReportItem item = r.get(1);
        assertEquals("Santo Domingo de Guzmán", item.get_capital());
    }

    @Test
    void testReportSeven() {
        ArrayList<CityReport.CityReportItem> r = app.getReportSeven();
        CityReport.CityReportItem item = r.get(0);
        assertEquals(10500000, item.get_population());
    }

    @Test
    void testReportEight(){
        ArrayList<CityReport.CityReportItem> r =  app.getReportEight("Asia");
        CityReport.CityReportItem i = r.get(0);
        assertEquals(10500000, i.get_population());
    }

    @Test
    void testReportNine(){
        ArrayList<CityReport.CityReportItem> r = app.getReportNine("Western Europe");

        CityReport.CityReportItem i = r.get(0);
        assertEquals("Berliini", i.get_district());
    }

    @Test
    void testReportTen(){
        ArrayList<CityReport.CityReportItem> r = app.getReportTen("france");

        CityReport.CityReportItem i = r.get(0);
        assertEquals(2125246, i.get_population());
    }

    @Test
    void testReportEleven(){
        ArrayList<CityReport.CityReportItem> r =  app.getReportEleven("Scotland");

        CityReport.CityReportItem i = r.get(0);
        assertEquals(619680, i.get_population());
    }

    @Test
    void testReportTwelve()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportTwelve(8);

        CityReport.CityReportItem i = r.get(0);
        assertEquals("Mumbai (Bombay)", i.get_name());
    }

    @Test
    void testReportThirteen()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportThirteen(5, "Europe");

        CityReport.CityReportItem i = r.get(0);
        assertEquals("Moscow", i.get_name());
    }

    @Test
    void testReportFourteen()
    {
        ArrayList<CityReport.CityReportItem> r =  app.getReportFourteen(5, "Caribbean");

        CityReport.CityReportItem i = r.get(0);
        assertEquals("La Habana", i.get_name());
    }
    
    @Test
    void testReportFifteen()
    {
        ArrayList<CityReport.CityReportItem> r =  app.getReportFifteen(5, "germany");

        CityReport.CityReportItem i = r.get(0);
        assertEquals("Berlin", i.get_name());
    }

    @Test
    void testReportSixteen()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportSixteen(3, "Noord-Brabant");

        CityReport.CityReportItem i = r.get(2);
        assertEquals("Netherlands", i.get_country());
    }

    @Test
    void testReportSeventeen()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportSeventeen();

        CityReport.CityReportItem i = r.get(2);
        assertEquals("Mexico", i.get_country());
    }

    @Test
    void testReportEighteen()
    {
        ArrayList<CityReport.CityReportItem> r =  app.getReportEighteen("Europe");

        CityReport.CityReportItem i = r.get(2);
        assertEquals("Germany", i.get_country());
    }

    @Test
    void testReportNineteen()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportNineteen("Caribbean");

        CityReport.CityReportItem i = r.get(2);
        assertEquals("Haiti", i.get_country());
    }

    @Test
    void testReportTwenty()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportTwenty("115");

        CityReport.CityReportItem i = r.get(114);
        assertEquals("Macao", i.get_country());
    }

    @Test
    void testReportTwentyOne()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportTwentyOne("Europe", "10");

        CityReport.CityReportItem i = r.get(9);
        assertEquals("Belarus", i.get_country());
    }

    @Test
    void testReportTwentyTwo()
    {
        ArrayList<CityReport.CityReportItem> r = app.getReportTwentyTwo("Caribbean", "10");

        CityReport.CityReportItem i = r.get(9);
        assertEquals("Kingston", i.get_country());
    }

}