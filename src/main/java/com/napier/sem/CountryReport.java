package com.napier.sem;

/**
 * Purpose of class: Class for a Country Report which inherits from Report
 */
public class CountryReport extends Report {

    private String _code;
    private String _name;
    private String _continent;
    private String _region;
    private String _capital;
    private int _population;

    public CountryReport(String _code, String _name, String _continent, String _region, String _capital, int _population) {
        this._code = _code;
        this._name = _name;
        this._continent = _continent;
        this._region = _region;
        this._capital = _capital;
        this._population = _population;
    }

    public String get_code() {
        return _code;
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
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

    public int get_population() {
        return _population;
    }

    public void set_population(int _population) {
        this._population = _population;
    }
}
