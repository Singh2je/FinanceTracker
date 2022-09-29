package home.project.group.financetracker.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class StatisticsFragment extends Fragment implements View.OnClickListener {

    Button expenseViewBtn, revenueViewBtn;
    LinearLayout expenseStatisticsView, revenueStatisticsView;

    PieChart expensePieChart, revenuePieChart;
    BarChart revenueBarChart;

    List<TransactionModel> transactionList;
    List<Double> expenseAmount;
    List<Double> revenueAmount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_statistics, container, false);

        expenseViewBtn = root.findViewById(R.id.expenseViewBtn);
        revenueViewBtn = root.findViewById(R.id.revenueViewBtn);

        expenseViewBtn.setOnClickListener(this);
        revenueViewBtn.setOnClickListener(this);

        expenseStatisticsView = root.findViewById(R.id.expenseStatisticsView);
        revenueStatisticsView = root.findViewById(R.id.revenueStatisticsView);

        expensePieChart = root.findViewById(R.id.expensePieChart);

        revenueBarChart = root.findViewById(R.id.revenueBarChart);
        revenuePieChart = root.findViewById(R.id.revenuePieChart);

        getData();
        return root;
    }

    private void getData() {
        ExpenseCharts.expenseCategoryPieChart(transactionList, expenseAmount, expensePieChart, getActivity());
        RevenueCharts.revenueCategoryPieChart(transactionList, revenueAmount, revenuePieChart, getActivity());
        RevenueCharts.revenueBarChart(revenueBarChart);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expenseViewBtn:
                revenueStatisticsView.setVisibility(View.GONE);
                expenseStatisticsView.setVisibility(View.VISIBLE);
                break;
            case R.id.revenueViewBtn:
                expenseStatisticsView.setVisibility(View.GONE);
                revenueStatisticsView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

}