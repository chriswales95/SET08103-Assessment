package com.napier.sem;

import java.util.ArrayList;

public class TotalPopulationReport extends Report {

    private ArrayList<TotalPopulationReportItem> _reportsItems = new ArrayList<>();

    @Override
    ReportType getReportType() {
        return ReportType.TotalPopulationReport;
    }

    public ArrayList<TotalPopulationReportItem> get_reportsItems() {
        return _reportsItems;
    }

    public void addItemToReport(TotalPopulationReportItem item){
        _reportsItems.add(item);
    }


    class TotalPopulationReportItem extends ReportItem {

        public TotalPopulationReportItem(int population) {
            this.set_population(population);
        }
    }
}
