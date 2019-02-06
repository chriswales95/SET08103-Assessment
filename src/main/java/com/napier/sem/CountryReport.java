package com.napier.sem;

/**
 * Purpose of class: Class for a Country Report which inherits from Report
 */
public class CountryReport extends Report {

    private String _code;
    private String _continent;
    private String _region;
    private String _capital;

    public CountryReport(String code, String name, String continent, String region, int population, String capital) {
        this._code = code;
        this.set_name(name);
        this._continent = continent;
        this._region = region;
        this.set_population(population);
        this._capital = capital;
    }

    public CountryReport(){}

    @Override
    Report.ReportType getReportType() {
        return ReportType.CountryReport;
    }

    public static String getReportFormat() {
        return "%-5s  %-40s  %-30.25s  %-30s  %-20s  %-20s";
    }

    public static void printReportHeader(){
        System.out.println("\n");
        System.out.printf(CountryReport.getReportFormat(), "CODE", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
        System.out.println("\n");
    }

    public String get_code() {
        return _code;
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public String get_continent() {
        return _continent;
    }

    public void set_continent(String _continent) {
        this._continent = _continent;
    }

    public String get_region() {
        return _region;
    }

    public void set_region(String _region) {
        this._region = _region;
    }

    public String get_capital() {
        return _capital;
    }

    public void set_capital(String _capital) {
        this._capital = _capital;
    }

}
