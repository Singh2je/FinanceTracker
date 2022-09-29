package home.project.group.financetracker.ui.calendar;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import home.project.group.financetracker.Dao.TransactionDao;
import home.project.group.financetracker.EntityClass.CategoriesModel;
import home.project.group.financetracker.EntityClass.TransactionModel;

@Database(entities = {CategoriesModel.class, TransactionModel.class}, version = 9)
public abstract class DatabaseClass extends RoomDatabase {

    private static DatabaseClass instance;

    static DatabaseClass getDatabase(final Context context) {
        if (instance == null) {
            synchronized (DatabaseClass.class) {
                instance = Room.databaseBuilder(context, DatabaseClass.class, "DATABASE").fallbackToDestructiveMigration().allowMainThreadQueries().build();
            }
        }
        return instance;
    }

    public abstract TransactionDao getDao();
}
