package home.project.group.financetracker.ui.statistics;

import android.app.Activity;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.RoundNumbers;

public class RevenueCharts {

    /**
     * This is revenue chart where data is shown for different revenue categories
     *
     * @param transactionList
     * @param revenueAmount
     * @param revenuePieChart
     * @param activity
     */
    public static void revenueCategoryPieChart(List<TransactionModel> transactionList, List<Double> revenueAmount, PieChart revenuePieChart, Activity activity) {
        transactionList = new ArrayList<>();
        transactionList = DatabaseClass.getDatabase(activity.getApplicationContext()).getDao().getGroupedCategories();

        revenueAmount = new ArrayList<>();

        /**
         * Store all revenue in another arraylist
         */
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getType().equals("R")) {
                revenueAmount.add(transactionList.get(i).getAmount());
            }
        }

        /**
         * Get Total revenue amount by adding all revenues from all categories
         */
        double totalRevenue = 0;
        for (int i = 0; i < revenueAmount.size(); i++) {
            totalRevenue += revenueAmount.get(i);
        }
        System.out.println(totalRevenue);


        /**
         * Create pie chart entries by find a percentage of each revenue and giving a
         * label of unique categories
         */
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getType().equals("R")) {
                entries.add(new PieEntry((float) RoundNumbers.round(((transactionList.get(i).getAmount() / totalRevenue) * 100), 1),
                        transactionList.get(i).getCategory().substring(0, 1).toUpperCase() + transactionList.get(i).getCategory().substring(1)));
                System.out.println((float) RoundNumbers.round(((transactionList.get(i).getAmount() / totalRevenue) * 100), 1));
                System.out.println(transactionList.get(i).getCategory().substring(0, 1).toUpperCase() + transactionList.get(i).getCategory().substring(1));
            }
        }

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue, R.color.colorPrimaryDark, R.color.colorAccent}, activity.getApplicationContext());
        set.setValueTextSize(0.1f);

        PieData data = new PieData(set);

        Description description = new Description();
        description.setEnabled(false);

        CharSequence title = "REVENUE";

        revenuePieChart.setData(data);
        revenuePieChart.setDescription(description);
        revenuePieChart.setCenterText(title);
        revenuePieChart.notifyDataSetChanged();
        revenuePieChart.invalidate(); // refresh
    }

    /**
     * This is revenue bar chart currently with dummy data
     *
     * @param revenueBarChart
     */
    public static void revenueBarChart(BarChart revenueBarChart) {
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0f, 30f));
        barEntries.add(new BarEntry(1f, 80f));
        barEntries.add(new BarEntry(2f, 60f));
        barEntries.add(new BarEntry(3f, 50f));
        // gap of 2f
        barEntries.add(new BarEntry(5f, 70f));
        barEntries.add(new BarEntry(6f, 60f));
        BarDataSet barDataSet = new BarDataSet(barEntries, "BarDataSet");

        XAxis xAxis = revenueBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(8f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);

        YAxis right = revenueBarChart.getAxisRight();
        right.setDrawLabels(false); // no axis labels
        right.setDrawAxisLine(false); // no axis line
        right.setDrawGridLines(false); // no grid lines
        right.setDrawZeroLine(true);

        YAxis left = revenueBarChart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f); // set custom bar width
        revenueBarChart.setData(barData);
        revenueBarChart.setFitBars(true); // make the x-axis fit exactly all bars
        revenueBarChart.setDrawGridBackground(false);
        revenueBarChart.animateXY(2000, 2000);
        revenueBarChart.setScaleEnabled(true);
        revenueBarChart.setDrawValueAboveBar(true);
        revenueBarChart.setDrawGridBackground(false);
        revenueBarChart.setDoubleTapToZoomEnabled(true);
        revenueBarChart.setPinchZoom(true);
        barDataSet.setColor(Color.parseColor("#606d5b"));
        revenueBarChart.invalidate(); // refresh
    }
}

