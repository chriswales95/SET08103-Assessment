package com.napier.sem;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Purpose of class: Main Application
 */
public class App {

    private static DatabaseHandler db = DatabaseHandler.Instance(); // Instance of database

    /**
     * Main method
     *
     * @param args args passed into main
     */
    public static void main(String[] args) {

        boolean loop = true;

        // Connect to database
        App app = new App();

        // Connect to database
        if (args.length < 1)
        {
            db.connect("localhost:33060");
        }
        else
        {
            db.connect(args[0]);
        }

        app.printReportOptions();

        // Loop until user enters 0 to exit
        while (loop) {
            int choice = app.getUserChoice();
            if (choice == 0) {
                loop = false;
            } else {
                Report r = db.getReport(choice);
                app.printReport(r);
            }
        }
        // Disconnect from database
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
        System.out.println("26. The population of the world");
        System.out.println("27. The population of a continent");
        System.out.println("28. The population of a region");
        System.out.println("29. The population of a country");
        System.out.println("30. The population of a district");
        System.out.println("31. The population of a city");
        System.out.println("32. The number of people who speak Chinese");
        System.out.println("33. The number of people who speak English");
        System.out.println("34. The number of people who speak Hindi");
        System.out.println("35. The number of people who speak Spanish");
        System.out.println("36. The number of people who speak Arabic");
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


    public void printReport(Report report) {

        if (report instanceof CountryReport) {
            CountryReport.printReportHeader();

            for (CountryReport.CountryReportItem item : ((CountryReport) report).get_reportsItems()) {
                System.out.printf(
                        CountryReport.getReportFormat(), item.get_code(), item.get_name(), item.get_continent(), item.get_region(), item.get_population(), item.get_capital());
                System.out.print("\n");
            }

        }
    }
}


