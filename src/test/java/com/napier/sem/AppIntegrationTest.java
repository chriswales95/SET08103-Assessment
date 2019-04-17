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
        ArrayList<CapitalCityReport.CapitalCityReportItem> r = app.getReportSeventeen();

        CapitalCityReport.CapitalCityReportItem i = r.get(2);
        assertEquals("Mexico", i.get_Country());
    }

    @Test
    void testReportEighteen()
    {
        ArrayList<CapitalCityReport.CapitalCityReportItem> r =  app.getReportEighteen("Europe");

        CapitalCityReport.CapitalCityReportItem  i = r.get(2);
        assertEquals("Germany", i.get_Country());
    }

    @Test
    void testReportNineteen()
    {
        ArrayList<CapitalCityReport.CapitalCityReportItem> r = app.getReportNineteen("Caribbean");

        CapitalCityReport.CapitalCityReportItem  i = r.get(2);
        assertEquals("Haiti", i.get_Country());
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
        assertEquals("Antigua and Barbuda", i.get_country());
    }

    @Test
    void testReportTwentyThree()
    {
        ArrayList<PopulationReport.PopulationReportItem> r = app.getReportTwentyThree();

        PopulationReport.PopulationReportItem i = r.get(0);
        assertEquals("North America", i.get_name());
    }

    @Test
    void testReportTwentyFour()
    {
        ArrayList<PopulationReport.PopulationReportItem> r = app.getReportTwentyFour();

        PopulationReport.PopulationReportItem i = r.get(0);
        assertEquals("Caribbean", i.get_name());
    }

    @Test
    void testReportTwentyFive()
    {
        ArrayList<PopulationReport.PopulationReportItem> r = app.getReportTwentyFive();

        PopulationReport.PopulationReportItem i = r.get(0);
        assertEquals(20387900, i.get_popNotInCity());
    }

    @Test
    void testReportTwentySix()
    {
        ArrayList<TotalPopulationReport.TotalPopulationReportItem> r = app.getReportTwentySix();

        TotalPopulationReport.TotalPopulationReportItem i = r.get(0);
        assertEquals(6078749450L, i.get_population());
    }

    @Test
    void testReportTwentySeven()
    {
        ArrayList<TotalPopulationReport.TotalPopulationReportItem> r = app.getReportTwentySeven("Europe");

        TotalPopulationReport.TotalPopulationReportItem i = r.get(0);
        assertEquals(730074600L, i.get_population());
    }

    @Test
    void testReportTwentyEight()
    {
        ArrayList<TotalPopulationReport.TotalPopulationReportItem> r = app.getReportTwentyEight("Middle East");

        TotalPopulationReport.TotalPopulationReportItem i = r.get(0);
        assertEquals(188380700, i.get_population());
    }

    @Test
    void testReportTwentyNine()
    {
        ArrayList<TotalPopulationReport.TotalPopulationReportItem> r = app.getReportTwentyNine("France");

        TotalPopulationReport.TotalPopulationReportItem i = r.get(0);
        assertEquals(59225700, i.get_population());
    }

    @Test
    void testReportThirty()
    {
        ArrayList<TotalPopulationReport.TotalPopulationReportItem> r = app.getReportThirty("Scotland");

        TotalPopulationReport.TotalPopulationReportItem i = r.get(0);
        assertEquals(1429620, i.get_population());
    }

    @Test
    void testReportThirtyOne()
    {
        ArrayList<TotalPopulationReport.TotalPopulationReportItem> r = app.getReportThirtyOne("Edinburgh");

        TotalPopulationReport.TotalPopulationReportItem i = r.get(0);
        assertEquals(450180, i.get_population());
    }
}