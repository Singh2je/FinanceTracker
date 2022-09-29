package home.project.group.financetracker.ui.transaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.TransactionModel;
import home.project.group.financetracker.R;
import home.project.group.financetracker.Utility.Theme;

public class TransactionFragment extends Fragment implements View.OnClickListener {

    EditText name, amount, note;
    Spinner category;
    Date sqlDate;
    TextView date;
    RadioButton radioBtnRevenue, radioBtnExpense;
    Button save, addCategory;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ArrayAdapter<String> dataAdapter;
    private List<String> categories = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private final String EXPENSE = "expense";
    private final String REVENUE = "revenue";
    private String category_txt;
    //By Default, current type is revenue
    private String currentCategoryType = REVENUE;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Fragment fragment = this;
        View root = Theme.themeDecider(inflater, fragment).inflate(R.layout.fragment_transaction, container, false);

        radioBtnRevenue = (RadioButton) root.findViewById(R.id.radioBtnRevenue);
        radioBtnExpense = (RadioButton) root.findViewById(R.id.radioBtnExpense);
        name = root.findViewById(R.id.txtName);
        category = (Spinner) root.findViewById(R.id.txtCategory);
        note = root.findViewById(R.id.txtNote);
        amount = root.findViewById(R.id.txtAmount);
        save = (Button) root.findViewById(R.id.btnSave);
        date = root.findViewById(R.id.txtDate);
        addCategory = (Button) root.findViewById(R.id.btnAddCategory);

        //Dummy data for categories table
        CategoriesModel categoriesModel1 = new CategoriesModel();
        categoriesModel1.setName("work");
        categoriesModel1.setType(REVENUE);
        CategoriesModel categoriesModel2 = new CategoriesModel();
        categoriesModel2.setName("grocery");
        categoriesModel2.setType("expense");
        CategoriesModel categoriesModel3 = new CategoriesModel();
        categoriesModel3.setName("gas");
        categoriesModel3.setType(EXPENSE);
        boolean isDuplicate1 = isDuplicateCategory(categoriesModel1);
        boolean isDuplicate2 = isDuplicateCategory(categoriesModel2);
        boolean isDuplicate3 = isDuplicateCategory(categoriesModel3);
        if (!isDuplicate1) {
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllCategoriesData(categoriesModel1);
        }
        if (!isDuplicate2) {
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllCategoriesData(categoriesModel2);
        }
        if (!isDuplicate3) {
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllCategoriesData(categoriesModel3);
        }

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        category.setAdapter(dataAdapter);

        save.setOnClickListener(this);
        date.setOnClickListener(this);
        radioBtnExpense.setOnClickListener(this);
        radioBtnRevenue.setOnClickListener(this);
        addCategory.setOnClickListener(this);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String strDate = month + "-" + day + "-" + year;
                date.setText(strDate);
                sqlDate = new Date(year, month, day);
            }
        };
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = dataAdapter.getItem(position);
                if (item != null) {
                    category_txt = item.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                saveData();
                break;
            case R.id.txtDate:
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case R.id.radioBtnExpense:
                fillAdapter(EXPENSE);
                currentCategoryType = EXPENSE;
                break;
            case R.id.radioBtnRevenue:
                fillAdapter(REVENUE);
                currentCategoryType = REVENUE;
                break;
            case R.id.btnAddCategory:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add " + (currentCategoryType == EXPENSE ? "an Expense" : "a Revenue") + " Category");
                types.add(EXPENSE);
                types.add(REVENUE);
                final EditText nameInput = new EditText(this.getContext());
                nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                nameInput.setHint("Name");
                builder.setView(nameInput);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CategoriesModel newCategory = new CategoriesModel();
                        newCategory.setName(nameInput.getText().toString().toLowerCase());
                        newCategory.setType(currentCategoryType);
                        boolean duplicate = isDuplicateCategory(newCategory);
                        if (!duplicate) {
                            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllCategoriesData(newCategory);
                        }
                        fillAdapter(currentCategoryType);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                break;
        }
    }

    private void fillAdapter(String type) {
        List<CategoriesModel> typeCategories = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().getAllTypeCategories(type);
        categories = new ArrayList<>();
        for (CategoriesModel typeCategory : typeCategories) {
            categories.add(typeCategory.getName());
        }
        dataAdapter.clear();
        if (categories != null) {
            for (String object : categories) {
                dataAdapter.insert(object, dataAdapter.getCount());
            }
        }
        dataAdapter.notifyDataSetChanged();
    }

    private void saveData() {
        String name_txt = name.getText().toString().trim();
        double amount_txt = Double.parseDouble(amount.getText().toString().trim());
        String note_txt = note.getText().toString().trim();

        if (radioBtnRevenue.isChecked()) {
            TransactionModel model = new TransactionModel();
            model.setName(name_txt);
            model.setAmount(amount_txt);
            model.setCategory(category_txt);
            model.setDate(sqlDate);
            model.setNote(note_txt);
            model.setType("R");
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllTransactionData(model);
        }

        if (radioBtnExpense.isChecked()) {
            TransactionModel model = new TransactionModel();
            model.setName(name_txt);
            model.setAmount(amount_txt);
            model.setCategory(category_txt);
            model.setDate(sqlDate);
            model.setNote(note_txt);
            model.setType("E");
            DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().insertAllTransactionData(model);
        }

        Toast.makeText(getActivity(), (radioBtnRevenue.isChecked() ? "Revenue" : "Expense") + " data successfully saved", Toast.LENGTH_SHORT).show();
    }

    public boolean isDuplicateCategory(CategoriesModel category) {
        CategoriesModel duplicate = DatabaseClass.getDatabase(getActivity().getApplicationContext()).getDao().checkForDuplicateCategory(category.getName().toLowerCase());
        if (duplicate == null) {
            return false;
        }
        return true;
    }
}