package com.napier.sem;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Purpose of class: Main Application
 */
public class App {

    private static DatabaseHandler db = DatabaseHandler.Instance();

    public static void main(String[] args) {

        boolean loop = true;

        App app = new App();
        db.connect();
        app.printReportOptions();

        while (loop) {
            int choice = app.getUserChoice();
            if (choice == 0) {
                loop = false;
            } else {
                app.generateReport(choice);
            }
        }
        db.disconnect();
    }

    /**
     * Print report options to console
     */
    private void printReportOptions() {
        System.out.println("Select a report to view:");
        System.out.println("1. All the countries in the world organised by largest population to smallest");
        System.out.println("2. All the countries in a continent organised by largest population to smallest");
        System.out.println("3. All the countries in a region organised by largest population to smallest");
        System.out.println("4. The top N populated countries in the world");
        System.out.println("5. The top N populated continent in the world");
        System.out.println("6. The top N populated countries in a region");
        System.out.println("7. All the cities in the world organised by largest population to smallest");
        System.out.println("8. All the cities in a continent organised by largest population to smallest");
        System.out.println("9. All the cities in a region organised by largest population to smallest");
        System.out.println("10. All the cities in a country organised by largest population to smallest");
        System.out.println("11. All the cities in a district organised by largest population to smallest.");
        System.out.println("12. The top N populated cities in the world");
        System.out.println("13. The top N populated cities in a continent");
        System.out.println("14. The top N populated cities in a region ");
        System.out.println("15. The top N populated cities in a country ");
        System.out.println("16. The top N populated cities in a district ");
        System.out.println("17. All the capital cities in the world organised by largest population to smallest");
        System.out.println("18. All the capital cities in a continent organised by largest population to smallest");
        System.out.println("19. All the capital cities in a region organised by largest to smallest");
        System.out.println("20. The top N populated capital cities in the world");
        System.out.println("21. The top N populated capital cities in a continent");
        System.out.println("22. The top N populated capital cities in a region");
        System.out.println("23. The population of people, people living in cities, and people not living in cities in each continent");
        System.out.println("24. The population of people, people living in cities, and people not living in cities in each region");
        System.out.println("25. The population of people, people living in cities, and people not living in cities in each country");
        System.out.println("26. The population of a continent");
        System.out.println("27. The population of a region");
        System.out.println("28. The population of a country");
        System.out.println("29. The population of a district");
        System.out.println("30. The population of a city");
        System.out.println("31. The number of people who speak Chinese");
        System.out.println("32. The number of people who speak English");
        System.out.println("33. The number of people who speak Hindi");
        System.out.println("34. The number of people who speak Spanish");
        System.out.println("35. The number of people who speak Arabic");
        System.out.println("\nEnter 0 to exit\n");
    }

    /**
     * Get input from user
     */
    public int getUserChoice() {

        try {
            System.out.println("\nEnter number: "); // Prompt user for input
            Scanner sc = new Scanner(System.in);
            return sc.nextInt(); // Read input in from console
        } catch (NoSuchElementException ex) {
            System.out.println("No input or incorrect input captured");
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    /**
     * Generate the necessary report depending on reportNumber
     *
     * @param reportNumber
     */
    public void generateReport(int reportNumber) {

        ArrayList<Report> reportResults = db.getReport(reportNumber);
        if (!reportResults.isEmpty()) {
            if (reportResults.get(0) instanceof CountryReport)
                CountryReport.printReportHeader();

        }

        for (Report report : reportResults) {
            if (reportResults.get(0) instanceof CountryReport) {
                CountryReport countryReport = (CountryReport) report;
                System.out.printf(CountryReport.getReportFormat(), countryReport.get_code(), countryReport.get_name(), countryReport.get_continent(), countryReport.get_region(), countryReport.get_population(), countryReport.get_capital());
                System.out.print("\n");
            }
        }
    }
}