package com.napier.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Purpose of class: Main Application
 */
@SpringBootApplication
@RestController
public class App {


    /**
     * Main method
     *
     * @param args args passed into main
     */
    public static void main(String[] args) {

        // Connect to database
        if (args.length < 1) {
            DatabaseHandler.connect("localhost:33060");
        } else {
            DatabaseHandler.connect(args[0]);
        }

        SpringApplication.run(App.class, args);
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
}

