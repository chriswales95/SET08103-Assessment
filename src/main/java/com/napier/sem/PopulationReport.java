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
        private long _popNotInCity;
        private long _popInCity;
        private float _popNotInCityPercentage;
        private float _popInCityPercentage;

        public PopulationReportItem(String name, long population, long popNotInCity, float popNotInCityPercentage, long popInCity, float popInCityPercentage) {
            this.set_name(name);
            this.set_population(population);
            this._popInCity = popInCity;
            this._popNotInCity = popNotInCity;
            this._popInCityPercentage = popInCityPercentage;
            this._popNotInCityPercentage = popNotInCityPercentage;
        }

        public long get_popNotInCity() {
            return _popNotInCity;
        }

        public void set_popNotInCity(long _popNotInCity) {
            this._popNotInCity = _popNotInCity;
        }

        public long get_popInCity() {
            return _popInCity;
        }

        public void set_popInCity(long _popInCity) {
            this._popInCity = _popInCity;
        }

        public double get_popNotInCityPercentage() {
            return _popNotInCityPercentage;
        }

        public void set_popNotInCityPercentage(float _popNotInCityPercentage) {
            this._popNotInCityPercentage = _popNotInCityPercentage;
        }

        public float get_popinCityyPercentage() {
            return _popInCityPercentage;
        }

        public void set_popinCityyPercentage(float _popInCityyPercentage) {
            this._popInCityPercentage = _popInCityyPercentage;
        }
    }
}
