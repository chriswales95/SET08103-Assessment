package com.napier.sem;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Purpose of class: Main Application
 * Last Modified by:
 * Date last modified:
 */
public class App
{
    public static void main(String[] args)
    {
        App a = new App();
        a.connect();
        a.disconnect();

    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; i++)
        {
            System.out.println("Connecting to database.......");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database using root and password
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "pass");
                System.out.println("Successfully connected!\n");

                System.out.println("World Population: \n" );
                System.out.println("Select a report to view:");

                System.out.println("1. All the countries in the world organised by largest population to smallest");
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

                System.out.println("\nEnter number: ");

                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();

                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted");
            }

            catch (NoSuchElementException ex){
                System.out.println("\nNo input captured. This will most likely occur with docker-compose up on Travis");
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
                System.out.println("--------------------------------------");
                System.out.println("Connection to database closed");
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}