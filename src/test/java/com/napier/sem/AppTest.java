package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest
{
    static App app;
    static DatabaseHandler db;

    @BeforeAll
    static void init()
    {
        app = new App();

    }

    @Test
    void testPrintReport()
    {
        CountryReport r = new CountryReport();
        CountryReport.CountryReportItem item = new CountryReport().new CountryReportItem();
        r.addItemToReport(item);

        item.set_capital("Edinburgh");
        item.set_code("ABC");
        item.set_continent("Europe");
        item.set_region("Midlothian");
        item.set_name("Scotland");
        item.set_population(100000);

        app.printReport(r);
    }

}