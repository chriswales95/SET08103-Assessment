package com.napier.sem;

import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Purpose of class: Main Application
 */
public class App {

    private static DatabaseHandler db = new DatabaseHandler();

    public static void main(String[] args) {
        App app = new App();

        db.connect();
        int choice = app.getUserChoice();
        app.generateReport(choice);
        db.disconnect();
    }

    /**
     * Display report options and get input
     */
    public int getUserChoice() {

        try {
            System.out.println("World Population: \n");
            System.out.println("Select a report to view:"); // select SUM(population) from country;
            System.out.println("1. All the countries in the world organised by largest population to smallest");  // select name, population from country order by population DESC;
            System.out.println("2. All the countries in a continent organised by largest population to smallest");
            System.out.println("3. All the countries in a region organised by largest population to smallest");
            System.out.println("4. The top N populated countries in the world");
            System.out.println("5. The top N populated countries in a region");
            System.out.println("6. All the cities in the world organised by largest population to smallest");
            System.out.println("7. All the cities in a continent organised by largest population to smallest");
            System.out.println("8. All the cities in a region organised by largest population to smallest");
            System.out.println("9. All the cities in a country organised by largest population to smallest");
            System.out.println("10. All the cities in a district organised by largest population to smallest.");
            System.out.println("11. The top N populated cities in the world");
            System.out.println("12. The top N populated cities in a continent");
            System.out.println("13. The top N populated cities in a region ");
            System.out.println("14. The top N populated cities in a country ");
            System.out.println("15. The top N populated cities in a district ");
            System.out.println("16. All the capital cities in the world organised by largest population to smallest");
            System.out.println("17. All the capital cities in a continent organised by largest population to smallest");
            System.out.println("18. All the capital cities in a region organised by largest to smallest");
            System.out.println("19. The top N populated capital cities in the world");
            System.out.println("20. The top N populated capital cities in a continent");
            System.out.println("21. The top N populated capital cities in a region");
            System.out.println("22. The population of people, people living in cities, and people not living in cities in each continent");
            System.out.println("23. The population of people, people living in cities, and people not living in cities in each region");
            System.out.println("24. The population of people, people living in cities, and people not living in cities in each country");
            System.out.println("25. The population of a continent");
            System.out.println("26. The population of a region");
            System.out.println("27. The population of a country");
            System.out.println("28. The population of a district");
            System.out.println("29. The population of a city");
            System.out.println("30. The number of people who speak Chinese");
            System.out.println("31. The number of people who speak English");
            System.out.println("32. The number of people who speak Hindi");
            System.out.println("33. The number of people who speak Spanish");
            System.out.println("34. The number of people who speak Arabic");

            System.out.println("\nEnter number: "); // Prompt user for input
            Scanner sc = new Scanner(System.in);
            return sc.nextInt(); // Read input in from console
        } catch (NoSuchElementException ex) {
            System.out.println("No input or incorrect input captured");
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public void generateReport(int reportNumber) {

        try {
            if (reportNumber > 34 || reportNumber <= 0) {
                throw new Exception("Choose a report between 1 and 34");
            }

            switch (reportNumber) {

                case 1:
                    System.out.printf("%-5s  %-40s  %-30.25s  %-30s  %-20s  %-20s", "CODE", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
                    System.out.print("\n");
                    for (Report r : db.getReport(reportNumber)) {
                        CountryReport x = (CountryReport) r;
                        System.out.printf("%-5s  %-40.35s  %-30.25s  %-30s  %-20d  %-20s", x.get_code(), x.get_name(), x.get_continent(), x.get_region(), x.get_population(), x.get_capital());
                        System.out.print("\n");
                    }
                    break;
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}