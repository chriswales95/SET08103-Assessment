package com.napier.sem;

import java.util.ArrayList;

public class PopulationReport extends Report {

    private ArrayList<PopulationReportItem> _reportsItems = new ArrayList<>();

    @Override
    ReportType getReportType() {
        return ReportType.PopulationReport;
    }

    public ArrayList<PopulationReportItem> get_reportsItems() {
        return _reportsItems;
    }

    public void addItemToReport(PopulationReportItem item){
        _reportsItems.add(item);
    }


    class PopulationReportItem extends ReportItem {
        private int _popNotInCity;
        private int _popInCity;
        private double _popNotInCityPercentage;
        private double _popinCityyPercentage;

        public PopulationReportItem(String name, int population, int popNotInCity, double popNotInCityPercentage, int popInCity, double popInCityPercentage) {
            this.set_name(name);
            this.set_population(population);
            this._popNotInCity = popInCity;
            this._popNotInCity = popNotInCity;
            this._popinCityyPercentage = popInCityPercentage;
            this._popinCityyPercentage = popNotInCityPercentage;
        }

        public int get_popNotInCity() {
            return _popNotInCity;
        }

        public void set_popNotInCity(int _popNotInCity) {
            this._popNotInCity = _popNotInCity;
        }

        public int get_popInCity() {
            return _popInCity;
        }

        public void set_popInCity(int _popInCity) {
            this._popInCity = _popInCity;
        }

        public double get_popNotInCityPercentage() {
            return _popNotInCityPercentage;
        }

        public void set_popNotInCityPercentage(double _popNotInCityPercentage) {
            this._popNotInCityPercentage = _popNotInCityPercentage;
        }

        public double get_popinCityyPercentage() {
            return _popinCityyPercentage;
        }

        public void set_popinCityyPercentage(double _popinCityyPercentage) {
            this._popinCityyPercentage = _popinCityyPercentage;
        }
    }
}
