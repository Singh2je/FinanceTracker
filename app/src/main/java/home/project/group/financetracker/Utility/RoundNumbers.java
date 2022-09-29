package home.project.group.financetracker.Utility;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import home.project.group.financetracker.R;

public class RoundNumbers {
    /**
     * Round decimals to specific nth place
     *
     * @param value     84.124
     * @param precision 1
     * @return 84.1
     */
    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
