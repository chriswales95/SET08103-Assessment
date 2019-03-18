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
}