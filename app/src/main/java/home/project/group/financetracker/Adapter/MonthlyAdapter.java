package home.project.group.financetracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Month;
import java.util.List;

import home.project.group.financetracker.R;

import static android.view.View.GONE;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyAdapter.ViewHolder> {

    Context context;
    List<List<Double>> monthly;

    public MonthlyAdapter(Context context, List<List<Double>> monthly) {
        this.context = context;
        this.monthly = monthly;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.monthly_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (monthly.get(position).get(0) == 0 && monthly.get(position).get(1) == 0) {
            holder.itemView.setVisibility(GONE);
            holder.month.setVisibility(GONE);
            holder.expenseTotal.setVisibility(GONE);
            holder.revenueTotal.setVisibility(GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.month.setText(Month.of(position + 1).toString());
            holder.expenseTotal.setText(String.valueOf(monthly.get(position).get(0)));
            holder.revenueTotal.setText(String.valueOf(monthly.get(position).get(1)));
        }

    }

    @Override
    public int getItemCount() {
        return monthly.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView month, expenseTotal, revenueTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.txtMonth);
            expenseTotal = itemView.findViewById(R.id.txtExpenseTotal);
            revenueTotal = itemView.findViewById(R.id.txtRevenueTotal);
        }
    }

}
