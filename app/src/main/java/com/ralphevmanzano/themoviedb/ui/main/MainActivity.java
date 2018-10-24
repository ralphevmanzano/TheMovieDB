package com.ralphevmanzano.themoviedb.ui.main;

import android.os.Bundle;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.ui.BaseActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragment(MovieListFragment.class, false, null, null);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    public void showFragment(Class<?> fragmentClass, boolean addToBackStack, @Nullable String tag, @Nullable Bundle bundle) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = Fragment.instantiate(this, fragmentClass.getName(), bundle);
        transaction.replace(R.id.container, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void popBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            fm.popBackStack();
        }
    }
}
