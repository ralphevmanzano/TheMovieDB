package com.ralphevmanzano.themoviedb.ui.main;

import android.os.Bundle;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.ui.BaseActivity;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import static androidx.navigation.ui.NavigationUI.navigateUp;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragment(MovieListFragment.class, false, null, null);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    public void showFragment(Class<?> fragmentClass, boolean addToBackStack, @Nullable String tag, @Nullable Bundle bundle) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = Fragment.instantiate(this, fragmentClass.getName(), bundle);
            transaction.replace(R.id.container, fragment, tag);
            if (addToBackStack) transaction.addToBackStack(null);
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
