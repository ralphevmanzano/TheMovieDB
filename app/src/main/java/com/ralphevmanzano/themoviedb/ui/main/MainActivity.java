package com.ralphevmanzano.themoviedb.ui.main;

import android.os.Bundle;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.ui.BaseActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import static androidx.navigation.ui.NavigationUI.navigateUp;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert host != null;
        navController = host.getNavController();
        setupActionBar(navController);


    }

    private void setupActionBar(NavController navController) {
        setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navigateUp(navController, (DrawerLayout) null);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }
}
