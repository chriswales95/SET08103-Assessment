package com.napier.sem;

/**
 * Purpose of class: Report class from which other reports inherit
 */
abstract public class Report {

    abstract ReportType getReportType();

    private String _name;
    private int _population;
    public enum ReportType{CountryReport, CityReport}



    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_population() {
        return _population;
    }

    public void set_population(int _population) {
        this._population = _population;
    }

}
