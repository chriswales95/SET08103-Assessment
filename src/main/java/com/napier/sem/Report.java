package com.napier.sem;

/**
 * Purpose of class: Abstract Report class from which other reports inherit
 */
abstract public class Report {

    abstract ReportType getReportType(); // abstract method which classes must override. Returns report type
    public enum ReportType{CountryReport, CityReport, CapitalCityReport, PopulationReport, TotalPopulationReport} //enum for Report type

    /**
     * Abstract subclass which models a report item
     */
    abstract class ReportItem{

        private String _name; // country name which appears on most reports
        private long _population; // population which appears on most reports

        /**
         * Method to return name
         * @return name
         */
        public String get_name() {
            return _name;
        }

        /**
         * Method to set name
         * @param _name
         */
        public void set_name(String _name) {
            this._name = _name;
        }

        /**
         * method to get population
         * @return population
         */
        public long get_population() {
            return _population;
        }

        /**
         * method to set population
         * @param _population
         */
        public void set_population(long _population) {
            this._population = _population;
        }
    }

}
