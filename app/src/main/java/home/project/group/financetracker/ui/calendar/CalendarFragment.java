package home.project.group.financetracker.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.Adapter.MonthlyAdapter;
import home.project.group.financetracker.Adapter.TransactionAdapter;
import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class CalendarFragment extends Fragment implements View.OnClickListener {

    RecyclerView dailyRecyclerView, monthlyRecyclerView;

    private List<TransactionModel> transactionList;
    private List<TransactionModel> monthlyTransaction;

    private List<Double> jan;
    private List<Double> feb;
    private List<Double> mar;
    private List<Double> apr;
    private List<Double> may;
    private List<Double> jun;
    private List<Double> jul;
    private List<Double> aug;
    private List<Double> sep;
    private List<Double> oct;
    private List<Double> nov;
    private List<Double> dec;

    private List<List<Double>> monthly;

    private MonthlyAdapter monthlyAdapter;

    private TransactionAdapter transactionAdapter;

    Button dailyViewBtn, monthlyViewBtn;
    LinearLayout dailyView, monthlyView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);

        dailyRecyclerView = root.findViewById(R.id.dailyRecyclerview);
        monthlyRecyclerView = root.findViewById(R.id.monthlyRecyclerView);

        dailyViewBtn = root.findViewById(R.id.dailyViewBtn);
        monthlyViewBtn = root.findViewById(R.id.monthlyViewBtn);

        dailyViewBtn.setOnClickListener(this);
        monthlyViewBtn.setOnClickListener(this);

        dailyView = root.findViewById(R.id.dailyView);
        monthlyView = root.findViewById(R.id.monthlyView);

        getData();
        return root;
    }

    /**
     * Get data for the recycler view
     */
    private void getData() {
        /**
         * Initialize the list and store all expense/revenue data from room
         */

        transactionList = new ArrayList<>();
        transactionList = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllTransactionData();

        monthlyTransaction = new ArrayList<>();
        monthlyTransaction = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().monthlyTransaction();

        jan = new ArrayList<>();
        feb = new ArrayList<>();
        mar = new ArrayList<>();
        apr = new ArrayList<>();
        may = new ArrayList<>();
        jun = new ArrayList<>();
        jul = new ArrayList<>();
        aug = new ArrayList<>();
        sep = new ArrayList<>();
        oct = new ArrayList<>();
        nov = new ArrayList<>();
        dec = new ArrayList<>();

        monthly = new ArrayList<>();

        for (int i = 0; i < monthlyTransaction.size(); i++) {
            if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 1) {
                jan.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 2) {
                feb.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 3) {
                mar.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 4) {
                apr.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 5) {
                may.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 6) {
                jun.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 7) {
                jul.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 8) {
                aug.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 9) {
                sep.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 10) {
                oct.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 11) {
                nov.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("E") && monthlyTransaction.get(i).getDate().getMonth() == 0) {
                dec.add(monthlyTransaction.get(i).getAmount());
            }
        }

        addExpenses(jan, monthly);
        addExpenses(feb, monthly);
        addExpenses(mar, monthly);
        addExpenses(apr, monthly);
        addExpenses(may, monthly);
        addExpenses(jun, monthly);
        addExpenses(jul, monthly);
        addExpenses(aug, monthly);
        addExpenses(sep, monthly);
        addExpenses(oct, monthly);
        addExpenses(nov, monthly);
        addExpenses(dec, monthly);

        for (int i = 0; i < monthlyTransaction.size(); i++) {
            if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 1) {
                jan.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 2) {
                feb.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 3) {
                mar.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 4) {
                apr.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 5) {
                may.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 6) {
                jun.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 7) {
                jul.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 8) {
                aug.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 9) {
                sep.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 10) {
                oct.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 11) {
                nov.add(monthlyTransaction.get(i).getAmount());
            } else if (monthlyTransaction.get(i).getType().equals("R") && monthlyTransaction.get(i).getDate().getMonth() == 0) {
                dec.add(monthlyTransaction.get(i).getAmount());
            }
        }

        addRevenues(jan, monthly);
        addRevenues(feb, monthly);
        addRevenues(mar, monthly);
        addRevenues(apr, monthly);
        addRevenues(may, monthly);
        addRevenues(jun, monthly);
        addRevenues(jul, monthly);
        addRevenues(aug, monthly);
        addRevenues(sep, monthly);
        addRevenues(oct, monthly);
        addRevenues(nov, monthly);
        addRevenues(dec, monthly);

        for (int i = 0; i < monthly.size(); i++) {
            Log.d("Expense/Revenue", monthly.get(i).toString());
        }

        transactionAdapter = new TransactionAdapter(getActivity().getApplicationContext(), transactionList, new TransactionAdapter.DeleteItemClickListener() {
            /**
             * Delete individual card if user click delete button
             *
             * @param position
             * @param id
             */
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().deleteTransactionData(id);
                getData();
            }
        });

        dailyRecyclerView.setAdapter(transactionAdapter);

        monthlyAdapter = new MonthlyAdapter(getActivity().getApplicationContext(), monthly);
        monthlyRecyclerView.setAdapter(monthlyAdapter);
    }

    /**
     * Get a total of all expenses in a specific month
     *
     * @param month
     */
    private void addExpenses(List<Double> month, List<List<Double>> monthly) {
        double sum = 0;
        for (int i = 0; i < month.size(); i++) {
            sum += month.get(i);
        }
        month.clear();
        month.add(sum);
        monthly.add(month);
    }

    /**
     * Get a total of all specific month's data and subtract expenses to get revenue
     *
     * @param month
     */
    private void addRevenues(List<Double> month, List<List<Double>> monthly) {
        double expenses = month.get(0);
        double sum = 0;
        for (int i = 0; i < month.size(); i++) {
            sum += month.get(i);
        }
        month.clear();
        double revenue = sum - expenses;
        month.add(expenses);
        month.add(revenue);
        monthly.remove(month);
        monthly.add(month);
    }


    /**
     * Set search menu to calendar fragment or page
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        /**
         * Filter out the recycler view list in real-time
         */
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                transactionAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dailyViewBtn:
                monthlyView.setVisibility(View.GONE);
                dailyView.setVisibility(View.VISIBLE);
                break;
            case R.id.monthlyViewBtn:
                dailyView.setVisibility(View.GONE);
                monthlyView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

}