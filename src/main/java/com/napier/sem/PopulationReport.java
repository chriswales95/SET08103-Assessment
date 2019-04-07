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

        public PopulationReportItem(String name, int population, int popInCity, int popNotInCity) {
            this.set_name(name);
            this.set_population(population);
            this._popNotInCity = popInCity;
            this._popNotInCity = popNotInCity;
        }
    }
}
