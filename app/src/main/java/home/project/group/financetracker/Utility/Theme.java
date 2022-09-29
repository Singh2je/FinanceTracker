package home.project.group.financetracker.Utility;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import home.project.group.financetracker.R;

public class Theme {
    public static LayoutInflater themeDecider(LayoutInflater inflater, Fragment fragment) {
        final Context contextThemeWrapperDark = new ContextThemeWrapper(fragment.getActivity(), R.style.DarkTheme);
        final Context contextThemeWrapperApp = new ContextThemeWrapper(fragment.getActivity(), R.style.AppTheme);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            inflater = inflater.cloneInContext(contextThemeWrapperDark);
        } else {
            inflater = inflater.cloneInContext(contextThemeWrapperApp);
        }
        return inflater;
    }

}
