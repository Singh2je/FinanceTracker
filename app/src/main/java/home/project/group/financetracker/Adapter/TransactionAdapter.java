package home.project.group.financetracker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> implements Filterable {

    Context context;
    List<TransactionModel> list;
    List<TransactionModel> allData;
    DeleteItemClickListener deleteItemClickListener;

    public TransactionAdapter(Context context, List<TransactionModel> list, DeleteItemClickListener deleteItemClickListener) {
        this.context = context;
        this.list = list;
        allData = new ArrayList<>(list);
        this.deleteItemClickListener = deleteItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText(list.get(position).getName());
        holder.amount.setText("$" + list.get(position).getAmount());
        if (list.get(position).getType().equals("R")) {
            holder.amount.setTextColor(Color.GREEN);
        } else if (list.get(position).getType().equals("E")) {
            holder.amount.setTextColor(Color.RED);
        } else {
            holder.amount.setTextColor(Color.BLACK);
        }

        holder.category.setText(list.get(position).getCategory());

        holder.deleteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemClickListener.onItemDelete(position, list.get(position).getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<TransactionModel> filteredList = new ArrayList<>();
            /**
             * If user doesn't provide any text then sends back original list with existing data
             */
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(allData);
            }
            /**
             * If user provides some text then use that text to compare it against transaction name
             * in the original list
             */
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (TransactionModel item : allData) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            /**
             * Clear the old list and display filtered list
             */
            list.clear();
            list.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface DeleteItemClickListener {
        void onItemDelete(int position, int id);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, amount, category;
        Button deleteId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            amount = itemView.findViewById(R.id.txtAmount);
            category = itemView.findViewById(R.id.txtCategory);
            deleteId = itemView.findViewById(R.id.deleteId);
        }
    }

}
