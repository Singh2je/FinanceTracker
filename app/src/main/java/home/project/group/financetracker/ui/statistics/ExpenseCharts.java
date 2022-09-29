package home.project.group.financetracker.ui.statistics;

import android.app.Activity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.RoundNumbers;

public class ExpenseCharts {
    /**
     * This is expense chart where data is shown for different expense categories
     *
     * @param transactionList
     * @param expenseAmount
     * @param expensePieChart
     * @param activity
     */
    public static void expenseCategoryPieChart(List<TransactionModel> transactionList, List<Double> expenseAmount, PieChart expensePieChart, Activity activity) {
        transactionList = new ArrayList<>();
        transactionList = DatabaseClass.getDatabase(activity.getApplicationContext()).getDao().getGroupedCategories();

        expenseAmount = new ArrayList<>();

        /**
         * Store all expenses in another arraylist
         */
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getType().equals("E")) {
                expenseAmount.add(transactionList.get(i).getAmount());
            }
        }

        /**
         * Get Total expense amount by adding all expenses from all categories
         */
        double totalExpense = 0;
        for (int i = 0; i < expenseAmount.size(); i++) {
            totalExpense += expenseAmount.get(i);
        }

        /**
         * Create pie chart entries by find a percentage of each expense and giving a
         * label of unique categories
         */
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getType().equals("E")) {
                entries.add(new PieEntry((float) RoundNumbers.round(((transactionList.get(i).getAmount() / totalExpense) * 100), 1),
                        transactionList.get(i).getCategory().substring(0, 1).toUpperCase() + transactionList.get(i).getCategory().substring(1)));
            }
        }

        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue, R.color.colorPrimaryDark, R.color.colorAccent}, activity.getApplicationContext());
        set.setValueTextSize(0.1f);

        PieData data = new PieData(set);

        Description description = new Description();
        description.setEnabled(false);

        CharSequence title = "EXPENSES";

        expensePieChart.setData(data);
        expensePieChart.setDescription(description);
        expensePieChart.setCenterText(title);
        expensePieChart.notifyDataSetChanged();
        expensePieChart.invalidate(); // refresh
    }
}
