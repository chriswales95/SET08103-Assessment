package com.napier.sem;

import java.util.ArrayList;

/**
 * Purpose of class: Class for a Country Report which inherits from Report
 */
public class CountryReport extends Report {

    private ArrayList<CountryReportItem> _reportsItems = new ArrayList<>(); // Array list to hold report items

    public ArrayList<CountryReportItem> get_reportsItems() {
        return _reportsItems;
    } // Get method to get array list for report items

    public void addItemToReport(CountryReportItem item){
        _reportsItems.add(item);
    } // method to add item to report

    /**
     * Method to return string for report format
     * @return string forma for CountryReport
     */
    public static String getReportFormat() {
        return "%-5s  %-40s  %-30.25s  %-30s  %-20s  %-20s";
    }

    /**
     * Prints Country Report header
     */
    public static void printReportHeader(){
        System.out.println("\n");
        System.out.printf(CountryReport.getReportFormat(), "CODE", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
        System.out.println("\n");
    }

    /**
     * Method to get Report type
     * @return Country Report report type
     */
    @Override
    Report.ReportType getReportType() {
        return ReportType.CountryReport;
    }

    /**
     * CountryReportItem class which is a subclass of ReportItem. Used to model a report item
     */
    class CountryReportItem extends ReportItem {

        private String _code; // country code
        private String _continent; // continent in which country is in
        private String _region; // region of country
        private String _capital; // capital city of country

        /**
         * Public constructor which takes in a series of arguments
         * @param code country code
         * @param name country name
         * @param continent continent which country is in
         * @param region region of country
         * @param population population of country
         * @param capital capital city of country
         */
        public CountryReportItem(String code, String name, String continent, String region, int population, String capital) {
            this._code = code;
            this.set_name(name);
            this._continent = continent;
            this._region = region;
            this.set_population(population);
            this._capital = capital;
        }

        // empty constructor
        public CountryReportItem(){}

        /**
         * method to return country code
         * @return returns country code
         */
        public String get_code() {
            return _code;
        }

        /**
         * method to set code
         * @param _code of country
         */
        public void set_code(String _code) {
            this._code = _code;
        }

        /**
         * method to get continent of country
         * @return country continent
         */
        public String get_continent() {
            return _continent;
        }

        /**
         * method to set continent of country
         * @param _continent of country
         */
        public void set_continent(String _continent) {
            this._continent = _continent;
        }

        /**
         * method to get region of country
         * @return region of country
         */
        public String get_region() {
            return _region;
        }

        /**
         * method to set region of country
         * @param _region of country
         */
        public void set_region(String _region) {
            this._region = _region;
        }

        /**
         * method to get capital of country
         * @return returns capital of country
         */
        public String get_capital() {
            return _capital;
        }

        /**
         * method to set capital of country
         * @param _capital of country
         */
        public void set_capital(String _capital) {
            this._capital = _capital;
        }
    }
}
