package com.ralphevmanzano.themoviedb.ui.details;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.databinding.FragmentSuggestionsBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.viewmodels.SuggestionsViewModel;

public class SuggestionsFragment extends BaseFragment<SuggestionsViewModel, FragmentSuggestionsBinding> {
    @Override
    public Class<SuggestionsViewModel> getViewModel() {
        return SuggestionsViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_suggestions;
    }
}
